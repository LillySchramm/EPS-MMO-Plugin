import React, { version } from 'react';
import ReactDOM from 'react-dom';
import Coord_Span from "../general/Coord_Span";
import Skin_Obj from "../general/Skin_Obj"
import itemTypes from '../../tools/itemTypeDir';

const api = require("../../tools/api/api")
const formater = require('../../tools/formater');
const coockie = require('../../tools/coockies');

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
                    <a href={'http://0.0.0.0:3000/admin/item/' + this.props.id}>
                        {this.props.id} 
                    </a>
                </td>

                <td>
                    <a href={'http://0.0.0.0:3000/admin/item/' + this.props.id}>
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
            </tr>
            
        );
    }
}

class Item_table extends React.Component {

    constructor(probs) {
        super(probs);

        this.state = {items: <span></span>}
        this.createTable()
    }

    componentDidMount(){
        this.ticker = setInterval(() => {this.createTable()}, 5000);        
    }    

    async createTable() {
        const session = coockie.readCookie('login');
        let url = "http://0.0.0.0:10100/admin/" + session;
        let response = await fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } });
        let data = await response.json();       


        if (data.verified) {
            url = "http://0.0.0.0:10100/admin/" + session + "/item/getall";
            response = await fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } });
            data = await response.json();  

            let table = [];
            data.items.forEach(element => {
                table.push(<Item_row id={element.ID} name={element.NAME} type={itemTypes[element.DATA.split(">>")[0]]} icon={element.ICON}/>);
            });         
            this.setState({items: table});
                
        } else {
            this.forceUpdate()

            coockie.deleteCoockie("login");
            this.setState({items: <span></span>});
        }

        this.forceUpdate()
    }

    render() {
        return (
            <table class='NPC_Table'>                
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Type</th>
                    <th> </th>
                </tr>
                {this.state.items}
            </table>
        );
    }
}
 

class Site_Items extends React.Component {
    constructor(probs) {
        super(probs);

        this.state = {
            name:""
        }

        this.onSubmit = this.onSubmit.bind(this)
        this.onChange = this.onChange.bind(this)
    }

    onSubmit(){
        api.newItem(this.state.name)
    }

    onChange(e){
        this.setState({
            name: e.target.value
        })
    }

    render() {
        return (
            <div class='Site_Items'>
                <br/>
                <RP_VER/>                

                <Item_table />
                <h2>Add item:</h2>
                <form>
                    <input type='text' value={this.state.name} placeholder="Enter name here" onChange={this.onChange}/>
                    <input type="button" value="Submit" onClick={this.onSubmit}/>
                </form>
            </div>
        );
    }

}

export default Site_Items