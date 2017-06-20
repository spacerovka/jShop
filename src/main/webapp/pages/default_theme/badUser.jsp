<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bad User</title>
</head>
<body>

	<h1 id="message">${message}</h1>
	<a href="/registration.html" >Signup</a>


 <c:if test=${not empty token}>

<h1>Resend email verification</h1>
<button onclick="resendToken()">Resend</button>
  
</c:if>

<%@include file="template_parts/footer.jsp"%>
<script type="text/javascript">
 
var serverContext = [[@{/}]];
 
function resendToken(){
        
    $.ajax ({ 
		url: '${pageContext.request.contextPath}/user/resendRegistrationToken', 
		type: 'POST', 
		dataType: 'text',	
		data : {token: ${token}},								
		complete: function(responseData, status, xhttp){ 				
			$("#message").html('('+responseData.responseText+')');
		}
	});
}
</script>

</body>
