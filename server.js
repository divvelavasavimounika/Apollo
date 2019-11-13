const bodyParser = require('body-parser');
const cors = require('cors');
const { ApolloServer } = require('apollo-server-express');
const express = require('express');
const app = express();
const config = require('./config/database');
const fs = require('fs');
const GenericAPI = require('./datasource');
const fetch = require('node-fetch');
const typeDefs = fs.readFileSync('./schema.graphql', { encoding: 'utf-8' })
const resolvers = require('./resolvers')
const db = require('./db');
const port = 8000;
app.use(cors(), bodyParser.json());
process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
const server = new ApolloServer({
    typeDefs,
    resolvers,
    dataSources: () => ({
        GenericAPI: new GenericAPI()
    })
});


server.applyMiddleware({ app });

app.listen(port);
