<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>${metaTitle}</title>
<meta name="description" content="${metaDescription}" />
<%@include file="template_parts/resources.jsp"%>
</head>
<body>
	<%@include file="template_parts/navbar.jsp"%>

	<div class="container">

		<div class="row">
			<div class="col-md-3">
				<%@include file="template_parts/left-menu.jsp"%>
			</div>
			<div class="col-md-9">

				<div class="thumbnail">
					<ol class="breadcrumb" itemscope
						itemtype="http://schema.org/BreadcrumbList">
						<li class="breadcrumb-item" itemprop="itemListElement" itemscope
							itemtype="http://schema.org/ListItem"><a href="/" itemscope
							itemtype="http://schema.org/Thing" itemprop="item"><span
								itemprop="name">Home</span>
								<meta itemprop="url"
									content="${pageContext.request.contextPath}/" /></a></li>
						<core:if test="${not empty breadCrumbs}">
							<core:forEach var="breadCrumb" items="${breadCrumbs}">
								<li class="breadcrumb-item" itemprop="itemListElement" itemscope
									itemtype="http://schema.org/ListItem"><a
									href="${pageContext.request.contextPath}/${breadCrumb.categoryURL}"
									itemscope itemtype="http://schema.org/Thing" itemprop="item"><span
										itemprop="name">${breadCrumb.categoryName}</span>
										<meta itemprop="url"
											content="${pageContext.request.contextPath}/${breadCrumb.categoryURL}" /></a>
								</li>
							</core:forEach>
						</core:if>
						<li class="breadcrumb-item" itemprop="itemListElement" itemscope
							itemtype="http://schema.org/ListItem"><span itemscope
							itemtype="http://schema.org/Thing" itemprop="item"><span
								itemprop="name">${product.name}</span>
								<meta itemprop="url"
									content="${pageContext.request.contextPath}/products/${product.url}" /></span>
						</li>
					</ol>


					<noscript>
						<style>
