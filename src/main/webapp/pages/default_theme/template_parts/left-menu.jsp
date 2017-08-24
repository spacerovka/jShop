<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<core:if test="${not empty LEFT_TOP_BLOCKS}">
<nav class="navbar navbar-default sidebar">
	<core:forEach var="block" items="${LEFT_TOP_BLOCKS}">
		<br>
		<div style="width: 100%">${block.content}</div>
	</core:forEach>
</nav>
</core:if>
<nav class="navbar navbar-default sidebar" role="navigation">
	<div class="container-fluid">
		<div class="navbar-header">
			<button type="button" class="navbar-toggle" data-toggle="collapse"
				data-target="#bs-sidebar-navbar-collapse-1">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div>
		<div class="collapse navbar-collapse"
			id="bs-sidebar-navbar-collapse-1">
			<ul class="nav navbar-nav">
				<li class="active"><a
					href="${pageContext.request.contextPath}/">Home<span
						style="font-size: 16px;"
						class="pull-right hidden-xs showopacity glyphicon glyphicon-home"></span></a></li>
				<core:if test="${not empty menu}">
					<core:forEach var="level1" items="${menu}">
						<core:choose>
							<core:when test="${not empty level1.children}">
								<li class="dropdown"><a
									href="${pageContext.request.contextPath}/${level1.categoryURL}"
									class="dropdown-toggle" data-toggle="dropdown">${level1.categoryName}
										<span class="caret"></span> <!-- <span style="font-size:16px;" class="pull-right hidden-xs showopacity glyphicon glyphicon-user"></span> -->
								</a>
									<ul class="dropdown-menu forAnimate" role="menu">
										<core:forEach var="level2" items="${level1.children}">
											<core:choose>
												<core:when test="${not empty level2.children}">
													<li class="dropdown-custom-sub"><a tabindex="-1"
														href="${pageContext.request.contextPath}/${level2.categoryURL}">${level2.categoryName}</a>
														<ul class="dropdown-menu dropdown-custom-menu">
															<core:forEach var="level3" items="${level2.children}">
																<core:choose>
																	<core:when test="${not empty level3.children}">
																		<li class="dropdown-custom-sub"><a tabindex="-1"
																			href="${pageContext.request.contextPath}/${level3.categoryURL}">${level3.categoryName}</a>
																			<ul class="dropdown-menu dropdown-custom-menu">
																				<core:forEach var="level4"
																					items="${level3.children}">
																					<li><a
																						href="${pageContext.request.contextPath}/${level4.categoryURL}">${level4.categoryName}
																					</a></li>
																				</core:forEach>

																			</ul></li>

																	</core:when>
																	<core:otherwise>
																		<li><a
																			href="${pageContext.request.contextPath}/${level3.categoryURL}">${level3.categoryName}
																		</a></li>
																	</core:otherwise>
																</core:choose>
															</core:forEach>

														</ul></li>

												</core:when>
												<core:otherwise>
													<li><a
														href="${pageContext.request.contextPath}/${level2.categoryURL}">${level2.categoryName}
													</a></li>
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

			</ul>
		</div>
	</div>
</nav>
<core:if test="${not empty LEFT_BOTTOM_BLOCKS}">
<nav class="navbar navbar-default sidebar">
	<core:forEach var="block" items="${LEFT_BOTTOM_BLOCKS}">
		<br>
		<div style="width: 100%">${block.content}</div>
	</core:forEach>
</nav>
</core:if>

