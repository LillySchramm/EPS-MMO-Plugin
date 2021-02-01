const express = require('express');
const app = express();

const sessionRoutes = require('./api/routes/session')
const loginRoutes = require('./api/routes/login')

app.use('/session', sessionRoutes);
app.use('/login', loginRoutes);

module.exports = app;