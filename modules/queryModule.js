var db = require('../db.js');
const getDetails = async (collectionName,inputs, values) => {
    var data;
    try {
        var query;
        for (var i = 0; i < inputs.length && values.length; i++) {
            query = { [inputs[i]]: values[i] };
            data = await db.getCollection(`${collectionName}`).findOne(query);
        }
        return data; 

    } catch (e) {
        throw e
    }

}




module.exports = { getDetails }


