<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<!--  onload='document.loginForm.username.focus();'
style="background-image:url('http://i.imgur.com/OMtgtDj.jpg');
background-size:cover;"> -->
	<c:if test="${not empty error}">
		<div>${error}</div>
	</c:if>
	<c:if test="${not empty message}">
		<div>${message}</div>
	</c:if>
	<c:if test="${not empty  errorMessage}">
		<div>${errorMessage}</div>
	</c:if>
	<form name='login' action="<c:url value='/login' />" method='POST'
		style="display: block; margin: 300px auto; width: 280px; font-size: 1.5em; font-family: Helvetica, sans-serif;">
		<table>
			<tr style="color: #E91E63;">
				<td>UserName:</td>
				<td><input type='text' name='username' value=''></td>
			</tr>
			<tr style="color: #E91E63;">
				<td>Password:</td>
				<td><input type='password' name='password' /></td>
			</tr>
			<tr>
				<td colspan='2' style="text-align: center;"><input
					name="submit" type="submit" value="submit"
					style="font-size: 1em; margin: 1em auto; background: #E91E63; border: none; padding: 0.5em; color: white;" /></td>
			</tr>
		</table>
		<input type="hidden" name="${_csrf.parameterName}"
			value="${_csrf.token}" />
	</form>
	<a href="${pageContext.request.contextPath}/forgotpassword">I forgot my password	</a> 

</body>
</html>