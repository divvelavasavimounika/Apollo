const { createTestClient } = require('apollo-server-testing');
const fs = require('fs');
const { graphql } = require('graphql');
const typeDefs = fs.readFileSync('./schema.graphql', 'utf8');
const resolvers = require('./resolvers');
const GenericAPI = require('./datasource');
const { ApolloServer } = require('apollo-server-express');
const nock = require('nock');

describe('createTests', () => {

    const myTestServer = new ApolloServer({
        typeDefs,
        resolvers,
        dataSources: () => ({
            GenericAPI: new GenericAPI()
        })
    });
    it('allows Queries', async () => {
        const query = `query myquery($inputString:String!)
        {
          genericResolver(input:$inputString)
        }`;
        const client = createTestClient(myTestServer);
        const res = await client.query({
            query, variables: {
                "inputString": "queryName:getData,ID:7"
            }

        });

        nock('https://reqres.in/api/users/7')
            .get('/api/')
            .reply(200, {
                "genericResolver": {
                    "avatar": "https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg",
                    "email": "michael.lawson@reqres.in",
                    "first_name": "Michael",
                    "id": 7,
                    "last_name": "Lawson",
                }
            });
        expect(res.data).toEqual({
            "genericResolver": "{\"id\":7,\"email\":\"michael.lawson@reqres.in\",\"first_name\":\"Michael\",\"last_name\":\"Lawson\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg\"}"
        });

    });


});





