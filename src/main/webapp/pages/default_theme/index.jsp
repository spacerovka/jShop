<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="template_parts/resources.jsp"%>
</head>
<body>	
<%@include file="template_parts/navbar.jsp"%>

	<div class="container">

		<!-- Jumbotron Header -->
		<header class="jumbotron hero-spacer">
			<h1>A Warm Welcome!</h1>
			<p>Lorem ipsum dolor sit amet, consectetur adipisicing elit.
				Ipsa, ipsam, eligendi, in quo sunt possimus non incidunt odit vero
				aliquid similique quaerat nam nobis illo aspernatur vitae fugiat
				numquam repellat.</p>
			<p>
				<a class="btn btn-primary btn-large">Call to action!</a>
			</p>
		</header>

		<hr>

		<!-- Title -->
		<div class="row">
			<div class="col-lg-12">
				<h3>Latest Features</h3>
			</div>
		</div>
		<!-- /.row -->

		<!-- Page Features -->
		<div class="row text-center">
			<core:if test="${not empty products}">
				<core:forEach var="product" items="${products}">
							<div class="col-md-3 col-sm-6 hero-feature">
								<div class="thumbnail">
									<img src="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/main/${product.image}" alt="${product.name}">
									<div class="caption">
										<h3>${product.name}</h3>
										<p>${product.shortDesc}</p>
										<p>
											<a href="#" class="btn btn-primary">Buy Now!</a> 
											<a href="/products/${product.url}" class="btn btn-default">More Info</a>
										</p>
									</div>
								</div>
							</div>
				</core:forEach>
			</core:if>
			

		</div>
		<!-- /.row -->

		<hr>
		
	</div>



	<h1
		style="top: 50%; left: 20%; font-size: 60px; color: red; background: rgb(255, 255, 255) none repeat scroll 0% 0%; padding: 30px; opacity: 0.8;">
		Hello World! <a href="${pageContext.request.contextPath}/login">Login
			to Journal</a>
	</h1>
	
	<%@include file="template_parts/footer.jsp"%>
</body>
</html>