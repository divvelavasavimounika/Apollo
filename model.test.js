
const userModel = require('./model');

describe("Checking user schema", () => {
    const user = userModel.User;
    const schema = user.schema;
    const type = schema.obj;
    const userName = type.userName;
    const email = type.email;



    test('UserName is expected to be String', () => {
        expect(userName).toBe(String);
    });

    test('email is expected to be String', () => {
        expect(email).toBe(String);
    });

})
