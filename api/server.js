const http = require('http');
const port = 10100;
const app = require('./app');

const db_manager = require('./mysql/db_manager')

db_manager.init()

const server = http.createServer(app);
server.listen(port);