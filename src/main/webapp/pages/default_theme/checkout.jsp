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

	<div class="container">

		<div class="row" id="cart">//sum in usd form with user info
			fields hiddenField orderId; hiddenField sum;



			input country; 
			input state;
			 input city;
			  input shipAddress; 
			  input zip;

			private String phone; private String email; select List shipName;
			display with AJAX shippingCost;</div>

	</div>


	<%@include file="template_parts/footer.jsp"%>


</body>
</html>
