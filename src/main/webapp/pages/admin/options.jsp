<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Options</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Options</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> List All</li>
						</ol>
					</div>
				</div>				
				<core:if test="${not empty flashMessage}">
				<div class="alert alert-success">
                    <strong>Request success!</strong> ${flashMessage}
                </div>
                </core:if>
				<div class="row">
				
                    <div class="col-lg-12">
                    <a href="${pageContext.request.contextPath}/a/optiongroup/add">
                    <button type="button" class="btn btn-primary">Add new option group</button></a>
                    
                    <a href="${pageContext.request.contextPath}/a/option/add">
                    <button type="button" class="btn btn-primary">Add new option</button></a>
                    
                        <h2>List of option groups</h2>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Description</th>                                        
                                        <th>Buttons</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <core:choose>
	        						<core:when test="${not empty optiongroupList}">
                                	<core:forEach var="optiongroup" items="${optiongroupList}">
                                	<tr>
                                        <td>${optiongroup.id}</td>
                                        <td>${optiongroup.optionGroupName}</td>
                                        <td>${optiongroup.description}</td>                                       
								  		<td>
								  		<a href="${pageContext.request.contextPath}/a/optiongroup/${optiongroup.id}/update">
								  		<button type="button" class="btn btn-sm btn-info">Edit</button></a>
								  		
								  		<a href="${pageContext.request.contextPath}/a/optiongroup/${optiongroup.id}/delete">
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
                        </div>
                        
                        <h2>List of options</h2>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Name</th>
                                        <th>Group</th>                                                                          
                                        <th>Buttons</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <core:choose>
	        						<core:when test="${not empty optionList}">
                                	<core:forEach var="option" items="${optionList}">
                                	<tr>
                                        <td>${option.id}</td>
                                        <td>${option.optionName}</td>
                                        <td>${option.optionGroup.optionGroupName}</td>                                       
								  		<td>
								  		<a href="${pageContext.request.contextPath}/a/option/${option.id}/update">
								  		<button type="button" class="btn btn-sm btn-info">Edit</button></a>
								  		
								  		<a href="${pageContext.request.contextPath}/a/option/${option.id}/delete">
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
                        </div>
                    </div>
                    
                </div>


			</div>
		</div>

	</div>
	<%@include file="_footer.jsp"%>
</body>
</html>