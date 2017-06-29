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
<title>Admin - edit Discount</title>
<%@include file="../resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="../_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Edit discount</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Discounts</a>
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

						<form:form action="${pageContext.request.contextPath}/a/discount"
							method="post" modelAttribute="discount">
							<div class="form-group">
								<label>ID</label>
								<p class="form-control-static">${discount.id}</p>
							</div>
							<form:hidden path="id" />

							<div class="form-group">
								<label>Sale name*</label>
								<form:input class="form-control" placeholder="Spring sale 20%"
									path="salename" id="nameinput" />
							</div>
							
							<div class="form-group">
								<label>Discount percents*</label>
								<form:input class="form-control" placeholder="20"
									path="discount" id="nameinput" />
							</div>
							
							<div class="form-group">
								<label>Coupon code*</label>
								<form:input class="form-control" placeholder="#get_my_spring"
									path="coupon" id="nameinput" />
							</div>
							
							<div class="form-group">
								<label>Status</label>
								<div class="radio">
									<label> <form:radiobutton path="status" value="true" />Active
										<%-- <form:input name="optionsRadios" id="optionsRadios1" value="true" checked="true" type="radio" path="status"/>Active --%>
									</label>
								</div>
								<div class="radio">
									<label> <form:radiobutton path="status" value="false" />Hidden
										<%-- <form:input name="optionsRadios" id="optionsRadios2" value="false" type="radio" path="status"/>Hidden --%>
									</label>
								</div>
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

	<%@include file="../_footer.jsp"%>
	
</body>
</html>