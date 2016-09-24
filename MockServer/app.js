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

app.get('/events/', events.getEvents);

//just a special get to test more easily
//request it like: 'http://10.17.32.136:8081/purchases/1/detail/special_case?caso=un_json_cualquiera'
app.get('/purchases/:purchase_id/detail/special_case', myml.getSpecialCase);

app.listen(8081);