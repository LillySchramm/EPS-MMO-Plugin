const express = require('express');
const app = express();

const sessionRoutes = require('./api/routes/session')

app.use('/session', sessionRoutes)

module.exports = app;