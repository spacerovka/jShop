<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<%@include file="../template_parts/resources.jsp"%>
</head>
<body>
	<%@include file="../template_parts/navbar.jsp"%>

	<div class="container">
		
		<security:authorize access="hasAnyAuthority('ADMIN','USER')">
			<b>You are logged in as: </b>
			<security:authentication property="principal.username" />
  with the role of <security:authentication
				property="principal.authorities" />
			<core:url value="/logout" var="logoutUrl" />
			<form id="logout" action="${logoutUrl}" method="post">
				<input type="hidden" name="${_csrf.parameterName}"
					value="${_csrf.token}" />
			</form>
			<core:if test="${pageContext.request.userPrincipal.name != null}">
				<a href="javascript:document.getElementById('logout').submit()">Logout</a>
			</core:if>
		</security:authorize>


		<div class="col-xs-12">
    <%-- <div class="card hovercard">
        <div class="card-background">
            <img class="card-bkimg" alt="" src="http://lorempixel.com/100/100/people/9/">
            <!-- http://lorempixel.com/850/280/people/9/ -->
        </div>
        <div class="useravatar">
            <img alt="" src="http://lorempixel.com/100/100/people/9/">
        </div>
        <div class="card-info"> <span class="card-title">${user.username}</span>

        </div>
    </div> --%>
    <div class="btn-pref btn-group btn-group-justified btn-group-lg" role="group" aria-label="...">
        <div class="btn-group" role="group">
            <button type="button"  id="following" class="btn btn-primary" href="#tab1" data-toggle="tab"><span class="glyphicon glyphicon-user" aria-hidden="true"></span>
                <div class="hidden-xs">Account</div>
            </button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" id="stars" class="btn btn-secondary" href="#tab2" data-toggle="tab"><span class="glyphicon glyphicon-star" aria-hidden="true"></span>
                <div class="hidden-xs">Orders</div>
            </button>
        </div>
        <div class="btn-group" role="group">
            <button type="button" id="favorites" class="btn btn-secondary" href="#tab3" data-toggle="tab"><span class="glyphicon glyphicon-heart" aria-hidden="true"></span>
                <div class="hidden-xs">Wish List</div>
            </button>
        </div>
    </div>

        <div class="well">
      <div class="tab-content">
        <div class="tab-pane fade in active" id="tab1">
          <h3>Account data</h3>
          <%@include file="_account_tab.jsp"%>
        </div>
        <div class="tab-pane fade in" id="tab2">
          <h3>My orders</h3>
          <%@include file="_orders_tab.jsp"%>
        </div>
        <div class="tab-pane fade in" id="tab3">
          <h3>Wish list</h3>   
          <%@include file="_wishlist_tab.jsp"%>
        </div>
      </div>
    </div>
    
    </div>


	</div>
	
	<%@include file="../template_parts/footer.jsp"%>
	<%@include file="../template_parts/add_to_cart_ajax.jsp"%>
	<script>
	$(document).ready(function() {
		$(".btn-pref .btn").click(function () {
		    $(".btn-pref .btn").removeClass("btn-primary").addClass("btn-default");
		    // $(".tab").addClass("active"); // instead of this do the below 
		    $(this).removeClass("btn-default").addClass("btn-primary");   
		});
		});
	</script>
</body>
</html>