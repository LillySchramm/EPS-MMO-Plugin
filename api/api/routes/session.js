const express = require('express');
const router = express.Router();
const db_manager = require('../../mysql/db_manager');
const sql = require('../../mysql/mysql');

router.get('/:id', (req,res,next) => {
    const id = req.params.id;  

    db_manager.getSession(id).then((resp) => {

        if(!sql.isRSempty(resp)){
            if(resp[0].ACTIVE == 1){
                res.status(200).json({
                    message: "session valid"
                });   
            }else{
                res.status(408).json({
                    message: "session inactive"
                });   
            }
             
        }else{
            res.status(401).json({
                message: "session key invalid"
            });    
        }

        
    })    
});

router.get('/', (req,res,next) => {
    res.status(400).json({
        error: 'Please specify a session key!'
    });
});

module.exports = router