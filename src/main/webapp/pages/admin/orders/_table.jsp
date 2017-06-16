<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<table class="table table-hover" >
                                <thead>
                                    <tr>
                                        <th>Number</th>
                                        <th>Client Name</th>
                                        <th>Client Phone</th>
                                        <th>Client Email</th>
                                        <th>Sum</th>                                        
                                        <th>Date</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <core:choose>
	        						<core:when test="${not empty orders}">
                                	<core:forEach var="order" items="${orders}">
                                	<tr>
                                        <td>${order.number}</td>
                                        <td>${order.userName}</td>
                                        <td>${order.phone}</td>
                                        <td>${order.email}</td>
                                        <td>${order.sum}</td>
                                        <td>${order.date}</td>                                       
								  		<td>
								  		<a href="${pageContext.request.contextPath}/a/order/${order.orderId}/update">
								  		<button type="button" class="btn btn-sm btn-info">Edit</button></a>
								  		
								  		<a href="${pageContext.request.contextPath}/a/order/${order.orderId}/delete">
								  		<button type="button" class="btn btn-sm btn-danger">Delete</button></a>
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