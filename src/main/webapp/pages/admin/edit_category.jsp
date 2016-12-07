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

				<div class="row">
					<div class="col-lg-12">
						<h1 class="page-header">Tables</h1>
						<ol class="breadcrumb">
							<li><i class="fa fa-dashboard"></i> <a href="index.html">Dashboard</a>
							</li>
							<li class="active"><i class="fa fa-table"></i> Tables</li>
						</ol>
					</div>
				</div>

				<div class="row">
                    <div class="col-lg-6 ">

                        <form role="form">
							<div class="form-group">
                                <label>ID</label>
                                <p class="form-control-static">100500</p>
                            </div>
                            
                            <div class="form-group">
                                <label>Category name</label>
                                <input class="form-control" placeholder="Category name">                                
                            </div>


							<div class="form-group input-group">
                                <span class="input-group-addon">http://mysite.com/</span>
                                <input class="form-control" placeholder="url" type="text">                                
                            </div>                                                     

                            <div class="form-group">
                                <label>Category description</label>
                                <textarea class="form-control" rows="3"></textarea>
                            </div>
                            
                            <div class="form-group">
                                <label>META title</label>
                                <input class="form-control" placeholder="Category name">  
                                <p class="help-block">Title tags are often used on search engine results pages (SERPs) to display preview snippets for a given page, and are important both for SEO and social sharing. </p>                              
                            </div>
                            
                            <div class="form-group">
                                <label>META description</label>
                                <input class="form-control" placeholder="Category name"> 
                                <p class="help-block">The meta description is a ~160 character snippet, a tag in HTML, that summarizes a page's content. Search engines show the meta description in search results mostly when the searched for phrase is contained in the description. Optimizing the meta description is a very important aspect of on-page SEO.</p>                               
                            </div>

                            <div class="form-group">
                                <label>Status</label>
                                <div class="radio">
                                    <label>
                                        <input name="optionsRadios" id="optionsRadios1" value="option1" checked="true" type="radio">Active
                                    </label>
                                </div>
                                <div class="radio">
                                    <label>
                                        <input name="optionsRadios" id="optionsRadios2" value="option2" type="radio">Hidden
                                    </label>
                                </div>                                
                            </div>

                          

                            <div class="form-group">
                                <label>Parent category</label>
                                <select class="form-control">
                                    <option>1</option>
                                    <option>2</option>
                                    <option>3</option>
                                    <option>4</option>
                                    <option>5</option>
                                </select>
                            </div>
                            

                            <button type="submit" class="btn btn-default">Submit Button</button>
                            <button type="reset" class="btn btn-default">Reset Button</button>

                        </form>

                    </div>
                    
                </div>


			</div>
		</div>

	</div>
	<%@include file="../template_parts/footer.jsp"%>
</body>
</html>