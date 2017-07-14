<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Admin blocks</title>
<%@include file="../resources.jsp"%>

</head>
<body>
	<div id="wrapper">
		<%@include file="../_topnav.jsp"%>
		<div id="page-wrapper">

			<div class="container-fluid">

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Blocks</h1>
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
				
                    <div class="col-sm-12">
                    <a href="${pageContext.request.contextPath}/a/block/add"><button type="button" class="btn btn-primary">Add new block</button></a>
                        <h2>List of blocks</h2>
                        <div class="table-responsive" id="table">
                            <%@include file="_table.jsp"%>
                                                          
                        </div>
                    </div>
                    
                </div>


			</div>
		</div>

	</div>
	<%@include file="../_footer.jsp"%>
	<script>
	function pageButtonClick(targetPage)
	{
		var pageSize = ${pageSize};
		var current = targetPage;	
		
		$.ajax ({ 
			url: '${pageContext.request.contextPath}${URL_PREFIX}ajax/blocks', 
			type: "POST", 						
			data : {current:current, pageSize:pageSize},
			complete: function(response){
				$('#table').html(response.responseText);				
			}
		}); 
	}
	</script>
</body>
</html>