var events = require('./services/events-service.js');

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
app.post('/events', events.postEvents);


//just a special get to test more easily
//request it like: 'http://10.17.32.136:8081/events/1/detail/special_case?caso=un_json_cualquiera'
app.get('/events/:event_id/detail/special_case', events.getSpecialCase);

app.listen(8081);