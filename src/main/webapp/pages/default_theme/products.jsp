<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


				
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
											<core:forEach begin="1" end="${product.rating}" varStatus="loop">
												<span class="glyphicon glyphicon-star"></span>
											</core:forEach>
											<core:forEach begin="${product.rating}" end="4" varStatus="loop">
												<span class="glyphicon glyphicon-star-empty"></span>
											</core:forEach>
										</p>
									</div>
									<div style="padding: 1rem;">
										<a class="btn btn-info" data-quantity="1" data-product_sku="" data-product_id="70" 
											rel="nofollow" href="#"
											onclick="addItemToCart('${product.sku}');">Add to cart</a>
										
									</div>
								</div>
							</div>


						</core:forEach>
					</core:if>



				
			
