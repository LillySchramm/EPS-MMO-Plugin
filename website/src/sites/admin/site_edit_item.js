import React from 'react';
import ReactDOM from 'react-dom';
import itemTypes from '../../tools/itemTypeDir';
import itemTypeDefaults from "../../tools/itemTypeDefaults";
import axios from 'axios';

const coockie = require('../../tools/coockies');
const api = require('../../tools/api/api');
const formater = require('../../tools/formater');
const rgbHex = require('rgb-hex');

class Item_type_select extends React.Component{
    render(){

        let options = [];
        let id = 0;
        
        Object.entries(itemTypes).forEach(([key, value]) => {
            if(key == this.props.cur){
                options.push(<option value={key} selected="selected">{value}</option>);
            }else{
                options.push(<option value={key}>{value}</option>);
            }                
        });        

        return(
            <select name="type_option" id="type_option" e_id={id} onChange={this.props.handler}>
                {                    
                    options                
                }
            </select>
        );
    }
}

class Item_edit extends React.Component {

    constructor(probs) {
        super(probs);        

        let url = window.location.href
        let unslashed = url.split('/');
        
        this.state = {item_id: unslashed[unslashed.length - 1], name:"" , icon:""}      
        
        this.onSubmit = this.onSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this)
    }

    async onSubmit(){        
        //console.log(value);
        api.setItemIcon(this.state.item_id, this.state.icon)
        api.editItem(this.state.item_id, "NAME", this.state.name)
        //api.editEffect(this.state.effect_id, "DATA", value);
    }

    handleChange(e){      
        let ele = e.target;
        let id = ele.getAttribute("id");

        switch(id){
            case "icon":
                this.getBase64(ele.files[0]);
                break
            case "name":
                this.setState({
                    name: ele.value
                })
            default:
                break
        }

    }

    componentDidMount(){    
        this.getEffect_Data()
    }    

    getBase64(file) { 
        const reader = new FileReader();
        reader.onloadend = () => {
          // use a regex to remove data url part
          const base64String = reader.result
            .replace("data:", "")
            .replace(/^.+,/, "");   
            this.setState({
                icon:base64String
            })
        };
        reader.readAsDataURL(file);   
    }

    async getEffect_Data(){
        const session = coockie.readCookie('login');
        let url = "http://0.0.0.0:10100/admin/" + session + "/item/get/" + this.state.item_id;
        let response = await fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } });
        let data = await response.json();  

        if (data.verified) {
           let _data = data.item;
           this.setState({
               name:_data.NAME,
               icon: _data.ICON,
               data: _data.DATA
           })
        } else {
            this.forceUpdate()
            coockie.deleteCoockie("login");  
        }

        this.forceUpdate()
    }

    render() {  
        
        if(this.state.icon == ""){return <h3>Loading.........</h3>}

        return (
            <div class="Site_Edit_Item"> 
                <h2>Currently selected:  [ID: {this.state.item_id}; NAME: {this.state.name}]</h2>                
                <form>
                <br/>
                    <h3>General</h3>
                    <br/>
                    <label>Type: </label>
                    <Item_type_select cur={this.state.data.split(">>")[0]} handler={this.handleChange}/>                
                    <br/>
                    <br/>
                    <input type='text' id='name' value={this.state.name}  onChange={this.handleChange}/> 
                    <br />
                    <br />
                    <img src={'data:image/jpeg;base64,' + this.state.icon} width='172px' height='172px' />
                    <br />
                    <input type="file"
                        id="icon" name="icon"
                        accept="image/png" onChange={this.handleChange}></input>
                    <br />
                    <input type="button" value="Submit" onClick={this.onSubmit}/>
                    <br/>
                </form>
            </div>
        );        
    }
}

export default Item_edit