<%@ taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<!-- Sidebar Menu Items - These collapse to the responsive navigation menu on small screens -->
            <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="${pageContext.request.contextPath}/admin/welcome"><i class="fa fa-fw fa-dashboard"></i> Home </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/a/categories"><i class="fa fa-fw fa-bar-chart-o"></i> Categories</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/a/products"><i class="fa fa-fw fa-table"></i> Products</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/a/optiongroups"><i class="fa fa-fw fa-table"></i> Product's option groups</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/a/options"><i class="fa fa-fw fa-file"></i> Product's options</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/a/orders"><i class="fa fa-fw fa-edit"></i> Orders</a>
                    </li>              
                    <li>
                        <a href="${pageContext.request.contextPath}/a/contactmessages "><i class="fa fa-fw fa-edit"></i> Messages</a>
                    </li>                
                    
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#shipping"><i class="fa fa-fw fa-arrows-v"></i> Shipping <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="shipping" class="collapse">
                            <li>
                                <a href="${pageContext.request.contextPath}/a/countries">Countries</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/a/sizes">Parcel sizes</a>
                            </li>
                        </ul>
                    </li>
                    
                    <li>
                        <a href="${pageContext.request.contextPath}/a/discounts"><i class="fa fa-fw fa-wrench"></i> Discount coupons</a>
                    </li>
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#demo"><i class="fa fa-fw fa-arrows-v"></i> Site preferences <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="demo" class="collapse">
                            <li>
                                <a href="${pageContext.request.contextPath}/a/properties">Properties</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/a/menu">Menu</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}/a/mainpage">Main page properties</a>
                            </li>
                        </ul>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/a/pages"><i class="fa fa-fw fa-file"></i> Static pages</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/a/users"><i class="fa fa-fw fa-desktop"></i> Users</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/a/blocks"><i class="fa fa-fw fa-wrench"></i> Blocks</a>
                    </li>
                    
                </ul>
            </div>
            
            <security:authorize access="hasAuthority('ADMIN')">
            </security:authorize>
            
             <security:authorize access="hasAuthority('MANAGER')">
             
              <div class="collapse navbar-collapse navbar-ex1-collapse">
                <ul class="nav navbar-nav side-nav">
                    <li class="active">
                        <a href="${pageContext.request.contextPath}${URL_PREFIX}welcome"><i class="fa fa-fw fa-dashboard"></i> Home </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}${URL_PREFIX}categories"><i class="fa fa-fw fa-bar-chart-o"></i> Categories</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}${URL_PREFIX}products"><i class="fa fa-fw fa-table"></i> Products</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}${URL_PREFIX}optiongroups"><i class="fa fa-fw fa-table"></i> Product's option groups</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}${URL_PREFIX}options"><i class="fa fa-fw fa-file"></i> Product's options</a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}${URL_PREFIX}orders"><i class="fa fa-fw fa-edit"></i> Orders</a>
                    </li>   
                    <li>
                        <a href="${pageContext.request.contextPath}${URL_PREFIX}contactmessages "><i class="fa fa-fw fa-edit"></i> Messages</a>
                    </li>               
                    
                    <li>
                        <a href="javascript:;" data-toggle="collapse" data-target="#shipping"><i class="fa fa-fw fa-arrows-v"></i> Shipping <i class="fa fa-fw fa-caret-down"></i></a>
                        <ul id="shipping" class="collapse">
                            <li>
                                <a href="${pageContext.request.contextPath}${URL_PREFIX}countries">Countries</a>
                            </li>
                            <li>
                                <a href="${pageContext.request.contextPath}${URL_PREFIX}sizes">Parcel sizes</a>
                            </li>
                        </ul>
                    </li>
                    
                    <li>
                        <a href="${pageContext.request.contextPath}${URL_PREFIX}discounts"><i class="fa fa-fw fa-wrench"></i> Discount coupons</a>
                    </li>  
                    
                </ul>
            </div>
            </security:authorize>
            <!-- /.navbar-collapse -->