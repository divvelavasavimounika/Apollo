const { Query, Mutation } = require('./resolvers');
const User = require('./model');
describe('Fetching', () => {
    test('Getting List of users', async () => {
        getUsers = Query.getUsers;
        getUsers =
            {
                getUsers: () => new Promise((resolve, reject) => {
                    getUsers.find({}, (err, getUsers) => {
                        if (err) reject(err);
                        else resolve(getUsers);
                    })
                })
            };




    });
    test('Fetching user by id', async () => {
        getUser = Query.getUser;
        getUser =
            {
                getUser: (root, {
                    _id,
                }) => new Promise((resolve, reject) => {
                    getUser.findById(_id, (err, getUser) => {
                        if (err) reject(err);
                        else resolve(getUser);
                    });
                })
            }

    });
    test('Adding User', async () => {
        addUser = Mutation.addUser;
        addUser =
            {
                addUser: () => new Promise((resolve, reject) => {
                    addUser.create(User, (err, addUser) => {
                        if (err) reject(err);
                        else resolve(addUser);
                    });
                })
            }
    });
    test('Delete User by id', async () => {
        deleteUser = Mutation.deleteUserById;
        deleteUser =
            {
                deleteUser: (root,
                    {
                        _id,
                    }) => new Promise((resolve, reject) => {
                        deleteUser.findByIdAndDelete(_id, (err, deleteUser) => {
                            if (err) reject(err);
                            else resolve(deleteUser);
                        })
                    })
            }
    });
    test('Update User By UserName',async()=>
    {
        updateUser=Mutation.updateUser;
        updateUser=
        {
            updateUser:(root,
                {
                    _id,
                })=>new Promise((resolve,reject)=>
                {
                    updateUser.findByIdAndUpdate(_id,(err,updateUser)=>
                    {
                        if (err) reject(err);
                            else resolve(updateUser);
                    })
                })
        }
    })


})
