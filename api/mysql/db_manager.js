const sql = require('./mysql')
const encrypt = require('../tools/encryption')
const crypto = require('crypto');

const databases = ["eps_sessions", "eps_users", "eps_regions", "eps_items", "eps_vars"];
const vars = ["resource_pack_ver"];

const tables = 
[
    {db_name: "eps_users", table_name: "players", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `UUID` TEXT NOT NULL , `RANK` TEXT NOT NULL , `MONEY` INT NOT NULL , PRIMARY KEY (`ID`)"},
    {db_name: "eps_users", table_name: "logins", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `USERNAME` TEXT NOT NULL , `PASSWORD` TEXT NOT NULL, PRIMARY KEY (`ID`)"},
    {db_name: "eps_users", table_name: "characters", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `UUID` TEXT NOT NULL , `STATS` TEXT NOT NULL , `LAST_POS` TEXT NOT NULL, `NAME` TEXT NOT NULL, `EXP` INT NOT NULL, `LEVEL` INT NOT NULL, PRIMARY KEY (`ID`)"},
    {db_name: "eps_regions", table_name: "regions", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL , `LEVEL` INT NOT NULL , PRIMARY KEY (`ID`)"},
    {db_name: "eps_regions", table_name: "cities", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL, `REGION_ID` INT NOT NULL , PRIMARY KEY (`ID`)"},
    {db_name: "eps_regions", table_name: "houses", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL , `COSTS` INT NOT NULL , `OWNER_UUID` TEXT NOT NULL , `BLOCKS_INSIDE` TEXT NOT NULL , `DOORS` TEXT NOT NULL , `SPAWN_POS` TEXT NOT NULL , `SHIELD_POS` TEXT NOT NULL , `CITY_ID` INT NOT NULL, `RENTTIME` INT NOT NULL , PRIMARY KEY (`ID`)"},
    {db_name: "eps_regions", table_name: "npc", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL, `SCRIPT` TEXT NOT NULL , `POS` TEXT NOT NULL, `ROTATION` TEXT NOT NULL, `SKIN` TEXT NOT NULL, PRIMARY KEY (`ID`)"},
    {db_name: "eps_regions", table_name: "static_effects", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `DATA` TEXT NOT NULL , `POS` TEXT NOT NULL, PRIMARY KEY (`ID`)"},
    {db_name: "eps_sessions", table_name: "sessions", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `SESSION_ID` TEXT NOT NULL, `UUID` TEXT NOT NULL, `ACTIVE` INT NOT NULL, PRIMARY KEY (`ID`)"},
    {db_name: "eps_sessions", table_name: "web_sessions", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `SESSION_ID` TEXT NOT NULL, `USERNAME` TEXT NOT NULL,  `EXP_DATE` DATE NOT NULL, PRIMARY KEY (`ID`)"},
    {db_name: "eps_sessions", table_name: "server_commands", constructor: "`ID` INT NOT NULL AUTO_INCREMENT , `FOR` TEXT NOT NULL, `CMD` TEXT NOT NULL, PRIMARY KEY (`ID`)"},
    {db_name: "eps_items", table_name:"items", constructor:" `ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL , `DATA` TEXT NOT NULL , `ICON` TEXT DEFAULT 'iVBORw0KGgoAAAANSUhEUgAAAQwAAAEMCAMAAAAGUnihAAAAV1BMVEX/ANz9ANr/AOO8AKEAAAAjAB0OAApIAD34ANb/AN77ANn/AOEzACsoACECAAJQAEP2ANT/AOv/AOX+ANv/AN33ANVKAD4XABL/AN/5ANc/ADXxANBhAFObooGdAAAAAW9yTlQBz6J3mgAAAolJREFUeNrt3N1OKjEYBdBCi4DIjwgo6Ps/5+mVV9IBc8x8NGvfkmwyK51eTXZK7UymucxKI09lvlj2UFEzYPEoTwIDBgwYMGCEqIABAwYMGDBgwIABAwaMCBUwYMCAAQMGDBgwYMAYFWPSzvMql5dZI+vNdpd6qKhJ03ZWr7kMJe17qcjynTTIlV9XTfJ9LxXTaRp4jV5KXj03X8a0227WPVTUkgHOWcnTSfOaXi7m9TLvoKIGBgwYMGDAgAEDBgwYMEavgAEDBgwYMGDAgAEDBowIFTBgwLgF46md9VDH2+F4Ku8dVNxwMjYl79OykcPH+dJFRU2at7MtOe0WjRzPn1+XUwcVNWng913KZdv6k9Pl6/N87KCiZvDk7HM9Yc1czh+HLipu+vRx3bqY3svpeHjroKLmFowH+IjTR7EwYMCAASNEBQwYMGDAgAEDBgwYMGBEqIABAwYMGDBgwIBhZsbMjJmZIAkx8BKjwsyMmZmrgQEDBgwYMGDAgAEDBozRK2DAgAEDBgwYMGDAgAEjQgUMGDDMzJiZ+V2FmRkzM2ZmzMzcU2Fm5v6T8QBPAgMGDBgwYISogAEDBgwYMGDAgAEDBowIFTBgwIABAwYMGH+JYWbGzMzPFWNPu0RKiIGXGBVmZszMXA0MGDBgwIABAwYMGDBgjF4BAwYMGDBgwIABAwYMGBEqYMCAYWbGzMzvKszMmJkxM2Nm5p4KMzP3n4wHeBIYMGDAgAEjRAUMGDBgwIABAwYMGDBgRKiAAQMGDBgwYMD4SwwzM2Zmfq4Ye9olUkIMvMSoMDNjZuZqYMCAAQMGDBgwYMCAAWP0ChgwYMCAAQMGDBgwYMCIUAEDBgwYMGD8D4x/b6v3cY7w8nAAAAAASUVORK5CYII=' , PRIMARY KEY (`ID`)"},
    {db_name: "eps_vars", table_name:"vars", constructor:" `ID` INT NOT NULL AUTO_INCREMENT , `NAME` TEXT NOT NULL , `INT_VAL` INT NOT NULL , `FLOAT_VAL` FLOAT NOT NULL , `TEXT_VAL` TEXT NOT NULL , PRIMARY KEY (`ID`)"}
]

function init(){

    //Init all databases
    
    databases.forEach(db => {
        sql.createDatabaseIfNotExist(db).then(() => {});
    });

    //Init all tables
    
    tables.forEach(tb => {
        sql.createTablefNotExist(tb.db_name, tb.table_name, tb.constructor).then(() => {});
    });

    //Init all VAR

    vars.forEach(v => {
        addVarIfNotExists(v);
    })

    //Init login

    sql.query("SELECT * FROM `eps_users`.`logins`").then(((res) => {
        if(sql.isRSempty(res)){
            let username = 'admin';
            let password = 'eps';

            encrypt.cryptPassword(password, (err, res) => {
                password = res;                    
                sql.query("INSERT INTO `eps_users`.`logins` (`ID`, `USERNAME`, `PASSWORD`) VALUES (NULL, '" + username + "', '" + password + "'); ");
            })            
        }
    }))
}      

const getSession = (key) =>{
    return new Promise((resolve, reject) => {
        sql.query("SELECT * FROM `eps_sessions`.`sessions` WHERE SESSION_ID = '" + key + "';").then((ret) => {
            resolve(ret);
        });
    });
}

const getUser = (name) =>{
    return new Promise((resolve, reject) => {
        sql.query("SELECT * FROM `eps_users`.`logins` WHERE USERNAME = '" + name + "';").then((ret) => {
            resolve(ret);
        });
    });
}

const verifyUser = (name, password) =>{
    return new Promise((resolve, reject) => {
        getUser(name).then((ret) =>{
            if(sql.isRSempty(ret)){
                resolve(false);
            }else{          
                encrypt.comparePassword(password, ret[0].PASSWORD, (err, r) => {
                    if(r){
                        resolve(true)
                    }else{
                        resolve(false)
                    }   
                })
                           
            }
        })
    })    
}

const verifyWebSession = (key) =>{
    return new Promise((resolve, reject) => {
    
        sql.query("SELECT * FROM `eps_sessions`.`web_sessions` WHERE SESSION_ID = '" + key + "' AND EXP_DATE = CAST(CURRENT_TIMESTAMP AS DATE)").then((ret) => {
            if(sql.isRSempty(ret)){
                resolve(false)
            }else{
                resolve(true)
            }
        })
      
    })    
}

const genSessionKey = () => {
    return new Promise((resolve, reject) => {
        
        let key = crypto.randomBytes(300).toString('hex'); 

        sql.query("SELECT * FROM `eps_sessions`.`web_sessions` WHERE SESSION_ID = '" + key + "';").then((ret) => {       
            if(sql.isRSempty(ret)){
                found = true;                    
                resolve(key);                                                            
            }else{
                resolve(genSessionKey())
            }                           
        })
             
    })
}

const genSession = (user) => {
    return new Promise((resolve, reject) => {
        genSessionKey().then((key) =>{
            sql.query("INSERT INTO `eps_sessions`.`web_sessions` (`ID`, `SESSION_ID`, `USERNAME`, `EXP_DATE`) VALUES (NULL, '"  + key + "', '" +user +"', CAST(CURRENT_TIMESTAMP AS DATE));").then((rs) => {
                resolve(key)
            })  
        });                       
    })         
}

const getAllInstances = () => {
    return new Promise((resolve, reject) => {        
        sql.query("SELECT * FROM `eps_sessions`.`server_sessions`;").then((rs) => {
            resolve(rs)
        })                      
    }) 
}

const sendCommadToAllInstances = (cmd) => {
    return new Promise((resolve, reject) => {
        getAllInstances().then((instances) => {
            instances.forEach(instance => {
                sql.query("INSERT INTO `eps_sessions`.`server_commands` (`ID`, `FOR`, `CMD`) VALUES (NULL, '" + instance.SESSION_ID + "', '" + cmd + "')").then((rs) => {
                    resolve(true);
                })
            });
        })
    })         
}

const addVarIfNotExists = (name) => {
    return new Promise((resolve, reject) => {        
        sql.query("SELECT * FROM `eps_vars`.`vars` WHERE NAME='" + name + "';").then((rs) => {
            if(sql.isRSempty(rs)){
                sql.query("INSERT INTO `eps_vars`.`vars` (`ID`, `NAME`, `INT_VAL`, `FLOAT_VAL`, `TEXT_VAL`) VALUES (NULL, '" + name +"', '0', '0', 'text') ").then(() => {
                    console.log("VAR '" + name + "' created.");
                })
            }
        })                      
    }) 
}

const isItemNameTaken = (n) => {
    return new Promise((resolve, reject) => {
        sql.query("SELECT * FROM `eps_items`.`items` WHERE `NAME` = '"+ n +"';").then((rs) => {
            resolve(!sql.isRSempty(rs))
        })       
    })        
}


module.exports = {init, getSession, getUser, verifyUser, verifyWebSession, genSession, sendCommadToAllInstances, isItemNameTaken}