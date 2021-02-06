import React from 'react';
import ReactDOM from 'react-dom';

const coockie = require('../../tools/coockies');
const api = require('../../tools/api/api');

class NPC_edit extends React.Component {

    constructor(probs) {
        super(probs);        

        let url = window.location.href
        let unslashed = url.split('/');
        
        this.state = {npc_name: '', npc_id: unslashed[unslashed.length - 1], npc_script: '', npc_skin:'', _npc_name: '', _npc_script: '', _npc_skin: ''}      
        
        this.onSubmit = this.onSubmit.bind(this);
        this.handleNameChange = this.handleNameChange.bind(this)
        this.handleSkinChange = this.handleSkinChange.bind(this)
        this.handleScriptChange = this.handleScriptChange.bind(this)
    }

    async onSubmit(){

        let rl = false;

        if(this.state._npc_name != ''){
            api.editNPC(this.state.npc_id, 'NAME', this.state.npc_name);
            this.setState({_npc_name: ''});
        }


        if(this.state._npc_skin != ''){
            api.editNPC(this.state.npc_id, 'SKIN', this.state.npc_skin);
            this.setState({_npc_skin: ''});
        }


        if(this.state._npc_script != ''){
            api.editNPC(this.state.npc_id, 'SCRIPT', this.state.npc_script);
            this.setState({_npc_script: ''});
        }

        if(rl){

        }
    }

    handleNameChange(e){
        this.setState({
            _npc_name: e.target.value,
            npc_name: e.target.value
        })
    }

    handleSkinChange(e){
        this.setState({
            _npc_skin: e.target.value,
            npc_skin: e.target.value
        })
    }

    handleScriptChange(e){
        this.setState({
            _npc_script: e.target.value,
            npc_script: e.target.value
        })
    }

    componentDidMount(){    
        this.getNPC_Data()
    }    

    async getNPC_Data(id){
        const session = coockie.readCookie('login');
        let url = "http://0.0.0.0:10100/admin/" + session + "/npc/get/" + this.state.npc_id;
        let response = await fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } });
        let data = await response.json();  

        if (data.verified) {
            this.setState({
                npc_name: data.npc.NAME,
                npc_script: data.npc.SCRIPT,
                npc_skin: data.npc.SKIN
            });                
        } else {
            this.forceUpdate()
            coockie.deleteCoockie("login");  
        }

        this.forceUpdate()
    }
    

    render() {
        return (
            <div>

                <h2>Currently selected: {this.state.npc_name} [ID:{this.state.npc_id}]</h2>

                <form>
                    <label>Name: </label>
                    <input type='text' value={this.state.npc_name} onChange={this.handleNameChange}/>
                    <br/>

                    <label>Skin: </label>
                    <input type='text' value={this.state.npc_skin} onChange={this.handleSkinChange}/>
                    <br/>

                    <label>Script: </label>
                    <input type='text' value={this.state.npc_script} onChange={this.handleScriptChange}/>
                    <br/>

                    <br/>
                    <input type="button" value="Submit" onClick={this.onSubmit}/>
                </form>

            </div>
        );
    }
}

export default NPC_edit