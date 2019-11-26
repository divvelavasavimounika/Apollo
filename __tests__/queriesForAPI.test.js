const { createTestClient } = require('apollo-server-testing');
const fs = require('fs');
const { graphql } = require('graphql');
const typeDefs = fs.readFileSync('./schema.graphql', 'utf8');
const resolvers = require('../resolvers');
const GenericAPI = require('../datasource');
const { ApolloServer } = require('apollo-server-express');


describe('createTests In API', () => {

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
        expect(res.data).toEqual({
            "genericResolver": "{\"id\":7,\"email\":\"michael.lawson@reqres.in\",\"first_name\":\"Michael\",\"last_name\":\"Lawson\",\"avatar\":\"https://s3.amazonaws.com/uifaces/faces/twitter/follettkyle/128.jpg\"}"
        });
    });


    it('allows throws an error in api', async () => {
        const query = `query myquery($inputString:String!)
            {
              genericResolver(input:$inputString)
            }`;
        const client = createTestClient(myTestServer);
        const res = await client.query({
            query, variables: {
                "inputString": "queryName:getData,ID:767"
            }

        });
        expect(res.data).toEqual({
            "genericResolver": "\"Data Not Found\""
        });
    });


    it('Fecth details not present', async () => {
        const query = `query myquery($inputString:String!)
            {
              genericResolver(input:$inputString)
            }`;
        const client = createTestClient(myTestServer);
        const res = await client.query({
            query, variables: {
                "inputString": "queryName:getData"
            }

        });
        expect(res.data).toEqual({
            "genericResolver": "\"Data Not Found\""
        });
    });
    it('Query name not given', async () => {
        const query = `query myquery($inputString:String!)
            {
              genericResolver(input:$inputString)
            }`;
        const client = createTestClient(myTestServer);
        const res = await client.query({
            query, variables: { "inputString": "queryName:" }
        });
        expect(res.data).toEqual({
            "genericResolver": "[]"
        });
    });


});







