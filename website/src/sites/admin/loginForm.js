import React from 'react';
import ReactDOM from 'react-dom';

const coockie = require('../../tools/coockies');

class LoginForm extends React.Component {
    constructor(probs){
      super(probs);    

      this.handleSubmit = this.handleSubmit.bind(this);
    } 
    
    handleSubmit(e){        
        e.preventDefault();

        
    }

    render(){
        return(
            <div>
                <h1>Please log in.</h1>
                <form>
                    <label>Username: </label>
                    <input type="text"/>
                    <br/>
                    <br/>
                    <label>Password: </label>
                    <input type="password" />                    
                    <br/>
                    <br/>
                    <input type="submit" value="Submit" />
                </form>
            </div>
        );      
    }
  
  }

  export default LoginForm