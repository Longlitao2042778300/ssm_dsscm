<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8" />
<link rel="apple-touch-icon" sizes="76x76"
	href="${pageContext.request.contextPath }/statics/img/apple-icon.png">
<link rel="icon" type="image/png" sizes="96x96"
	href="${pageContext.request.contextPath }/statics/img/favicon.png">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>百货中心供应链管理系统</title>
<meta
	content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0'
	name='viewport' />
<meta name="viewport" content="width=device-width" />
<!-- Bootstrap core CSS     -->
<link
	href="${pageContext.request.contextPath }/statics/css/bootstrap.min.css"
	rel="stylesheet" />
<!-- Animation library for notifications   -->
<link
	href="${pageContext.request.contextPath }/statics/css/animate.min.css"
	rel="stylesheet" />
<!--  Paper Dashboard core CSS    -->
<link
	href="${pageContext.request.contextPath }/statics/css/paper-dashboard.css"
	rel="stylesheet" />
<!--  CSS for Demo Purpose, don't include it in your project     -->
<link href="${pageContext.request.contextPath }/statics/css/demo.css"
	rel="stylesheet" />
<!--  Fonts and icons     -->
<link
	href="http://maxcdn.bootstrapcdn.com/font-awesome/latest/css/font-awesome.min.css"
	rel="stylesheet">
<link href='https://fonts.googleapis.com/css?family=Muli:400,300'
	rel='stylesheet' type='text/css'>
<link
	href="${pageContext.request.contextPath }/statics/css/themify-icons.css"
	rel="stylesheet">
</head>

<body>

	<div class="wrapper">
		<div class="sidebar" data-background-color="white"
			data-active-color="danger">
			<div class="sidebar-wrapper">
				<div class="logo">
					<a href="${pageContext.request.contextPath }/jsp/index.jsp"
						class="simple-text">百货中心供应链管理系统</a>
				</div>

				<ul class="nav">
					<li><a
						href="${pageContext.request.contextPath }/jsp/index.jsp"> <i
							class="ti-home"></i>
							<p>首&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;页</p> </a></li>
					<li><a href="${pageContext.request.contextPath }/product/list"> <i class="ti-view-list-alt"></i>
							<p>商品管理</p> </a></li>
					<li><a
						href="${pageContext.request.contextPath }/bill/list">
							<i class="ti-shopping-cart-full"></i>
							<p>采购订单管理</p> </a></li>
					<li><a href="${pageContext.request.contextPath }/order/list"> <i class="ti-stats-up"></i>
							<p>销售订单管理</p> </a></li>
					<li><a
						href="${pageContext.request.contextPath }/provider/list">
							<i class="ti-package"></i>
							<p>供应商管理</p> </a></li>
					<li><a
						href="${pageContext.request.contextPath }/user/list">
							<i class="ti-id-badge"></i>
							<p>用户管理</p> </a></li>

					<li><a
						href="${pageContext.request.contextPath }/role/list">
							<i class="ti-key"></i>
							<p>用户权限</p> </a></li>

					<%--<li><a href="#"> <i class="ti-comments"></i>
							<p>新闻管理</p> </a></li>
					<li><a href="#"> <i class="ti-notepad"></i>
							<p>信息查询</p> </a></li>--%>
					<%--注销用户--%>
					<li class="active-pro"><a onclick="return confirm('您确定要退出登录吗？')"
						href="${pageContext.request.contextPath }/user/logout">
						<i class="ti-control-backward"></i>
							<p>退出</p> </a></li>
				</ul>
			</div>
		</div>

		<div class="main-panel">
			<nav class="navbar navbar-default">
				<div class="container-fluid">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar bar1"></span> <span class="icon-bar bar2"></span>
							<span class="icon-bar bar3"></span>
						</button>
						<a class="navbar-brand"
							href="${pageContext.request.contextPath }/index.jsp">DSSCM
							<small>( Department Store Supply Chain Management )</small>
						</a>
					</div>
					<div class="collapse navbar-collapse">
						<ul class="nav navbar-nav navbar-right">
							<%--没有登录就不显示查看登录用户详细信息和修改密码--%>
							<c:if test="${not empty userSession}">
								<li><a
									href="${pageContext.request.contextPath }/user/view/${userSession.id}">
										<i class="ti-user"></i>
										<p>${userSession.userName }</p> </a>
								</li>
								<%--消息列表--%>
								<%--<li class="dropdown"><a href="#" class="dropdown-toggle"
									data-toggle="dropdown"> <i class="ti-bell"></i>
										<p class="notification">5</p>
										<p>信息</p> <b class="caret"></b> </a>
									<ul class="dropdown-menu">
										<li><a href="#">Notification 1</a></li>
										<li><a href="#">Notification 2</a></li>
										<li><a href="#">Notification 3</a></li>
										<li><a href="#">Notification 4</a></li>
										<li><a href="#">Another notification</a></li>
									</ul></li>--%>
								<li><a
									href="${pageContext.request.contextPath }/jsp/pwdmodify.jsp">
										<i class="ti-settings"></i>
										<p>密码修改</p> </a>
								</li>
							</c:if>
							<%--user为空，显示登录按钮--%>
							<c:if test="${empty userSession}">
								<li><a href="${pageContext.request.contextPath }/jsp/login.jsp">
									<i class="ti-user"></i>
									<p>登录</p> </a>
								</li>
							</c:if>
							<%--显示当前时间--%>
							<li><a href="" class="dropdown-toggle"
								data-toggle="dropdown"> <i class="ti-alarm-clock"></i>
									<p id="time"></p> </a></li>
						</ul>

					</div>
				</div>
			</nav>


			<input type="hidden" id="path" name="path"
				value="${pageContext.request.contextPath }" /> <input type="hidden"
				id="referer" name="referer"
				value="<%=request.getHeader("Referer")%>" />