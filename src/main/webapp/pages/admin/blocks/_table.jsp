<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
	<table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Type</th>                                       
                                        <th>URL</th>    
                                        <th>Content</th>                                    
                                        <th>Active</th>
                                        <th>Buttons</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <core:choose>
	        						<core:when test="${not empty blockList}">
                                	<core:forEach var="block" items="${blockList}">
                                	<tr>
                                        <td>${block.id}</td>
                                        <td>${block.position}</td>                                                                              
                                        <td>
                                        	<core:choose>
	        									<core:when test="${empty block.blockURL}">
	        										Display on all pages
	        									</core:when>
								  				<core:otherwise>
								  					${block.blockURL}
								  				</core:otherwise>
								  			</core:choose>
                                        
                                        </td>
                                        <td><core:out value="${block.content}" /></td>
                                        <td>
                                        	<core:choose>
	        									<core:when test="${block.status==true}">
	        										Active
	        									</core:when>
								  				<core:otherwise>
								  					Hidden
								  				</core:otherwise>
								  			</core:choose>
								  		</td>
								  		<td>
								  		<a href="${pageContext.request.contextPath}${URL_PREFIX}block/${block.id}/update">
								  		<button type="button" class="btn btn-sm btn-info">Edit</button></a>
								  		
								  		<a href="${pageContext.request.contextPath}${URL_PREFIX}block/${block.id}/delete">
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
<%@include file="../_paginator.jsp"%>