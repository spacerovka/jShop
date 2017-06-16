<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
	<table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>                                       
                                        <th>URL</th>    
                                        <th>Created</th>                                    
                                        <th>Active</th>
                                        <th>Buttons</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <core:choose>
	        						<core:when test="${not empty pagesList}">
                                	<core:forEach var="page" items="${pagesList}">
                                	<tr>
                                        <td>${page.id}</td>
                                        <td>${page.name}</td>                                                                              
                                        <td>${page.url}</td>
                                        <td>${page.created}</td>
                                        <td>
                                        	<core:choose>
	        									<core:when test="${page.status==true}">
	        										Active
	        									</core:when>
								  				<core:otherwise>
								  					Hidden
								  				</core:otherwise>
								  			</core:choose>
								  		</td>
								  		<td>
								  		<a href="${pageContext.request.contextPath}/a/page/${page.id}/update">
								  		<button type="button" class="btn btn-sm btn-info">Edit</button></a>
								  		
								  		<a href="${pageContext.request.contextPath}/a/page/${page.id}/delete">
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
