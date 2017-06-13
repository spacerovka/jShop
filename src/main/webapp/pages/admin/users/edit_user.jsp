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
<title>Admin - Edit User</title>
<%@include file="../resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="../_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Edit user</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Users</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> Edit user</li>
						</ol>
					</div>
				</div>
				<core:if test="${not empty errorMessage}">

					<div class="alert alert-danger">
						<strong>Warning!</strong> ${errorMessage}
					</div>
				</core:if>
				<div class="row">
					<div class="col-lg-6 ">

						<form:form action="${pageContext.request.contextPath}/a/user"
							method="post" modelAttribute="user">
							<core:if test="${user.id!=null}">
								<div class="form-group">
									<label>ID</label>
									<p class="form-control-static">${user.id}</p>
								</div>
							</core:if>
							<form:hidden path="id" />

							<div class="form-group">
								<label>Username(login)*</label>
								<form:input class="form-control"
									placeholder="Username for login" path="userName" id="userName" />
							</div>

							<div class="form-group">
								<label>Email*</label>
								<form:input class="form-control"
									placeholder="Username for login" path="email" id="email" />
							</div>
							<core:if test="${user.id==null}">
								<div class="form-group">
									<label>Password*</label>
									<form:input class="form-control"
										placeholder="Username for login" path="password" id="password" />
								</div>
							</core:if>

														
							<div class="form-group">
								<form:checkboxes items="${roleList}" path="userRole"
									itemLabel="role" itemValue="role" delimiter="<br/>" />
							</div>

							<div class="form-group">
								<label>Status</label>
								<div class="radio">
									<label> <form:radiobutton path="enabled" value="true" />Active
										<%-- <form:input name="optionsRadios" id="optionsRadios1" value="true" checked="true" type="radio" path="status"/>Active --%>
									</label>
								</div>
								<div class="radio">
									<label> <form:radiobutton path="enabled" value="false" />Hidden
										<%-- <form:input name="optionsRadios" id="optionsRadios2" value="false" type="radio" path="status"/>Hidden --%>
									</label>
								</div>
							</div>


							<div class="form-group">
								<label>First Name</label>
								<form:input class="form-control" path="firstName" id="firstName" />
							</div>

							<div class="form-group">
								<label>Last Name</label>
								<form:input class="form-control" path="lastName" id="lastName" />
							</div>


							<div class="form-group">
								<label>City</label>
								<form:input class="form-control" path="city" id="city" />
							</div>


							<div class="form-group">
								<label>State</label>
								<form:input class="form-control" path="state" id="state" />
							</div>


							<div class="form-group">
								<label>ZIP</label>
								<form:input class="form-control" path="zip" id="zip" />
							</div>

							<form:hidden path="emailVerified" />
							<form:hidden path="registerDate" />
							<form:hidden path="ip" />


							<div class="form-group">
								<label>Phone</label>
								<form:input class="form-control" path="phone" id="phone" />
							</div>


							<div class="form-group">
								<label>Address</label>
								<form:input class="form-control" path="address" id="address" />
							</div>


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