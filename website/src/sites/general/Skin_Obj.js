import React from 'react';
import ReactDOM from 'react-dom';

const formater = require('../../tools/formater');

class Skin_Obj extends React.Component {
    constructor(probs) {
        super(probs);
    }

    render() {
        let base_url = "https://mc-heads.net/" + this.props.type + "/";     

        let base64_encoded = "";

        try{
            console.log(this.props.skin)
            base64_encoded = atob(this.props.skin);
        }catch{}
        
        let id = "Alex";

        if(base64_encoded != ""){    
            console.log(base64_encoded)
            let jsonObj = JSON.parse(base64_encoded);   
            id = jsonObj.textures.SKIN.url;
            let _id = id.split('/')
            id = _id[4];
       }       

        return (            
            <div>
                <img src={base_url + id + "/" + this.props.size} alt="PIC"/>
            </div>        
        );
    }

}

export default Skin_Obj