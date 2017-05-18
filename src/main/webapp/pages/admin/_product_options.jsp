<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="core" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>


								<core:forEach items="${product.productOptions}" var="productOption" varStatus="statusPO">
									<div class="form-group col-xs-6">
										<label>Option group ${product.productOptions[statusPO.index].optionGroup.id}</label>
										<%-- <form:hidden path="productOptions[${statusPO.index}].optionGroup.id" value="${productOption..optionGroup.id}" /> --%>
										<form:select path="productOptions[${statusPO.index}].optionGroup.id" class="form-control"
											onchange="if (this.selectedIndex) updateProductOption();" >
											<option value="-1">Select...</option>
											
											<form:options items="${optiongroupList}" itemValue="id"
													itemLabel="optionGroupName" />
																				
		
											<%-- <core:forEach items="${optiongroupList}" var="optiongroup">
												<option value="${optiongroup.id}">${optiongroup.optionGroupName}</option>
											</core:forEach> --%>
										</form:select>	
									</div>
									<div class="form-group col-xs-6">
										<label>Option ${product.productOptions[statusPO.index].option.id}</label>	
										<form:select path="productOptions[${statusPO.index}].option.id" class="form-control"
											value="${product.productOptions[statusPO.index].option.id}">
											<option value="-1">Select...</option>	
											
											<form:options items="${productOption.optionGroup.options}" itemValue="id"
													itemLabel="optionName" />																			
		
											<%-- <core:forEach items="${productOption.optionGroup.options}" var="option">
												<option value="${option.id}">${option.optionName}</option>
											</core:forEach> --%>
										</form:select>		
									</div>
								</core:forEach>
							