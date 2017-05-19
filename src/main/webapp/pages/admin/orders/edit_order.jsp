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
<%@include file="../resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="../_topnav.jsp"%>
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

						<%@include file="_edit_order_form.jsp"%>

					</div>

					<div class="col-lg-6 ">
						
<div class="form-group col-xs-12">
		<%@include file="products.jsp"%>
	</div>


					</div>
				</div>


			</div>
		</div>

	</div>
	<%@include file="../_footer.jsp"%>
		
	<script>
	
	function addProduct()
	{
		console.log("addProductOption");
		$.ajax ({ 
			url: '${pageContext.request.contextPath}/a/addProductToOrder', 
			type: "POST", 						
			data : $('#productForm').serialize(),
			complete: function(response){
				$('#updateForm').html(response.responseText);
				$("html, body").animate({ scrollTop: $(document).height() }, 1000);
			}
		}); 
	}	
	
	</script>
	
	<!-- function addQuantity(sku) {
			console.log("removeQuantity");
			$.ajax({
				url : '${pageContext.request.contextPath}/addQuantity',
				type : "POST",
				dataType : "text",
				data : {
					sku : sku
				},
				complete : function(response) {
					$('#cart').html(response.responseText);
					updateCartItemCount();
				}
			});
		}

		function removeQuantity(sku) {
			console.log("removeQuantity");
			$.ajax({
				url : '${pageContext.request.contextPath}/removeQuantity',
				type : "POST",
				dataType : "text",
				data : {
					sku : sku
				},
				complete : function(response) {
					$('#cart').html(response.responseText);
					updateCartItemCount();
				}
			});
		} -->
</body>
</html>