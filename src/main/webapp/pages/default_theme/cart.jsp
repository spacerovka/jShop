<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="template_parts/resources.jsp"%>
</head>
<body>
	<%@include file="template_parts/navbar.jsp"%>
	<section class="shopping-cart">
		<div class="container">
			<c:choose>
				<c:when test="${not empty order}">
					<div class="row cart-row" id="cart">
						<%@include file="template_parts/cart.jsp"%>
					</div>
				</c:when>
				
				<c:otherwise>
					<header class="jumbotron hero-spacer">
						<h1>Your cart is empty!</h1>
					</header>
				</c:otherwise>
				
			</c:choose>
		</div>

	</section>
	<%@include file="template_parts/footer.jsp"%>

	<script>
		$('.panel-toggle').click(function(e) {
			$(this).toggleClass('active');
			var $target = $(this).attr('href');
			$($target).toggleClass('expanded');
			e.preventDefault();
		});

		function addQuantity(sku) {
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
		
		function addShipping(){
			var country = $('#country').val();
			$.ajax({
				url : '${pageContext.request.contextPath}/addShipping',
				type : "POST",
				dataType : "text",
				data : {
					country : country
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
		}
		
		function removeProduct(sku) {
			console.log("removeQuantity");
			$.ajax({
				url : '${pageContext.request.contextPath}/removeProduct',
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

		function updateCartItemCount() {
			$.ajax({
				url : '${pageContext.request.contextPath}/updateCartItemCount',
				type : 'GET',
				dataType : 'text',
				contentType : "application/json",
				complete : function(responseData, status, xhttp) {
					$('#cart-item-count').html(
							'(' + responseData.responseText + ')');
				}
			});
		}
		
		function addCoupon() {
			console.log("addCoupon");
			var code = $("#coupon-code").val();
			$.ajax({
				url : '${pageContext.request.contextPath}/addCoupon',
				type : "POST",
				dataType : "text",
				data : {
					code : code
				},
				complete : function(response) {
					$('#cart').html(response.responseText);
					updateCartItemCount();
				}
			});
		}
		updateCartItemCount();
	</script>
</body>
</html>
