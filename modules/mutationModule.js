var db = require('../db.js');

const post = async (keys, values) => {
    var customer = new Map(), data;
    console.log("In query Module keys", keys, "values", values);
    try {
        for (var i = 0; i < keys.length && values.length; i++) {
            customer.set(keys[i], values[i]);
        }
        data = await db.getCollection('Customer').insertOne(customer);
        return data;
    }
    catch (e) {
        throw e;
    }

}
const remove = async (keys, values) => {
    var query, data;
    console.log("keys", keys, values);
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
        console.log("keys", keys, values);
        for (var i = 0; i < keys.length && values.length; i++) {
            query = { [keys[0]]: values[0] };
        }
        keys.shift();
        values.shift();
        console.log("keys", keys, values);
        for (var i = 0; i < keys.length && values.length; i++) {
            customer.set(keys[i], values[i]);
        }
        data = await db.getCollection('Customer').updateOne(query, { $set: customer });
        return data;
    } catch (e) {
        throw e;
    }
}
module.exports = { post, remove, update }