var fs = require('fs');
var value;
var jsonParsed;
var language;
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
               else 
               {
                  console.log("In file mode")
                  var keysArray = new Array;
                  var valuesArray = new Array;
                  var jsonKeys = new Array;
                  var listKeys = new Array;
                  var jsonValues = new Array;
                  var listValues = new Array;
                  var jsonMap = new Array();


                  //For Json Keys
                  for (var i = 0; i < jsonParsed.length; i++) {
                     keys = Object.keys(jsonParsed[i]);
                     jsonKeys.push(keys);
                  }

                  for (var i = 0; i < jsonKeys.length; i++) {
                     for (var j = 0; j < keys.length; j++) {
                        listKeys.push(jsonKeys[i][j]);
                     }
                  }
                 

                  //For Json values
                  for (var i = 0; i < jsonParsed.length; i++) {
                     values = Object.values(jsonParsed[i]);
                     jsonValues.push(values);
                  }

                  for (var i = 0; i < jsonValues.length; i++) {
                     for (var j = 0; j < values.length; j++) {
                        listValues.push(jsonValues[i][j]);
                     }
                  }

                  for (var value in listValues) {
                     listValues[value] = listValues[value].trim();
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


                  for (var i = 0; i < listValues.length; i++) {
                     for (var j = 0; j < keysArray.length && valuesArray.length; j++) {
                        if (listValues[i] == keysArray[j]) {
                           listValues[i] = valuesArray[j];
                        }
                     }

                  }
               
                  for (var i = 0; i < listKeys.length && listValues.length; i++) {
                     jsonMap.push(listKeys[i],listValues[i]);  
                  }
                
                  jsonParsed = JSON.stringify(jsonMap);
                  console.log(jsonParsed);

               }

            })


         }

      });
      return jsonParsed;
   }

}
module.exports = { Query }

