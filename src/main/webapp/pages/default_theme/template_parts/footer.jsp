<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<spring:url value="/resources/default_theme/js/bootstrap.min.js"
	var="bootstrapJS" />
<script src="${bootstrapJS}"></script>
<spring:url value="/resources/default_theme/js/ajax.js" var="ajaxJS" />
<script src="${ajaxJS}"></script>
<!-- gallery on product page js https://tympanus.net/codrops/2011/09/20/responsive-image-gallery/-->
<spring:url value="/resources/default_theme/js/jquery.tmpl.min.js"
	var="tmpl" />
<script type="text/javascript" src="${tmpl}"></script>

<spring:url value="/resources/default_theme/js/jquery.easing.1.3.js"
	var="easing" />
<script type="text/javascript" src="${easing}"></script>

<spring:url value="/resources/default_theme/js/jquery.elastislide.js"
	var="elastislide" />
<script type="text/javascript" src="${elastislide}"></script>

<spring:url value="/resources/default_theme/js/gallery.js" var="gallery" />
<script type="text/javascript" src="${gallery}"></script>

<spring:url value="/resources/default_theme/js/jquery_mask.js"
	var="jquery_mask" />
<script type="text/javascript" src="${jquery_mask}"></script>

<spring:url value="/resources/default_theme/js/simple_popup.js"
	var="simple_popup" />
<script type="text/javascript" src="${simple_popup}"></script>


<footer class="footer">

	<core:forEach var="block" items="${BOTTOM_BLOCKS}">
		<div style="width: 100%">${block.content}</div>
	</core:forEach>
	<div class="container">
		<div class="col-md-4">
			<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
				<p class="text-muted">
					<b>You are logged in as: </b>
					<security:authentication property="principal.username" />
					with the role of
					<security:authentication property="principal.authorities" />
					<core:url value="/logout" var="logoutUrl" />

					<core:if test="${pageContext.request.userPrincipal.name != null}">
						<a href="javascript:document.getElementById('logout').submit()">Logout</a>
					</core:if>
				</p>
				<form id="logout" action="${logoutUrl}" method="post"
					style="display: inline;">
					<input type="hidden" name="${_csrf.parameterName}"
						value="${_csrf.token}" />
				</form>
			</security:authorize>
			<br />
			<p style="color: white">© Java Based ecommerce CMS 2016-2017</p>

			<core:forEach var="block" items="${FOOTER_COL_LEFT_BLOCKS}">
				<br />
				<div style="width: 100%">${block.content}</div>
			</core:forEach>
		</div>

		<div class="col-md-4">
			<core:forEach var="block" items="${FOOTER_COL_CENTER_BLOCKS}">
				<br>
				<div style="width: 100%">${block.content}</div>
			</core:forEach>
		</div>

		<div class="col-md-4">
			<core:forEach var="block" items="${FOOTER_COL_RIGHT_BLOCKS}">
				<br>
				<div style="width: 100%">${block.content}</div>
			</core:forEach>
		</div>
	</div>

</footer>