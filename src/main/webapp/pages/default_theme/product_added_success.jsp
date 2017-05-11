<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>




<div class="col-xs-6">
	<img
		src="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/main/${product.image}"
		alt="${product.name}" style="max-width: 100%;max-height: 150px;">
</div>

<div class="col-xs-6">
	<h3 style="color:white;">${product.name}</h3>

	<p>
		<b>$${product.price}</b>
	</p>
</div>

<div class="col-xs-12" style="margin: 1rem;">
	<p>${product.shortDesc}</p>
</div>

<div class="col-xs-6"
	style="padding-bottom: 2rem; position: absolute; bottom: 0px; left: 0px;">
	<a href="#" class="btn btn-warning"><i class="fa fa-angle-left"></i>
		Continue Shopping</a>
</div>

<div class="col-xs-6"
	style="padding-bottom: 2rem; position: absolute; bottom: 0px; right: 0px;">
	<a href="#" class="btn btn-success btn-block">Checkout <i
		class="fa fa-angle-right"></i></a>
</div>


