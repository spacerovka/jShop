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

	<core:if test="${not empty options}">
		<core:forEach var="option" items="${options}">
			<core:out value="${option.optionGroup.optionGroupName}" />	
			<core:out value="${option.option.optionName} " />		
					<br>
					--<core:out value="${option.product.name}" />	
			<br>
		</core:forEach>
	</core:if>
	<br/><br/>
	<core:if test="${not empty optionsByType}">
		<core:forEach var="option2" items="${optionsByType}">
			<core:out value="${option2.optionGroup.optionGroupName}" />	
			<core:out value="${option2.option.optionName} " />		
					<br>
					--<core:out value="${option2.product.name}" />	
			<br>
		</core:forEach>
	</core:if>
	<br/><br/>
	
	
	categoryOptions
	<br/><br/>
	<core:if test="${not empty categoryOptions}">
		<core:forEach var="option3" items="${categoryOptions}">
			<core:out value="${option3.category.categoryName}" />	
			<core:out value="${option3.option.optionName} " />											
			<br>
		</core:forEach>
	</core:if>
	<br/><br/>
	
	products
	<br/><br/>
	<core:if test="${not empty products}">
		<core:forEach var="product" items="${products}">
			<core:out value="${product.name}" />	
			<core:out value="${product.price} " />		
					<br>
					--<core:out value="${product.category.categoryName}" />	
			<br>
		</core:forEach>
	</core:if>

</body>
</html>