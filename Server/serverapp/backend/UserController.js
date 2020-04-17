/**
 *  User Controller class - handles express routes relating to users
 *   by Davies Lumumba April 9th 2020
 * */
const express = require("express");
const UserService = require("./UserService");
const router = express.Router();

// routes
router.post('/register', register);
router.post('/update', update);
router.get('/:id', getById);
router.get('/', getAll);
router.delete('/:id', _delete);

module.exports = router;

function register(req, res, next) {
    UserService.register(req.body, res)
        .then((err) => {
            if (err) {
                res.json({'status': err});
            } else {
                res.json({'status': 'success'});
            }
        })
        .catch(err => next(err));
}

function getAll(req, res) {
    UserService.getAll(res)
        .then(users => {if (users.length === 0) {
            res.json({'status': 'no users'});
        } else {
            res.json({'users': users});
        }})
        .catch(err => res.json({'status': err}));
}

function getById(req, res, next) {
    UserService.getById(req.params.id)
        .then(user => user ? res.json(user) : res.sendStatus(404))
        .catch(err => next(err));
}

function _delete(req, res, next) {
    UserService.delete(req.params.id)
        .then(() => res.json({}))
        .catch(err => next(err));
}

function update(req, res, next) {
    UserService.update(req.params.id, req.body)
        .then(() => res.json({}))
        .catch(err => next(err));
}