<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


	<!--Items List-->
	<div class="col-xs-12">
		<h2 class="title">Products</h2>
		<table class="items-list">
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
					style="max-width: 150px;"
							src="${pageContext.request.contextPath}/resources/uploads/products/${product.value.productId}/main/${product.value.thumb}" 
							alt="${product.value.product_name}"></a></td>
					<td class="name"><a href="shop-single-item-v1.html">${product.value.product_name}</a></td>
					<td class="price">${product.value.price} $</td>
					<td class="qnt-count"><a class="incr-btn" href="#" onclick="removeQuantity('${product.value.product_SKU}');">-</a> <input
						class="quantity form-control" type="text" value="${product.value.product_quantity}"> <a
						class="incr-btn" href="#" onclick="addQuantity('${product.value.product_SKU}');">+</a></td>
					<td class="total">${product.value.subTotal} $</td>
					<td class="delete"><i class="icon-delete"></i></td>
				</tr>
				</core:forEach>
				
			</tbody>
		</table>
	</div>

	<!--Sidebar-->
	<div class="col-xs-12">
	<hr/>
		<h3>Totals</h3>
		<form class="cart-sidebar" method="post">
			<div class="cart-totals">
				<table>
					<tbody>
						<tr>
							<td>Cart subtotal</td>
							<td class="total align-r">${order.sum} $</td>
						</tr>
						<tr class="devider">
							<td>Shipping</td>
							<td class="align-r">Free shipping</td>
						</tr>
						<tr>
							<td>Order total</td>
							<td class="total align-r">${order.sum} $</td>
						</tr>
					</tbody>
				</table>

				<!-- <h3>Have a coupon?</h3>
				<div class="coupon">
					<div class="form-group">
						<label class="sr-only" for="coupon-code">Enter coupon code</label>
						<input type="text" class="form-control" id="coupon-code"
							name="coupon-code" placeholder="Enter coupon code">
					</div>
					<input type="submit" class="btn btn-primary btn-sm btn-block"
						name="apply-coupon" value="Apply coupon">
				</div> -->

				
			</div>

		</form>
	</div>
