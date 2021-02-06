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
        request("npc/set/" + id +"/" + attr + "/" + value).then(() => {

        })
    });
}

module.exports = {editNPC}