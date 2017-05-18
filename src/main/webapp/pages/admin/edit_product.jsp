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
					<div class="col-lg-6 " id="updateForm">

						<%@include file="_edit_product_form.jsp"%>

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
	
	<script>
	
	function addProductOption()
	{
		console.log("addProductOption");
		$.ajax ({ 
			url: '${pageContext.request.contextPath}/a/addProductOption', 
			type: "POST", 						
			data : $('#productForm').serialize(),
			complete: function(response){
				$('#updateForm').html(response.responseText);
				$("html, body").animate({ scrollTop: $(document).height() }, 1000);
			}
		}); 
	}
	
	function updateProductOption()
	{
		console.log("updateProductOption");
		$.ajax ({ 
			url: '${pageContext.request.contextPath}/a/updateProductOption', 
			type: "POST", 						
			data : $('#productForm').serialize(),
			complete: function(response){
				$('#updateForm').html(response.responseText);
				$("html, body").animate({ scrollTop: $(document).height() }, 1);
			}
		}); 
	}
	</script>
</body>
</html>