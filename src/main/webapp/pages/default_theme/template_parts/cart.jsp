<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!--Items List-->
<div class="col-lg-9 col-md-9">
	<h2 class="title">Shopping cart</h2>
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
							style="max-width: 90%;"
							src="${pageContext.request.contextPath}/resources/uploads/products/${product.value.productId}/main/${product.value.thumb}"
							alt="${product.value.product_name}"></a></td>
					<td class="name"><a href="shop-single-item-v1.html">${product.value.product_name}</a></td>
					<td class="price">${product.value.price}$</td>
					<td class="qnt-count"><a class="incr-btn" href="#"
						onclick="removeQuantity('${product.value.product_SKU}');">-</a> <input
						class="quantity form-control" type="text"
						value="${product.value.product_quantity}"> <a
						class="incr-btn" href="#"
						onclick="addQuantity('${product.value.product_SKU}');">+</a></td>
					<td class="total">${product.value.subTotal}$</td>
					<td class="delete"><i class="icon-delete" onclick="removeProduct('${product.value.product_SKU}');"></i></td>
				</tr>
			</core:forEach>

		</tbody>
	</table>
</div>

<!--Sidebar-->
<div class="col-lg-3 col-md-3">
	<h3>Cart totals</h3>
	<form class="cart-sidebar" method="post">
		<div class="cart-totals">
			<table>
				<tbody>
					<core:if test="${order.discount>0}">
						<tr>
							<td>Discount:</td>
							<td class="total align-r">${order.discount}%</td>
						</tr>
					</core:if>					
					<tr class="devider">
						<td>Shipping</td>
						<td class="align-r">Free shipping</td>
					</tr>
					<tr>
						<td>Order total</td>
						<td class="total align-r">${order.sum}$</td>
					</tr>
				</tbody>
			</table>

			<h3>Have a coupon?</h3>
			<div class="coupon">
				<div class="form-group">
					<core:if test="${not empty couponError}">
						<div class="alert alert-danger">
							<strong>Oops!</strong> ${couponError}
						</div>
					</core:if>
					<label class="sr-only" for="coupon-code">Enter coupon code</label>
					<input type="text" class="form-control" id="coupon-code"
						name="coupon-code" placeholder="Enter coupon code">
				</div>
				<input type="submit" class="btn btn-primary btn-sm btn-block"
					name="apply-coupon" value="Apply coupon" onclick="addCoupon();">
			</div>

			<a class="btn btn-black btn-block"
				href="${pageContext.request.contextPath}/checkout"
				title="to-checkout">Proceed to checkout</a>
		</div>

		<a class="panel-toggle" href="#calc-shipping">
			<h3>Calculate shipping</h3>
		</a>
		<div class="hidden-panel calc-shipping" id="calc-shipping">
			<div class="form-group">
				<div class="select-style">
					<select name="country">
						<option>Australia</option>
						<option>Belgium</option>
						<option>Germany</option>
						<option>United Kingdom</option>
						<option>Switzerland</option>
						<option>USA</option>
					</select>
				</div>
			</div>
			<div class="form-group">
				<label class="sr-only" for="state">State/ province</label> <input
					type="text" class="form-control" id="state" name="state"
					placeholder="State/ province">
			</div>
			<div class="form-group">
				<label class="sr-only" for="postcode">Postcode/ ZIP</label> <input
					type="text" class="form-control" id="postcode" name="postcode"
					placeholder="Postcode/ ZIP">
			</div>
			<input type="submit" class="btn btn-primary btn-sm btn-block"
				name="update-totals" value="Update totals">
		</div>
	</form>
</div>
