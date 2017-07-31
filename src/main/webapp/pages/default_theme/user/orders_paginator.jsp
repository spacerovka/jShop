<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<div class="col-xs-12 text-center">
	<div id="dataTables-example_paginate"
		class="dataTables_paginate paging_simple_numbers">
		<ul class="pagination">
			<core:if test="${RENDER_PREV_order}">
				<li id="dataTables-example_previous" tabindex="0"
					aria-controls="dataTables-example"
					class="paginate_button previous"><a href="#"
					onclick="pageButtonClick('${current_order -1}')">Previous</a></li>
			</core:if>
			<core:forEach var="page_order" items="${pagination_order}">
				<core:choose>
					<core:when test="${page_order == current_order}">
						<li tabindex="0" aria-controls="dataTables-example"
							class="paginate_button active"><a href="#">${page_order}</a></li>
					</core:when>
					<core:otherwise>
						<li tabindex="0" aria-controls="dataTables-example"
							class="paginate_button "><a href="#"
							onclick="pageButtonClick_order('${page_order}')">${page_order}</a>
					</core:otherwise>
				</core:choose>
			</core:forEach>
			<core:if test="${RENDER_NEXT_order}">
				<li id="dataTables-example_next" tabindex="0"
					aria-controls="dataTables-example" class="paginate_button next">
					<a href="#" onclick="pageButtonClick_order('${current_order +1}')">Next</a>
				</li>
			</core:if>
		</ul>
	</div>
</div>