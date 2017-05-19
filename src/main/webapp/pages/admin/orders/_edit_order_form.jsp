<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="${pageContext.request.contextPath}/a/order"
	method="post" modelAttribute="order">

	<div class="form-group col-xs-12 col-md-6">
		<label>Full Name*</label>
		<form:input class="form-control" placeholder="John R Smith"
			path="userName" id="userName" />
	</div>
	<h3 class="col-xs-12">Shiping address</h3>
	<div class="form-group col-xs-12 col-md-6">
		<label>Country*</label>
		<form:select path="country" class="form-control">
			<option value="-1">Select...</option>
			<core:forEach items="${countryList}" var="country">
				<option value="${country}">${country}</option>
			</core:forEach>
		</form:select>

	</div>

	<div class="form-group col-xs-12 col-md-6">
		<label>State</label>
		<form:input class="form-control" placeholder="John R Smith"
			path="state" id="state" />
	</div>
	<div class="form-group col-xs-12 col-md-6">
		<label>City*</label>
		<form:input class="form-control" placeholder="John R Smith"
			path="city" id="city" />
	</div>

	<div class="form-group col-xs-12 col-md-6">
		<label>Local Address*</label>
		<form:input class="form-control" placeholder="John R Smith"
			path="shipAddress" id="shipAddress" />
	</div>

	<div class="form-group col-xs-12 col-md-6">
		<label>Zip*</label>
		<form:input class="form-control" path="zip" id="userName" />
	</div>

	<h3 class="col-xs-12">Contacts</h3>
	<div class="form-group col-xs-12 col-md-6">
		<label>Phone(international format)*</label>
		<form:input class="form-control" path="phone" id="phone" />
	</div>

	<div class="form-group col-xs-12 col-md-6">
		<label>Email*</label>
		<form:input class="form-control" path="email" id="email" />
	</div>
	

	<div class="form-group text-center col-xs-12">
		<button type="submit" class="btn btn-success">Update</button>
	</div>
</form:form>