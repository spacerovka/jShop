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
                    <a href="${pageContext.request.contextPath}/a/optiongroup/add">
                    <button type="button" class="btn btn-primary">Add new option group</button></a>
                    
                    <a href="${pageContext.request.contextPath}/a/option/add">
                    <button type="button" class="btn btn-primary">Add new option</button></a>
                    
                        <h2>List of option groups</h2>
                        <div class="table-responsive" id="groupstable">
                        	<%@include file="_groups_table.jsp"%>
                            
                        </div>
                        
                        <h2>List of options</h2>
                        <div class="table-responsive" id="optionstable">
                        	<%@include file="_options_table.jsp"%>
                            
                        </div>
                    </div>
                    
                </div>


			</div>
		</div>

	</div>
	<%@include file="../_footer.jsp"%>
</body>
</html>