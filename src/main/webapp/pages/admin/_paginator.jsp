<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<div class="col-xs-12">
	<div id="dataTables-example_paginate"
		class="dataTables_paginate paging_simple_numbers">
		<ul class="pagination">
			<core:if test="${RENDER_PREV}">
				<li id="dataTables-example_previous" tabindex="0"
					aria-controls="dataTables-example"
					class="paginate_button previous"><a href="#"
					onclick="pageButtonClick('${current -1}')">Previous</a></li>
			</core:if>
			<core:forEach var="page" items="${pagination}">
				<core:choose>
					<core:when test="${page == current}">
						<li tabindex="0" aria-controls="dataTables-example"
							class="paginate_button active"><a href="#">${page}</a></li>
					</core:when>
					<core:otherwise>
						<li tabindex="0" aria-controls="dataTables-example"
							class="paginate_button "><a href="#"
							onclick="pageButtonClick('${page}')">${page}</a>
					</core:otherwise>
				</core:choose>
			</core:forEach>
			<core:if test="${RENDER_NEXT}">
				<li id="dataTables-example_next" tabindex="0"
					aria-controls="dataTables-example" class="paginate_button next">
					<a href="#" onclick="pageButtonClick('${current +1}')">Next</a>
				</li>
			</core:if>
		</ul>
	</div>
</div>