
function submitAjax(){
	console.log("submitAjax...");
	
	
	var data = $("textarea#wrongJson")[0].value;
	console.log(data);
	
	//$("textarea#response").val("sss");
	
	//var url = "readOnlyProp/99";
	var url = "http://localhost:8080/restful/readOnlyProp/99";
	$.ajax({
	    url: url,
	    type: "PUT",
	    data: JSON.stringify(data),
	    contentType: "application/json",
	    complete: _callBack
	});
}

function _callBack(x,y,z){
	$("textarea#response").val("sss");
	
}

