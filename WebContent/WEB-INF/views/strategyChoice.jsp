<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<title>Template &middot; Bootstrap</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<!-- Le styles -->
<link href="bootstrap/dist/css/bootstrap.css" rel="stylesheet"
	type="text/css">
<link href="bootstrap/dist/css/carousel.css" rel="stylesheet"
	type="text/css">
<style type="text/css">
body {
	padding-top: 20px;
	padding-bottom: 60px;
}

/* Custom container */
.container {
	margin: 0 auto;
	max-width: 1000px;
}

.container>hr {
	margin: 60px 0;
}

/* Main marketing message and sign up button */
.jumbotron {
	margin: 80px 0;
	text-align: center;
}

.jumbotron h1 {
	font-size: 100px;
	line-height: 1;
}

.jumbotron .lead {
	font-size: 24px;
	line-height: 1.25;
}

.jumbotron .btn {
	font-size: 21px;
	padding: 14px 24px;
}

/* Supporting marketing content */
.marketing {
	margin: 60px 0;
}

.marketing p+h4 {
	margin-top: 28px;
}

/* Customize the navbar links to be fill the entire space of the .navbar */
.navbar .navbar-inner {
	padding: 0;
}
</style>
<link href="bootstrap/docs/assets/css/bootstrap-responsive.css"
	rel="stylesheet">

<!-- HTML5 shim, for IE6-8 support of HTML5 elements -->
<!--[if lt IE 9]>
      <script src="../assets/js/html5shiv.js"></script>
    <![endif]-->

<!-- Fav and touch icons -->
<link rel="apple-touch-icon-precomposed" sizes="144x144"
	href="../assets/ico/apple-touch-icon-144-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="114x114"
	href="../assets/ico/apple-touch-icon-114-precomposed.png">
<link rel="apple-touch-icon-precomposed" sizes="72x72"
	href="../assets/ico/apple-touch-icon-72-precomposed.png">
<link rel="apple-touch-icon-precomposed"
	href="../assets/ico/apple-touch-icon-57-precomposed.png">
<link rel="shortcut icon" href="../assets/ico/favicon.png">
</head>

<body>

	<div class="container">

		<div class="navbar-wrapper">
			<div class="container">

				<nav class="navbar navbar-inverse navbar-static-top">
					<div class="container">
						<div class="navbar-header">
							<button type="button" class="navbar-toggle collapsed"
								data-toggle="collapse" data-target="#navbar"
								aria-expanded="false" aria-controls="navbar">
								<span class="sr-only">Toggle navigation</span> <span
									class="icon-bar"></span> <span class="icon-bar"></span> <span
									class="icon-bar"></span>
							</button>
							<a class="navbar-brand">Catch The Monkey</a>
						</div>
						<div id="navbar" class="navbar-collapse collapse">
							<ul class="nav navbar-nav">
								<li><a href="index.html">Home</a></li>
								<li><a href="contact.html">Contact</a></li>
							</ul>
							<c:if test="${not empty sessionScope.user.firstName}">
								<ul class="nav navbar-nav navbar-right">
									<li class="dropdown"><a class="dropdown-toggle"
										role="button" data-toggle="dropdown"><i
											class="glyphicon glyphicon-user"></i>
											${sessionScope.user.firstName} <span class="caret"></span></a>
										<ul class="dropdown-menu" role="menu">
											<li><a href="profile.html">My Profile</a></li>
											<li><a href="updateBalance.html">Add Cash</a></li>
										</ul></li>
									<li><a href="#">Wallet: <i
											class="glyphicon glyphicon-euro"></i>${sessionScope.user.account.balance}</a></li>
									<li><a
										href="${pageContext.request.contextPath}/logout.html"><i
											class="glyphicon glyphicon-lock"></i>Logout</a></li>
								</ul>
							</c:if>
						</div>
					</div>
				</nav>

			</div>
		</div>

		<!-- Jumbotron -->
		<div class="jumbotron">
			<h1>Choose Strategy</h1>
			<p class="lead">Select the strategy or strategies that you wish
				to sign up for. Once you have signed up you will be directed to the
				strategies main page. The main page will allow you to view the
				strategy's statistical information and invest as much capital as you
				wish.</p>
			<c:if test="${empty sessionScope.user}">
				<p>
					<a class="btn btn-lg btn-primary" href="register.html"
						role="button">Register today</a>
				</p>
			</c:if>
		</div>

		<!-- Example row of columns -->
		<div class="row-fluid">
			<div class="span4">
				<h2>Raglan Road</h2>
				<p>Raglan road is a high risk/high reward strategy that parses
					and process very specific data obtained from the Betfair exchange
					and seeks to make profit through a range of high risk positions.</p>
				<c:choose>
					<c:when test="${empty sessionScope.user}">
						<p>
							<a class="btn btn-large btn-success" href="register.html"
								role="button">Register today</a>
						</p>
					</c:when>
					<c:otherwise>
						<form:form method="POST" action="/CTM/strategy1.html">
							<input type="submit" value="Invest"
								class="btn btn-large btn-success" />
						</form:form>
					</c:otherwise>
				</c:choose>

			</div>
			<div class="span4">
				<h2>Ginger Mc</h2>
				<p>Donec id elit non mi porta gravida at eget metus. Fusce
					dapibus, tellus ac cursus commodo, tortor mauris condimentum nibh,
					ut fermentum massa justo sit amet risus. Etiam porta sem malesuada
					magna mollis euismod. Donec sed odio dui.</p>
				<c:choose>
					<c:when test="${empty sessionScope.user}">
						<p>
							<a class="btn btn-large btn-success" href="register.html"
								role="button">Register today</a>
						</p>
					</c:when>
					<c:otherwise>
						<form:form method="POST" action="/CTM/strategy2.html">
							<input type="submit" value="Invest"
								class="btn btn-large btn-success" />
						</form:form>
					</c:otherwise>
				</c:choose>
			</div>
			<div class="span4">
				<h2>Lucayan</h2>
				<p>Donec sed odio dui. Cras justo odio, dapibus ac facilisis in,
					egestas eget quam. Vestibulum id ligula porta felis euismod semper.
					Fusce dapibus, tellus ac cursus commodo, tortor mauris condimentum
					nibh, ut fermentum massa.</p>
				<c:choose>
					<c:when test="${empty sessionScope.user}">
						<p>
							<a class="btn btn-large btn-success" href="register.html"
								role="button">Register today</a>
						</p>
					</c:when>
					<c:otherwise>
						<form:form method="POST" action="/CTM/strategy3.html">
							<input type="submit" value="Invest"
								class="btn btn-large btn-success" />
						</form:form>
					</c:otherwise>
				</c:choose>
			</div>
		</div>

		<hr>

		<div class="footer">
			<p>&copy; Paul Benson</p>
		</div>

	</div>
	<!-- /container -->

	<!-- Le javascript
    ================================================== -->
	<!-- Placed at the end of the document so the pages load faster -->

</body>
</html>
