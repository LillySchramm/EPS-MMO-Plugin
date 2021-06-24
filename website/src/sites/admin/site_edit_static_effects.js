import React from 'react';
import ReactDOM from 'react-dom';
import particleTypes from '../../tools/particleNameDir';
import effectTypes from '../../tools/effectTypeDir';
import particleTypeDefaults from "../../tools/particleEffectDefaults";

const coockie = require('../../tools/coockies');
const api = require('../../tools/api/api');
const formater = require('../../tools/formater');
const rgbHex = require('rgb-hex');

class Effect_type_select extends React.Component{
    render(){

        let options = [];
        let id = 0;

        if(this.props.type == "display"){            
            Object.entries(particleTypes).forEach(([key, value]) => {
                if(key == this.props.cur){
                    options.push(<option value={key} selected="selected">{value}</option>);
                }else{
                    options.push(<option value={key}>{value}</option>);
                }                
            });
        }else if(this.props.type == "effects"){
            id = 1;
            Object.entries(effectTypes).forEach(([key, value]) => {
                if(value != "null"){
                    if(key == this.props.cur){
                        options.push(<option value={key} selected="selected">{value}</option>);
                    }else{
                        options.push(<option value={key}>{value}</option>);
                    }
                }         
            });
        }

        return(
            <select name="type_option" id="type_option" e_id={id} onChange={this.props.handler}>
                {                    
                    options                
                }
            </select>
        );
    }
}

class Effect_type_form extends React.Component{
    render(){
        let color_selector = <span></span>;        

        let r = this.props.data[this.props.data.length - 3];
        let g = this.props.data[this.props.data.length - 2];
        let b = this.props.data[this.props.data.length - 1];

        r = parseInt(r);
        g = parseInt(g);
        b = parseInt(b);

        if(this.props.data[1] == "REDSTONE") color_selector = <input e_id={-1} type="color" value={"#" + rgbHex(r,g,b)} onChange={this.props.handler}/>    

        switch(this.props.data[0]){
            case "single":
                return(
                    <div>                        
                        <label>Effect: </label>
                        <Effect_type_select type="effects" cur={this.props.data[1]} handler={this.props.handler} />   
                        <br/>
                        
                        {color_selector}
                    </div>                    
                );
                break;
           
            case "circle":
                return(
                    <div>
                        <label>Effect: </label>
                        <Effect_type_select type="effects" cur={this.props.data[1]} handler={this.props.handler} />   
                        <br/>
                        {color_selector}
                        <br/>
                        <h3>Properties</h3>
                        <br/>
                        <label>Radius: </label>
                        <input e_id={2} type="number" value={this.props.data[2]} onChange={this.props.handler}/> 
                        <br/>
                        <label>Ring Points: </label>
                        <input e_id={3} type="number" value={this.props.data[3]} onChange={this.props.handler}/> 
                        <br/>
                    </div>                        
                );
                break;
                case "pillar":
                    return(
                        <div>
                            <label>Effect: </label>
                            <Effect_type_select type="effects" cur={this.props.data[1]} handler={this.props.handler} />   
                            <br/>
                            {color_selector}
                            <br/>
                            <h3>Properties</h3>
                            <br/>
                            <label>Radius: </label>
                            <input e_id={2} type="number" value={this.props.data[2]} onChange={this.props.handler}/> 
                            <br/>
                            <label>Ring Points: </label>
                            <input e_id={3} type="number" value={this.props.data[3]} onChange={this.props.handler}/> 
                            <br/>
                            <label>Height: </label>
                            <input e_id={4} type="number" value={this.props.data[4]} onChange={this.props.handler}/> 
                            <br/>
                            <label>Rings: </label>
                            <input e_id={5} type="number" value={this.props.data[5]} onChange={this.props.handler}/> 
                            <br/>
                        </div>                        
                    );
                    break;
        }
        return(
            <h3>ERROR: Type Not Found</h3>
        );
    }
}


class Effect_edit extends React.Component {

    constructor(probs) {
        super(probs);        

        let url = window.location.href
        let unslashed = url.split('/');
        
        this.state = {effect_id: unslashed[unslashed.length - 1], data: undefined}      
        
        this.onSubmit = this.onSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this)
    }

    async onSubmit(){
        let value = "";

        this.state.data.forEach((k) => {
            value += k + ">>";
        });

        console.log(value);
        api.editEffect(this.state.effect_id, "DATA", value);
    }

    handleChange(e){
        let ele = e.target;
        let id = ele.getAttribute("e_id");
        let _data = this.state.data;
        
        if(id != 0){
            _data[id] = e.target.value;
        }else{
            _data = particleTypeDefaults[e.target.value];            
        }

        if(id == -1){
            let hex = formater.hexToRgb(e.target.value);
            
            _data[_data.length - 3] = hex.r;
            _data[_data.length - 2] = hex.g;
            _data[_data.length - 1] = hex.b;

            return;
        }

        this.setState(
            {
                data: _data
            }
        )
    }

    componentDidMount(){    
        this.getEffect_Data()
    }    

    async getEffect_Data(){
        const session = coockie.readCookie('login');
        let url = "http://mine:10100/admin/" + session + "/staticeffects/get/" + this.state.effect_id;
        let response = await fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } });
        let data = await response.json();  

        if (data.verified) {
           let _data = data.effect.DATA.split(">>");
           this.setState({
               data: _data
           })
        } else {
            this.forceUpdate()
            coockie.deleteCoockie("login");  
        }

        this.forceUpdate()
    }

    render() {  
        
        if(this.state.data == undefined){return <h3>Loading.........</h3>}

        return (
            <div class="Site_Edit_Effects"> 
                <h2>Currently selected: {particleTypes[this.state.data[0]]} [ID: {this.state.effect_id}; EFFECT: {effectTypes[this.state.data[1]]}]</h2>                
                <form>
                    <br/>
                    <h3>General</h3>
                    <br/>
                    <label>Display: </label>
                    <Effect_type_select type="display" cur={this.state.data[0]} handler={this.handleChange}/>                
                    <br/>
                    <br/>
                    <Effect_type_form data={this.state.data} handler={this.handleChange} />
                    <br/>
                    <br/>
                    <input type="button" value="Submit" onClick={this.onSubmit}/>
                    <br/>
                </form>
            </div>
        );        
    }
}

export default Effect_edit