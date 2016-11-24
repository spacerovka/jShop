<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="template_parts/resources.jsp"%>
</head>
<body
	style="background-image: url('resources/img/main.jpg'); background-size: cover;">
	<!-- style="background-image:url('http://s57.radikal.ru/i156/1410/ba/c69a205d33d5.jpg');
background-size:cover;"> -->


	<div class="container"
	style="background: rgb(255, 255, 255) none repeat scroll 0% 0%; padding: 30px; opacity: 0.8;">

		<core:if test="${not empty users}">
			<core:forEach var="user" items="${users}">
				<p><core:out value="${user}"/></p>
			</core:forEach>
		</core:if>		
		
	</div>



	<h1
		style="top: 50%; left: 20%; font-size: 60px; color: red; background: rgb(255, 255, 255) none repeat scroll 0% 0%; padding: 30px; opacity: 0.8;">
		Hello! <a href="${pageContext.request.contextPath}/login" style="font-size: 30px;">Login</a>
	</h1>
	
	<%@include file="template_parts/footer.jsp"%>
</body>
</html>