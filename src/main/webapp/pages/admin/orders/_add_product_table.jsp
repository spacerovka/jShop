<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>SKU</th>
                                        <th>Name</th>
                                        <th>Price</th>
                                        <th>In Stock</th>
                                        <th>Category</th>
                                        <th>Buttons</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <core:choose>
	        						<core:when test="${not empty productList}">
                                	<core:forEach var="product" items="${productList}">
                                	<tr>
                                        <td>${product.sku}</td>
                                        <td>${product.name}</td>
                                        <td>${product.price}</td>
                                        <td>${product.instock}</td>
                                        <td><core:choose>
	        									<core:when test="${product.category!=null}">
	        										${product.category.categoryName}(id:${product.category.id})
	        									</core:when>
								  				<core:otherwise>
								  					-
								  				</core:otherwise>
								  			</core:choose>
								  		</td>                                        								  		
								  		<td>
								  		<a onclick="addProduct(${product.id},${order.id}});">
								  		<button type="button" class="btn btn-sm btn-info">Add to order</button></a>
								  		</td>
                                    </tr>
                                	</core:forEach>
                                </core:when>
                                <core:otherwise>
                                <td colspan="5" style="text-align:center">No data found</td>
                                </core:otherwise>
                                </core:choose>                      
                                    
                                    
                                </tbody>
                            </table>
                            <%@include file="../_paginator.jsp"%>