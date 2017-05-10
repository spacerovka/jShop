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
					<core:if test="${not empty products}">
						<core:forEach var="product" items="${products}">
							<div class="col-sm-4 col-lg-4 col-md-4">
								<div class="thumbnail">
									<img
										src="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/main/${product.image}"
										alt="${product.name}" style="height: 150px;">
									<div class="caption">
										<h4 class="pull-right">
											$
											<core:out value="${product.price}" />
										</h4>
										<h4>
											<a
												href="${pageContext.request.contextPath}/products/${product.url}"><core:out
													value="${product.name}" /></a>
										</h4>
										<p>${product.shortDesc}</p>
									</div>
									<div class="ratings">
										<p class="pull-right">${fn:length(product.reviews)}
											reviews</p>
										<p>
											<c:forEach begin="1" end="${product.rating}" varStatus="loop">
												<span class="glyphicon glyphicon-star"></span>
											</c:forEach>
											<c:forEach begin="${product.rating}" end="4" varStatus="loop">
												<span class="glyphicon glyphicon-star-empty"></span>
											</c:forEach>
										</p>
									</div>
								</div>
							</div>


						</core:forEach>
					</core:if>



				</div>

			</div>

		</div>

	</div>


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
</body>
</html>
