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
						<h1 class="page-header">Top menu items</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Dashboard</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> Tables</li>
						</ol>
					</div>
				</div>				
				<core:if test="${not empty flashMessage}">
				<div class="alert alert-success">
                    <strong>Request success!</strong> ${flashMessage}
                </div>
                </core:if>
				<div class="row">
				
                    <div class="col-lg-8">
                    <a href="${pageContext.request.contextPath}/a/menu/add"><button type="button" class="btn btn-primary">Add new menu item</button></a>
                        <h2>List of categories</h2>
                        <div class="table-responsive">
                            <table class="table table-hover">
                                <thead>
                                    <tr>
                                        <th>ID</th>
                                        <th>Type</th>
                                        <th>Name</th>
                                        <th>URL</th>                                        
                                        <th>Active</th>
                                        <th>Buttons</th>
                                    </tr>
                                </thead>
                                <tbody>
                                <core:choose>
	        						<core:when test="${not empty menuItemList}">
                                	<core:forEach var="menuItem" items="${menuItemList}">
                                	<tr>
                                        <td>${menuItem.id}</td>
                                        <td>${menuItem.type}</td>
                                        <td>${menuItem.text}</td>                                        
                                        <td>${menuItem.URL}</td>
                                        <td>
                                        	<core:choose>
	        									<core:when test="${menuItem.status==true}">
	        										Active
	        									</core:when>
								  				<core:otherwise>
								  					Hidden
								  				</core:otherwise>
								  			</core:choose>
								  		</td>
								  		<td>
								  		<a href="${pageContext.request.contextPath}/a/menu/${menuItem.id}/update">
								  		<button type="button" class="btn btn-sm btn-info">Edit</button></a>
								  		
								  		<a href="${pageContext.request.contextPath}/a/menu/${menuItem.id}/delete">
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
	<%@include file="../template_parts/footer.jsp"%>
</body>
</html>