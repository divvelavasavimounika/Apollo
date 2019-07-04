
const express = require('express');
const { ApolloServer, gql } = require('apollo-server-express');
const mongose = require('./config')
const { User } = require('./model')

const typeDefs = gql`
   type User
   {
       id:ID!
       userName:String
       email:String
   }

   type Query
   {
       getUsers:[User]
       getUser(id:String!):User
       
   }
   type Mutation
   {
       addUser(userName:String!,email:String!):User
       deleteUserByUserName(userName:String!):User
       deleteUserById(id:ID!):User
       deleteUserByEmail(email:String!):User
       updateUser(id:ID!,userName:String!):User

   }
`;

const resolvers = {
    Query: {
        getUsers: async () => await User.find({}).exec(),
        getUser: async (_, args) => {
            try {
                let response = await User.findById(args.id);
                return response;
            }
            catch (e) {
                return e.message;
            }
        }
    },
    Mutation:
    {


        addUser: async (_, args) => {
            try {
                let response = await User.create(args);
                return response;
            }
            catch (e) {
                return e.message;
            }
        },
        deleteUserById: async (_, args) => {
            try {
                console.log(args.id);
                let response = await User.findByIdAndDelete(args.id);
                console.log(response);
                return response;
            }
            catch (e) {
                return e.message;
            }
        },
        deleteUserByUserName: async (_, args) => {
            try {
                let response = await User.deleteOne(args);
                return response;
            }
            catch (e) {
                return e.message;
            }
        },
        deleteUserByEmail: async (_, args) => {
            try {
                let response = await User.deleteOne(args);
                return response;
            }
            catch (e) {
                return e.message;
            }
        },
        updateUser: async (_, args) => {
            console.log(args.id, args.userName)
            try {
                let response = await User.findByIdAndUpdate
                    (
                        args.id,
                        { $set: { userName: args.userName } },
                        {
                            new: true
                        }
                    );

                console.log(response)
                return response;
            }
            catch (e) {
                return e.message;
            }
        }
    }
};

const server = new ApolloServer({ typeDefs, resolvers });
const app = express();

server.applyMiddleware({ app });

app.listen({ port: 4000 }, () =>
    console.log(`🚀 Server ready at http://localhost:4000 ${server.graphqlPath}`)
);



