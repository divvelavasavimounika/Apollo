const mongoose = require('mongoose');
describe('DataBase Connection', () => {
  beforeAll((done) => {
    mongoose.connect('mongodb://127.0.0.1:27017/graphqldb');

    let db = mongoose.connection;

    db.on('error', console.error.bind(console, 'connection error:'));

    db.once('open', () => {
      done();
    });
  });

  it('proves that one equals one', () => {
    expect(true).toBe(true);
  }),
  it('proves false one not equals zero',()=>
  {
    expect(0).toBe(0);
  })
  
});
