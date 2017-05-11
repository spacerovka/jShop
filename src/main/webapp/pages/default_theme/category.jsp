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

		<div class="row">
			<div class="col-md-3">
			<%@include file="template_parts/left-menu.jsp"%>
			<%@include file="template_parts/filters.jsp"%>
			</div>

			<div class="col-md-9">

				<ol class="breadcrumb" itemscope
					itemtype="http://schema.org/BreadcrumbList">
					<li class="breadcrumb-item" itemprop="itemListElement" itemscope
						itemtype="http://schema.org/ListItem"><a href="/" itemscope
						itemtype="http://schema.org/Thing" itemprop="item"><span
							itemprop="name">Home</span>
							<meta itemprop="url"
								content="${pageContext.request.contextPath}/" /></a></li>
					<core:if test="${not empty breadCrumbs}">
						<core:forEach var="breadCrumb" items="${breadCrumbs}" varStatus="loop">

							<c:if test="${!loop.last}">
								<li class="breadcrumb-item" itemprop="itemListElement" itemscope
									itemtype="http://schema.org/ListItem"><a
									href="${pageContext.request.contextPath}/${breadCrumb.categoryURL}" itemscope
									itemtype="http://schema.org/Thing" itemprop="item"><span
										itemprop="name">${breadCrumb.categoryName}</span>
										<meta itemprop="url"
											content="${pageContext.request.contextPath}/${breadCrumb.categoryURL}" /></a>
								</li>

							</c:if>
							<c:if test="${loop.last}">
								<li class="breadcrumb-item" itemprop="itemListElement" itemscope
									itemtype="http://schema.org/ListItem"><span itemscope
									itemtype="http://schema.org/Thing" itemprop="item"><span
										itemprop="name">${breadCrumb.categoryName}</span>
										<meta itemprop="url"
											content="${pageContext.request.contextPath}/${breadCrumb.categoryURL}" /></span>
								</li>
							</c:if>
						</core:forEach>
					</core:if>

				</ol>

				<div class="row" id="products">
					<%@include file="products.jsp"%>
				</div>

			</div>

		</div>

	</div>
	
	<%@include file="template_parts/popup_add_to_cart.jsp"%>
	<%@include file="template_parts/footer.jsp"%>


	<script>
	function searchAjax() {
		console.log("Search clicked!");
		var filters = [];
	     $('#filters :checked').each(function() {
	       filters.push($(this).val());
	     });
	    
		
		var a = [];
		a[0] = filters.toString();
		a[1] = '${childCategories}';
		console.log(a);
		$.ajax({		    
		    type: 'POST',		    	
            dataType:'text',		
            data : {
                myArray: a 
            },
		    url:"${pageContext.request.contextPath}/chooseFilter",		    
		    success : function(response) {   

		    	$('#products').html(response);
		    	window.history.pushState(filters.toString(), '${title}', '${pageContext.request.contextPath}/${categoryURL}/filters='+filters.toString());

		    },      
		    error : function(){
		        alert("error");
		    }
		});
	}	
	</script>
	
	<%@include file="template_parts/add_to_cart_ajax.jsp"%>	
	
	
</body>
</html>
