<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<div class="col-md-3">
	<p class="lead hidden-sm">Shop Name</p>
	<nav class="navbar navbar-default sidebar" role="navigation">
    <div class="container-fluid">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#bs-sidebar-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>      
    </div>
    <div class="collapse navbar-collapse" id="bs-sidebar-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Home<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>
        <core:if test="${not empty menu}">
        	<core:forEach var="level1" items="${menu}">
        		<core:choose>
	        		<core:when test="${not empty level1.children}">
	        			<li class="dropdown">
          				<a href="${level1.categoryURL}" class="dropdown-toggle" data-toggle="dropdown">${level1.categoryName} 
          				<!-- <span class="caret"></span><span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span> -->
          				</a>
          				<ul class="dropdown-menu forAnimate" role="menu">
	          				<core:forEach var="level2" items="${level1.children}">
        						<core:choose>
	        					<core:when test="${not empty level2.children}">
				        			<li class="dropdown-custom-sub">
					                <a tabindex="-1" href="${level2.categoryURL}">${level2.categoryName}</a>
					                <ul class="dropdown-menu dropdown-custom-menu">
					                		<core:forEach var="level3" items="${level2.children}">
				        						<core:choose>
					        					<core:when test="${not empty level3.children}">
								        			<li class="dropdown-custom-sub">
									                <a tabindex="-1" href="${level3.categoryURL}">${level3.categoryName}</a>
									                <ul class="dropdown-menu dropdown-custom-menu">
									                	<core:forEach var="level4" items="${level3.children}">
									                		<li><a href="${level4.categoryURL}">${level4.categoryName} </a></li>
									                	</core:forEach>
									                
									                </ul>
									                </li>        
											    
								  				</core:when>
								  				<core:otherwise>
								 				   <li><a href="${level3.categoryURL}">${level3.categoryName} </a></li>
								  				</core:otherwise>
							        			</core:choose>
							        		</core:forEach>
					                
					                </ul>
					                </li>        
							    
				  				</core:when>
				  				<core:otherwise>
				 				   <li><a href="${level2.categoryURL}">${level2.categoryName} </a></li>
				  				</core:otherwise>
			        			</core:choose>
			        		</core:forEach>
          				
          				</ul>
				    
	  				</core:when>
	  				<core:otherwise>
	 				   <li><a href="${level1.categoryURL}">${level1.categoryName}
	 				   <!-- <span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span> icon TODO-->
	 				   </a></li>
	  				</core:otherwise>
        		</core:choose>
        	</core:forEach>
        </core:if>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">Usuarios <span class="caret"></span><span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span></a>
          <ul class="dropdown-menu forAnimate" role="menu">
            <li><a href="{{URL::to('createusuario')}}">Crear</a></li>
            <li><a href="#">Modificar</a></li>
            <li><a href="#">Reportar</a></li>
            <li class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li class="divider"></li>
            <li><a href="#">Informes</a></li>
            <li class="dropdown-custom-sub">
                <a tabindex="-1" href="#">Hover me for more options</a>
                <ul class="dropdown-menu dropdown-custom-menu">
                  <li><a tabindex="-1" href="#">Second level</a></li>
                  <li class="dropdown-custom-sub">
                    <a href="#">Even More..</a>
                    <ul class="dropdown-menu dropdown-custom-menu">
                        <li><a href="#">3rd level</a></li>
                    	<li><a href="#">3rd level</a></li>
                    </ul>
                  </li>
                  <li><a href="#">Second level</a></li>
                  <li><a href="#">Second level</a></li>
                </ul>
              </li>
          </ul>
        </li>          
        <li ><a href="#">Libros<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-th-list"></span></a></li>        
        <li ><a href="#">Tags<span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-tags"></span></a></li>
      </ul>
    </div>
  </div>
</nav>
</div>