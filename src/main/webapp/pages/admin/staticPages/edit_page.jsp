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
<title>Admin edit block</title>
<%@include file="../resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="../_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Edit page</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Pages</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> Edit</li>
						</ol>
					</div>
				</div>
				<core:if test="${not empty flashMessage}">

					<div class="alert alert-danger">
						<strong>Warning!</strong> ${errorMessage}
					</div>
				</core:if>
				<div class="row">
					<div class="col-lg-6 ">

						<form:form action="${pageContext.request.contextPath}/a/page"
							method="post" modelAttribute="page">
							<div class="form-group">
								<label>ID</label>
								<p class="form-control-static">${page.id}</p>
							</div>
							<form:hidden path="id" />

							<div class="form-group input-group">
								<label>Name</label>
								<form:input class="form-control" placeholder="url" type="text"
									path="name" />
							</div>

							<div class="form-group input-group">
								<span class="input-group-addon">http:/${pageContext.request.contextPath}/</span>
								<form:input class="form-control" placeholder="url" type="text"
									path="url" id="urlinput" />
							</div>

							<div class="form-group">
								<label>Content</label>
								<form:textarea class="form-control" placeholder="<div></div>"
									path="content" id="nameinput" />
							</div>

							<div class="form-group">
								<label>META title</label>
								<form:input class="form-control" placeholder="Product title"
									path="metaTitle" />
								<p class="help-block">Title tags are often used on search
									engine results pages (SERPs) to display preview snippets for a
									given page, and are important both for SEO and social sharing.</p>
							</div>

							<div class="form-group">
								<label>META description</label>
								<form:input class="form-control"
									placeholder="Product description" path="metaDescription" />
								<p class="help-block">The meta description is a ~160
									character snippet, a tag in HTML, that summarizes a page's
									content. Search engines show the meta description in search
									results mostly when the searched for phrase is contained in the
									description. Optimizing the meta description is a very
									important aspect of on-page SEO.</p>
							</div>

							<div class="form-group">
								<label>Status</label>
								<div class="radio">
									<label> <form:radiobutton path="status" value="true" />Active
									</label>
								</div>
								<div class="radio">
									<label> <form:radiobutton path="status" value="false" />Hidden
									</label>
								</div>
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
	<%@include file="../_tinyMCE.jsp"%>
	<script>
		$(document).ready(function() {
			var focus = 0, blur = 0;
			$("#nameinput").focusout(function() {
				focus++;
				var data = $("#nameinput").val();
				var urlinput = $("#urlinput");
				$.ajax({
					type : "POST",
					contentType : "application/json",
					url : "${home}a/translit",
					data : JSON.stringify({
						cropName : data
					}),
					dataType : 'text',
					timeout : 100000,
					success : function(data) {
						console.log("SUCCESS: ", data.d);

						urlinput.val(data.substring(4));
					},
					error : function(e) {
						console.log("ERROR: ", e);
					},
					done : function(e) {
						console.log("DONE");
					}
				});
			});

		});
	</script>
</body>
</html>