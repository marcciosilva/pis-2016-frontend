// GET
var events_list = require('../replies/events_list.json');

var getEvents = function (request, response, next) {
	console.log('GET')
    response.send(JSON.stringify(events_list), 200);
};

var postEvents = function (request, response, next) {
	//En caso de ser un POST imprime en la consola el body que recibió
	console.log('POST');
	console.log(request.body);
    response.send(JSON.stringify(events_list), 200);
};

var getSpecialCase = function (request, response, next) {
    var caso = request.query.caso
    
    if (caso == "" || caso == null){
        response.send(JSON.stringify(events_list), 200);
    }else{
    	var fbresponse = require('../replies/'+caso+'.json')
        response.send(JSON.stringify(fbresponse), 200);
    }
};

exports.getEvents = getEvents;
exports.getSpecialCase = getSpecialCase;
exports.postEvents = postEvents;
