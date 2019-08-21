const bodyParser = require('body-parser');
const cors = require('cors');
const express = require('express');
const config = require('./config/database')
const port = process.env.PORT || 9006;
const app = express();
const fs = require('fs')
const typeDefs = fs.readFileSync('./schema.graphql', { encoding: 'utf-8' })
const resolvers = require('./resolvers')
const { makeExecutableSchema } = require('graphql-tools')
const schema = makeExecutableSchema({ typeDefs, resolvers })
const db = require('./db')

app.use(cors(), bodyParser.json());

const { graphiqlExpress, graphqlExpress } = require('apollo-server-express')

app.use('/graphql', graphqlExpress({ schema }))
app.use('/graphiql', graphiqlExpress({ endpointURL: '/graphql' }));

db.connect(config.database, function (err) {
    if (err) {
        process.exit(1)
    } else {
        console.log("Connected to port",port);
        app.listen(port);
    }
})

