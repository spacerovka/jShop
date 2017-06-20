<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Reset Password</title>
</head>

<body>
    <h1>Reset Password</h1>
 	<div id="errormsg" style="display: none"></div>
    <label>Email</label>
    <input id="email" name="email" type="email" value="" />
    <button type="submit" onclick="resetPass()">Reset</button>
 
<a href="${pageContext.request.contextPath}/registration.html}">Registration</a>
<a href="${pageContext.request.contextPath}/login">Login</a>

<%@include file="template_parts/footer.jsp"%>
 
<script>
/* var serverContext = [[@{/}]];
function resetPass1(){
    var email = $("#email").val();
    $.post(serverContext + "user/resetPassword",{email: email} ,
      function(data){
          window.location.href = 
           serverContext + "login?message=" + data.message;
    })
    .fail(function(data) {
        if(data.responseJSON.error.indexOf("MailError") > -1)
        {
            window.location.href = serverContext + "emailError.html";
        }
        else{
            window.location.href = 
              serverContext + "login?message=" + data.responseJSON.message;
        }
    });
} */

function resetPass()
{	
	var email = $("#email").val();
    
	$.ajax ({ 
		url: '${pageContext.request.contextPath}/user/resetPassword', 
		type: 'POST', 
		dataType: 'text',	
		data : {email: email},								
		complete: function(responseData, status, xhttp){ 				
			$("#errormsg").html('('+responseData.responseText+')');
		}
	});
}
 
</script>

</body>