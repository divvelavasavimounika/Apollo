var db = require('../db.js');
const getCustomerDetails = async (inputs, values) => {
    var data;
    try {
        var query;
        for (var i = 0; i < inputs.length && values.length; i++) {
            query = { [inputs[i]]: values[i] };
            data = await db.getCollection('Customer').findOne(query);
        }
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



module.exports = { getCustomerDetails, findCustomerSites }


