const { RESTDataSource } = require('apollo-datasource-rest');
const fetch = require("node-fetch");
var dataItems = {};
class GenericAPI extends RESTDataSource {
    constructor() {
        super();
    }

    async getData(endpoint, inputs, values) {
        try {
            const info = await this.get(`${endpoint}/${values[0]}`);
    
            return info.data;
        }
        catch (e) {
            return "Data Not Found";
        }



    }
    async invokeAPI(method, endUrl, inputs, values) {

        let endpoint;
      
        if (method == "POST") {
            endpoint = endUrl;
          
        }
        else if (method == "DELETE") {

            endpoint = `${endUrl}/${values[0]}`;
         
        }
        else if (method == "PUT") {
         
            endpoint = `${endUrl}/${values.shift()}`;
         
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
              
                if (result.status === 201 || result.status === 200) {
                    return result.json().then((data) => {
                        let merged = { ...object.body, ...data };
                      
                        return merged;
                    });
                } else if (result.status === 400 || result.status === 404 ||
                    result.status === 405 || result.status === 500) {
                  
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