/**
 * User model class
 *  by Davies Lumumba April 9th 2020
 * */
const mongoose = require("mongoose");
const Schema = mongoose.Schema;

// This opens a connection to the online database
const connectionString = 'mongodb+srv://cluster0-nalim.mongodb.net/test?retryWrites=true&w=majority';

// Make sure to whitelist your IP addresses to allow connection.
// You can also create you own passwords to the DB
mongoose.connect(connectionString,
    {user: 'Davies', pass: 'Davy@2018', useNewUrlParser: true , useUnifiedTopology: true});

// Template of object to hold User in DB
const schema = new Schema({
    username: { type: String, unique: true, required: true },
    firstName: { type: String, required: true },
    lastName: { type: String, required: true },
    createdDate: { type: Date, default: Date.now }
});

let User  = mongoose.model("User", schema);

//Export this as “User” class
module.exports = User;