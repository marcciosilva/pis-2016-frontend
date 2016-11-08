// GET
var user_list = require('../replies/user_list.json');
// auth
var auth_success = require('../replies/auth_success.json');
var auth_invalid_credentials = require('../replies/auth_invalid_credentials.json');
var auth_already_logged = require('../replies/auth_already_logged.json');
// getRoles
var getroles_both = require('../replies/getroles_both.json');
var getroles_zonas = require('../replies/getroles_zonas.json');
var getroles_recursos = require('../replies/getroles_recursos.json');
var getroles_fail = require('../replies/getroles_fail.json');
var getroles_empty = require('../replies/getroles_empty.json');
// login
var login_success = require('../replies/login_success.json');
var login_no_auth = require('../replies/login_no_auth.json');
//logout
var logout_success = require('../replies/logout_success.json');
var logout_cod5 = require('../replies/logout_cod5.json');
var logout_cod2 = require('../replies/logout_cod2.json');
// keepAlive
var keepalive_success = require('../replies/keepalive_success.json');
var keepalive_fail = require('../replies/keepalive_fail.json');

var getUsers = function (request, response, next) {
	response.setHeader("content-type","application/json");
    response.send(JSON.stringify(user_list), 200);
};

// POST

// Devuelve una response exitosa siempre
var postUserSuccess = function(request, response, next) {
	console.log('POST a /users/authenticate');
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(auth_success));
};

// Devuelve una response fallida siempre
var postUserInvalidCredentials = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(auth_invalid_credentials));
};

// Devuelve una response fallida siempre
var postUserAlreadyLogged = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(auth_already_logged));
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

var postUserGetRolesEmpty = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(getroles_empty));
};

var postUserLoginSuccess = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(login_success));
};

var postUserLoginFail = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(login_no_auth));
};

var postUserLogoutSuccess = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(logout_success));
};

var postUserLogoutCod2 = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(logout_cod2));
};

var postUserLogoutCod5 = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(logout_cod5));
};

var postUserKeepAliveSuccess = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(keepalive_success));
};

var postUserKeepAliveFail = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(keepalive_fail));
};

exports.getUsers = getUsers;
exports.postUserSuccess = postUserSuccess;
exports.postUserInvalidCredentials = postUserInvalidCredentials;
exports.postUserAlreadyLogged = postUserAlreadyLogged;
//getroles
exports.postUserGetRolesBoth = postUserGetRolesBoth;
exports.postUserGetRolesFail = postUserGetRolesFail;
exports.postUserGetRolesRecursos = postUserGetRolesRecursos;
exports.postUserGetRolesZonas = postUserGetRolesZonas;
exports.postUserGetRolesEmpty = postUserGetRolesEmpty;
//login
exports.postUserLoginSuccess = postUserLoginSuccess;
exports.postUserLoginFail = postUserLoginFail;
//logout
exports.postUserLogoutSuccess = postUserLogoutSuccess;
exports.postUserLogoutCod2 = postUserLogoutCod2;
exports.postUserLogoutCod5 = postUserLogoutCod5;
//keepAlive
exports.postUserKeepAliveSuccess = postUserKeepAliveSuccess;
exports.postUserKeepAliveFail = postUserKeepAliveFail;