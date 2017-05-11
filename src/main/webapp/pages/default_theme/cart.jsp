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

		<div class="row" id="cart">			

			<%@include file="template_parts/cart.jsp"%>

		</div>

	</div>


	<%@include file="template_parts/footer.jsp"%>
	
	<script>
		
	function addQuantity(sku)
	{
		console.log("removeQuantity");
		$.ajax ({ 
			url: '${pageContext.request.contextPath}/addQuantity', 
			type: "POST", 
			dataType: "text",			
			data : sku,
			complete: function(response){
				$('#cart').html(response);
			}
		}); 
	}
	
	function removeQuantity(sku)
	{
		console.log("removeQuantity");
		$.ajax ({ 
			url: '${pageContext.request.contextPath}/removeQuantity', 
			type: "POST", 
			dataType: "text",			
			data : sku,
			complete: function(response){
				$('#cart').html(response);				
			}
		}); 
	}
	
	
	</script>
</body>
</html>
