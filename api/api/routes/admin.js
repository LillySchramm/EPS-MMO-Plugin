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

module.exports = router;