const { RESTDataSource } = require('apollo-datasource-rest');
const fetch = require("node-fetch");
var dataItems = {};
class GenericAPI extends RESTDataSource {
    constructor() {
        super();
    }

    async getData(endpoint, inputs, values) {
        console.log("In get Data", "Inputs", inputs, "Values", values);
        try {
            const info = await this.get(`${endpoint}/${values[0]}`);
            console.log("Info", info);
            return info.data;
        }
        catch (e) {
            return "Data Not Found";
        }



    }
    async invokeAPI(method, endUrl, inputs, values) {

        let endpoint;
        console.log("endpoint", endUrl);
        if (method == "POST") {
            endpoint = endUrl;
            console.log("In invoke api", "Inputs", inputs, "Values", values);
            console.log("Endpoint", endpoint);
        }
        else if (method == "DELETE") {

            console.log("In invoke api", "Inputs", inputs, "Values", values);
            endpoint = `${endUrl}/${values[0]}`;
            console.log("Endpoint", endpoint);
        }
        else if (method == "PUT") {
            console.log("In invoke api", "Inputs", inputs, "Values", values);
            endpoint = `${endUrl}/${values.shift()}`;
            console.log("Endpoint", endpoint);
        }


        for (var i = 0; i < inputs.length && values.length; i++) {
            dataItems[inputs[i]] = values[i];
        }
        const object = {
            method: method,
            body: dataItems
        };
        return await this.operation(endpoint, object);


    }

    async operation(endpoint, object) {
        return fetch(endpoint, object)
            .then((result) => {
                console.log("API Response Status", result.status);
                if (result.status === 201 || result.status === 200) {
                    return result.json().then((data) => {
                        let merged = { ...object.body, ...data };
                        console.log("Merged", merged);
                        return merged;
                    });
                } else if (result.status === 400 || result.status === 404 ||
                    result.status === 405 || result.status === 500) {
                    return "Data not found";
                } else if (result.status === 204) {
                    return result.status;
                }
            })
            .catch((error) => {
                throw error;

            });
    }
}
module.exports = GenericAPI;