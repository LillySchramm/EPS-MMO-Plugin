const express = require('express');
const router = express.Router();
const path = require('path');
const db_manager = require('../../mysql/db_manager');
const sql = require('../../mysql/mysql');
const fs = require('fs');

router.post("/test", async (req, res) => {
    if (req.files === null) {
        return res.status(400).json({ msg: 'No file uploaded' });
    }

    const file = req.files.file;

    let dir = path.resolve(__dirname, '../..')

    file.mv(`${dir}/uploads/${file.name}`, err => {
    if (err) {      
        console.error(err);
        return res.status(500).send(err);
    }

    const contents = fs.readFileSync(`${dir}/uploads/${file.name}`, {encoding: 'base64'});

    sql.query("INSERT INTO `eps_items`.`items` (`ID`, `DATA`, `ICON`) VALUES (NULL, '1', '"+ contents +"') ").then(() =>{
        res.json({ fileName: file.name, filePath: `/uploads/${file.name}` });
        });
    })
})
module.exports = router
