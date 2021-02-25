import React from 'react';
import ReactDOM from 'react-dom';
import particleTypes from '../../tools/particleNameDir';
import Skin_Obj from '../general/Skin_Obj'

const coockie = require('../../tools/coockies');
const api = require('../../tools/api/api');

class Effect_type_select extends React.Component{
    render(){

        let options = [];

        Object.entries(particleTypes).forEach(([key, value]) => {
            if(key == this.props.cur){
                options.push(<option value={key} selected="selected">{value}</option>);
            }else{
                options.push(<option value={key}>{value}</option>);
            }
            
        });         

        return(
            <select name="type_option" id="type_option" e_id={0} onChange={this.props.handler}>
                {                    
                    options                
                }
            </select>
        );
    }
}

class Effect_type_form extends React.Component{
    render(){
        switch(this.props.data[0]){
            case "single":
                return(
                    <input e_id={0} type='text' value={"this.state.data[0]"} onChange={this.handleChange}/>
                );
                break;
           
            case "circle":
                return(
                    <h3>2</h3>
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
        
        this.state = {effect_id: unslashed[unslashed.length - 1], data: ["s"]}      
        
        this.onSubmit = this.onSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this)
    }

    async onSubmit(){
    }

    handleChange(e){
        let ele = e.target;
        let id = ele.getAttribute("e_id");
        let _data = this.state.data;

        _data[id] = e.target.value;

        this.setState(
            {
                data: _data
            }
        )
    }

    componentDidMount(){    
        this.getEffect_Data()
    }    

    async getEffect_Data(id){
        const session = coockie.readCookie('login');
        let url = "http://0.0.0.0:10100/admin/" + session + "/staticeffects/get/" + this.state.effect_id;
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
        return (
            <div class="Site_Edit_Effects"> 
                <h2>Currently selected: {particleTypes[this.state.data[0]]} [ID:{this.state.effect_id}]</h2>                
                <form>
                    <Effect_type_select cur={this.state.data[0]} handler={this.handleChange}/>                
                    <br/>
                    <Effect_type_form data={this.state.data} handler={this.handleChange} />
                    <br/>
                    <input type="button" value="Submit" onClick={this.onSubmit}/>
                </form>
            </div>
        );        
    }
}

export default Effect_edit