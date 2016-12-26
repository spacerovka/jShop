<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
	<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<spring:url value="/resources/js/bootstrap.min.js" var="bootstrapJS" />
<script src="${bootstrapJS}"></script>
<spring:url value="/resources/js/ajax.js" var="ajaxJS" />
<script src="${ajaxJS}"></script>

<footer class="footer">
	<div class="container">
		
		<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
			<p class="text-muted"><b>You are logged in as: </b>
			<security:authentication property="principal.username" />
  with the role of <security:authentication
				property="principal.authorities" />
			<core:url value="/logout" var="logoutUrl" />
			
			<core:if test="${pageContext.request.userPrincipal.name != null}">
				<a href="javascript:document.getElementById('logout').submit()">Logout</a>
			</core:if></p>
			<form id="logout" action="${logoutUrl}" method="post" style="display:inline;">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
		</security:authorize>
		
	</div>
</footer>