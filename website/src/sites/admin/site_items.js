import React from 'react';
import ReactDOM from 'react-dom';
import Coord_Span from "../general/Coord_Span";
import Skin_Obj from "../general/Skin_Obj"

const formater = require('../../tools/formater');
const coockie = require('../../tools/coockies');

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
    }

    componentDidMount(){
        this.ticker = setInterval(() => {this.createTable()}, 500);        
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
                table.push(<Item_row id={element.ID} name={element.NAME} type={'element.type'} icon={element.ICON}/>);
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
    }

    render() {
        return (
            <div class='Site_Items'>
                <Item_table />
            </div>
        );
    }

}

export default Site_Items