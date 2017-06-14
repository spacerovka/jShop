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
<title>${SITE_NAME}Registration</title>
<%@include file="template_parts/resources.jsp"%>
</head>
<body>
	<%@include file="template_parts/navbar.jsp"%>
	<section class="shopping-cart">
		<div class="container">

			<c:choose>
				<c:when test="${not empty flashMessage}">
					<h1>${flashMessage}</h1>
				</c:when>

				<c:otherwise>
					
					<div class="row cart-row" id="cart">
						<h1>Register new user</h1>
						<%@include file="template_parts/registration_form.jsp"%>
					</div>
				</c:otherwise>

			</c:choose>

		</div>

	</section>
	<%@include file="template_parts/footer.jsp"%>

</body>
</html>
