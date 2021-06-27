const express = require('express');
const router = express.Router();
const db_manager = require('../../mysql/db_manager');
const sql = require('../../mysql/mysql');

const resourcePack = require('../../tools/resourcepack')

router.get('/:session', (req,res,next) => {
    let session = req.params.session;
    
    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){            
            res.status(200).json({
                verified: true
            });            
        }else{
            res.status(200).json({
                verified: false
            });   
        }        
    })    
});

router.get('/:session/staticeffects/getall', (req,res,next) => {
    let session = req.params.session;
    
    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){      
            sql.query("SELECT * FROM `eps_regions`.`static_effects` LIMIT 50;").then((npcs => {
                res.status(200).json({
                    npc: npcs,
                    verified: true
                });      
            }))                  
        }else{
            res.status(200).json({
                verified: false
            });   
        }        
    })    
});

router.get('/:session/staticeffects/get/:id', (req,res,next) => {
    let session = req.params.session;
    let id = req.params.id;
    
    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){      
            sql.query("SELECT * FROM `eps_regions`.`static_effects` WHERE ID = " + id).then((effect => {
                res.status(200).json({
                    effect: effect[0],
                    verified: true
                });      
            }))
                  
        }else{
            res.status(200).json({
                verified: false
            });   
        }        
    })    
});

router.get('/:session/npc/getall', (req,res,next) => {
    let session = req.params.session;
    
    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){      
            sql.query("SELECT * FROM `eps_regions`.`npc`;").then((npcs => {
                res.status(200).json({
                    npc: npcs,
                    verified: true
                });      
            }))
                  
        }else{
            res.status(200).json({
                verified: false
            });   
        }        
    })    
});

router.get('/:session/npc/get/:id', (req,res,next) => {
    let session = req.params.session;
    let id = req.params.id;
    
    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){      
            sql.query("SELECT * FROM `eps_regions`.`npc` WHERE ID = " + id).then((npcs => {
                res.status(200).json({
                    npc: npcs[0],
                    verified: true
                });      
            }))
                  
        }else{
            res.status(200).json({
                verified: false
            });   
        }        
    })    
});

router.get('/:session/server/instances/reload/all', (req,res,next) => {
    let session = req.params.session;  
    
    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){      
            db_manager.sendCommadToAllInstances("server reload").then((() => {
                res.status(200).json({
                    verified: true
                });      
            }))                  
        }else{
            res.status(200).json({
                verified: false
            });   
        }        
    })    
});

// TODO: I need to make this POST someday
router.get('/:session/npc/set/:npc_id/:attr/:value', (req,res,next) => {
    let session = req.params.session;
    let npc_id = req.params.npc_id;
    let attr = req.params.attr;
    let value = req.params.value;
    
    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){      
            sql.query("UPDATE `eps_regions`.`npc` SET " + attr + " = '" + value.hexDecode() + "' WHERE ID = " + npc_id + ";").then(() => {
                db_manager.sendCommadToAllInstances("npc reload " + npc_id).then((() => {
                    res.status(200).json({
                        verified: true
                    });      
                }))
            })          
                              
        }else{
            res.status(200).json({
                verified: false
            });   
        }       
    })    
});

router.get('/:session/staticeffects/set/:e_id/:attr/:value', (req,res,next) => {
    let session = req.params.session;
    let e_id = req.params.e_id;
    let attr = req.params.attr;
    let value = req.params.value;
    
    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){      
            sql.query("UPDATE `eps_regions`.`static_effects` SET " + attr + " = '" + value.hexDecode() + "' WHERE ID = " + e_id + ";").then(() => {
                db_manager.sendCommadToAllInstances("effect reload " + e_id).then((() => {
                    res.status(200).json({
                        verified: true
                    });      
                }))
            })          
                              
        }else{
            res.status(200).json({
                verified: false
            });   
        }       
    })    
});

