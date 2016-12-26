$(document).ready(function() {	
		var focus = 0;
		$( "#nameinput" ).focusout(function() {
		    focus++;	    
		    var data = $( "#nameinput" ).val();
		    var urlinput = $( "#urlinput" );
		    $.ajax({
				type : "POST",
				contentType : "application/json",
				url : home+"a/translit",
				data : JSON.stringify({ data }),
				dataType:'text',
				timeout : 100000,
				success : function(data) {
					console.log("SUCCESS: ", data);
					urlinput.val(data);
				},
				error : function(e) {
					console.log("ERROR: ", e);				
				},
				done : function(e) {
					console.log("DONE");
				}
			});
		  }); 
		
	});