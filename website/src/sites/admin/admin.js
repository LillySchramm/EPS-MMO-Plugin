import React from 'react';
import ReactDOM from 'react-dom';

import LoginForm from './loginForm'

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
                    <h1>Logged in successfully </h1>
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