const coockie = require('../coockies');

const request = (req) => {
    return new Promise((resolve, reject) => {    
        const session = coockie.readCookie('login');
        let url = "http://0.0.0.0:10100/admin/" + session + "/" + req;
        fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } }).then((response) => {
            response.json().then((data) => {
                if (data.verified) {
                    resolve(data)
                } else {
                    coockie.deleteCoockie("login");
                    resolve('')
                }
            })            
        });        
    });
}

const editNPC = (id, attr, value) => {
    return new Promise((resolve, reject) => {    
        request("npc/set/" + id +"/" + attr + "/" + value.hexEncode()).then(() => {

        })
    });
}

String.prototype.hexEncode = function(){
    var hex, i;
  
    var result = "";
    for (i=0; i<this.length; i++) {
        hex = this.charCodeAt(i).toString(16);
        result += ("000"+hex).slice(-4);
    }
  
    return result
  }
  
String.prototype.hexDecode = function(){
    var j;
    var hexes = this.match(/.{1,4}/g) || [];
    var back = "";
    for(j = 0; j<hexes.length; j++) {
        back += String.fromCharCode(parseInt(hexes[j], 16));
    }

    return back;
}

module.exports = {editNPC}