router.get('/:session/item/new/:name/:type', (req,res,next) => {
    let session = req.params.session;
    let name = req.params.name;
    let type = req.params.type

    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){    
            db_manager.isItemNameTaken(name).then((r) => {
                if(r){
                    res.status(200).json({
                        verified: true,
                        success: false
                    });   
                }else{
                    sql.query("INSERT INTO `eps_items`.`items` (`ID`, `NAME`, `DATA`) VALUES (NULL, '" + name +"', '" + type + "')").then(() => {
                        res.status(200).json({
                            verified: true,
                            success: true
                        });   
                    })      
                }
            })                           
        }else{
            res.status(200).json({
                verified: false
            });   
        }       
    })    
});

router.get('/:session/item/getall/:type', (req,res,next) => {
    let session = req.params.session;
    let type = req.params.type;

    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){                       
            sql.query("SELECT * FROM `eps_items`.`items` WHERE DATA LIKE '%" + type + "%' LIMIT 50;").then((items => {
                res.status(200).json({
                    items: items,
                    verified: true
                });      
            }))
        }else{
            res.status(200).json({
                verified: false
            });   
        }       
    })    
});

router.get('/:session/item/get/:id', (req,res,next) => {
    let session = req.params.session;
    let id = req.params.id;

    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){                    
            sql.query("SELECT * FROM `eps_items`.`items` WHERE ID = " + id).then((items => {
                res.status(200).json({
                    item: items[0],
                    verified: true
                });      
            }))           
        }else{
            res.status(200).json({
                verified: false
            });   
        }       
    })    
});

router.get('/:session/item/delete/:id', (req,res,next) => {
    let session = req.params.session;
    let id = req.params.id;

    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){                    
            sql.query("DELETE FROM `eps_items`.`items` WHERE ID = " + id).then((() => {
                res.status(200).json({
                    verified: true
                });      
            }))           
        }else{
            res.status(200).json({
                verified: false
            });   
        }       
    })    
});

router.get('/:session/genResourcePack', (req,res,next) => {
    let session = req.params.session;
    let id = req.params.id;

    var date = new Date().today() + " @ " + new Date().timeNow();

    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){       
            resourcePack.genResourcePack().then(() => {
                sql.query("UPDATE `eps_vars`.`vars` SET `INT_VAL` = `INT_VAL` + 1 WHERE NAME = 'resource_pack_ver'; ").then(() => {
                    sql.query("UPDATE `eps_vars`.`vars` SET TEXT_VAL='" + date + "' WHERE NAME = 'resource_pack_last_edit';")
                    res.status(200).json({
                        verified: true
                    }); 
                })
            });
          
        }else{
            res.status(200).json({
                verified: false
            });   
        }       
    })    
});

router.post('/:session/item/icon/:id', (req,res,next) => { // This is an realy undescriptive path. Need to change it once I work on the API again。
    let session = req.params.session;
    let id = req.params.id;
    let icon = req.body.base
    let name = req.body.name
    let data = req.body.data

    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){                    
            sql.query("UPDATE `eps_items`.`items` SET ICON = '" + icon + "', NAME = '" + name + "', DATA = '" + data + "' WHERE ID = " + id + ";").then((items => {
                res.status(200).json({
                    verified: true
                });      
            }))           
        }else{
            res.status(200).json({
                verified: false
            });   
        }        
    })    
});

// For todays date;
Date.prototype.today = function () { 
    return ((this.getDate() < 10)?"0":"") + this.getDate() +"/"+(((this.getMonth()+1) < 10)?"0":"") + (this.getMonth()+1) +"/"+ this.getFullYear();
}

// For the time now
Date.prototype.timeNow = function () {
     return ((this.getHours() < 10)?"0":"") + this.getHours() +":"+ ((this.getMinutes() < 10)?"0":"") + this.getMinutes() +":"+ ((this.getSeconds() < 10)?"0":"") + this.getSeconds();
}


module.exports = router;