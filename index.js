const bodyParser = require('body-parser');
const cors = require('cors');
const { ApolloServer, gql } = require('apollo-server-express');
const express = require('express');
const mongoose = require('./config');


const app = express();

const fs = require('fs')
const typeDefs = fs.readFileSync('./schema.graphql', { encoding: 'utf-8' })
const resolvers = require('./resolvers')

const { makeExecutableSchema } = require('graphql-tools')
const schema = makeExecutableSchema({ typeDefs, resolvers })

app.use(cors(), bodyParser.json());

const server = new ApolloServer({ schema });


server.applyMiddleware({ app });

app.listen({ port: 4000 }, () =>
    console.log(`🚀 Server ready at http://localhost:4000 ${server.graphqlPath}`)
);

