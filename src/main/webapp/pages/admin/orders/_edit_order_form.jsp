<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="${pageContext.request.contextPath}${URL_PREFIX}order"
	method="post" modelAttribute="order">
	<form:hidden path="orderId"/>
	<form:hidden path="number"/>
	<form:hidden path="sum"/>
	
	<div class="form-group col-xs-12 col-md-6">
		<label>User</label>
		<form:input class="form-control" placeholder="John R Smith"
			path="username" id="username" />
	</div>
	
	<div class="form-group col-xs-12 col-md-6">
		<label>Full Name*</label>
		<form:input class="form-control" placeholder="John R Smith"
			path="fullName" id="fullName" />
	</div>
	<h3 class="col-xs-12">Shiping address</h3>
	<div class="form-group col-xs-12 col-md-6">
		<label>Country*</label>
		<form:select path="country" class="form-control" items="${countryList}">
			<%-- <option value="Unselected">Select...</option>
			<core:forEach items="${countryList}" var="country">
				<option value="${country}">${country}</option>
			</core:forEach> --%>
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
	<h3 class="col-xs-12">Shipping details</h3>
	<div class="form-group col-xs-12 col-md-6">
		<label>Shipping Type</label>
		<form:input class="form-control" path="shipName" id="shipName" />
	</div>
	
	<div class="form-group col-xs-12 col-md-6">
		<label>Shipping Cost</label>
		<form:input class="form-control" path="shippingCost" id="shippingCost" />
	</div>
	
	<div class="form-group col-xs-12 col-md-6">
		<label>Tracking Number</label>
		<form:input class="form-control" path="trackNumber" id="trackNumber" />
	</div>		
	<h3 class="col-xs-12">Additional info</h3>
	<div class="form-group col-xs-12">
		<label>Manager Comment</label>
		<form:textarea class="form-control" path="managerComment" id="managerComment" />
	</div>
	
	<div class="form-group col-xs-12 col-md-6">
		<form:checkbox path="shipped"/>Shipped
	</div>
	<div class="form-group col-xs-12 col-md-6">
	<form:checkbox path="confirmed"/>Confirmed
	</div>

	<div class="form-group text-center col-xs-12">
		<button type="submit" class="btn btn-success">Update</button>
	</div>
</form:form>