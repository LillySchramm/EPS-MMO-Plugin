import React, { version } from 'react';
import ReactDOM from 'react-dom';
import Coord_Span from "../general/Coord_Span";
import Skin_Obj from "../general/Skin_Obj"
import itemTypes from '../../tools/itemTypeDir';
import ItemTypeSelect from '../general/Item_Type_Select';

const api = require("../../tools/api/api")
const formater = require('../../tools/formater');
const coockie = require('../../tools/coockies');

class DEL_Button extends React.Component {
    constructor(props){
        super(props)        
        this.delete = this.delete.bind(this);
    }

    delete(){
        api.deleteItem(this.props.item_id).then(() => {
            this.props.reloadTable()
        })
    }

    render() {
        return (
            <button type="button" class="btn btn-error btn-lg" onClick={this.delete}>DELETE ITEM</button> 
        );
    }
}

class RP_VER extends React.Component {
    constructor(probs){
        super(probs)

        this.state = {
            last_edit:"",
            version:0
        }
        this.reGen = this.reGen.bind(this)

        this.fetchVersion()
    }

    fetchVersion(){
        api.getResourcePackVersion().then((r) => {
            this.setState({
                last_edit:r.last_changed,
                version:r.ver
            })
        })
    }

    reGen(){
        api.regenResourcePack().then(() => {
            this.fetchVersion();
        })
    }

    render() {
        //I know that I am using some Bootstrap stuff down there. Bootstrap will be the base of the website design once finished.
        return(
        <span>
            <h3>The resource pack was regenerated last at: <br/> {this.state.last_edit} | VER[{this.state.version}]</h3><br/>
            <button type="button" class="btn btn-primary btn-lg" onClick={this.reGen}>Regenerate</button> 
        </span>)
    }
}

class Item_row extends React.Component {

    constructor(probs) {
        super(probs);
    }

    render() {
        return (            
            <tr>
                <td>
                    <a href={'http://localhost:3000/admin/item/' + this.props.id}>
                        {this.props.id} 
                    </a>
                </td>

                <td>
                    <a href={'http://localhost:3000/admin/item/' + this.props.id}>
                        {this.props.name}
                    </a>
                </td>

                <td>                    
                    <p>
                        {this.props.type}
                    </p>
                </td>

                <td>                    
                    <img src={'data:image/jpeg;base64,' + this.props.icon} width='72px' height='72px' />
                </td>

                <td>
                    <DEL_Button item_id={this.props.id} reloadTable={this.props.reloadTable}/>
                </td>
            </tr>
            
        );
    }
}

class Item_table extends React.Component {

    constructor(probs) {
        super(probs);

        this.state = 
        {
            items: <span></span>,
            type:"general",
            name:""
        }
        this.handleTypeChange = this.handleTypeChange.bind(this)
        this.e_createTable = this.e_createTable.bind(this)
        this.onChange = this.onChange.bind(this)
        this.onSubmit = this.onSubmit.bind(this)

        this.createTable()
    }
    
    onChange(e){
        this.setState({
            name: e.target.value
        })
    }
    
    componentDidMount(){}    

    e_createTable(){
        this.createTable();
    }

    async createTable() {
        const session = coockie.readCookie('login');
        let url = "http://localhost:10100/admin/" + session;
        let response = await fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } });
        let data = await response.json();       


        if (data.verified) {
            url = "http://localhost:10100/admin/" + session + "/item/getall/" + this.state.type;
            response = await fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } });
            data = await response.json();  

            let table = [];
            data.items.forEach(element => {
                table.push(<Item_row id={element.ID} name={element.NAME} type={itemTypes[element.DATA.split(">>")[0]]} icon={element.ICON} reloadTable={this.e_createTable}/>);
            });         
            this.setState({items: table});
                
        } else {
            this.forceUpdate()

            coockie.deleteCoockie("login");
            this.setState({items: <span></span>});
        }

        this.forceUpdate()
    }

    handleTypeChange(e){
        this.setState({
            type:e.target.value
        })
        this.createTable();
    }

    onSubmit(){
        api.newItem(this.state.name, this.state.type)
        this.createTable();
    }

    render() {
        return (
            <span>
                <br/>
                <br/>
                <label>Type: </label>
                <ItemTypeSelect cur={this.state.type} handler={this.handleTypeChange}/>
                <br/>
                <table class='NPC_Table'>                
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Type</th>
                        <th> </th>
                        <th> </th>
                    </tr>
                    {this.state.items}
                </table>  

                <h2>Add item:</h2>
                <form>
                    <input type='text' value={this.state.name} placeholder="Enter name here" onChange={this.onChange}/>
                    <input type="button" value="Submit" onClick={this.onSubmit}/>
                </form>              
            </span>
        );
    }
} 

class Site_Items extends React.Component {
    constructor(probs) {
        super(probs);
        this.state = {
            name:""
        }
    }

    render() {
        return (
            <div class='Site_Items'>
                <br/>
                <RP_VER/>               

                <Item_table />

            </div>
        );
    }

}

export default Site_Items