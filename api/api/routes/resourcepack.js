const express = require('express');
const router = express.Router();
const path = require('path');
const sql = require('../../mysql/mysql')

router.get('/download/:bump', (req,res,next) => {
    const file = path.resolve(__dirname, '../..') + "/data/" + `pack.zip`;
    res.download(file);
});

router.get('/version', (req,res,next) => {
    sql.query('SELECT INT_VAL FROM `eps_vars`.`vars` WHERE NAME = "resource_pack_ver"').then(rs => {
        res.status(200).json({
            ver:rs[0].INT_VAL
        })
    })
})


module.exports = router