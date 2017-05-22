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
						<h1 class="page-header">Products</h1>
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
				
                    <div class="col-lg-12">
                    <div class="form-group col-xs-12">
                    <a href="${pageContext.request.contextPath}/a/product/add">
                    <button type="button" class="btn btn-primary">Add new product</button></a>
                    </div>
                    <div class="form-group col-xs-4">
                        	<label>Product name</label>
                        	<input class="form-control" type="text" id="searchName"/>
                        </div>
                        <div class="form-group col-xs-4">
                        	<label>Product url</label>
                        	<input class="form-control" type="text" id="searchUrl"/>
                        </div>
                        <div class="form-group col-xs-4">
                        	<a class="btn btn-default" href="#" onclick="findProduct();">Search</a>
                        </div>
                        <div class="form-group col-xs-12">
                        <h2>List of products</h2>
                        </div>
                         <div class="form-group col-xs-12">
                        <div class="table-responsive" id="table">
                        	<%@include file="_table.jsp"%>
                            
                        </div>
                        </div>
                    </div>
                    
                </div>


			</div>
		</div>

	</div>
	<%@include file="../_footer.jsp"%>
	
	<script>
	function findProduct()
	{
		var url = $('#searchUrl').val();
		var name = $('#searchName').val();
		console.log("updateProductOption");
		$.ajax ({ 
			url: '${pageContext.request.contextPath}/a/findProducts', 
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