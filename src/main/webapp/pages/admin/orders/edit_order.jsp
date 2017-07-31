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
					<core:forEach var="error" items="${errorSummary}">
						<div class="alert alert-danger">

							<strong>Error!</strong> ${error}

						</div>
					</core:forEach>
				</core:if>
				<div id="order">
					<%@include file="_order.jsp"%>
				</div>
			</div>
		</div>

	</div>
	<%@include file="../_footer.jsp"%>

	<script>
		function addProduct(id, orderid) {
			console.log("addProduct");
			$
					.ajax({
						url : '${pageContext.request.contextPath}/ajax/addProductToOrder',
						type : "POST",
						data : {
							id : id, orderid:orderid
						},
						complete : function(response) {
							$('#order').html(response.responseText);
							$("html, body").animate({
								scrollTop : $(document).height()
							}, 1000);
						}
					});
		}
		
		function addQuantity(sku, orderid) {
			console.log("addQuantity");
			$.ajax({
				url : '${pageContext.request.contextPath}/ajax/addQuantity',
				type : "POST",
				dataType : "text",
				data : {
					sku : sku,
					orderid:orderid
				},
				complete : function(response) {
					$('#order').html(response.responseText);
					$("html, body").animate({
						scrollTop : $(document).height()
					}, 1000);
				}
			});
		}

		function removeQuantity(sku,orderid) {
			console.log("removeQuantity");
			$.ajax({
				url : '${pageContext.request.contextPath}/ajax/removeQuantity',
				type : "POST",
				dataType : "text",
				data : {
					sku : sku,
					orderid:orderid
				},
				complete : function(response) {
					$('#order').html(response.responseText);
					$("html, body").animate({
						scrollTop : $(document).height()
					}, 1000);
				}
			});
		}
		
		function addCoupon() {
			console.log("addCoupon");
			var code = $("#coupon-code").val();
			$.ajax({
				url : '${pageContext.request.contextPath}/ajax/addCoupon',
				type : "POST",
				dataType : "text",
				data : {
					code : code,
					orderid:orderid
				},
				complete : function(response) {
					$('#order').html(response.responseText);
					$("html, body").animate({
						scrollTop : $(document).height()
					}, 1000);
				}
			});
		}
	</script>

	<script>
		/* function searchButtonClick() {
			var sku = $('#searchSKU').val();
			var name = $('#searchName').val();
			console.log("findProductsForOrder");
			$
					.ajax({
						url : '${pageContext.request.contextPath}/ajax/findProductsForOrder',
						type : "POST",
						data : {
							name : name,
							sku : sku
						},
						complete : function(response) {
							$('#table').html(response.responseText);
						}
					});
		} */
		
		function searchButtonClick()
		{
			pageButtonClick('1');
		}
		
		function pageButtonClick(targetPage)
		{
			var pageSize = ${pageSize};
			var current = targetPage;
			var name = $('#searchName').val();
			var sku = $('#searchSKU').val();		
			console.log("findProductsForOrder");
			$.ajax ({ 
				url: '${pageContext.request.contextPath}${URL_PREFIX}findProductsForOrder', 
				type: "POST", 						
				data : {name:name, sku : sku, current:current, pageSize:pageSize},
				complete: function(response){
					$('#table').html(response.responseText);
				}
			}); 
		}
	</script>

	
</body>
</html>