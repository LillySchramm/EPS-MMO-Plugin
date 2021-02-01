const express = require('express');
const router = express.Router();
const db_manager = require('../../mysql/db_manager');
const sql = require('../../mysql/mysql');

const crypto = require('crypto');

router.get('/:username/:password', (req,res,next) => {
    username = req.params.username;
    password = req.params.password;
    
    db_manager.verifyUser(username,password).then((ret) => {

        let key = ""

        if(ret){
            key = crypto.randomBytes(100).toString('hex');
        }

        res.status(200).json({
            successfull: ret,
            session: key
        });   
    })    
});

module.exports = router;