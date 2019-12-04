var db = require('../db.js');
const getDetails = (collectionName, inputs, values) => {
    var data;
    try {
        var query;
        for (var i = 0; i < inputs.length && values.length; i++) {
            query = { [inputs[i]]: values[i] };
        }
        data = db.getCollection(`${collectionName}`).findOne(query);

        return data;
    } catch (e) {
        return "No data found";
    }

}
const validateUserById =async id => {
    try {

        var data;
    
          data =  await db.getCollection('userTable').findOne({ UserId: id });

        return data
            ;
    } catch (e) {
        throw e
    }
}

module.exports = { getDetails, validateUserById }


