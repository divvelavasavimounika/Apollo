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
    it('allows Mutations For Adding', async () => {


        const mutation = `mutation myMutation($myname_Variable:String!,$op_type:String!)
        {
          genericMutation(input:$myname_Variable,mutationType:$op_type)
        }`;
        const client = createTestClient(myTestServer);
        const res = await client.mutate({
            mutation, variables: {
                "myname_Variable": "first_name:Mounika,last_name:Divvela,avatar:jkasdhkad,email:dvmounika1996@gmail.com,phone:789456099",
                "op_type": "AddInAPI"
            }
        });
        nock('https://reqres.in/api/users')
            .get('/api/')
            .reply(200, {
                "genericMutation": "{\"first_name\":\"Mounika\",\"last_name\":\"Divvela\",\"avatar\":\"jkasdhkad\",\"email\":\"dvmounika1996@gmail.com\",\"phone\":\"789456099\",\"id\":\"775\"}"

            });
        expect(res.data).toEqual(res.data);

    });

    it('allows Mutations for Updating', async () => {


        const mutation = `mutation myMutation($myname_Variable:String!,$op_type:String!)
        {
          genericMutation(input:$myname_Variable,mutationType:$op_type)
        }`;
        const client = createTestClient(myTestServer);
        const res = await client.mutate({
            mutation, variables:
            {
                "myname_Variable": "id:2,first_name:Mounika,last_name:Divvela,avatar:jkasdhkad,email:dvmounika1996@gmail.com",
                "op_type": "UpdateToAPI"
            }
        });
        nock('https://reqres.in/api/users')
            .get('/api/2')
            .reply(200, {
                "genericMutation": "{\"first_name\":\"Mounika\",\"last_name\":\"Divvela\",\"avatar\":\"jkasdhkad\",\"email\":\"dvmounika1996@gmail.com\",\"updatedAt\":\"2019-11-20T07:24:29.251Z\"}"
            });
        expect(res.data).toEqual(res.data);
    });

    it('allows Mutations for Deleting', async () => {


        const mutation = `mutation myMutation($myname_Variable:String!,$op_type:String!)
        {
          genericMutation(input:$myname_Variable,mutationType:$op_type)
        }`;
        const client = createTestClient(myTestServer);
        const res = await client.mutate({
            mutation, variables:
            {
                "myname_Variable": "ID:2",
                "op_type": "DeleteInAPI"
            }
        });
        nock('https://reqres.in/api/users')
            .get('/api/2')
            .reply(200, {
                "genericMutation": "\"Deleted and status is:204\""
            });
        expect(res.data).toEqual(res.data);

    });

});

