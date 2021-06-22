import React from 'react';
import ReactDOM from 'react-dom';
import itemTypes from '../../tools/itemTypeDir';
import itemTypeDefaults from "../../tools/itemTypeDefaults";
import axios from 'axios';
import ItemTypeSelect from '../general/Item_Type_Select';

const coockie = require('../../tools/coockies');
const api = require('../../tools/api/api');
const formater = require('../../tools/formater');
const rgbHex = require('rgb-hex');

class Item_edit extends React.Component {

    constructor(probs) {
        super(probs);        

        let url = window.location.href
        let unslashed = url.split('/');
        
        this.state = {item_id: unslashed[unslashed.length - 1], name:"" , icon:"", data:""}      
        
        this.onSubmit = this.onSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this)
    }

    async onSubmit(){
        api.editItem(this.state)
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
                break
            case "type_option":
                this.setState({
                    data:ele.value
                })
                break
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
        let url = "http://localhost:10100/admin/" + session + "/item/get/" + this.state.item_id;
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
                    <ItemTypeSelect cur={this.state.data.split(">>")[0]} handler={this.handleChange}/>                
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