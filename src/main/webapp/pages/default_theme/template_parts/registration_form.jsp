<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<!--Items List-->
<div class="col-xs-12 col-md-6">
	<form:form action="${pageContext.request.contextPath}/registration"
		method="post" modelAttribute="user">


		<core:if test="${not empty errorSummary}">
			<core:forEach var="error" items="${errorSummary}">
				<div class="alert alert-danger">

					<strong>Error!</strong> ${error}

				</div>
			</core:forEach>
		</core:if>

		<div class="form-group">
			<label>Email*</label>
			<form:input class="form-control" path="email" id="email"
				placeholder="valid@email.com" />
		</div>

		<div class="form-group">
			<label>First Name</label>
			<form:input class="form-control" path="firstName" id="firstName" />
		</div>

		<div class="form-group">
			<label>Last Name</label>
			<form:input class="form-control" path="lastName" id="lastName" />
		</div>
	<h4>Pick a username and a password for your account</h4>
		<div class="form-group  col-xs-12 col-md-6">
			<label>Username/login*</label>
			<form:input class="form-control" placeholder="John R Smith"
				path="username" id="userName" />
		</div>

		<div class="form-group col-xs-12 col-md-6">
			<label>Password*</label>
			<form:input class="form-control" path="password" id="password" />
		</div>
		
		<div class="g-recaptcha col-sm-5"
          data-sitekey="${CAPTCHA_SITE}"></div>
        <span id="captchaError" class="alert alert-danger col-sm-4"
          style="display:none"></span>

		<div class="form-group text-center col-xs-12">
			<button type="submit" class="btn btn-success">Register</button>
		</div>
	</form:form>

</div>



