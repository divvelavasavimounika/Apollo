var fs = require('fs');
var data = fs.readFileSync('QueryMap.json', 'utf8');
const queryModule = require('./modules/queryModule');
const db = require('./db');
const express = require('express');
const app = express();
const config = require('./config/database')
var result = { menuitems: [] };
var map = new Map();
var input, Queries, inputs, get_Values, get_keys, endpoint, outputs, customerInfo, link;
const Query =
{

    genericResolver: async (_, args) => {
        var keys = new Array();
        var values = new Array();
        input = args.input;
        input = JSON.stringify(input);
        input = input.replace(/["]+/g, '');
        input = input.split(",");
        for (var i = 0; i < input.length; i++) {
            var array = input[i].toString();
            array = array.split(":");
            map.set(array[0], array[1]);

        }

        get_keys = map.keys();
        get_Values = map.values();
        for (var elem of get_keys) {
            keys.push(elem);
        }

        for (var elem of get_Values) {
            values.push(elem);
        }

        var jsondata = JSON.parse(data);
        jsondata.map(element => {
            Queries = element.Queries;

        });
        for (var i = 0; i < values.length; i++) {
            Queries.map(element => {
                if (element.queryName == values[i]) {
                    console.log("element", element.queryName, link);
                    link = element.queryName;
                    details = element.details;
                    details.forEach(element => {
                        inputs = element.InputFieldList;
                        endpoint = element.endpoint;
                        outputs = element.OutputFieldList;

                    });
                }
            });
        }
        inputs = JSON.stringify(inputs);
        inputs = inputs.split(",").toString();
        inputs = inputs.replace(/["]+/g, '');
        inputs = inputs.split(",");

        values = values.splice("1");

        outputs = JSON.stringify(outputs);
        outputs = outputs.split(",").toString();
        outputs = outputs.replace(/["]+/g, '');
        outputs = outputs.split(",");


        if (endpoint == "mongodb://localhost:27017/genericResolver") {
            console.log("Into Database");
            db.connect(endpoint, function (err) {
                if (err) {
                    process.exit(1)
                } else {
                    console.log("Connected to Port");

                }
            });
            try {
                customerInfo = await queryModule.getCustomerDetails(inputs, values);

                if (customerInfo == null) {

                    return "No data found";

                } else {
                    const outputKeys = Object.keys(customerInfo);
                    const outputValues = Object.values(customerInfo);
                    for (var i = 0; i < outputKeys.length && outputValues.length; i++) {
                        outputKeys[i] = outputKeys[i].trim();
                        for (var j = 0; j < outputs.length; j++) {
                            if (outputKeys[i] == outputs[j]) {

                                result.menuitems.push({
                                    [outputs[j]]: outputValues[i]
                                });
                            }
                        }


                    }
                    console.log("Result", result.menuitems);
                    result.menuitems = JSON.stringify(result.menuitems);
                }

            }
            catch (e) {
                console.log(e);
            }

        }
        else {
            db.connect(config.database, function (err) {
                if (err) {
                    process.exit(1)
                } else {
                    console.log("Connected to Port");

                }
            });
            endpoint = endpoint.split(":");
            endpoint = endpoint.splice("2");
            endpoint = endpoint[0];
            app.listen(endpoint);

            app.get('/' + link, async (req, res) => {
                var Sites;
                var keys, keyValues;
                var outputKeys = new Array();
                var outputValues = new Array();
                var outputkeys = new Array();
                var outputvalues = new Array();
                try {


                    customerInfo = await queryModule.findCustomerSites(inputs, values);
                    if (customerInfo == null) {
                        res.send("No data Found");
                    }
                    else {
                        customerInfo.map(ele => {
                            Sites = ele.Sites;
                        });
                        for (let index = 0; index < Sites.length; index++) {
                            const element = Sites[index];
                            keys = Object.keys(element);
                            keyValues = Object.values(element);
                            outputKeys.push(keys);
                            outputValues.push(keyValues);
                        }


                        for (var i = 0; i < outputKeys.length; i++) {
                            for (let j = 0; j < outputKeys[i].length; j++) {
                                const element = outputKeys[i][j];
                                outputkeys.push(element);
                            }
                        }
                        for (var i = 0; i < outputValues.length; i++) {
                            for (let j = 0; j < outputValues[i].length; j++) {
                                const element = outputValues[i][j];
                                outputvalues.push(element);
                            }
                        }


                        for (let i = 0; i < outputkeys.length && outputvalues.length; i++) {
                            outputkeys[i] = outputkeys[i].trim();
                            for (var j = 0; j < outputs.length; j++) {
                                if (outputkeys[i] == outputs[j]) {
                                    result.menuitems.push({
                                        [outputs[j]]: outputvalues[i]
                                    });
                                }
                            }

                        }
                    }
                    res.send(result.menuitems);
                    result.menuitems = JSON.stringify(result.menuitems);

                }
                catch (e) {
                    console.log(e);
                }
            });

        }


        return result.menuitems;

    }
},
    Mutation = {

        genericMutation: async (_, args) => {

            console.log("arguments are", args);
            var keys = new Array();
            var values = new Array();
            input = args.input;
            input = JSON.stringify(input);
            input = input.replace(/["]+/g, '');
            input = input.split(",");

            for (var i = 0; i < input.length; i++) {
                var array = input[i].toString();
                array = array.split(":");
                map.set(array[0], array[1]);

            }

            get_keys = map.keys();
            get_Values = map.values();
            for (var elem of get_keys) {
                keys.push(elem);
            }

            for (var elem of get_Values) {
                values.push(elem);
            }

            var jsondata = JSON.parse(data);
            jsondata.map(element => {
                Queries = element.Queries;

            });

            for (var i = 0; i < values.length; i++) {
                values[i] = values[i].trim();

                Queries.map(element => {

                    if (element.queryName == values[i]) {


                        link = element.queryName;
                        details = element.details;

                        details.forEach(element => {
                            inputs = element.InputFieldList;
                            endpoint = element.endpoint;
                            outputs = element.OutputFieldList;

                        });

                    }
                    else
                    {
                        return "Please enter fields"
                    }

                });
            }

            inputs = JSON.stringify(inputs);
            inputs = inputs.split(",").toString();
            inputs = inputs.replace(/["]+/g, '');
            inputs = inputs.split(",");

            values = values.splice("1");

            outputs = JSON.stringify(outputs);
            outputs = outputs.split(",").toString();
            outputs = outputs.replace(/["]+/g, '');
            outputs = outputs.split(",");

            db.connect(endpoint, function (err) {
                if (err) {
                    process.exit(1)
                } else {
                    console.log("Connected to Port");

                }
            });
            if (link == "AddUser") {

                try {
                    console.log("In try block")
                    customerInfo = await queryModule.postCustomerDetails(inputs, values);
                    console.log("status", customerInfo);

                }
                catch (e) {
                    throw e;
                }

                return customerInfo;
            }
            else if (link == "DeleteUser") {
                try {
                    customerInfo = await queryModule.deleteCustomer(inputs, values);
                    if (customerInfo == null) {
                        return "No data found";
                    } else {
                        return "Deleted successfully";
                    }

                }
                catch (e) {
                    throw e;
                }
            }

            else {
                try {
                    customerInfo = await queryModule.updateCustomer(inputs, values);
                    if (customerInfo == null) {
                        return "No data found";
                    } else {
                        return "Updated successfully";
                    }
                }
                catch (e) {
                    throw e;
                }


            }

        }


    }





module.exports = { Query, Mutation }