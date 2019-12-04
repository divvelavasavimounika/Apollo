let fs = require('fs');
const bodyParser = require('body-parser');
let querydata = fs.readFileSync('./QueryMap.json', 'utf8');
let mutationdata = fs.readFileSync('./MutationMap.json', 'utf-8');
const queryModule = require('./modules/queryModule');
const mutationModule = require('./modules/mutationModule');
const db = require('./db');
const express = require('express');
const app = express();
const config = require('./config/database');
let result = { menuitems: [] };
let input, Queries, inputs, get_Values, get_keys, endpoint, outputs, info, link, Mutations, collectionName;
let queryFlag = false;
let mutationFlag = false;
let validUser = false;
const method = { type: null };
const Add = "Add", Delete = "Delete", Update = "Update";
let language;
var jsondata = "";
var validateResponse = "";
var responseData;
const Query =
{
    validateUser: async (_, args) => {
        await db.connect(config.database, function (err) {
            if (err) {
                console.log("Error", e);
            }
        });
        console.log("arguments", args);
        let id = args.UserId;
        language = args.user_language_pref;


        let passwordById = await queryModule.validateUserById(id);
        console.log("passwordById", passwordById);
        if (passwordById == null) {
            validateResponse = "Not a Valid user";
        }
        else {

            if (args.Credentials == passwordById.Credentials) {
                console.log("args.Credentials", args.Credentials, "passwordById", passwordById.Credentials);
                validateResponse = "Is  a valid user";
                if (validateResponse === "Is  a valid user") {
                    validUser = true;

                    id = args.UserId;
                    value = args.JsonName;
                    var keysArray = new Array;
                    var valuesArray = new Array;
                    let map = new Map();
                    fs.readFile(`${value}` + '.json', { encoding: 'utf-8' }, function (err, res) {
                        if (err)
                            console.log("Error", err)
                        else {
                            jsondata = JSON.stringify(res);
                            fs.readFile('Resources/' + `${value}` + '_' + `${language}` + '.properties', { encoding: 'utf-8' }, function (err, res) {
                                if (err)
                                    console.log(err);
                                else {

                                    var res = res.split("=").toString();
                                    var res1 = res.split(/\r\n|\n|\r/)
                                    for (var i = 0; i < res1.length; i++) {
                                        var data = res1[i].toString();
                                        var value = data.split(",").toString();
                                        var str = value
                                        array = str.split(",");
                                        map.set(array[0], array[1]);
                                    }

                                    get_keys = map.keys();
                                    get_Values = map.values();
                                    for (var elem of get_keys) {
                                        keysArray.push(elem);

                                    }
                                    for (var elem of get_Values) {
                                        valuesArray.push(elem);
                                    }
                                    for (var i = 0; i < keysArray.length; i++) {
                                        keysArray[i] = keysArray[i].trim()
                                    }

                                    for (i = 0; i < keysArray.length && valuesArray.length; i++) {
                                        jsondata = jsondata.replace(keysArray[i], valuesArray[i]);

                                    }


                                }
                                responseData = (function () {

                                    return jsondata;
                                })();

                            });

                        }
                    });


                }

            }

        }

        return JSON.parse(responseData);

    },


    genericResolver: async (_, args, { dataSources }) => {

        var keys = new Array();
        var values = new Array();
        input = args.input;
        input = JSON.stringify(input);
        input = input.replace(/["]+/g, '');
        input = input.split(",");
        let map = new Map();
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
        if (validUser == true) {
            var jsondata = JSON.parse(querydata);
            jsondata.map(element => {
                Queries = element.Queries;

            });
            for (var i = 0; i < values.length; i++) {
                Queries.map(element => {
                    if (element.queryName == values[i]) {
                        queryFlag = true;
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

            if (queryFlag == false) {

                return "QueryName Not Found";
            }
            else {

                inputs = JSON.stringify(inputs);
                inputs = inputs.split(",").toString();
                inputs = inputs.replace(/["]+/g, '');
                inputs = inputs.split(",");


                outputs = JSON.stringify(outputs);
                outputs = outputs.split(",").toString();
                outputs = outputs.replace(/["]+/g, '');
                outputs = outputs.split(",");

                if (endpoint.includes("mongodb")) {

                    await db.connect(endpoint, function (err) {
                        if (err) {
                            process.exit(1);
                        }
                    });
                    collectionName = values.shift();
                    values = values.splice("1");
             
                    try {
                        if (inputs != null) {
                          
                            info = await queryModule.getDetails(collectionName, inputs, values);
                          
                            if (info == null) {
                                return "No data found";
                            } else {

                                if ((outputs.length > 0) && (outputs.includes('') == false)) {
                                    const outputKeys = Object.keys(info);
                                    const outputValues = Object.values(info);
                                   
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
                                  
                                    result.menuitems = JSON.stringify(result.menuitems);

                                }
                                else {
                                    
                                    return JSON.stringify(info);
                                }
                            }



                        }
                        else {
                            info = await queryModule.getDetails(collectionName, keys, values);
                            console.log(info);
                            if (info == null) {
                                return "No data found";
                            } else {

                                const outputKeys = Object.keys(info);
                                const outputValues = Object.values(info);
                             
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
                        return result.menuitems;
                    }
                    catch (e) {
                        console.log(e);
                    }

                }
                else if (endpoint.includes("http") || endpoint.includes("https")) {
                    values = values.splice("1");
                    connection = endpoint;
                    try {
                        if (inputs != null) {
                            info = await dataSources.GenericAPI.getData(connection, inputs, values);
                            if (info == null) {
                                return "No data found";
                            } else {
                                if ((outputs.length > 0) && (outputs.includes('') == false)) {
                                    const outputKeys = Object.keys(info);
                                    const outputValues = Object.values(info);

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

                                    result.menuitems = JSON.stringify(result.menuitems);
                                }
                                else {

                                    return JSON.stringify(info);
                                }

                            }
                        }
                        else {
                            info = await dataSources.GenericAPI.getData(connection, keys, values);

                            if (info == null) {
                                return "No data found";
                            } else {
                                if ((outputs.length > 0) && (outputs.includes('') == false)) {
                                    const outputKeys = Object.keys(info);
                                    const outputValues = Object.values(info);

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

                                    result.menuitems = JSON.stringify(result.menuitems);
                                }
                                else {

                                    return JSON.stringify(info);
                                }

                            }
                        }
                    }
                    catch (e) {
                        console.log(e);
                    }
                    queryFlag = false;
                    return result.menuitems;
                }
            }

        }
        else {
            return "wrong password"
        }

    }

},
    Mutation = {
        genericMutation: async (_, args, { dataSources }) => {

            var mutationType = args.mutationType;
            let map = new Map();
            var keys = new Array();
            var values = new Array();
            input = args.input;
            input = JSON.stringify(input);
            input = input.replace(/["]+/g, '');
            input = input.split(",");
            get_keys = 0;
            get_Values = 0;

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
            if (validUser == true) {

                var jsondata = JSON.parse(mutationdata);

                jsondata.map(element => {
                    Mutations = element.Mutations;
                });

                Mutations.map(element => {
                    if (`${mutationType}`.includes(element.mutationName)) {
                        mutationFlag = true;
                        link = element.mutationName;

                        details = element.details;
                        details.forEach(element => {
                            inputs = element.InputFieldList;
                            endpoint = element.endpoint;
                            outputs = element.OutputFieldList;
                        });
                    }
                });
                if (mutationFlag == false) {

                    return "Mutation Name Not Found";
                }
                else {
                    inputs = JSON.stringify(inputs);
                    inputs = inputs.split(",").toString();
                    inputs = inputs.replace(/["]+/g, '');
                    inputs = inputs.split(",");

                    outputs = JSON.stringify(outputs);
                    outputs = outputs.split(",").toString();
                    outputs = outputs.replace(/["]+/g, '');
                    outputs = outputs.split(",");
                    if (endpoint.includes("mongodb")) {
                        db.connect(endpoint, function (err) {
                            if (err) {
                                process.exit(1)
                            }
                        });
                        collectionName = values.shift();
                        keys.shift();




                        if (mutationType.includes(Add)) {
                            try {
                                if (inputs != null) {

                                    info = await mutationModule.post(collectionName, inputs, values);
                                    if (info == null) {
                                        return "No data found";
                                    } else {
                                        const outputKeys = Object.keys(info[0]);
                                        const outputValues = Object.values(info[0]);

                                        if ((outputs.length > 0) && (outputs.includes('') == false)) {
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

                                            result.menuitems = JSON.stringify(result.menuitems);
                                            return JSON.stringify(result.menuitems);
                                        }
                                        else {

                                            return JSON.stringify(info);
                                        }

                                    }
                                }
                                else {

                                    info = await mutationModule.post(collectionName, keys, values);
                                    if (info == null) {
                                        return "No data found";
                                    } else {
                                        const outputKeys = Object.keys(info[0]);
                                        const outputValues = Object.values(info[0]);

                                        if ((outputs.length > 0) && (outputs.includes('') == false)) {

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

                                            result.menuitems = JSON.stringify(result.menuitems);
                                            return JSON.stringify(result.menuitems);
                                        }
                                        else {

                                            return JSON.stringify(info);
                                        }

                                    }
                                }

                            }
                            catch (e) {
                                throw e;
                            }
                        }
                        else if (mutationType.includes(Delete)) {
                            try {
                                if (inputs != null) {

                                    info = await mutationModule.remove(collectionName, inputs, values);
                                    console.log("Info", info);
                                    if (info == 0) {
                                        return "Data is not present"
                                    }
                                    else {
                                        return "Data Deleted Succesfully" + " " + inputs + ":" + values;
                                    }
                                }
                                else {

                                    info = await mutationModule.remove(collectionName, keys, values);
                                    console.log("Into else block")
                                    if (info == 0) {
                                        return "Data is not present"
                                    }
                                    else {
                                        return "Data Deleted Succesfully" + " " + inputs + ":" + values;
                                    }
                                }
                            }
                            catch (e) {
                                throw e;
                            }
                        }

                        else if (mutationType.includes(Update)) {

                            try {
                                if (inputs != null) {
                                    console.log("Inputs", inputs, "Values", values);
                                    info = await mutationModule.update(collectionName, inputs, values);
                                    console.log(info);
                                    if (info == null) {
                                        return "No data found";
                                    } else {
                                        const outputKeys = Object.keys(info);
                                        const outputValues = Object.values(info);
                                        console.log("outputKeys", outputKeys, "outputValues", outputValues, "outputs", outputs)
                                        if ((outputs.length > 0) && (outputs.includes('') == false)) {
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
                                            return JSON.stringify(result.menuitems);
                                        }
                                        else {

                                            return JSON.stringify(info);
                                        }

                                    }

                                }
                                else {
                                    info = await mutationModule.update(collectionName, keys, values);
                                    console.log(info);
                                    if (info == null) {
                                        return "No data found";
                                    } else {
                                        const outputKeys = Object.keys(info);
                                        const outputValues = Object.values(info);

                                        if ((outputs.length > 0) && (outputs.includes('') == false)) {
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

                                            result.menuitems = JSON.stringify(result.menuitems);
                                            return JSON.stringify(result.menuitems);
                                        }
                                        else {

                                            return JSON.stringify(info);
                                        }

                                    }
                                }

                            }
                            catch (e) {
                                throw e;
                            }
                        }
                    }
                    else if (endpoint.includes("http") || endpoint.includes("https")) {
                        connection = endpoint;
                        endpoint = endpoint.split(":");
                        endpoint = endpoint.splice("2");
                        endpoint = endpoint[0];
                        app.use(bodyParser.json());
                        app.listen(endpoint);



                        if (mutationType.includes(Add)) {

                            try {
                                method.type = "POST";
                                if (inputs != null) {

                                    info = await dataSources.GenericAPI.invokeAPI(method.type, connection, inputs, values);
                                    if (info == null) {
                                        return "No data found";
                                    } else {
                                        const outputKeys = Object.keys(info);
                                        const outputValues = Object.values(info);

                                        if ((outputs.length > 0) && (outputs.includes('') == false)) {
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
                                            return JSON.stringify(result.menuitems);
                                        }
                                        else {

                                            return JSON.stringify(info);
                                        }

                                    }
                                }
                                else {
                                    info = await dataSources.GenericAPI.invokeAPI(method.type, connection, keys, values);
                                    if (info == null) {
                                        return "No data found";
                                    } else {
                                        const outputKeys = Object.keys(info);
                                        const outputValues = Object.values(info);

                                        if ((outputs.length > 0) && (outputs.includes('') == false)) {
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
                                            return JSON.stringify(result.menuitems);
                                        }
                                        else {
                                            console.log(info);
                                            return JSON.stringify(info);
                                        }

                                    }
                                }


                            }
                            catch (e) {
                                throw e;
                            }
                        }
                        else if (mutationType.includes(Delete)) {
                            try {
                                method.type = "DELETE";
                                if (inputs != null) {
                                    data = await dataSources.GenericAPI.invokeAPI(method.type, connection, inputs, values)

                                    if (data == 0) {
                                        return "Data is not present"
                                    }
                                    else {
                                        return JSON.stringify("Deleted and status is:" + data);
                                    }

                                }
                                else {
                                    data = await dataSources.GenericAPI.invokeAPI(method.type, connection, keys, values)


                                    if (data == 0) {
                                        return "Data is not present";
                                    }
                                    else {
                                        return JSON.stringify("Deleted and status is:" + data);
                                    }

                                }

                            }
                            catch (e) {
                                throw e;
                            }
                        }

                        else if (mutationType.includes(Update)) {
                            try {
                                method.type = "PUT";
                                if (inputs != null) {

                                    data = await dataSources.GenericAPI.invokeAPI(method.type, connection, inputs, values);


                                    if (data == null) {
                                        return "No data found";
                                    } else {

                                        const outputKeys = Object.keys(data);
                                        const outputValues = Object.values(data);

                                        if ((outputs.length > 0) && (outputs.includes('') == false)) {
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
                                            return JSON.stringify(result.menuitems);
                                        }
                                        else {
                                            return JSON.stringify(data);
                                        }

                                    }
                                }
                                else {
                                    data = await dataSources.GenericAPI.invokeAPI(method.type, connection, keys, values);

                                    if (data == null) {
                                        return "No data found";
                                    } else {
                                        const outputKeys = Object.keys(data);
                                        const outputValues = Object.values(data);

                                        if ((outputs.length > 0) && (outputs.includes('') == false)) {
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
                                            return JSON.stringify(result.menuitems);
                                        }
                                        else {

                                            return JSON.stringify(info);
                                        }

                                    }
                                }

                            }
                            catch (e) {
                                throw e;
                            }
                        }

                    }
                }
                mutationFlag = false;
            }
            else {
                return "wrong Password"
            }

        }


    }


module.exports = { Query, Mutation }