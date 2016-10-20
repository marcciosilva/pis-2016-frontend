// Define an Enum function for the base Object class
Object.defineProperty(Object.prototype,'Enum', {
	value: function() {
		for(i in arguments) {
			Object.defineProperty(this,arguments[i], {
				value:parseInt(i),
				writable:false,
				enumerable:true,
				configurable:true
			});
		}
	},
	writable:false,
	enumerable:false,
	configurable:false
}); 

var events = require('./services/events-service.js');
var users = require('./services/users-service.js');
var consumeWS = require('./services/consumeWS-service.js');


var express = require('express')
  , app = express();

var mock = null
  , response = null;

app.configure(function(){
  app.use(express.bodyParser());
  app.use(app.router);
  app.use(express.compress());
  app.use(express.static(__dirname + '/statics'));
});

app.get('/eventos/listar', events.getEvents);
app.post('/events', events.postEvents);
app.get('/eventos/obtener', events.getEventDetail);


//just a special get to test more easily
//request it like: 'http://10.17.32.136:8081/events/1/detail/special_case?caso=un_json_cualquiera'
app.get('/events/:event_id/detail/special_case', events.getSpecialCase);

//Manejo usuarios

//auth
//http://localhost:8081/users/authenticate
var AuthChoice={};
AuthChoice.Enum('Success', 'InvalidCredentials', 'AlreadyLogged');
// var AuthChoice = Object.freeze({success:1, invalidCredentials:2, alreadyLogged:3});
// authChoice = success;
authChoice = AuthChoice.Success;
switch (authChoice) {
	case AuthChoice.Success:
		app.post('/users/authenticate', users.postUserSuccess);
		break;
	case AuthChoice.InvalidCredentials:
		app.post('/users/authenticate', users.postUserInvalidCredentials);
	case AuthChoice.AlreadyLogged:
		app.post('/users/authenticate', users.postUserAlreadyLogged);
	default:
		break;
}

//getRoles
var GetRolesChoice={};
GetRolesChoice.Enum('Both', 'Fail', 'OnlyResources', 'OnlyZones', 'EmptyResponse');
///users/getroles
getRolesChoice = GetRolesChoice.Both;
switch (getRolesChoice) {
	case GetRolesChoice.Both:
		app.post('/users/getroles', users.postUserGetRolesBoth);
		break;
	case GetRolesChoice.Fail:
		app.post('/users/getroles', users.postUserGetRolesFail);
		break;
	case GetRolesChoice.OnlyResources:
		app.post('/users/getroles', users.postUserGetRolesRecursos);
		break;
	case GetRolesChoice.OnlyZones:
		app.post('/users/getroles', users.postUserGetRolesZonas);
		break;
	case GetRolesChoice.EmptyResponse:
		app.post('/users/getroles', users.postUserGetRolesEmpty);
		break;
	default:
		break;
}

//logout
var LogoutChoice={};
LogoutChoice.Enum('Success', 'Code2', 'Code5');
///users/logout
logoutChoice = LogoutChoice.Success;
switch (logoutChoice) {
	case LogoutChoice.Success:
		app.post('/users/logout', users.postUserLogoutSuccess);
		break;
	case LogoutChoice.Code2:
		app.post('/users/logout', users.postUserLogoutCod2);
		break;
	case LogoutChoice.Code5:
		app.post('/users/logout', users.postUserLogoutCod5);
		break;
	default:
		break;
}

//login
///users/login
var LoginChoice={};
LoginChoice.Enum('Success', 'Fail');
loginChoice = LoginChoice.Success;
switch (loginChoice) {
	case LoginChoice.Success:
		app.post('/users/login', users.postUserLoginSuccess);
		break;
	case LoginChoice.Fail:
		app.post('/users/login', users.postUserLoginFail);
		break;
	default:
		break;
}

//consumeWS
var WSChoice={};
WSChoice.Enum('Success', 'Fail');
wsChoice = LoginChoice.Success;
switch (wsChoice) {
	case WSChoice.Success:
		app.post('/consumeWS', consumeWS.postConsumeWSSuccess);
		break;
	case WSChoice.Fail:
		app.post('/consumeWS', consumeWS.postConsumeWSError);
		break;
	default:
		break;
}


// keepAlive
var KeepAliveChoice={};
KeepAliveChoice.Enum('Success', 'Fail');
KeepAliveChoice = LoginChoice.Success;
switch (KeepAliveChoice) {
	case KeepAliveChoice.Success:
		app.get('/users/expiration_time', users.getUserKeepAliveSuccess);
		break;
	case KeepAliveChoice.Fail:
		app.get('/users/expiration_time', users.getUserKeepAliveFail);
		break;
	default:
		break;
}

app.listen(8081);
