import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route} from 'react-router-dom';

import LoginForm from './loginForm'
import Site_NPC from './site_npc';
import Admin_Header from './admin_header';
import NPC_edit from './site_edit_npc';

const coockie = require('../../tools/coockies');

class AdminPage extends React.Component {
    constructor(probs){
      super(probs);    
    }   

    componentDidMount(){
        this.ticker = setInterval(() => {this.forceUpdate()}, 250);        
    }    
  
    render(){   
        if(coockie.readCookie("login")){                 
            return [
                <div>
                    <Admin_Header/>
                    <Router>
                        <Route path='/admin/npc' exact component={Site_NPC} />      
                    </Router>    
                    <Router>
                        <Route path='/admin/npc/:id' exact component={NPC_edit} />      
                    </Router>    
                </div>
            ];
        }else{
            return (
                <LoginForm/>
            );
        }        
    }
  
  }

  export default AdminPage