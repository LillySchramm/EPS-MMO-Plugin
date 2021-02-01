const express = require('express');
const router = express.Router();
const db_manager = require('../../mysql/db_manager');
const sql = require('../../mysql/mysql');

router.get('/:username/:password', (req,res,next) => {
    username = req.params.username;
    password = req.params.password;
    
    db_manager.verifyUser(username,password).then((ret) => {

        let key = ""        

        if(ret){
            db_manager.genSession(username).then((r) => {
                key = r;
                res.status(200).json({
                    successfull: ret,
                    session: key
                });   
            })
        }else{
            res.status(200).json({
                successfull: ret,
                session: key
            });   
        }

        
    })    
});

module.exports = router;