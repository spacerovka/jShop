<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="template_parts/resources.jsp"%>
</head>
<body>
	<%@include file="template_parts/navbar.jsp"%>

	<div class="container">

		<div class="row" id="cart">
			<header class="jumbotron hero-spacer col-xs-12">
				<core:choose>

					<core:when test="${not empty item}">

						<h2>Contact Us:</h2>
						<core:if test="${not empty errorSummary}">
							<core:forEach var="error" items="${errorSummary}">
								<div class="alert alert-danger">

									<strong>Error!</strong> ${error}

								</div>
							</core:forEach>
						</core:if>

						<form:form action="${pageContext.request.contextPath}/contact"
							method="post" modelAttribute="item">
							
							<div class="form-group">
								<label>User name: </label>
								<form:input path="userName" class="form-control" />
							</div>

							<div class="form-group">
								<label>Email:</label>
								<form:input path="userEmail" class="form-control" />
							</div>
							
							<div class="form-group">
								<label>Theme: </label>
								<form:input path="theme" class="form-control" />
							</div>

							<div class="form-group">
								<label>Comment: </label>
								<form:textarea style="height:500px;" class="form-control"
									path="comment" />
							</div>

							<div class="form-group">
								<label>Date: ${item.created}</label>
							</div>


							<button type="submit" class="btn btn-success">Submit
								Button</button>
							<button type="reset" class="btn btn-info">Reset
								Button</button>

						</form:form>
					</core:when>

					<core:otherwise>
						<h2>Your message is send!</h2>
					</core:otherwise>

				</core:choose>

			</header>
		</div>
	</div>
	<%@include file="template_parts/footer.jsp"%>


</body>
</html>
