const express = require('express');
const fileupload = require('express-fileupload')
const app = express();

const sessionRoutes = require('./api/routes/session')
const loginRoutes = require('./api/routes/login')
const adminRoutes = require('./api/routes/admin')
const uploadRoutes = require('./api/routes/upload');

const cors = require('cors')

app.use(cors());
app.use(fileupload())

app.use('/session', sessionRoutes);
app.use('/login', loginRoutes);
app.use('/admin', adminRoutes);
app.use('/upload', uploadRoutes);

module.exports = app;