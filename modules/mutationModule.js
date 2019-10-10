var db = require('../db.js');

const post = async (keys, values) => {
    var customer = new Map(), data;
    try {
        for (var i = 0; i < keys.length && values.length; i++) {
            customer.set(keys[i], values[i]);
        }
        data = await db.getCollection('Customer').insertOne(customer);
        if (data != null) {
            data = db.getCollection('Customer').find({}).sort({ _id: -1 }).limit(1).toArray();
            console.log("data", data);
        }
        return data;
    }
    catch (e) {
        throw e;
    }

}
const remove = async (keys, values) => {
    var query, data;
    try {
        for (var i = 0; i < keys.length && values.length; i++) {
            query = { [keys[i]]: values[i] };
        }
        data = await db.getCollection('Customer').deleteOne(query);

        return data;
    } catch (e) {
        throw e;
    }
}

const update = async (keys, values) => {
    var query, data, customer = new Map();
    try {
        for (var i = 0; i < keys.length && values.length; i++) {
            query = { [keys[0]]: values[0] };
        }
        keys.shift();
        values.shift();
        for (var i = 0; i < keys.length && values.length; i++) {
            customer.set(keys[i], values[i]);
        }
        data = await db.getCollection('Customer').updateOne(query, { $set: customer });
        if (data != null) {
            data = db.getCollection('Customer').findOne(query);
        }
        return data;
    } catch (e) {
        throw e;
    }
}
module.exports = { post, remove, update }