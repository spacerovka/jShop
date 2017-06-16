<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<nav class="navbar navbar-inverse navbar-fixed-top">
	<core:forEach var="block" items="${TOP_BLOCKS}">
		<div style="width: 100%">${block.content}</div>
	</core:forEach>
	<div class="container">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed"
				data-toggle="collapse" data-target="#navbar" aria-expanded="false"
				aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
			<a class="navbar-brand" href="#">${SITE_NAME}</a>
		</div>
		<core:set var="currentPage"
			value="${fn:substring(pageContext.request.servletPath, 1, -1)}" />
		<div id="navbar" class="navbar-collapse collapse">
			<ul class="nav navbar-nav">
				<core:if test="${not empty MENU_LEFT}">
					<core:forEach var="menuItem" items="${MENU_LEFT}">
						<li ${menuItem.URL == currentPage ? ' class="active"' : ''}><a
							href="${pageContext.request.contextPath}/${menuItem.URL}">${menuItem.text}</a></li>
					</core:forEach>
					<!-- <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li><a href="#">Action</a></li>
                <li><a href="#">Another action</a></li>
                <li><a href="#">Something else here</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Nav header</li>
                <li><a href="#">Separated link</a></li>
                <li><a href="#">One more separated link</a></li>
              </ul>
            </li> -->
				</core:if>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<core:if test="${not empty MENU_RIGHT}">
					<core:forEach var="menuItem" items="${MENU_RIGHT}">
						<li ${menuItem.URL == currentPage ? ' class="active"' : ''}><a
							href="${pageContext.request.contextPath}/${menuItem.URL}">${menuItem.text}</a></li>
					</core:forEach>
				</core:if>




				<core:choose>
					<core:when test="${pageContext.request.userPrincipal.name != null}">
						<li><core:url value="/logout" var="logoutUrl" />
							<form id="logout" action="${logoutUrl}" method="post">
								<input type="hidden" name="${_csrf.parameterName}"
									value="${_csrf.token}" />
							</form> <a href="javascript:document.getElementById('logout').submit()">Logout</a>
						</li>
					</core:when>
					<core:otherwise>
						<li ${currentPage == '/login' ? ' class="active"' : ''}><a
							href="${pageContext.request.contextPath}/login">Login</a></li>
						<li ${currentPage == '/registration' ? ' class="active"' : ''}><a
							href="${pageContext.request.contextPath}/registration">Register</a></li>
					</core:otherwise>
				</core:choose>



				<li><a href="./">Cart <span id="cart-item-count"
						class="product-count">(0)</span> <span class="sr-only">(current)</span></a>
				</li>
			</ul>
		</div>
		<!--/.nav-collapse -->
	</div>
</nav>