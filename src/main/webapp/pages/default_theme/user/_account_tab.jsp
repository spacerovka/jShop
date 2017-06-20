<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<div class="container-fluid">


	<core:if test="${not empty errorMessage}">

		<div class="alert alert-danger">
			<strong>Warning!</strong> ${errorMessage}
		</div>
	</core:if>
	<div class="row">
		<div class="col-lg-6 ">
			
				<div id="errormsg" style="display: none"></div>

			<div class="form-group">
			<label>Old Password</label>
				<input id="oldpass" name="oldpassword" type="password"
					class="form-control" placeholder="Old Password" />
			</div>
			<div class="form-group">
			<label>New Password</label>
				<input id="pass" name="password" type="password"
					class="form-control" placeholder="New Password" />
			</div>
			<div class="form-group">
			<label>Conform new Password</label>
				<input id="passConfirm" type="password" class="form-control"
					placeholder="Conform new Password" /> <span id="error"
					style="display: none">Password mismatch</span>

			</div>
			<button class="btn btn-success" onclick="savePass()">Change
				Password</button>
			<hr class="underline" />
		</div>
		<div class="col-lg-6 ">

			<form:form
				action="${pageContext.request.contextPath}/user/updateUser"
				method="post" modelAttribute="user">

				<form:hidden path="id" />

				<form:hidden path="username" />

				<div class="form-group">
					<label>Email</label>
					<form:input class="form-control" placeholder="Username for login"
						path="email" id="email" />
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


				<div class="form-group">
					<label>Phone</label>
					<form:input class="form-control" path="phone" id="phone" />
				</div>


				<div class="form-group">
					<label>Address</label>
					<form:input class="form-control" path="address" id="address" />
				</div>


				<button type="submit" class="btn btn-success">Save changes</button>

			</form:form>

		</div>

	</div>


</div>

