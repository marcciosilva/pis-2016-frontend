var geoloc_success = require('../replies/geoloc_success.json');

var postGeoLocation = function (request, response, next) {
    console.log('POST');
    console.log(request.body);
    response.send(JSON.stringify(geoloc_success), 200);
}

exports.postGeoLocation = postGeoLocation;
