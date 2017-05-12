<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<spring:url value="/resources/default_theme/css/bootstrap.min.css" var="bootstrapCSS" />
<link href="${bootstrapCSS}" rel="stylesheet" />
<link href="//maxcdn.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet">

<spring:url value="/resources/default_theme/css/font-awesome.min.css" var="awesomeCSS" />
<link href="${awesomeCSS}" rel="stylesheet" />

<spring:url value="/resources/default_theme/css/main.css" var="mainCSS" />
<link href="${mainCSS}" rel="stylesheet" />

<spring:url value="/resources/default_theme/css/gallery.css" var="galleryCSS" />
<link href="${galleryCSS}" rel="stylesheet" />

<spring:url value="/resources/default_theme/css/cart.css" var="cartCSS" />
<link href="${cartCSS}" rel="stylesheet" />

<c:url value="/" var="home" />
