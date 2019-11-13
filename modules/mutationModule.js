var db = require('../db.js');

const post = async (collectionName, keys, values) => {
    var info = new Map(), data;
    try {
        for (var i = 0; i < keys.length && values.length; i++) {
            info.set(keys[i], values[i]);
        }
        data = await db.getCollection(`${collectionName}`).insertOne(info);
        if (data != null) {
            data = db.getCollection(`${collectionName}`).find({}).sort({ _id: -1 }).limit(1).toArray();
            console.log("data", data);
        }
        return data;
    }
    catch (e) {
        throw e;
    }

}
const remove = async (collectionName, keys, values) => {
    var query, data;
    try {
        for (var i = 0; i < keys.length && values.length; i++) {
            query = { [keys[i]]: values[i] };
        }
        data = await db.getCollection(`${collectionName}`).deleteOne(query);
        //console.log('Data',data);
        return data['deletedCount'];
    } catch (e) {
        throw e;
    }
}

const update = async (collectionName, keys, values) => {
    var query, data, info = new Map();
    try {
        for (var i = 0; i < keys.length && values.length; i++) {
            query = { [keys[0]]: values[0] };
        }

        keys.shift();
        values.shift();

        for (var i = 0; i < keys.length && values.length; i++) {
            info.set(keys[i], values[i]);
        }

        data = await db.getCollection(`${collectionName}`).updateOne(query, { $set: info });
        if (data != null) {
            data = db.getCollection(`${collectionName}`).findOne(query);
        }
        return data;
    } catch (e) {
        throw e;
    }
}
module.exports = { post, remove, update }