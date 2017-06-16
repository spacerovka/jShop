<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>${page.metaTitle}</title>
<%@include file="template_parts/resources.jsp"%>
</head>
<body>
	<%@include file="template_parts/navbar.jsp"%>

	<div class="container">

		<!-- Slider -->
<hr>

<!-- Jumbotron Header -->
		<header class="jumbotron hero-spacer">
			${page.content}
		</header>

		
	

	<hr>

	</div>

	<%@include file="template_parts/footer.jsp"%>
	
	
</body>
</html>