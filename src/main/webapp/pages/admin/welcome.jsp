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
<%@include file="resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="_topnav.jsp"%>

		<div id="page-wrapper">

			<div class="container-fluid">

				<!-- Page Heading -->
				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">
							Dashboard <small>Statistics Overview</small>
						</h1>
						<ol class="breadcrumb">
							<li class="active"><i class="fa fa-dashboard"></i> Dashboard
							</li>
						</ol>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-12">
						<div class="alert alert-info alert-dismissable">
							<button type="button" class="close" data-dismiss="alert"
								aria-hidden="true">×</button>
							<i class="fa fa-info-circle"></i> <strong>Like SB Admin?</strong>
							Try out <a
								href="http://startbootstrap.com/template-overviews/sb-admin-2"
								class="alert-link">SB Admin 2</a> for additional features!
						</div>
					</div>
				</div>
				<!-- /.row -->

				<div class="row">
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-primary">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-comments fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">26</div>
										<div>New Comments!</div>
									</div>
								</div>
							</div>
							<a href="#">
								<div class="panel-footer">
									<span class="pull-left">View Details</span> <span
										class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-green">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-tasks fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">12</div>
										<div>New Tasks!</div>
									</div>
								</div>
							</div>
							<a href="#">
								<div class="panel-footer">
									<span class="pull-left">View Details</span> <span
										class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-yellow">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-shopping-cart fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">124</div>
										<div>New Orders!</div>
									</div>
								</div>
							</div>
							<a href="#">
								<div class="panel-footer">
									<span class="pull-left">View Details</span> <span
										class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
					<div class="col-lg-3 col-md-6">
						<div class="panel panel-red">
							<div class="panel-heading">
								<div class="row">
									<div class="col-xs-3">
										<i class="fa fa-support fa-5x"></i>
									</div>
									<div class="col-xs-9 text-right">
										<div class="huge">13</div>
										<div>Support Tickets!</div>
									</div>
								</div>
							</div>
							<a href="#">
								<div class="panel-footer">
									<span class="pull-left">View Details</span> <span
										class="pull-right"><i class="fa fa-arrow-circle-right"></i></span>
									<div class="clearfix"></div>
								</div>
							</a>
						</div>
					</div>
				</div>
				<!-- /.row -->


				<div class="row">

					<div class="col-lg-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-money fa-fw"></i> Transactions Panel
								</h3>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-bordered table-hover table-striped">
										<thead>
											<tr>
												<th>Order #</th>
												<th>Order Date</th>
												<th>Order Time</th>
												<th>Amount (USD)</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>3326</td>
												<td>10/21/2013</td>
												<td>3:29 PM</td>
												<td>$321.33</td>
											</tr>
											<tr>
												<td>3325</td>
												<td>10/21/2013</td>
												<td>3:20 PM</td>
												<td>$234.34</td>
											</tr>
											<tr>
												<td>3324</td>
												<td>10/21/2013</td>
												<td>3:03 PM</td>
												<td>$724.17</td>
											</tr>
											<tr>
												<td>3323</td>
												<td>10/21/2013</td>
												<td>3:00 PM</td>
												<td>$23.71</td>
											</tr>
											<tr>
												<td>3322</td>
												<td>10/21/2013</td>
												<td>2:49 PM</td>
												<td>$8345.23</td>
											</tr>
											<tr>
												<td>3321</td>
												<td>10/21/2013</td>
												<td>2:23 PM</td>
												<td>$245.12</td>
											</tr>
											<tr>
												<td>3320</td>
												<td>10/21/2013</td>
												<td>2:15 PM</td>
												<td>$5663.54</td>
											</tr>
											<tr>
												<td>3319</td>
												<td>10/21/2013</td>
												<td>2:13 PM</td>
												<td>$943.45</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="text-right">
									<a href="#">View All Transactions <i
										class="fa fa-arrow-circle-right"></i></a>
								</div>
							</div>
						</div>
					</div>
					<div class="col-lg-6">
						<div class="panel panel-default">
							<div class="panel-heading">
								<h3 class="panel-title">
									<i class="fa fa-money fa-fw"></i> Transactions Panel
								</h3>
							</div>
							<div class="panel-body">
								<div class="table-responsive">
									<table class="table table-bordered table-hover table-striped">
										<thead>
											<tr>
												<th>Order #</th>
												<th>Order Date</th>
												<th>Order Time</th>
												<th>Amount (USD)</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td>3326</td>
												<td>10/21/2013</td>
												<td>3:29 PM</td>
												<td>$321.33</td>
											</tr>
											<tr>
												<td>3325</td>
												<td>10/21/2013</td>
												<td>3:20 PM</td>
												<td>$234.34</td>
											</tr>
											<tr>
												<td>3324</td>
												<td>10/21/2013</td>
												<td>3:03 PM</td>
												<td>$724.17</td>
											</tr>
											<tr>
												<td>3323</td>
												<td>10/21/2013</td>
												<td>3:00 PM</td>
												<td>$23.71</td>
											</tr>
											<tr>
												<td>3322</td>
												<td>10/21/2013</td>
												<td>2:49 PM</td>
												<td>$8345.23</td>
											</tr>
											<tr>
												<td>3321</td>
												<td>10/21/2013</td>
												<td>2:23 PM</td>
												<td>$245.12</td>
											</tr>
											<tr>
												<td>3320</td>
												<td>10/21/2013</td>
												<td>2:15 PM</td>
												<td>$5663.54</td>
											</tr>
											<tr>
												<td>3319</td>
												<td>10/21/2013</td>
												<td>2:13 PM</td>
												<td>$943.45</td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="text-right">
									<a href="#">View All Transactions <i
										class="fa fa-arrow-circle-right"></i></a>
								</div>
							</div>
						</div>
					</div>

				</div>
				<!-- /.row -->

			</div>
			<!-- /.container-fluid -->

		</div>






		<h1 style="top: 50%; left: 20%; font-size: 60px; color: red;">Hello
			Admin!</h1>
		<security:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_USER')">
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
	</div>
	<%@include file="../template_parts/footer.jsp"%>
</body>
</html>