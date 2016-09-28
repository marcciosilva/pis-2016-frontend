// GET
var user_list = require('../replies/user_list.json');
// auth
var auth_success = require('../replies/auth_success.json');
var auth_user_fail = require('../replies/auth_user_fail.json');
var auth_pass_fail = require('../replies/auth_pass_fail.json');
// getRoles
var getroles_both = require('../replies/getroles_both.json');
var getroles_zonas = require('../replies/getroles_zonas.json');
var getroles_recursos = require('../replies/getroles_recursos.json');
var getroles_fail = require('../replies/getroles_fail.json');
// login
var login_success = require('../replies/login_success.json');
var login_no_auth = require('../replies/login_no_auth.json');

var getUsers = function (request, response, next) {
	response.setHeader("content-type","application/json");
    response.send(JSON.stringify(user_list), 200);
};

// POST

// Devuelve una response exitosa siempre
var postUserSuccess = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(auth_success));
};

// Devuelve una response fallida siempre
var postUserIdFail = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(auth_user_fail));
};

// Devuelve una response fallida siempre
var postUserPassFail = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(auth_pass_fail));
};

var postUserGetRolesBoth = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(getroles_both));
};

var postUserGetRolesZonas = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(getroles_zonas));
};

var postUserGetRolesRecursos = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(getroles_recursos));
};

var postUserGetRolesFail = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(getroles_fail));
};

var postUserLoginSuccess = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(login_success));
};

var postUserLoginFail = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(login_no_auth));
};

exports.getUsers = getUsers;
exports.postUserSuccess = postUserSuccess;
exports.postUserIdFail = postUserIdFail;
exports.postUserPassFail = postUserPassFail;
exports.postUserGetRolesBoth = postUserGetRolesBoth;
exports.postUserGetRolesFail = postUserGetRolesFail;
exports.postUserGetRolesRecursos = postUserGetRolesRecursos;
exports.postUserGetRolesZonas = postUserGetRolesZonas;
exports.postUserLoginSuccess = postUserLoginSuccess;
exports.postUserLoginFail = postUserLoginFail;
