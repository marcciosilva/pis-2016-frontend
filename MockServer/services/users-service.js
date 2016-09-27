// GET
var user_list = require('../replies/user_list.json');
var auth_success = require('../replies/auth_success.json');
var auth_user_fail = require('../replies/auth_user_fail.json');
var auth_pass_fail = require('../replies/auth_pass_fail.json');

var getUsers = function (request, response, next) {
    response.send(JSON.stringify(user_list), 200);
};

// POST

// Devuelve una response exitosa siempre
var postUserSuccess = function(request, response, next) {
	response.send(JSON.stringify(auth_success));
};

// Devuelve una response fallida siempre
var postUserIdFail = function(request, response, next) {
	response.send(JSON.stringify(auth_user_fail));
};

// Devuelve una response fallida siempre
var postUserPassFail = function(request, response, next) {
	response.send(JSON.stringify(auth_pass_fail));
};

exports.getUsers = getUsers;
exports.postUserSuccess = postUserSuccess;
exports.postUserIdFail = postUserIdFail;
exports.postUserPassFail = postUserPassFail;
