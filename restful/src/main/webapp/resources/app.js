App = Ember.Application.create();
 
App.ApplicationController = Em.ObjectController.extend({
	name: null,
	password: null,
	errors: null,	

	actions : {
		createUser : function() {
			var person = {"name": this.get('name'), "password": this.get('password')};
		    var self = this;
		    $.ajax({
		      type : 'post',
		      contentType : "application/json",
		      dataType : "json",
              data : JSON.stringify(person),
		      url : 'persons' 
		    })
		    .done(function(response) {
		    	self.set('errors', null);
		    	alert('you created user successfully!');		    	
		    })
		    .fail(function(xhr, status, error) {
		    	if(xhr.status === 400) {
		    		self.set('errors',xhr.responseJSON.fieldErrors);
		    	}
		    });
			
		    
		}
	}

});

