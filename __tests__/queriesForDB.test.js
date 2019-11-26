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
    
    it('allows Queries', async () => {

        const query = `query myquery($inputString:String!)
        {
          genericResolver(input:$inputString)
        }`;
        const client = createTestClient(myTestServer);
        const res = await client.query({
            query, variables: {
                "inputString": "collectionName:Customer,queryName:getCustomerDetails,CustomerName:yashika,Location:Ameenpur"
            }
        });
        expect(res.data).toEqual({

            "genericResolver": "{\"_id\":\"5dcd04066bccae7bcfdb7fae\",\"CustomerName\":\"yashika\",\"Location\":\"Ameenpur\",\"District\":\"RangaReddy\",\"State\":\"Andhra Pradesh\"}"

        });
    });

    it('if query name is not found in db', async () => {
        const query = `query myquery($inputString:String!)
        {
          genericResolver(input:$inputString)
        }`;
        const client = createTestClient(myTestServer);
        const res = await client.query({
            query, variables: {
                "inputString": "collectionName:Customer"
            }
        });
        expect(res.data).toEqual({
            "genericResolver": "[]"
        });
    });

    it('if empty string', async () => {
        const query = `query myquery($inputString:String!)
        {
          genericResolver(input:$inputString)
        }`;
        const client = createTestClient(myTestServer);
        const res = await client.query({
            query, variables: {
                "inputString": ""
            }
        });
        expect(res.data).toEqual(
            {
                "genericResolver":"No data found"
            });
    })

    it('should return if data not found in  db', async () => {
        const query = `query myquery($inputString:String!)
        {
          genericResolver(input:$inputString)
        }`;
        const client = createTestClient(myTestServer);

        const res = await client.query({
            query, variables: {
                "inputString": "collectionName:user,queryName:getCustomerDetails,CustomerName:Mounika"
            }
        });
        expect(res.data).toEqual({ "genericResolver":"No data found" });
    });




    db.close();
});


