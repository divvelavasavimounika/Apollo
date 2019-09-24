const bodyParser = require('body-parser');
const cors = require('cors');
const { ApolloServer } = require('apollo-server-express');
const express = require('express');
const app = express();
const config = require('./config/database')
const fs = require('fs')
const typeDefs = fs.readFileSync('./schema.graphql', { encoding: 'utf-8' })
const resolvers = require('./resolvers')
const db = require('./db');
const { makeExecutableSchema } = require('graphql-tools')
const schema = makeExecutableSchema({ typeDefs, resolvers })
const port = process.env.PORT || 8000;
app.use(cors(), bodyParser.json());

const server = new ApolloServer({ schema });


server.applyMiddleware({ app });

app.listen(port);
