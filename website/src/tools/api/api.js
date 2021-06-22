const axios = require('axios')
const coockie = require('../coockies');

const request = (req) => {
    return new Promise((resolve, reject) => {    
        const session = coockie.readCookie('login');
        let url = "http://localhost:10100/admin/" + session + "/" + req;
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

const request_wo_admin = (req) => {
    return new Promise((resolve, reject) => {    
        let url = "http://localhost:10100/" + req;
        fetch(url, { mode: 'cors', headers: { 'Access-Control-Allow-Origin': '*' } }).then((response) => {
            response.json().then((data) => {
                resolve(data)
            })            
        });        
    });
}

const post_request = (req, data) => {
    return new Promise((resolve, reject) => {    
        const session = coockie.readCookie('login');
        let url = "http://localhost:10100/admin/" + session + "/" + req;
        
        axios.post(url, data).then(r => {
            console.log(r)
            resolve(r)
        })
    });
}

const editNPC = (id, attr, value) => {
    return new Promise((resolve, reject) => {    
        request("npc/set/" + id +"/" + attr + "/" + value.hexEncode()).then(() => {
        })
    });
}

const editEffect = (id, attr, value) => {
    return new Promise((resolve, reject) => {    
        request("staticeffects/set/" + id +"/" + attr + "/" + value.hexEncode()).then(() => {
        })
    });
}

const editItem = (state) => {
    return new Promise((resolve, reject) => {    
        post_request("item/icon/" + state.item_id, {base: state.icon, name:state.name, data:state.data}).then((r) => {
            resolve(r)
        })
    });
}

const newItem = (name, type) => {
    return new Promise((resolve, reject) => {    
        request("item/new/" + name + "/" + type).then((d) => {
            resolve(d)
        })
    });
}

const deleteItem = (id) => {
    return new Promise((resolve, reject) => {    
        request("item/delete/" + id).then((d) => {
            resolve(d)
        })
    });
}

const regenResourcePack = () => {
    return new Promise((resolve, reject) => {    
        request("genResourcePack/").then((d) => {
            resolve(d);
        }) 
    })
}

const getResourcePackVersion = () => {
    return new Promise((resolve, reject) =>  {
        request_wo_admin("resourcepack/version/").then(r => {
            resolve(r);
        })
    })
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

module.exports = {editNPC, editEffect, editItem, deleteItem,newItem, regenResourcePack, getResourcePackVersion}