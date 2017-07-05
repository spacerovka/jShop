<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="row">
	<div class="col-lg-6 " id="updateForm">

		<%@include file="_edit_order_form.jsp"%>

	</div>

	<div class="col-lg-6 ">

		<div class="form-group col-xs-12">
			<%@include file="products.jsp"%>
		</div>
		<hr/>
		<h1>Add products to order</h1>
		<div class="form-group col-xs-4">
			<label>Product name</label> <input class="form-control" type="text"
				id="searchName" />
		</div>
		<div class="form-group col-xs-4">
			<label>Product sku</label> <input class="form-control" type="text"
				id="searchSKU" />
		</div>
		<div class="form-group col-xs-4">
			<a class="btn btn-default" href="#"
				style="display: block; margin-top: 2.4rem;"
				onclick="searchButtonClick();">Search</a>
		</div>

		<div class="form-group col-xs-12">
			<div class="table-responsive" id="table">
				<%@include file="_add_product_table.jsp"%>

			</div>
		</div>
	</div>
</div>