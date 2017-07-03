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
<title>Site properties</title>
<%@include file="resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Site properties</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Dashboard</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> Tables</li>
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
							<h2>List of properties</h2>
						</div>
						<div class="form-group col-xs-12">
							<div class="table-responsive" id="table">
								<form:form action="${pageContext.request.contextPath}/a/properties"
							method="post" modelAttribute="propertyWrapper">
									<table class="table table-hover">
										<thead>
											<tr>
												<th>ID</th>
												<th>Name</th>
												<th>Value</th>
											</tr>
										</thead>
										<tbody>
											<core:choose>
												<core:when test="${not empty propertyWrapper.propertyList}">
													<core:forEach var="prop" items="${propertyWrapper.propertyList}" varStatus="statusPO">
														<tr>
															<td>${prop.id} <form:hidden path="propertyList[${statusPO.index}].id" /></td>
															<td>${prop.name}<form:hidden path="propertyList[${statusPO.index}].name" /></td>
															<td><form:input class="form-control" placeholder="Property value"
																	
																	path="propertyList[${statusPO.index}].content" /></td><!-- value="${prop.content}" -->
															
														</tr>
													</core:forEach>
												</core:when>
												<core:otherwise>
													<td colspan="5" style="text-align: center">No data
														found</td>
												</core:otherwise>
											</core:choose>

										</tbody>
									</table>
									<button type="submit" class="btn btn-default">Submit
								Button</button>
								</form:form>
							</div>
						</div>
					</div>

				</div>


			</div>
		</div>

	</div>
	<%@include file="_footer.jsp"%>
	<script>
	function searchButtonClick()
	{
		var url = $('#searchUrl').val();
		var name = $('#searchName').val();		
		$.ajax ({ 
			url: '${pageContext.request.contextPath}/a/findCategories', 
			type: "POST", 						
			data : {name:name, url:url},
			complete: function(response){
				$('#table').html(response.responseText);
			}
		}); 
	}
	</script>
</body>
</html>