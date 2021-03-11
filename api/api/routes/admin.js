const express = require('express');
const router = express.Router();
const db_manager = require('../../mysql/db_manager');
const sql = require('../../mysql/mysql');

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
            sql.query("SELECT * FROM `eps_regions`.`npc` LIMIT 50;").then((npcs => {
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

router.get('/:session/item/new/:name', (req,res,next) => {
    let session = req.params.session;
    let name = req.params.name;

    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){    
            db_manager.isItemNameTaken(name).then((r) => {
                if(r){
                    res.status(200).json({
                        verified: true,
                        success: false
                    });   
                }else{
                    sql.query("INSERT INTO `eps_items`.`items` (`ID`, `NAME`, `DATA`) VALUES (NULL, '" + name +"', '')").then(() => {
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

router.get('/:session/item/getall', (req,res,next) => {
    let session = req.params.session;

    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){                       
            sql.query("SELECT * FROM `eps_items`.`items` LIMIT 50;").then((items => {
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

router.get('/:session/item/set/:e_id/:attr/:value', (req,res,next) => {
    let session = req.params.session;
    let e_id = req.params.e_id;
    let attr = req.params.attr;
    let value = req.params.value;
    
    db_manager.verifyWebSession(session).then((ret) => {
        if(ret){      
            sql.query("UPDATE `eps_items`.`items` SET " + attr + " = '" + value.hexDecode() + "' WHERE ID = " + e_id + ";").then(() => {
                res.status(200).json({
                    verified: true
                });      
            })                                        
        }else{
            res.status(200).json({
                verified: false
            });   
        }       
    })    
});



module.exports = router;