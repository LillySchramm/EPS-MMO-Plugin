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


module.exports = router;