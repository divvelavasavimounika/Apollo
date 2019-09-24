var db = require('../db.js');

const endpoint = async (inputs, values) => {
    var data;
    try {
        var query;
        console.log("Inputs--->", inputs, "Values--->", values);
        for (var i = 0; i < inputs.length && values.length; i++) {
            console.log("Inputs<----------", inputs[i], "Values<-------------", values[i]);
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
        console.log("Length",inputs.length,values.length);
        for (var i = 0; i < inputs.length && values.length; i++) {
            data = await db.getCollection('Customer').aggregate([{ $unwind: '$Sites' }, { $match: { [inputs[i]]: values[i] } }, { $group: { '_id': null, 'Sites': { $push: '$Sites' } } }]).toArray();
        }

        //console.log("Data", data);
        return data;
    }
    catch (e) {
        console.log(e);
    }

}


module.exports = { endpoint, findCustomerSites }


