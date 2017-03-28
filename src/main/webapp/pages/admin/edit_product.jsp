<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin product</title>
<%@include file="resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Edit product</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Product</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> Edit</li>
						</ol>
					</div>
				</div>
				<core:if test="${not empty errorSummary}">

					<div class="alert alert-danger">
						<strong>Error!</strong> ${errorSummary}
					</div>
				</core:if>
				<div class="row">
					<div class="col-lg-6 ">

						<form:form action="${pageContext.request.contextPath}/a/product"
							method="post" modelAttribute="product">
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
								<form:input class="form-control" placeholder="SKU" path="SKU" />
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
								<form:textarea class="form-control" placeholder=""
									path="longDesc"></form:textarea>
							</div>


							<div class="form-group">
								<label>META title</label>
								<form:input class="form-control" placeholder="Product title"
									path="metaTitle" />
								<p class="help-block">Title tags are often used on search
									engine results pages (SERPs) to display preview snippets for a
									given page, and are important both for SEO and social sharing.
								</p>
							</div>

							<div class="form-group">
								<label>META description</label>
								<form:input class="form-control"
									placeholder="Product description" path="metaDescription" />
								<p class="help-block">The meta description is a ~160
									character snippet, a tag in HTML, that summarizes a page's
									content. Search engines show the meta description in search
									results mostly when the searched for phrase is contained in the
									description. Optimizing the meta description is a very
									important aspect of on-page SEO.</p>
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


							<button type="submit" class="btn btn-default">Submit
								Button</button>
							<button type="reset" class="btn btn-default">Reset
								Button</button>

						</form:form>

					</div>

					<div class="col-lg-6 ">
						<div class="panel panel-success">
							<div class="panel-heading">
								<h3 class="panel-title">Main Image</h3>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<label>Select file to upload. New file rewrites the old one.</label>
									<div>
									<core:if test="${not empty mainImage}">
										<img src="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/main/${mainImage}"
												style="max-width: 60%;margin-left: 20% !important;"></img>
									</core:if>
									</div>
									<form:form method="post"
										action="${pageContext.request.contextPath}/uploadProductMainImage"
										enctype="multipart/form-data">

										<input type="hidden" value="products/${product.id}/"
											name="prefix" />
										<div id="fuTable">

											<input name="files" type="file">

										</div>
										<br>
										<input type="submit" value="Upload Main Image"
											class="btn btn-default">
									</form:form>
								</div>
							</div>
						</div>



						<div class="panel panel-success">
							<div class="panel-heading">
								<h3 class="panel-title">Upload Images</h3>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<core:forEach items="${images}" var="img">
										<img
											src="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/${img}"
											width="30%"></img>
									</core:forEach>
								</div>
								<script>
									function AddMoreFile(tableID) {

										var table = document
												.getElementById('fuTable');
										var colInput = document
												.createElement("input");
										colInput.type = "file";
										colInput.name = "files";
										table.appendChild(colInput);
									}
								</script>
								<div class="form-group">
									<label>Select files to upload. Click Add More button to
										add more files.</label>

									<form:form method="post"
										action="${pageContext.request.contextPath}/uploadProductFiles"
										enctype="multipart/form-data">

										<input type="hidden" value="products/${product.id}/"
											name="prefix" />
										<div id="fuTable">

											<input name="files" type="file">

										</div>
										<br>
										<input type="button" value="Add More File"
											class="btn btn-default" onclick="AddMoreFile('fuTable')">
										<input type="submit" value="Upload" class="btn btn-default">
									</form:form>
								</div>
							</div>
						</div>


					</div>
				</div>


			</div>
		</div>

	</div>
	<%@include file="_footer.jsp"%>
	<script>
		$(document).ready(function() {
			var focus = 0, blur = 0;
			$("#nameinput").focusout(function() {
				focus++;
				var data = $("#nameinput").val();
				var urlinput = $("#urlinput");
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "${home}a/translit",
					data : JSON.stringify({
						cropName : data
					}),
					dataType : 'text',
					timeout : 100000,
					success : function(data) {
						console.log("SUCCESS: ", data.d);

						urlinput.val(data.substring(4));
					},
					error : function(e) {
						console.log("ERROR: ", e);
					},
					done : function(e) {
						console.log("DONE");
					}
				});
			});

		});
	</script>
</body>
</html>