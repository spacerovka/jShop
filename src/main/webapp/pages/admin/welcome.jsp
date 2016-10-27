<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
   <%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %> 
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body 
style="background-image:url('http://teenhdgirls.com/wp-content/uploads/11320810/Ass-X-Art-Kaylee-Miu-Perfect-Nude.jpg');
background-size:cover;">
<h1 style="position:absolute;top:50%;left:20%;font-size:60px;color:red;">Hello Admin!</h1>
<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
  <b>You are logged in as: </b><security:authentication property="principal.username"/>
  with the role of <security:authentication property="principal.authorities"/>
   <core:url value="/logout" var="logoutUrl" />
<form id="logout" action="${logoutUrl}" method="post" >
  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
</form>
<core:if test="${pageContext.request.userPrincipal.name != null}">
	<a href="javascript:document.getElementById('logout').submit()">Logout</a>
</core:if>
   
</security:authorize>

</body>
</html>