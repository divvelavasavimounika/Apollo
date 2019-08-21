var db = require('./db.js');
const queryModule = require('./modules/queries')
var response=""; 
const Query = {

    validateUser: async (_, args) => {
        // console.log("User Id:", args.UserId, "Password:", args.Credentials);
        const id = args.UserId;
        let passwordById = await queryModule.findById(id);
        console.log("Id", passwordById);
        if (passwordById == null) {
            console.log("id doesn't exists");
            response="Not a Valid user";

        }
        else {
            console.log("password here", passwordById.Credentials)
            if (args.Credentials == passwordById.Credentials) {
                console.log(id, "is a valid user");
                response="Is is a valid user";
            }
            else {
                console.log(id, "is not a valid user");
                response="Please enter correct passowrd";
            }
        }


        return response;
    }


}

module.exports = { Query }