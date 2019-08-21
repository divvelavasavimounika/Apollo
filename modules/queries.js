var db = require('../db.js');

const findById = async id => {
    try {
        console.log("id val", id)
        return await db.getCollection('userTable').findOne({ UserId: id });
    } catch (e) {
        throw e
    }
}
module.exports = { findById }
