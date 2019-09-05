var db = require('../db.js');

const validateUserById = async id => {
    try {
        // console.log("id val", id)
        return await db.getCollection('userTable').findOne({ UserId: id });

    } catch (e) {
        throw e
    }
}
const authorizeUserById = async id => {
    try {
        // console.log("id val", id)
        const data = await db.getCollection('user_Authorization').aggregate([{ $match: { 'UserId': id } }, { $group: { '_id': null, 'data': { $push: '$authorisationData' } } }]).toArray();
        //console.log("Data",data);
        return data;

    } catch (e) {
        throw e
    }
}

module.exports = { validateUserById, authorizeUserById }
