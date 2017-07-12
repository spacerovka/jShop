<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>User Name</th>
                                        <th>Email</th>
                                        <th>Theme</th>
                                        <th>Status</th>
                                        <th>Buttons</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <core:choose>
	        						<core:when test="${not empty list}">
                                	<core:forEach var="item" items="${list}">
                                	<tr>
                                        <td>${item.id}</td>
                                        <td>${item.userName}</td>
                                        <td>${item.userEmail}</td>      
                                        <td>${item.theme}</td>                                     
                                        <td>
                                        	<core:choose>
	        									<core:when test="${item.watched==true}">
	        										Watched
	        									</core:when>
								  				<core:otherwise>
								  					New
								  				</core:otherwise>
								  			</core:choose>
								  		</td>
								  		<td>
								  		<a href="${pageContext.request.contextPath}${URL_PREFIX}contactmessage/${item.id}/update">
								  		<button type="button" class="btn btn-sm btn-info">Edit</button></a>
								  		
								  		<a href="${pageContext.request.contextPath}${URL_PREFIX}contactmessage/${item.id}/delete">
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