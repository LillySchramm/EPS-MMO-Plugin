const express = require('express');
const router = express.Router();
const path = require('path');

router.get('/', (req,res,next) => {
    const file = path.resolve(__dirname, '../..') + "/data/" + `pack.zip`;
    res.download(file);
});


module.exports = router