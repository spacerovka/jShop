<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reset Password</title>
</head>

<body>
	<h1 th:text="#{message.resetYourPassword}">reset</h1>
	<form>
		<br /> <label class="col-sm-2" >Password</label>
		<span class="col-sm-5"><input class="form-control"
			id="password" name="newPassword" type="password" value="" /></span>
		<div class="col-sm-12"></div>
		<br />
		<br /> <label class="col-sm-2">Confirm password</label>
		<span class="col-sm-5"><input class="form-control"
			id="matchPassword" type="password" value="" /></span>
		<div id="errormsg" class="col-sm-12 alert alert-danger"
			style="display: none">error</div>

		<div class="col-sm-12">
			<br />
			<br />
			<button class="btn btn-primary" type="submit" onclick="savePass()">Update password</button>
		</div>
	</form>



	<%@include file="template_parts/footer.jsp"%>


<script>


$(document).ready(function () {
    $('form').submit(function(event) {
        savePass(event);
    });
    
    $(":password").keyup(function(){
        if($("#password").val() != $("#matchPassword").val()){
            $("#globalError").show().html(/*[[#{PasswordMatches.user}]]*/);
        }else{
            $("#globalError").html("").hide();
        }
    });
   
});
function savePass(event){
    event.preventDefault();
    $(".alert").html("").hide();
    $(".error-list").html("");
    if($("#password").val() != $("#matchPassword").val()){
        $("#globalError").show().html(/*[[#{PasswordMatches.user}]]*/);
        return;
    }
    
var password = $("#password").val();
    
	$.ajax ({ 
		url: '${pageContext.request.contextPath}/user/resetPassword', 
		type: 'POST', 
		dataType: 'text',	
		data : {password: password},								
		complete: function(responseData, status, xhttp){ 				
			$("#errormsg").html('('+responseData.responseText+')');
		}
	});
   
}
</script>    

</body>