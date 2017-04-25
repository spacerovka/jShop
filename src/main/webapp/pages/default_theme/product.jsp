<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<title>${metaTitle}</title>
<meta name="description" content="${metaDescription}"/>
<%@include file="template_parts/resources.jsp"%>
</head>
<body>
	<%@include file="template_parts/navbar.jsp"%>

	<div class="container">

		<div class="row">

			<%@include file="template_parts/left-menu.jsp"%>
			
			<div class="col-md-9">

                <div class="thumbnail">
	                <ol class="breadcrumb" itemscope itemtype="http://schema.org/BreadcrumbList">
	                <li class="breadcrumb-item" itemprop="itemListElement" itemscope
     					 itemtype="http://schema.org/ListItem">
      					<a href="/" itemscope itemtype="http://schema.org/Thing"
      						 itemprop="item"><span itemprop="name">Home</span>
      						 <meta itemprop="url" content="http:/${pageContext.request.contextPath}/" /></a>
      				</li>
	                <core:if test="${not empty breadCrumbs}">	                
		        		<core:forEach var="breadCrumb" items="${breadCrumbs}">
		        			<li class="breadcrumb-item" itemprop="itemListElement" itemscope
      							itemtype="http://schema.org/ListItem">
      							<a href="/${breadCrumb.categoryURL}" itemscope itemtype="http://schema.org/Thing"
       							itemprop="item"><span itemprop="name">${breadCrumb.categoryName}</span>
       							<meta itemprop="url" content="http:/${pageContext.request.contextPath}/${breadCrumb.categoryURL}" /></a>
      						</li>
		        		</core:forEach>	        		
	        		</core:if>
	        		<li class="breadcrumb-item" itemprop="itemListElement" itemscope
      					itemtype="http://schema.org/ListItem">
      					<span itemscope itemtype="http://schema.org/Thing"
      					 itemprop="item"><span itemprop="name">${product.name}</span>
      					 <meta itemprop="url" content="http:/${pageContext.request.contextPath}/products/${product.url}" /></span>
      				</li>
	        		</ol>
        		
                                    
                  <noscript>
			<style>
				.es-carousel ul{
					display:block;
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
				                    <li><a href="#">
				                    	<img src="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/main/${mainImage}" data-large="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/main/${mainImage}" alt="${product.name}" data-description="${product.shortDesc}">
				                 	</a></li>
				                  </core:if>
									<core:forEach items="${images}" var="img">
										<li><a href="#">
											<img src="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/${img}" data-large="${pageContext.request.contextPath}/resources/uploads/products/${product.id}/${img}" alt="${product.name}" data-description="${product.shortDesc}" />
										</a></li>
									</core:forEach>
								</ul>
							</div>
						</div>
						<!-- End Elastislide Carousel Thumbnail Viewer -->
					</div><!-- rg-thumbs -->
				</div><!-- rg-gallery -->
                  
                    
                    <div class="caption-full">
                        <h4 class="pull-right">$${product.price}</h4>
                        <h4><a href="#">${product.name}</a>
                        </h4>
                        ${product.longDesc}
					</div>
                    <div class="ratings">
                        <p class="pull-right">3 reviews</p>
                        <p>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            4.0 stars
                        </p>
                    </div>
                </div>

                <div class="well">

                    <div class="text-right">
                        <a class="btn btn-success">Leave a Review</a>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-md-12">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            Anonymous
                            <span class="pull-right">10 days ago</span>
                            <p>This product was great in terms of quality. I would definitely buy another!</p>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-md-12">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            Anonymous
                            <span class="pull-right">12 days ago</span>
                            <p>I've alredy ordered another one!</p>
                        </div>
                    </div>

                    <hr>

                    <div class="row">
                        <div class="col-md-12">
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star"></span>
                            <span class="glyphicon glyphicon-star-empty"></span>
                            Anonymous
                            <span class="pull-right">15 days ago</span>
                            <p>I've seen some better than this, but not at this price. I definitely recommend this item.</p>
                        </div>
                    </div>

                </div>

            </div>
		</div>
	</div>

	<%@include file="template_parts/footer.jsp"%>
</body>
</html>