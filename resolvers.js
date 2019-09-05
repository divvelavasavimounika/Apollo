var db = require('./db.js');
var fs = require('fs');
const queryModule = require('./modules/queryModule')
var validateResponse = "";
var authorizeResponse = "";
var value;
var data;
var menuItems;
var result = { menuitems: [] };
var value = "";

const Query = {

    validateUser: async (_, args) => {
        const id = args.UserId;
        let passwordById = await queryModule.validateUserById(id);
        if (passwordById == null) {
            validateResponse = "Not a Valid user";

        }
        else {

            if (args.Credentials == passwordById.Credentials) {

                validateResponse = "Is  a valid user";
            }
            else {
                validateResponse = "Please enter a correct passowrd";
            }
        }

        return validateResponse;
    },
    authorizeUser: async (_, args) => {
        if (validateResponse === "Is  a valid user") {
            var id = args.UserId;
            value = args.JsonName;
     
            let authoriseById = await queryModule.authorizeUserById(id);
            authoriseById.forEach(element => {
                data = element.data;
            });
            data = JSON.stringify(data)
            var res = data.split("|").toString();
            var rs = res.split(",").toString();
            var r = rs.replace(/[[\]]/g, '');
            r = r.replace(/["]+/g, '');
            var array = r.split(",");
            fs.readFile(`${value}` + '.json', { encoding: 'utf-8' }, function (err, res) {
                if (err)
                    console.log("Error", err)
                else {
                    var jsondata = JSON.parse(res);
                }
                jsondata.map(element => {
                    menuItems = element.menuItems;
                })
                for (var i = 0; i < array.length; i++) {
                    menuItems.map(element => {
                        if (element.type == "single") {
                            if (array[i] == element.link.id) {
                                result.menuitems.push({ type: element.type });
                                result.menuitems.push({
                                    link:
                                    {
                                        id: element.link.id,
                                        href: element.link.href,
                                        onClick: element.link.onClick,
                                        label: element.link.label,
                                        type: element.link.type
                                    }

                                });

                            }
                        }
                        else if (element.type == "multiple") {
                            var dropdownValues = element.dropdown.dropdownvalues;
                            dropdownValues.map(e => {
                                if (array[i] == e.id) {
                                    result.menuitems.push({ type: element.type });
                                    result.menuitems.push({
                                        href: e.href,
                                        label: e.label,
                                        id: e.id,
                                        type: e.type

                                    });

                                }

                            });

                        }

                    });

                }
              
                authorizeResponse = JSON.stringify(result.menuitems);
           

            });


        }

        else {
            authorizeResponse = "Please Validate User First"
        }
  
        return authorizeResponse;
     
    }


}

module.exports = { Query }