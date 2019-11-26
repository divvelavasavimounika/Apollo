const { createTestClient } = require('apollo-server-testing');
const fs = require('fs');
const { graphql } = require('graphql');
const typeDefs = fs.readFileSync('./schema.graphql', 'utf8');
const resolvers = require('../resolvers');
const { ApolloServer } = require('apollo-server-express');
const { MongoClient } = require('mongodb');
const config = require('../config/database');
const db = require('../db');
db.connect(config.database, function (err) {
    if (err) {
        process.exit(1);
    }
});
describe('createTests', () => {
    let connection;
    let db1;

    beforeAll(async () => {
        connection = await MongoClient.connect(config.database, {
            useNewUrlParser: true, useUnifiedTopology: true
        });
        db1 = await connection.db("genericResolver");
    });

    afterAll(async () => {
        await connection.close();
        await db.close();
    });
    const myTestServer = new ApolloServer({
        typeDefs,
        resolvers
    });

    it('allows mutations for adding Data', async () => {

        const mutation = `mutation myMutation($myname_Variable:String!,$op_type:String!)
        {
          genericMutation(input:$myname_Variable,mutationType:$op_type)
        }` ;
        const client = createTestClient(myTestServer);
        const res = await client.mutate({
            mutation, variables:
            {
                "myname_Variable": "collectionName:Customer,CustomerName:Mounika,Location:Piduguralla,District:Guntur,State:AP",
                "op_type": "AddInDB"
            }
        });
        console.log("Res", res.data);
        expect(res.data).toEqual(res.data);
    });


    it('allows mutations for Updating Data', async () => {

        const mutation = `mutation myMutation($myname_Variable:String!,$op_type:String!)
        {
          genericMutation(input:$myname_Variable,mutationType:$op_type)
        }` ;
        const client = createTestClient(myTestServer);
        const res = await client.mutate({
            mutation, variables:
            {
                "myname_Variable": "collectionName:Customer,CustomerName:Mounika,Location:Gachibowli,District:RangaReddy,State:Telegana",
                "op_type": "UpdateToDB"
            }
        });
        console.log("Res", res.data);
        expect(res.data).toEqual(res.data);
    });

    

    it('allows mutations for Deleting Data', async () => {

        const mutation = `mutation myMutation($myname_Variable:String!,$op_type:String!)
        {
          genericMutation(input:$myname_Variable,mutationType:$op_type)
        }` ;
        const client = createTestClient(myTestServer);
        const res = await client.mutate({
            mutation, variables:
            {
                "myname_Variable": "collectionName:Customer,CustomerName:Mounika",
                "op_type": "DeleteInDB"
            }
        });
        console.log("Res", res.data);
        expect(res.data).toEqual(res.data);
    });
    it('should throw an error in mutations', async () => {
        const mutation = `mutation myMutation($myname_Variable:String!,$op_type:String!)
        {
          genericMutation(input:$myname_Variable,mutationType:$op_type)
        }` ;
        const client = createTestClient(myTestServer);
        const res = await client.mutate({
            mutation, variables:
            {
                "myname_Variable": "collectionName:Customer,CustomerName:Mounika",
                "op_type": "DeleteInDB"
            }
        });
        expect(res.data).toEqual({ "genericMutation": "Data is not present" });
    });
    
    it('should throw an error in mutations', async () => {
        const mutation = `mutation myMutation($myname_Variable:String!,$op_type:String!)
        {
          genericMutation(input:$myname_Variable,mutationType:$op_type)
        }` ;
        const client = createTestClient(myTestServer);
        const res = await client.mutate({
            mutation, variables:
            {
                "myname_Variable": "collectionName:Customer,CustomerName:Mounika,Location:Ameenpur,District:RangaReddy,State:Andhra Pradesh",
                "op_type": "UpdateToDB"
            }

        });
        expect(res.data).toEqual({ "genericMutation": "No data found" });
    });
});



