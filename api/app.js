const express = require('express');
const app = express();

const sessionRoutes = require('./api/routes/session')
const loginRoutes = require('./api/routes/login')
const cors = require('cors')

app.use(cors());

app.use('/session', sessionRoutes);
app.use('/login', loginRoutes);

module.exports = app;