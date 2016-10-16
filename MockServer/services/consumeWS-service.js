// POST

var consumeWS = require('../replies/consumeWS.json');

// Devuelve una response exitosa siempre
var postConsumeWSSuccess = function(request, response, next) {
	response.setHeader("content-type","application/json");
	response.send(JSON.stringify(consumeWS));
};

exports.postConsumeWSSuccess = postConsumeWSSuccess;

