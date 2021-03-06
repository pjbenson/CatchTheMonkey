<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta charset="utf-8">
<title>Catch The Monkey</title>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<link
	href="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css"
	rel="stylesheet">

<!--[if lt IE 9]>
          <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
<link rel="shortcut icon" href="/bootstrap/img/favicon.ico">
<link rel="apple-touch-icon" href="/bootstrap/img/apple-touch-icon.png">
<link rel="apple-touch-icon" sizes="72x72"
	href="/bootstrap/img/apple-touch-icon-72x72.png">
<link rel="apple-touch-icon" sizes="114x114"
	href="/bootstrap/img/apple-touch-icon-114x114.png">
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<script src="bootstrap/docs/assets/js/ie-emulation-modes-warning.js"
	type="text/javascript"></script>

<!-- CSS code from Bootply.com editor -->
<style type="text/css">
      html, body {
        height: 100%;
        width: 100%;
        padding: 0;
        margin: 0;
      }

      #full-screen-background-image {
        z-index: -999;
        min-height: 100%;
        min-width: 1024px;
        width: 100%;
        height: auto;
        position: fixed;
        top: 0;
        left: 0;
      }

      #wrapper {
        position: relative;
        width: 800px;
        min-height: 400px;
        margin: 100px auto;
        color: #333;
      }

      a.to-top:link,
      a.to-top:visited, 
      a.to-top:hover {
        margin-top: 1000px;
        display: block;
        font-weight: bold;
        padding-bottom: 30px;
        font-size: 30px;
      }

    </style>

</head>

<!-- HTML code from Bootply.com editor -->

<body>
<img src="bootstrap/img/Cheltenham.gif" id="full-screen-background-image" /> 
	<!-- Header -->
	
			<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
				<div class="container">
					<div class="navbar-header">
						<button type="button" class="navbar-toggle collapsed"
							data-toggle="collapse" data-target="#navbar"
							aria-expanded="false" aria-controls="navbar">
							<span class="sr-only">Toggle navigation</span> <span
								class="icon-bar"></span> <span class="icon-bar"></span> <span
								class="icon-bar"></span>
						</button>
						<a class="navbar-brand" href="index.html">Catch The Monkey</a>
					</div>
					<div id="navbar" class="navbar-collapse collapse">
						<ul class="nav navbar-nav">
							<li class="active"><a href="">Profile</a></li>
							<li><a href="contact.html">Contact</a></li>
							<li class="dropdown"><a href="#" class="dropdown-toggle"
								data-toggle="dropdown" role="button" aria-expanded="false">Options
									<span class="caret"></span>
							</a>
								<ul class="dropdown-menu" role="menu">
									<li><a href="updateBalance.html">Update Wallet</a></li>
									<li><a href="strategyChoice.html">Invest in Strategy</a></li>
									<li><a href="creditcard.html">Add Credit Card</a></li>
								</ul></li>
						</ul>
						<ul class="nav navbar-nav navbar-right">
								<li><a ><i class="glyphicon glyphicon-user"></i>
										${sessionScope.user.firstName}</a>
								<li><a href="#">Wallet: <i
										class="glyphicon glyphicon-euro"></i>${sessionScope.user.account.balance}</a></li>
								<li><a
									href="${pageContext.request.contextPath}/logout.html"><i
										class="glyphicon glyphicon-lock"></i>Logout</a></li>
							</ul>
					</div>
					</div>
			</nav>




	<!-- /Header -->
	<hr>
	<hr>
	<!-- Main -->
	<div class="container">
		<div class="row">
			<div class="col-md-12 col-xs-10">
				<div class="well panel panel-default">
					<div class="panel-body">
						<div class="row">
							<div class="col-xs-12 col-sm-4 text-center">
								<img src="bootstrap/img/avatar.jpg" alt=""
									class="center-block img-circle img-thumbnail img-responsive">
							</div>
							<!--/col-->
							<div class="col-xs-12 col-sm-8">
								<h2>${sessionScope.user.firstName}
									${sessionScope.user.lastName}</h2>
								<p>
									<strong>Email: </strong> ${sessionScope.user.userEmail}
								</p>
								<p>
									<strong>Age: </strong> ${sessionScope.user.userAge}
								</p>
								<p>
									<strong>Wallet: </strong><i class="glyphicon glyphicon-euro"></i>
									${sessionScope.user.account.balance}
								</p>
								<p>
									<strong>Your Strategies: </strong>
								</p>
							</div>
							<!--/col-->
							<div class="clearfix"></div>
							<c:if test="${not empty raglanroad}">
								<div class="col-xs-12 col-sm-4">
									<h2>
										<strong><i class="glyphicon glyphicon-euro"></i>${raglanroad}
										</strong>
									</h2>
									<a class="btn btn-success btn-block" href="raglanroad.html"
										role="button">Raglan Road</a>
								</div>
							</c:if>
							<!--/col-->
							<c:if test="${not empty lucayan}">
								<div class="col-xs-12 col-sm-4">
									<h2>
										<strong><i class="glyphicon glyphicon-euro"></i>${lucayan}</strong>
									</h2>
									<a class="btn btn-primary btn-block" href="lucayan.html"
										role="button">Lucayan</a>
								</div>
							</c:if>
							<!--/col-->
							<c:if test="${not empty gingermc}">
								<div class="col-xs-12 col-sm-4">
									<h2>
										<strong><i class="glyphicon glyphicon-euro"></i>${gingermc}</strong>
									</h2>
									<a class="btn btn-info btn-block" href="gingermc.html"
										role="button">GingerMc</a>
								</div>
							</c:if>
						</div>
						<!--/row-->
					</div>
					<!--/panel-body-->
				</div>
				<!--/panel-->
			</div>
			<!--/col-->
		</div>
		<!--/row-->
	</div>
	<!--/container-->
	<!-- /Main -->


	<div class="modal" id="addWidgetModal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
						aria-hidden="true">×</button>
					<h4 class="modal-title">Add Money</h4>
				</div>
				<div class="modal-body">
					<form id="loginForm" action="" method="post"
						class="form-horizontal">
						<div class="form-group">
							<label class="col-xs-3 control-label">Amount</label>
							<div class="col-xs-5">
								<input type="number" class="form-control" name="balance" />
							</div>
						</div>

						<div class="form-group">
							<div class="col-xs-5 col-xs-offset-3">
								<button type="submit" class="btn btn-default">Add</button>
							</div>
						</div>
					</form>
				</div>
				<div class="modal-footer">
					<a href="#" data-dismiss="modal" class="btn">Close</a> <a href="#"
						class="btn btn-primary">Save changes</a>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dalog -->
	</div>
	<!-- /.modal -->





	<script type='text/javascript'
		src="//ajax.googleapis.com/ajax/libs/jquery/1.9.1/jquery.min.js"></script>


	<script type='text/javascript'
		src="//maxcdn.bootstrapcdn.com/bootstrap/3.2.0/js/bootstrap.min.js"></script>





</body>
</html>