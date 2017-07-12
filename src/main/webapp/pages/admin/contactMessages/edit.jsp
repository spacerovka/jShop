<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin - watch Message</title>
<%@include file="../resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="../_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">View message</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Messages</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> View</li>
						</ol>
					</div>
				</div>
				
				<div class="row">
					<div class="col-lg-6 ">

						<form:form action="${pageContext.request.contextPath}${URL_PREFIX}contactmessage"
							method="post" modelAttribute="item">
							<div class="form-group">
								<label>Date: <fmt:formatDate pattern="yyyy-MM-dd hh:mm" value="${item.created}" /></label>								
							</div>
							<form:hidden path="id" />

							<div class="form-group">
								<label>User name: ${item.userName}</label>								
							</div>
							
							<div class="form-group">
								<label>Email: ${item.userEmail}</label>								
							</div>
							
							<div class="form-group">
								<label>Theme: ${item.theme}</label>								
							</div>
							
							<div class="form-group">
								<label>Message: </label>
								<form:textarea disabled="true" class="form-control"
									path="comment" style="height:500px;" />
							</div>
							
							
		
							
						</form:form>

					</div>

					<div class="col-lg-6 ">
						
					</div>

				</div>


			</div>
		</div>

	</div>

	<%@include file="../_footer.jsp"%>
	
</body>
</html>