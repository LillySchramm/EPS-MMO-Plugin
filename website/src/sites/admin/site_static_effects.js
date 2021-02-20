import React from 'react';
import ReactDOM from 'react-dom';
import particleTypes from '../../tools/particleNameDir';
import Coord_Span from "../general/Coord_Span";
import Skin_Obj from "../general/Skin_Obj"

const formater = require('../../tools/formater');
const coockie = require('../../tools/coockies');

class Effect_row extends React.Component {

    constructor(probs) {
        super(probs);
    }

    render() {
        return (
            
            <tr>
                <td>
                    <a href={'http://0.0.0.0:3000/admin/staticeffects/' + this.props.id}>
                        {this.props.id} 
                    </a>
                </td>

                <td>                    
                    <Coord_Span pos={this.props.pos}/>
                </td>

                <td>                    
                    <span>{particleTypes[this.props.type]}</span>
                </td>
               
            </tr>
            
        );
    }
}

class Effect_table extends React.Component {

    constructor(probs) {
        super(probs);

        this.state = {npcs: <span></span>}
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
            url = "http://0.0.0.0:10100/admin/" + session + "/staticeffects/getall";
            response = await fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } });
            data = await response.json();  

            let table = [];
            data.npc.forEach(element => {
                table.push(<Effect_row id={element.ID} pos={element.POS} type={element.DATA.split(">>")[0]}/>);
            });         
            this.setState({npcs: table});
                
        } else {
            this.forceUpdate()

            coockie.deleteCoockie("login");
            this.setState({npcs: <span></span>});
        }

        this.forceUpdate()
    }

    render() {
        return (
            <table class='Effects_Table'>                
                <tr>
                    <th>ID</th>
                    <th>Position</th>
                    <th>Type</th>
                </tr>
                {this.state.npcs}
            </table>
        );
    }
}

class Site_Effects extends React.Component {
    constructor(probs) {
        super(probs);
    }

    render() {
        return (
            <div class='Site_NPC'>
                <Effect_table />
            </div>
        );
    }

} 

export default Site_Effects