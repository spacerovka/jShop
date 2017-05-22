<%@page import="com.fasterxml.jackson.annotation.JsonInclude.Include"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<form:form action="${pageContext.request.contextPath}/a/product"
	method="post" modelAttribute="product" id="productForm">
	<div class="form-group">
		<label>ID</label>
		<p class="form-control-static">${product.id}</p>
	</div>
	<form:hidden path="id" />

	<div class="form-group">
		<label>Name*</label>
		<form:input class="form-control" placeholder="Product name"
			path="name" id="nameinput" />
	</div>

	<div class="form-group ${skuError}">
		<label>SKU*</label>
		<form:input class="form-control" placeholder="SKU" path="sku" />
	</div>


	<div class="form-group input-group ${urlError}">
		<span class="input-group-addon">http:/${pageContext.request.contextPath}/</span>
		<form:input class="form-control" placeholder="url" type="text"
			path="url" id="urlinput" />
	</div>

	<div class="form-group">
		<label>Instock amount</label>
		<form:input class="form-control" placeholder="0" path="instock" />
	</div>

	<div class="form-group ${priceError}">
		<label>Price*</label>
		<form:input class="form-control" placeholder="1.00" path="price" />
	</div>

	<div class="form-group">
		<label>Location</label>
		<form:input class="form-control" placeholder="City, State"
			path="location" />
	</div>

	<div class="form-group">
		<label>Cart Description</label>
		<form:input class="form-control" placeholder="" path="cartDesc" />
	</div>

	<div class="form-group">
		<label>Short Description</label>
		<form:input class="form-control" placeholder="" path="shortDesc" />
	</div>

	<div class="form-group">
		<label>Long Description</label>
		<form:textarea class="form-control" placeholder="" path="longDesc"></form:textarea>
	</div>


	<div class="form-group">
		<label>META title</label>
		<form:input class="form-control" placeholder="Product title"
			path="metaTitle" />
		<p class="help-block">Title tags are often used on search engine
			results pages (SERPs) to display preview snippets for a given page,
			and are important both for SEO and social sharing.</p>
	</div>

	<div class="form-group">
		<label>META description</label>
		<form:input class="form-control" placeholder="Product description"
			path="metaDescription" />
		<p class="help-block">The meta description is a ~160 character
			snippet, a tag in HTML, that summarizes a page's content. Search
			engines show the meta description in search results mostly when the
			searched for phrase is contained in the description. Optimizing the
			meta description is a very important aspect of on-page SEO.</p>
	</div>

	<div class="form-group">
		<label>Status</label>
		<div class="radio">
			<label> <form:radiobutton path="status" value="true" />Active
			</label>
		</div>
		<div class="radio">
			<label> <form:radiobutton path="status" value="false" />Hidden
			</label>
		</div>
	</div>


	<div class="form-group">
		<label>Parent category</label>

		<form:select path="category.id" class="form-control">
			<option value="-1">Select...</option>
			<form:options items="${parentCategoryList}" itemValue="id"
				itemLabel="categoryName" />

			<core:forEach items="${parentCategoryList}" var="category">
				<option value="${category.id}">${category.categoryName}</option>
			</core:forEach>
		</form:select>
 
	</div>
	<div class="form-group" id="options">
		<%@include file="_product_options.jsp"%>

		<div class="text-right">
			<a style="display: inline-block;" href="#"
				onclick="addProductOption();" id="addOption"
				class="btn btn-default">Add option</a>
		</div>
	</div>
<hr/>

	<button type="submit" class="btn btn-default">Submit Button</button>
	<button type="reset" class="btn btn-default">Reset Button</button>

</form:form>