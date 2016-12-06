<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Category tree</title>
<%@include file="../template_parts/resources.jsp"%>
</head>
<body>

	<core:if test="${not empty categories}">
		<core:forEach var="category" items="${categories}">
			<core:out value="${category.categoryName}" />
			<core:if test="${not empty category.children}">
				<core:forEach var="level2" items="${category.children}">
					<br>
					--<core:out value="${level2.categoryName}" />
				</core:forEach>
			</core:if>
			<br>
		</core:forEach>
	</core:if>

</body>
</html>