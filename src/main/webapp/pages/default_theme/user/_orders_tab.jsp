<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<h3>My orders</h3>
<div>
	<core:if test="${not empty orders}">
		<core:forEach var="order" items="${orders}">
			<div class="row box">
				<hr class="underline">
				<h2 class="intro-text text-center">

					<strong>Order № ${order.number} placed <fmt:formatDate
							value="${order.date}" pattern="yyyy-MM-dd HH:mm" /></strong>
				</h2>
				<hr class="underline">
				<div class="col-md-3 col-xs-12">
					<h2 class="title">
						Sum: <b><core:out value="${order.sum}" />$</b>
					</h2>

					<p>
						Date: <b><fmt:formatDate value="${order.date}"
								pattern="yyyy-MM-dd HH:mm" /></b>
					</p>

					<p>
						Delivery address : <b>${order.country}, <core:if
								test="${not empty order.state}">${order.state},</core:if>
							${order.city}, ${order.shipAddress}, ${order.zip}
						</b>
					</p>

					<p>
						Status:
						<core:choose>
							<core:when test="${order.confirmed}">
								<b>Confirmed</b>
							</core:when>
							<core:when test="${order.shipped}">
								<b>Shipped</b>
							</core:when>
							<core:otherwise>
								<b>Processing by manager</b>
							</core:otherwise>
						</core:choose>

					</p>

					<p>
						Tracking Number: <b><core:out value="${order.trackNumber}" /></b>
					</p>

					<hr class="visible-xs">
				</div>



				<div class="col-md-9 col-xs-12">

					<table class="items-list" style="width: 100%;">
						<tbody>
							<tr>
								<th>&nbsp;</th>
								<th>Product name</th>
								<th>Product price</th>
								<th>Quantity</th>
								<th>Total</th>
							</tr>
							<!--Item-->
							<core:forEach items="${order.product_list}" var="product">
								<tr class="item first">
									<td class="thumb"><a href="shop-single-item-v1.html"><img
											style="max-width: 200px;"
											src="${pageContext.request.contextPath}/resources/uploads/products/${product.value.productId}/main/${product.value.thumb}"
											alt="${product.value.product_name}"></a></td>
									<td class="name"><a href="shop-single-item-v1.html">${product.value.product_name}</a></td>
									<td class="price">${product.value.price}$</td>
									<td class="qnt-count">${product.value.product_quantity}"</td>
									<td class="total">${product.value.subTotal}$</td>
									<td class="delete"><i class="icon-delete"></i></td>
								</tr>
							</core:forEach>

						</tbody>
					</table>
				</div>
				<hr class="underline">
			</div>
		</core:forEach>
	</core:if>
	<div class="row box">
		<%@include file="orders_paginator.jsp"%>
	</div>
	<hr>

</div>