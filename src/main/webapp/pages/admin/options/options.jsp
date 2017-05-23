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
<%@include file="../resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="../_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Options</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Options</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> List All</li>
						</ol>
					</div>
				</div>
				<core:if test="${not empty flashMessage}">
					<div class="alert alert-success">
						<strong>Request success!</strong> ${flashMessage}
					</div>
				</core:if>
				<div class="row">

					<div class="col-lg-12">
						<div class="form-group col-xs-12">
							<a href="${pageContext.request.contextPath}/a/optiongroup/add">
								<button type="button" class="btn btn-primary">Add new
									option group</button>
							</a> <a href="${pageContext.request.contextPath}/a/option/add">
								<button type="button" class="btn btn-primary">Add new
									option</button>
							</a>
						</div>
						<div class="form-group col-xs-12">
							<h2>List of option groups</h2>
						</div>
						<div class="form-group col-xs-6">
							<label>Option group name</label> <input class="form-control"
								type="text" id="searchName_group" />
						</div>
						<div class="form-group col-xs-6">
							<a class="btn btn-default" href="#" onclick="searchGroup();"
								style="display: block; margin-top: 2.4rem;">Search</a>
						</div>

						<div class="form-group col-xs-12">
							<div class="table-responsive" id="groupstable">
								<%@include file="_groups_table.jsp"%>

							</div>
						</div>
						<hr />
						<div class="form-group col-xs-12">
							<h2>List of options</h2>
						</div>
						<div class="form-group col-xs-6">
							<label>Option name</label> <input class="form-control"
								type="text" id="searchName_option" />
						</div>
						<div class="form-group col-xs-6">
							<a class="btn btn-default" href="#" onclick="searchOption();"
								style="display: block; margin-top: 2.4rem;">Search</a>
						</div>

						<div class="form-group col-xs-12">
							<div class="table-responsive" id="optionstable">
								<%@include file="_options_table.jsp"%>

							</div>
						</div>
					</div>

				</div>


			</div>
		</div>

	</div>
	<%@include file="../_footer.jsp"%>

	<script>
		function searchOption() {
			var name = $('#searchName_option').val();
			$.ajax({
				url : '${pageContext.request.contextPath}/a/findOption',
				type : "POST",
				data : {
					name : name
				},
				complete : function(response) {
					$('#optionstable').html(response.responseText);
				}
			});
		}
	</script>

	<script>
		function searchGroup() {
			var name = $('#searchName_group').val();
			$.ajax({
				url : '${pageContext.request.contextPath}/a/findGroup',
				type : "POST",
				data : {
					name : name
				},
				complete : function(response) {
					$('#groupstable').html(response.responseText);
				}
			});
		}
	</script>
</body>
</html>