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
let map = new Map();
let input, Queries, inputs, get_Values, get_keys, endpoint, outputs, info, link, Mutations, collectionName;
let queryFlag = false;
let mutationFlag = false;
const method =
{
    type: null
}
const Add = "Add", Delete = "Delete", Update = "Update";
const Query =
{
    genericResolver: async (_, args, { dataSources }) => {

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
        console.log("Keys", keys, "values", values);
        var jsondata = JSON.parse(querydata);
        jsondata.map(element => {
            Queries = element.Queries;

        });
        for (var i = 0; i < values.length; i++) {
            Queries.map(element => {
                if (element.queryName == values[i]) {
                    queryFlag = true;
                    link = element.queryName;
                    console.log("element", element.queryName, link);
                    details = element.details;
                    details.forEach(element => {
                        inputs = element.InputFieldList;
                        endpoint = element.endpoint;
                        outputs = element.OutputFieldList;

                    });
                }

            });
        }
        console.log("queryFlag", queryFlag);
        if (queryFlag == false) {
            console.log("QueryName Not found");
            return "QueryName Not Found";
            // process.exit(1);
        }
        else {
            console.log("QueryName found");
            inputs = JSON.stringify(inputs);
            inputs = inputs.split(",").toString();
            inputs = inputs.replace(/["]+/g, '');
            inputs = inputs.split(",");

            outputs = JSON.stringify(outputs);
            outputs = outputs.split(",").toString();
            outputs = outputs.replace(/["]+/g, '');
            outputs = outputs.split(",");

            if (endpoint.includes("mongodb")) {
                console.log("Into Database");
                db.connect(endpoint, function (err) {
                    if (err) {
                        process.exit(1)
                    } else {
                        console.log("Connected to Port");
                    }
                });
                collectionName = values.shift();
                values = values.splice("1");
                try {
                    if (inputs != null) {
                        console.log("Into Inputs block");
                        info = await queryModule.getDetails(collectionName, inputs, values);
                        console.log(info);
                        if (info == null) {
                            return "No data found";
                        } else {
                            if ((outputs.length > 0) && (outputs.includes('') == false)) {
                                const outputKeys = Object.keys(info);
                                const outputValues = Object.values(info);
                                console.log("outputKeys", outputKeys, "outputValues", outputValues, "outputs", outputs)
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
                            console.log("outputKeys", outputKeys, "outputValues", outputValues, "outputs", outputs)
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
                endpoint = endpoint.split(":");
                endpoint = endpoint.splice("2");
                endpoint = endpoint[0];
                app.listen(endpoint);
                data = await dataSources.GenericAPI.getData(connection, inputs, values);
                const outputKeys = Object.keys(data);
                const outputValues = Object.values(data);
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
            queryFlag = false;
            return result.menuitems;
        }

    }

},
    Mutation = {
        genericMutation: async (_, args, { dataSources }) => {
            console.log("arguments are", args);
            var mutationType = args.mutationType;
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
            var jsondata = JSON.parse(mutationdata);

            jsondata.map(element => {
                Mutations = element.Mutations;
            });
            console.log("mutationType", mutationType)
            Mutations.map(element => {
                if (`${mutationType}`.includes(element.mutationName)) {
                    mutationFlag = true;
                    link = element.mutationName;
                    console.log("mutationType:", mutationType, "and mutationName:", link);
                    details = element.details;
                    details.forEach(element => {
                        inputs = element.InputFieldList;
                        endpoint = element.endpoint;
                        outputs = element.OutputFieldList;
                    });
                }
            });
            if (mutationFlag == false) {
                console.log("Mutation Name Not Found");
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
                        } else {
                            console.log("Connected to Port");
                        }
                    });
                    collectionName = values.shift();
                    keys.shift();
                    console.log(keys);

                    if (mutationType.includes(Add)) {
                        try {
                            if (inputs != null) {
                                console.log("Inputs", inputs, "Values", values);
                                info = await mutationModule.post(collectionName, inputs, values);
                                if (info == null) {
                                    return "No data found";
                                } else {
                                    const outputKeys = Object.keys(info[0]);
                                    const outputValues = Object.values(info[0]);
                                    console.log("outputKeys", outputKeys, "outputValues", outputValues, "outputs", outputs);
                                    console.log("Outputs length", outputs.length);

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
                                console.log("Into else block")
                                info = await mutationModule.post(collectionName, keys, values);
                                if (info == null) {
                                    return "No data found";
                                } else {
                                    const outputKeys = Object.keys(info[0]);
                                    const outputValues = Object.values(info[0]);
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

                        }
                        catch (e) {
                            throw e;
                        }
                    }
                    else if (mutationType.includes(Delete)) {
                        try {
                            if (inputs != null) {
                                console.log("Inputs", inputs, "Values", values)
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
                                console.log("Keys", keys, "Values", values)
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
                    console.log(endpoint);
                    endpoint = endpoint[0];
                    app.use(bodyParser.json());
                    app.listen(endpoint);
                    if (mutationType.includes(Add)) {
                        console.log(mutationType)
                        try {
                            method.type = "POST";
                            if (inputs != null) {
                                data = await dataSources.GenericAPI.invokeAPI(method.type, connection, inputs, values);
                                console.log("Mutations Result:", data);
                                if (data == null) {
                                    return "No data found";
                                } else {
                                    const outputKeys = Object.keys(data);
                                    const outputValues = Object.values(data);
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
                                data = await dataSources.GenericAPI.invokeAPI(method.type, connection, keys, values);
                                if (data == null) {
                                    return "No data found";
                                } else {
                                    const outputKeys = Object.keys(data);
                                    const outputValues = Object.values(data);
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
                                console.log("Deleted");
                                if (data == 0) {
                                    return "Data is not present"
                                }
                                else {
                                    return JSON.stringify("Deleted and status is:" + data);
                                }

                            }
                            else {
                                data = await dataSources.GenericAPI.invokeAPI(method.type, connection, keys, values)
                                console.log("Deleted");
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
                                console.log("Data", data);
                                if (data == null) {
                                    return "No data found";
                                } else {
                                    const outputKeys = Object.keys(data);
                                    const outputValues = Object.values(data);
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
                                data = await dataSources.GenericAPI.invokeAPI(method.type, connection, keys, values);
                                console.log("Data", data);
                                if (data == null) {
                                    return "No data found";
                                } else {
                                    const outputKeys = Object.keys(data);
                                    const outputValues = Object.values(data);
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

                        }
                        catch (e) {
                            throw e;
                        }
                    }

                }
            }

            mutationFlag = false;
        }


    }


module.exports = { Query, Mutation }