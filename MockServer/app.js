var events = require('./services/events-service.js');
var users = require('./services/users-service.js');

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

app.get('/events', events.getEvents);

//just a special get to test more easily
//request it like: 'http://10.17.32.136:8081/events/1/detail/special_case?caso=un_json_cualquiera'
app.get('/events/:event_id/detail/special_case', events.getSpecialCase);

//Manejo usuarios
app.get('/users', users.getUsers);
//http://localhost:8081/users/success
app.post('/users/success', users.postUserSuccess);
//http://localhost:8081/users/username-fail
app.post('/users/username-fail', users.postUserIdFail);
//http://localhost:8081/users/success
app.post('/users/pass-fail', users.postUserPassFail);


app.listen(8081); 