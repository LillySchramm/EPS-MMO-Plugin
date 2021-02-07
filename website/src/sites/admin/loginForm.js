import React from 'react';
import ReactDOM from 'react-dom';

const coockie = require('../../tools/coockies');

class LoginForm extends React.Component {
    constructor(probs){
        super(probs);    
        
        this.state  = {username: 'username', password: ''};

        this.onSubmit = this.onSubmit.bind(this);
        this.handleUsernameChange = this.handleUsernameChange.bind(this);
        this.handlePasswordChange = this.handlePasswordChange.bind(this);

    } 
    
    handleUsernameChange(event) { 
        this.setState({username: event.target.value}); 
    
    }

    handlePasswordChange(event) { 
        this.setState({password: event.target.value});   
    }    

    async onSubmit(){             
        const url = "http://0.0.0.0:10100/login/" + this.state.username + "/" + this.state.password;
        const response = await fetch(url, {mode: 'cors', headers: {'Access-Control-Allow-Origin':'*' }});
        const data = await response.json();  

        if(data.successfull){
            coockie.setCookie("login",data.session,3)
        }                
    }

    render(){
        return(
            <div class='LoginForm'>
                <h1>Please log in.</h1>
                <form>
                    <input type="text" placeholder="Username" onChange={this.handleUsernameChange}/>        
                    <br/>    
                    <input type="password" placeholder="Password" onChange={this.handlePasswordChange}/>     
                    <br/>
                    <input type="button" value="Submit" onClick={this.onSubmit}/>
                </form>
            </div>
        );      
    }
  
  }

  export default LoginForm