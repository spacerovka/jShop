<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Order info</title>
<%@include file="../template_parts/resources.jsp"%>
</head>
<body>
	<!-- style="background-image:url('http://s57.radikal.ru/i156/1410/ba/c69a205d33d5.jpg');
background-size:cover;"> -->
	<%@include file="../template_parts/navbar.jsp"%>

	<div class="container">
		<core:if test="${not empty orders}">
			<core:forEach var="order" items="${orders}">
				<div class="row box">
					<div class="col-lg-12">
						<hr class="underline">
						<h2 class="intro-text text-center">
							<strong>Summary info</strong>
						</h2>
						<hr class="underline">

						<hr class="visible-xs">
						<p>
							Order id is <b><core:out value="${order.orderId}" /></b>
						</p>
						<p>
							User is <b>${order.username}</b>
						</p>
						<p>
							Order number is <b><core:out value="${order.number}" /></b>
						</p>
						<p>
							Order sum is <b><core:out value="${order.sum}" />$</b>
						</p>
					</div>
				</div>


				<core:forEach items="${order.product_list}" var="product">

					<div class="row box">
						<div class="col-lg-12">
							<hr class="underline">
							<h2 class="intro-text text-center">
								<strong><core:out value="${product.value.product_name}" /></strong>
							</h2>
							<hr class="underline">
							<p>
								Id: <b><core:out value="${product.value.productId}" /></b>
							</p>

							<p>
								Price: <b><core:out value="${product.value.price}" />$</b>
							</p>
							<p>
								Quantity: <b><core:out
										value="${product.value.product_quantity}" /></b>
							</p>
						</div>
					</div>
				</core:forEach>
			</core:forEach>
		</core:if>

		<hr>

	</div>



	<%@include file="../template_parts/footer.jsp"%>
</body>
</html>