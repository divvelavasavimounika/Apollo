const { RESTDataSource } = require('apollo-datasource-rest');
const fetch = require("node-fetch");
class GenericAPI extends RESTDataSource {
    constructor() {
        super();
        //process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
    }
    async getData(endpoint, inputs, values) {
        process.env.NODE_TLS_REJECT_UNAUTHORIZED = "0";
        console.log("In get Data", "Inputs", inputs, "Values", values);
        const info = await this.get(`${endpoint}/${values[0]}`);
        console.log("Info", info.data);
        return info.data;

    }
    async invokeAPI(method, endUrl, inputs, values) {

        let endpoint;
        if (method == "POST") {
            endpoint = endUrl;
            console.log("Endpoint", endpoint);
        }
        else if (method == "DELETE") {
            endpoint = `${endUrl}/${values[0]}`;
        }
        else if (method == "PUT") {
            endpoint = `${endUrl}/${values.shift()}`;
        }
        var dataItems = {};
        try {
            for (var i = 0; i < inputs.length && values.length; i++) {
                dataItems[inputs[i]] = values[i];
            }
            const object = {
                method: method,
                body: dataItems
            };
            return await this.operation(endpoint, object);
        }
        catch (e) {
            console.log(e);
        }
    }

    async operation(endpoint, object) {
        return fetch(endpoint, object)
            .then((result) => {
                console.log("API Response Status", result.status);
                if (result.status === 201 || result.status === 200) {
                    // console.log("Response Headers", result.headers);
                    return result.json().then((data) => {
                        let merged = { ...object.body, ...data };
                        console.log("Merged", merged);
                        return merged;
                    });
                } else if (result.status === 400 || result.status === 404 ||
                    result.status === 405 || result.status === 500) {
                    //  console.log("Resonse Headers", result.headers);
                    return result.json().then((data) => {
                        // console.log("Error Response Data", data);
                        let errorObject = new ApolloError({
                            message: 'Internal server error',
                            errorDetails: {
                                errorCode: data.failures[0].code,
                                errorReason: data.message,
                                errorDesc: data.name,
                                errorSeverity: data.severity,
                                errorCategory: data.category
                            }
                        });
                        return errorObject;
                    });
                } else if (result.status === 204) {
                    return result.status;
                }
            })
            .catch((error) => {
                console.log('Error at ESB API call:: ', error);
            });
    }
}
module.exports = GenericAPI;