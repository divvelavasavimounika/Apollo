const { User } = require('./model');

const Query = {
    getUsers:  () =>  User.find({}).exec(),
    getUser:  (_, args) => {
        try {
            let response =  User.findById(args.id);
            return response;
        }
        catch (e) {
            return e.message;
        }
    }
},
    Mutation =
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

                let response = await User.findByIdAndDelete(args.id);

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

            try {
                let response = await User.findByIdAndUpdate
                    (
                        args.id,
                        { $set: { userName: args.userName } },
                        {
                            new: true
                        }
                    );

                return response;
            }
            catch (e) {
                return e.message;
            }
        }
    }

module.exports = { Query, Mutation }