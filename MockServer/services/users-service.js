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
var getroles_empty = require('../replies/getroles_empty.json');
// login
var login_success = require('../replies/login_success.json');
var login_no_auth = require('../replies/login_no_auth.json');
//logout
var logout_success = require('../replies/logout_success.json');
var logout_cod5 = require('../replies/logout_cod5.json');
var logout_cod2 = require('../replies/logout_cod2.json');

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

exports.getUsers = getUsers;
exports.postUserSuccess = postUserSuccess;
exports.postUserIdFail = postUserIdFail;
exports.postUserPassFail = postUserPassFail;
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