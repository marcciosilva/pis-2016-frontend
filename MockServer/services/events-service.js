// GET
var events_list = require('../replies/events_list.json');

var getEvents = function (request, response, next) {
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

var postEvents = function (request, response, next) {
   response.send(JSON.stringify(events_list), 200);
};


exports.getEvents = getEvents;
exports.getSpecialCase = getSpecialCase;
exports.postEvents = postEvents
