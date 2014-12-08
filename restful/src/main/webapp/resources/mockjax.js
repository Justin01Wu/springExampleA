

var fakePerson =  {
    "id":1234,
    "name":"Fake Name"
};

// getPerson 
$.mockjax({
  url : /persons\/99/i,
  responseTime : 100,
  type : 'get',
  dataType : 'json',
  responseText : JSON.stringify(fakePerson)
// target ajax is using jquery 1.9.1. done method, so we must use stringify, it is a bug of mockjax
// see here for details: https://github.com/appendto/jquery-mockjax/issues/95
});



