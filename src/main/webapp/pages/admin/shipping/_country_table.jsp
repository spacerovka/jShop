<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	
<table class="table table-hover" >
                                <thead>
                                    <tr>
                                        <th>ID</th>                                        
                                        <th>Name</th>
                                        <th>Base Tarif</th>                                        
                                        <th>Buttons</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <core:choose>
	        						<core:when test="${not empty list}">
                                	<core:forEach var="item" items="${list}">
                                	<tr>
                                        <td>${item.id}</td>
                                        <td>${item.name}</td>
                                        <td>${item.basetarif}$</td>                                        
                                       
								  		<td>
								  		<a href="${pageContext.request.contextPath}${URL_PREFIX}country/${item.id}/update">
								  		<button type="button" class="btn btn-sm btn-info">Edit</button></a>
								  		
								  		<a href="${pageContext.request.contextPath}${URL_PREFIX}country/${item.id}/delete">
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