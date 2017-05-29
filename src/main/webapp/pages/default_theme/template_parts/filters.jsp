<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>

	<p class="lead hidden-sm">Filters</p>
	
	<nav class="navbar navbar-default sidebar" role="filters" id="filters">
		<div class="container-fluid">
			<div class="collapse navbar-collapse"
				id="bs-sidebar-navbar-collapse-2">
				<!-- <h2>Filters</h2> -->
				<ul class="nav navbar-nav" style="width: 100%;">
					<core:forEach items="${categoryOptions}" var="entry">

						<li>
							<span style="color: rgb(85, 85, 85); background-color: rgb(231, 231, 231); padding: 10px 15px; display: block;">
								${entry.key}</span>
							<ul>
								<core:forEach items="${entry.value}" var="option">
									<li class="checkbox"><label><input type="checkbox" onchange="searchAjax();"
											value="${option.option.id}">${option.option.optionName}</label>												
									</li>
								</core:forEach>
							</ul>
						</li>
					</core:forEach>


				</ul>
			</div>
		</div>
	</nav>
