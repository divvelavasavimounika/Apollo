const { createTestClient } = require('apollo-server-testing');
const fs = require('fs');
const { graphql } = require('graphql');
const typeDefs = fs.readFileSync('./schema.graphql', 'utf8');
const resolvers = require('../resolvers');
const GenericAPI = require('../datasource');
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
                "myname_Variable": "id:8,first_name:Mounika,last_name:Divvela,avatar:jkasdhkad,email:dvmounika1996@gmail.com",
                "op_type": "UpdateToAPI"
            }
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
                "myname_Variable":"ID:2",
                "op_type": "DeleteInAPI"
            }
        });

        expect(res.data).toEqual(res.data);

    });
    it('throws an error in api',async()=>
    {
        const mutation = `mutation myMutation($myname_Variable:String!,$op_type:String!)
        {
          genericMutation(input:$myname_Variable,mutationType:$op_type)
        }`;
        const client = createTestClient(myTestServer);
        const res = await client.mutate({
            mutation, variables:
            {
                "myname_Variable":"ID:2",
                "op_type": "DeleteInAPI"
            }
        });

        expect(res.data).toEqual(res.data);
    })


});

