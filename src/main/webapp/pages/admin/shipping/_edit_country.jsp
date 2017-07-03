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
<title>Admin category</title>
<%@include file="../resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="../_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Edit item</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Countries</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> Edit</li>
						</ol>
					</div>
				</div>
				<core:if test="${not empty errorSummary}">
					<core:forEach var="error" items="${errorSummary}">
						<div class="alert alert-danger">

							<strong>Error!</strong> ${error}

						</div>
					</core:forEach>
				</core:if>
				<div class="row">
					<div class="col-lg-6 ">

						<form:form action="${pageContext.request.contextPath}${URL_PREFIX}country"
							method="post" modelAttribute="country">
							<div class="form-group">
								<label>ID</label>
								<p class="form-control-static">${country.id}</p>
							</div>
							<form:hidden path="id" />

							<div class="form-group">
								<label>Name</label>
								<form:input class="form-control"
									path="name" id="nameinput" />
							</div>
							
							
							
							<div class="form-group">
								<label>Base tarif (sum cost is calculated of base tariff + cost per each product based on size)</label>
								<form:input class="form-control"
									path="basetarif" id="basetarif" />
							</div>
							
							<core:forEach var="cost" items="${country.costList}">
							//hidden id
							// hidden size.id
							//field for cost							
							</core:forEach>


							<button type="submit" class="btn btn-default">Submit
								Button</button>
							<button type="reset" class="btn btn-default">Reset
								Button</button>

						</form:form>

					</div>

					
				</div>


			</div>
		</div>

	</div>

	<%@include file="../_footer.jsp"%>
	
</body>
</html>