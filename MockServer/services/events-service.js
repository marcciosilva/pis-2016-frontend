// GET
var events_list = require('../replies/events_list.json');
var event_detail = require('../replies/event_detail.json');
var event_detail_error = require('../replies/event_detail_error.json');
// getImageData
var getimagedata_error = require('../replies/getimagedata_error.json');
// reportTime
var report_time_success = require('../replies/report_time_success.json');
var report_time_fail = require('../replies/report_time_fail.json');
// offlineAttachDescription
var offline_update_desc_success = require('../replies/offline_update_desc_success.json');

function delay(ms) {
    console.log("Esperando " + ms + " ms...");
    var cur_d = new Date();
    var cur_ticks = cur_d.getTime();
    var ms_passed = 0;
    while(ms_passed < ms) {
        var d = new Date();  // Possible memory leak?
        var ticks = d.getTime();
        ms_passed = ticks - cur_ticks;
        // d = null;  // Prevent memory leak?
    }
}

var getEvents = function (request, response, next) {
    delay(2000);
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
    // delay(2000);
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

var getImageData = function (request, response, next) {
    // delay(2000);
    console.log('GET a getEventDetail');
    var idImagen = request.query.idImagen;
    if (idImagen == "" || idImagen == null){
        response.send("No se seleccionó ninguna imágen. Mensaje únicamente de Mockserver.", 200);
    }else{
        try {
            var fbresponse = require('../replies/getimagedata_success_'+idImagen+'.json')
            response.send(JSON.stringify(fbresponse), 200);
        }
        catch(err) {
            response.send(JSON.stringify(getimagedata_error), 200);
        }        
    }
};

var postArrivalTime = function (request, response, next) {
    console.log('POST a postArrivalTime');
    console.log(request.body);
    response.send(JSON.stringify(report_time_fail), 200);
};

var postOfflineUpdateDesc = function (request, response, next) {
    console.log('POST a postOfflineUpdateDesc');
    console.log(request.body);
    response.send(JSON.stringify(offline_update_desc_success), 200);
};

exports.postArrivalTime = postArrivalTime;
exports.getEvents = getEvents;
exports.getEventDetail = getEventDetail;
exports.getSpecialCase = getSpecialCase;
exports.postEvents = postEvents;
exports.getImageData = getImageData;
exports.postOfflineUpdateDesc = postOfflineUpdateDesc;
