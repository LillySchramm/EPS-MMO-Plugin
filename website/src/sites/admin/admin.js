import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route} from 'react-router-dom';

import LoginForm from './loginForm'
import Site_NPC from './site_npc'
import Site_Effects from './site_static_effects'
import Site_Items from './site_items'
import Admin_Header from './admin_header';
import NPC_edit from './site_edit_npc';
import Effect_edit from './site_edit_static_effects';
import Item_edit from './site_edit_item';

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

                    <Router>
                        <Route path='/admin/staticeffects' exact component={Site_Effects} />      
                    </Router>    
                    <Router>
                        <Route path='/admin/staticeffects/:id' exact component={Effect_edit} />      
                    </Router>  

                    <Router>
                        <Route path='/admin/items/' exact component={Site_Items} />      
                    </Router>  
                    <Router>
                        <Route path='/admin/item/:id' exact component={Item_edit} />      
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