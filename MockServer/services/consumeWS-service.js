// POST

var consumeWS = require('../replies/consumeWS.json');
var unavailableWS = require('../replies/consumeWS_unavailable.json');

// Devuelve una response exitosa
var postConsumeWSSuccess = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(consumeWS));
};


// Devuelve una response erronea (WS no disponible)
var postConsumeWSError = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(unavailableWS));
};

exports.postConsumeWSSuccess = postConsumeWSSuccess;
exports.postConsumeWSError = postConsumeWSError;

