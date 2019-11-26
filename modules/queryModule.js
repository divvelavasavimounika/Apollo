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

module.exports = { getDetails }


