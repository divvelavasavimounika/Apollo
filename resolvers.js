var fs = require('fs');
var value;
var jsonParsed;
var language;

var array = new Array;
var map = new Map();
var get_keys;
var get_Values;



const Query =
{

   jsonData: async (root, args) => {
      value = args.name;
      language = args.user_language_pref;
      fs.readFile('data/' + `${value}` + '.json', { encoding: 'utf-8' }, function (err, res) {
         if (err)
            console.log("In err", err);
         else {
            jsonParsed = JSON.parse(res)
            fs.readFile('Resources/' + `${value}` + '_' + `${language}` + '.properties', { encoding: 'utf-8' }, function (err, res) {
               if (err)
                  console.log(err);
               else {
                  var keysArray = new Array;
                  var jsonValues = new Array;
                  var list = new Array;
                  var valuesArray = new Array;

                  for (var i = 0; i < jsonParsed.length; i++) {
                     values = Object.values(jsonParsed[i]);
                     jsonValues.push(values);
                  }

                  for (var i = 0; i < jsonValues.length; i++) {
                     for (var j = 0; j < values.length; j++) {
                        list.push(jsonValues[i][j]);
                     }
                  }

                  for (var key in list) {
                     list[key] = list[key].trim();
                  }


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


                  for (var i = 0; i < list.length; i++) {

                     for (var j = 0; j < keysArray.length && valuesArray.length; j++) {

                        if (list[i] == keysArray[j]) {
                           console.log("Matched", "List values", list[i], "Keys values", keysArray[j]);
                           console.log("Values ", valuesArray[j]);
                           list[i] = valuesArray[j]



                        }
                     }

                  }
                  console.log("Finally List", list);

               }

               jsonParsed = JSON.stringify(list);
            })


         }

      });
      return jsonParsed;
   }

}
module.exports = { Query }

