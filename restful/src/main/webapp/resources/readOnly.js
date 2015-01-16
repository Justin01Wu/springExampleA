
function submitAjax(dataAreaId){
	console.log("submitAjax...");
	
	
	var data = $("textarea#"+ dataAreaId)[0].value;
	console.log(data);
	
	
	var url = "readOnlyProp/99";
	
	//var url = "http://localhost:8080/restful/readOnlyProp/99";
	// please add --disable-web-security to chrome command for corss domain testing
	
	$.ajax({
	    url: url,
	    accepts: {json: 'application/json'},
	    dataType: 'json',  // expect server return type, ie 'Content-Type'n response
	    type: "PUT",
	    data: data,
	    contentType: "application/json",
	    error: _errorHandle,
	    success: _successHandle
	});
}

function _errorHandle(jqXHR, textStatus, errorThrown ){
	console.log("_errorHandle...");
	var errorMsg = JSON.stringify(jqXHR.responseJSON, null, "\t"); // Indented with tab
	$("textarea#response").val(errorMsg);
	
}

function _successHandle(data, textStatus, jqXHR ){
	console.log("_successHandle...");
	var returnMsg = JSON.stringify(jqXHR.responseJSON, null, 4); // Indented 4 spaces
	$("textarea#response").val(returnMsg);
	
}


