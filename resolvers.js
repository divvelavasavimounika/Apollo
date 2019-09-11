var fs = require('fs');
const queryModule = require('./modules/queryModule')
var value;
var data;
var menuItems;
var result = { menuitems: [] };
var value = "";
var map = new Map();
const Query = {

    validateUser: async (_, args) => {
        var id = args.UserId;
        var validateResponse = "";
        let passwordById = await queryModule.validateUserById(id);
        if (passwordById == null) {
            validateResponse = "Not a Valid user";

        }
        else {

            if (args.Credentials == passwordById.Credentials) {

                validateResponse = "Is  a valid user";
                if (validateResponse === "Is  a valid user") {
                    id = args.UserId;
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
                                        result.menuitems.push({
                                            type: element.type, link:
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
                                            result.menuitems.push({
                                                type: element.type, dropdownvalues: [
                                                    {
                                                        href: e.href,
                                                        label: e.label,
                                                        id: e.id,
                                                        type: e.type
                                                    }

                                                ]
                                            });


                                        }

                                    });

                                }

                            });

                        }

                        fs.readFile('Resources/' + 'id.properties', { encoding: 'utf-8' }, function (err, res) {
                            if (err)
                                console.log(err);
                            else {

                                var keysArray = new Array;
                                var valuesArray = new Array;
                                var res = res.split("=").toString();
                                var res1 = res.split("\r\n")
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

                                for (var i = 0; i < keysArray.length && valuesArray.length; i++) {
                                    result.menuitems.map(element => {
                                        if (element.type == "single" && element.link.label == keysArray[i]) {

                                            element.link.label = valuesArray[i];

                                        }
                                        else if (element.type == "multiple") {
                                            var dropdownValues = element.dropdownvalues;
                                            dropdownValues.map(ele => {
                                                if (ele.label == keysArray[i])
                                                    ele.label = valuesArray[i]

                                            });

                                        }
                                    });
                                }

                            }

                        });

                    });
                    validateResponse = JSON.stringify(result.menuitems);
                    console.log("Response", validateResponse);

                }





            }
            else {
                validateResponse = "Please enter a correct passowrd";
            }
        }

        return validateResponse;
    }

}

module.exports = { Query }