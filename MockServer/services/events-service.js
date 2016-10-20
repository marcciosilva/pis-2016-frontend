// GET
var events_list = require('../replies/events_list.json');
var event_detail = require('../replies/event_detail.json');
var event_detail_error = require('../replies/event_detail_error.json');

var getEvents = function (request, response, next) {
	console.log('GET');
    response.send(JSON.stringify(events_list), 200);
};

var postEvents = function (request, response, next) {
	//En caso de ser un POST imprime en la consola el body que recibió
	console.log('POST a postEvents');
	console.log(request.body);
    response.send(JSON.stringify(events_list), 200);
};

var getSpecialCase = function (request, response, next) {
    var caso = request.query.caso;
    if (caso == "" || caso == null){
        response.send(JSON.stringify(events_list), 200);
    }else{
    	var fbresponse = require('../replies/'+caso+'.json')
        response.send(JSON.stringify(fbresponse), 200);
    }
};

var getEventDetail = function (request, response, next) {
    console.log('GET a getEventDetail');
    var idEvento = request.query.idEvento;
    if (idEvento == "" || idEvento == null){
        response.send("No se seleccionó ningún evento. Mensaje únicamente de Mockserver.", 200);
    }else{
        try {
            var fbresponse = require('../replies/event_detail_'+idEvento+'.json')
            response.send(JSON.stringify(fbresponse), 200);
        }
        catch(err) {
            response.send(JSON.stringify(event_detail_error), 200);
        }        
    }
};

exports.getEvents = getEvents;
exports.getEventDetail = getEventDetail;
exports.getSpecialCase = getSpecialCase;
exports.postEvents = postEvents;
