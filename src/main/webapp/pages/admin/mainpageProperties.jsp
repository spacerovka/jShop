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
<title>Insert title here</title>
<%@include file="resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Main page properties</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Product</a>
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
				
                    <div class="col-xs-12 col-md-6">
                   
                        <h2>List of properties</h2>
                        <div class="table-responsive">
                            <form:form action="${pageContext.request.contextPath}/a/mainproperties"
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
                    
                    <div class="col-xs-12 col-md-6">
                    	<div class="panel panel-success">
							<div class="panel-heading">
								<h3 class="panel-title">Upload Images</h3>
							</div>
							<div class="panel-body">
								<div class="form-group">
									<core:forEach items="${images}" var="img">
										<img
											src="${pageContext.request.contextPath}/resources/uploads/mainpage/${img}"
											width="30%"></img>
									</core:forEach>
								</div>
								<script>
									function AddMoreFile(tableID) {

										var table = document
												.getElementById('fuTable');
										var colInput = document
												.createElement("input");
										colInput.type = "file";
										colInput.name = "files";
										table.appendChild(colInput);
									}
								</script>
								<div class="form-group">
									<label>Select files to upload. Click Add More button to
										add more files.</label>

									<form:form method="post"
										action="${pageContext.request.contextPath}/uploadMainPageImages"
										enctype="multipart/form-data">

										<input type="hidden" value="mainpage/"
											name="prefix" />
										<div id="fuTable">

											<input name="files" type="file">

										</div>
										<br>
										<input type="button" value="Add More File"
											class="btn btn-default" onclick="AddMoreFile('fuTable')">
										<input type="submit" value="Upload" class="btn btn-default">
									</form:form>
								</div>
							</div>
						</div>
                    
                    </div>
                    
                </div>


			</div>
		</div>

	</div>
	<%@include file="_footer.jsp"%>
</body>
</html>