.es-carousel ul {
	display: block;
}
</style>
					</noscript>
					<script id="img-wrapper-tmpl" type="text/x-jquery-tmpl">	
			<div class="rg-image-wrapper">
				{{if itemsCount > 1}}
					<div class="rg-image-nav">
						<a href="#" class="rg-image-nav-prev">Previous Image</a>
						<a href="#" class="rg-image-nav-next">Next Image</a>
					</div>
				{{/if}}
				<div class="rg-image"></div>
				<div class="rg-loading"></div>
				<div class="rg-caption-wrapper">
					<div class="rg-caption" style="display:none;">
						<p></p>
					</div>
				</div>
			</div>
		</script>
					<div id="rg-gallery" class="rg-gallery">
						<div class="rg-thumbs">
							<!-- Elastislide Carousel Thumbnail Viewer -->
							<div class="es-carousel-wrapper">

								<div class="es-carousel">
									<ul>
										<core:if test="${not empty mainImage}">
											<li><a href="#"> <img
													src="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/main/${mainImage}"
													data-large="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/main/${mainImage}"
													alt="${product.name}"
													data-description="${product.shortDesc}">
											</a></li>
										</core:if>
										<core:forEach items="${images}" var="img">
											<li><a href="#"> <img
													src="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/${img}"
													data-large="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/${img}"
													alt="${product.name}"
													data-description="${product.shortDesc}" />
											</a></li>
										</core:forEach>
									</ul>
								</div>
							</div>
							<!-- End Elastislide Carousel Thumbnail Viewer -->
						</div>
						<!-- rg-thumbs -->
					</div>
					<!-- rg-gallery -->


					<div class="caption-full">
						<h4 class="pull-right">$${product.price}</h4>
						<h4>
							<a href="#">${product.name}</a>
						</h4>
						${product.longDesc}
					</div>
					<div class="well">
						<div class="text-right">
							<a style="display: inline-block;" href="#reviews-anchor"
								id="open-review-box" class="btn btn-info">Leave
								a Review</a> 
							<a style="display: inline-block;" rel="nofollow"
								href="#" onclick="addItemToCart('${product.sku}');"
								class="btn btn-success btn-green">Add to cart</a>
							<core:if test="${pageContext.request.userPrincipal.name != null}">	
							
								<core:choose>
									<core:when test="${not savedToWishList}">
										<a style="display: inline-block; color: rosybrown;" rel="nofollow" id="addtowishlist"
											href="#" onclick="addtowishlist('${product.sku}');" class="btn"><i
											class="fa fa-heart"></i></a>
									</core:when>
									<core:otherwise>
										<div style="display: inline-block; color: rgba(186, 50, 195, 0.84);" class="btn"><i
										class="fa fa-heart"></i></div>
									</core:otherwise>
								</core:choose>	
							</core:if>
						</div>
					</div>
					<div class="ratings">
						<p class="pull-right">${fn:length(product.reviews)}reviews</p>
						<p>
							<c:forEach begin="1" end="${product.rating}" varStatus="loop">
								<span class="glyphicon glyphicon-star"></span>
							</c:forEach>
							<c:forEach begin="${product.rating}" end="4" varStatus="loop">
								<span class="glyphicon glyphicon-star-empty"></span>
							</c:forEach>
							${product.rating}.0 stars
						</p>
					</div>
				</div>


				<div class="well">


					<div class="row" id="post-review-box" style="display: none;">
						<div class="col-md-12">
							<form:form method="post" modelAttribute="review" id="reviewForm">

								<form:hidden id="ratings-hidden" path="rating" />
								<form:hidden path="product.id" />
								<div class="col-xs-12 col-md-6">
									<form:input path="userName" class="form-control animated"
										placeholder="Name" />
								</div>
								<div class="col-xs-12 col-md-6">
									<form:input path="userEmail" id="userEmail"
										class="form-control animated" placeholder="Email" />
								</div>
								<div class="col-md-12" style="margin-top: 2rem;">
									<form:textarea path="comment"
										style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 54px;"
										rows="5" id="new-review" class="form-control animated"
										placeholder="Enter your review here..." name="comment"
										cols="50"></form:textarea>
								</div>
								<div class="text-right">
									<div class="stars starrr" data-rating="0"></div>
									<a href="#" class="btn btn-danger btn-sm" id="close-review-box"
										style="margin-right: 10px; display: none;"> <span
										class="glyphicon glyphicon-remove"></span>Cancel
									</a>
									<button class="btn btn-success btn-lg" type="submit">Save</button>
								</div>
							</form:form>
						</div>
					</div>

					<hr>


					<core:forEach items="${product.reviews}" var="productReview">
						<div class="row">
							<div class="col-md-12">
								<c:forEach begin="1" end="${productReview.rating}"
									varStatus="loop">
									<span class="glyphicon glyphicon-star"></span>
								</c:forEach>
								<c:forEach begin="${productReview.rating}" end="4"
									varStatus="loop">
									<span class="glyphicon glyphicon-star-empty"></span>
								</c:forEach>
								${productReview.userName} <span class="pull-right">${productReview.created}</span>
								<p>${productReview.comment}</p>
							</div>
						</div>

						<hr>
					</core:forEach>
				</div>

			</div>
		</div>
	</div>
	<div id="popup" class="popup-wrapper" style="display: none;">
		<div class="popup-content">
			<div class="popup-title">
				<button type="button" class="popup-close">&times;</button>
				<h3></h3>
			</div>
			<div class="popup-body">
				<p id="reviewPostMessage"
					style="text-align: center; font-size: 20px;">Popup body</p>
			</div>
		</div>
	</div>
	<%@include file="template_parts/footer.jsp"%>

	<spring:url value="/resources/default_theme/js/starsrating.js"
		var="starsrating" />
	<script type="text/javascript" src="${starsrating}"></script>



	<script type="text/javascript">
    $(function(){

      // initialize the autosize plugin on the review text area
      $('#new-review').autosize({append: "\n"});

      var reviewBox = $('#post-review-box');
      var newReview = $('#new-review');
      var openReviewBtn = $('#open-review-box');
      var closeReviewBtn = $('#close-review-box');
      var ratingsField = $('#ratings-hidden');

      openReviewBtn.click(function(e)
      {
        reviewBox.slideDown(400, function()
          {
            $('#new-review').trigger('autosize.resize');
            newReview.focus();
          });
        openReviewBtn.fadeOut(100);
        closeReviewBtn.show();
      });

      closeReviewBtn.click(function(e)
      {
        e.preventDefault();
        reviewBox.slideUp(300, function()
          {
            newReview.focus();
            openReviewBtn.fadeIn(200);
          });
        closeReviewBtn.hide();
        
      });

      // If there were validation errors we need to open the comment form programmatically 
      
      // Bind the change event for the star rating - store the rating value in a hidden field
      $('.starrr').on('starrr:change', function(e, value){
        ratingsField.val(value);
        console.log(value);
      });
    });
  </script>

	<script type="text/javascript">	
								$(document).ready(function() {
									var popup = $('#popup').popup({
										 width: 400,
										 height: 130
									 });
									
									$('#reviewForm').submit(function(e) {
										e.preventDefault();
										console.log("submit form");
										
							 
										var str = $("#reviewForm").serialize();
										console.log(str);
										$.ajax({
										    type:"post",		
								            dataType:'text',		
								           	data: str,
										    url:"${pageContext.request.contextPath}/createReview",
										    async: false,										    
										    success : function(data) {
										    	if(data=="SUCCESS"){										    		
										    		document.getElementById('reviewPostMessage').innerHTML = "Review uploaded successfuly!";
										    		popup.open();
										    		$('#close-review-box').click();
										    		
										    	}else{
										    		document.getElementById('reviewPostMessage').innerHTML =data;
										    		popup.open();
										    	}
												console.log("SUCCESS: ", data);
										       
										    }
										});
										
										
							        
									});
								});								
							</script>

	<script>			
							$(document).ready(function(){
								$('#userEmail').mask("A", {
									translation: {
										"A": { pattern: /[\w@\-.+]/, recursive: true }
									}
								});
							});
							</script>

	<script>
function addtowishlist(sku){
		
	$.ajax ({ 
		url: '${pageContext.request.contextPath}/addtowishlist', 
		type: "POST", 						
		data : {sku:sku},
		complete: function(response){
			console.log(response.responseText);			
			$( "#addtowishlist" ).replaceWith( '<div style="display: inline-block; color: rgba(186, 50, 195, 0.84);" class="btn"><i class="fa fa-heart"></i></div>' );
		}
	}); 
}
</script>
	<%@include file="template_parts/add_to_cart_ajax.jsp"%>
</body>
</html>