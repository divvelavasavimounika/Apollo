const MongoClient = require('mongodb').MongoClient


const state = {
    db: null,
}
exports.connect = (url, done) => {
    if (state.db) return done()
    MongoClient.connect(url, { useNewUrlParser: true }, { useUnifiedTopology: true },
        (err, client) => {
            if (err) { console.log(err); return done(err) }
            else {
                console.log("Data base connected");
                state.db = client.db('genericResolver')
                done()
            }

        });
}

exports.getCollection = (collectionName) => {
    return state.db.collection(collectionName)
};

exports.getDB = () => state.db

console.log("Database ", state.db);
exports.close = (done) => {
    if (state.db) {
        state.db.close((err, result) => {
            state.db = null
            state.mode = null
            done(err)
        })
    }
}