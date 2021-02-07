import React from 'react';
import ReactDOM from 'react-dom';

const coockie = require('../../tools/coockies');

class Admin_Header extends React.Component {
    constructor(probs){
        super(probs);    
    }   

    render(){
        return(
            <div class="Admin_Header">
                <h1>Admin Panel</h1>
                <hr/>   
                <h2> <a href='http://0.0.0.0:3000/admin/npc'>NPC</a> | <a href='http://0.0.0.0:3000/admin/instances'>Instances</a> | <a href='http://0.0.0.0:3000/admin/items'>Items</a> | <a href='http://0.0.0.0:3000/admin/players'>Players</a></h2>
                <hr/>                
            </div>
        );      
    }
  
  }

  export default Admin_Header