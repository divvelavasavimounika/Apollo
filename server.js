const bodyParser = require('body-parser');
const cors = require('cors');
const express = require('express');
const { graphiqlExpress, graphqlExpress } = require('apollo-server-express')


const port = process.env.PORT || 9006;
const app = express();

const fs = require('fs')
const typeDefs = fs.readFileSync('./schema.graphql', { encoding: 'utf-8' })
const resolvers = require('./resolvers')

const { makeExecutableSchema } = require('graphql-tools')
const schema = makeExecutableSchema({ typeDefs, resolvers })

app.use(cors(), bodyParser.json());


app.use('/graphql', graphqlExpress({ schema }))

// app.use('/graphiql',graphiqlExpress({end}))
app.use('/graphiql', graphiqlExpress({ endpointURL: '/graphql' }));

app.listen(
   port, () => console.info(
      `Server started on port ${port}`
   )
);