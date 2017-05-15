<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin option</title>
<%@include file="resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Edit option group</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Options</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> Edit option group</li>
						</ol>
					</div>
				</div>
				<core:if test="${not empty errorSummary}">

					<div class="alert alert-danger">
						<strong>Error!</strong> ${errorSummary}
					</div>
				</core:if>
				<div class="row">
					<div class="col-lg-6 ">

						<form:form action="${pageContext.request.contextPath}/a/optiongroup"
							method="post" modelAttribute="optiongroup">
							<div class="form-group">
								<label>ID</label>
								<p class="form-control-static">${optiongroup.id}</p>
							</div>
							<form:hidden path="id" />

							<div class="form-group">
								<label>Name*</label>
								<form:input class="form-control" placeholder="Product name"
									path="optionGroupName" id="nameinput" />
							</div>	
							
							<div class="form-group">
								<label>Description</label>
								<form:input class="form-control" placeholder="Description"
									path="description" id="description" />
							</div>							

							

							<button type="submit" class="btn btn-default">Submit
								Button</button>
							<button type="reset" class="btn btn-default">Reset
								Button</button>

						</form:form>

					</div>

					<div class="col-lg-6 ">
						

					</div>
				</div>


			</div>
		</div>

	</div>
	<%@include file="_footer.jsp"%>
	
</body>
</html>