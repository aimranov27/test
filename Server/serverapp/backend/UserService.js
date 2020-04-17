/**
*  UserService class - contains logic for retrieving/sending data to the database
*  by Davies Lumumba April 9th 2020
* */
const User = require("./User.js");

//Exports these functions for use in the controller class
module.exports = {
    register,
    update,
    getAll,
    getById,
    delete: _delete
};

/**
 *  Asynchronous function to add a user to the db after initial sign in on the app
 * */
async function register(userParam, res) {
    // Validate username
    if (await User.findOne({username: userParam.username})) {
        throw 'Username "' + userParam.username + '" is already taken';
    }
    const newUser = new User({ // defined in User.js
        username: userParam.username,
        firstName: userParam.firstName,
        lastName: userParam.lastName,
    });
    // Save the new user
    await newUser.save();
}

/**
 *  Asynchronous function to get all the users saved on the db
 * */
async function getAll() {
    return await User.find();
}

/**
 *  Asynchronous function to request for a user by their ID
 * */
async function getById(id) {
    return await User.findById(id);
}

async function update(id, userParam) {
    const user = await User.findById(id);
    // validate
    if (!user) throw 'User not found';
    if (user.username !== userParam.username && await User.findOne({username: userParam.username})) {
        throw 'Username "' + userParam.username + '" is already taken';
    }
    // copy userParam properties to user
    Object.assign(user, userParam);
    await user.save();
}

async function _delete(id) {
    await User.findByIdAndRemove(id);
}