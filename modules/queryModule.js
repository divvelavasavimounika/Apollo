var db = require('../db.js');
const getCustomerDetails = async (inputs, values) => {
    var data;
    try {
        var query;
        for (var i = 0; i < inputs.length && values.length; i++) {
            query = { [inputs[i]]: values[i] };
            data = await db.getCollection('Customer').findOne(query);
        }
        console.log("Data", data);
        return data;

    } catch (e) {
        throw e
    }

}
const findCustomerSites = async (inputs, values) => {
    var data;
    try {
        for (var i = 0; i < inputs.length && values.length; i++) {
            data = await db.getCollection('Customer').aggregate([{ $unwind: '$Sites' }, { $match: { [inputs[i]]: values[i] } }, { $group: { '_id': null, 'Sites': { $push: '$Sites' } } }]).toArray();
        }

        return data;
    }
    catch (e) {
        console.log(e);
    }

}
const postCustomerDetails = async (keys, values) => {
    var customer = new Map(), data;
    console.log("In query Module keys", keys, "values", values);
    try {
        for (var i = 0; i < keys.length && values.length; i++) {
            customer.set(keys[i], values[i]);
        }
        data = await db.getCollection('Customer').insertOne(customer);
        return "Inserted customer successfully";
    }
    catch (e) {
        throw e;
    }

}
const deleteCustomer = async (keys, values) => {
    var query, data;
    console.log("keys",keys,values);
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

const updateCustomer = async (keys, values) => {
    var query, data, customer = new Map();
    try {
        console.log("keys",keys,values);
        for (var i = 0; i < keys.length && values.length; i++) {
            query = { [keys[0]]: values[0] };
        }
        keys.shift();
        values.shift();
        console.log("keys",keys,values);
        for (var i = 0; i < keys.length && values.length; i++) {
            customer.set(keys[i], values[i]);
        }
        data = await db.getCollection('Customer').updateOne(query, { $set: customer });
        return data;
    } catch (e) {
        throw e;
    }
}

module.exports = { getCustomerDetails, findCustomerSites, postCustomerDetails, deleteCustomer, updateCustomer }


