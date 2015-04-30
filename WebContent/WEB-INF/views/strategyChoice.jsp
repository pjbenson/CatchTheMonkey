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
	<script src="bootstrap/docs/assets/js/ie-emulation-modes-warning.js"
	type="text/javascript"></script>
<link href="bootstrap/dist/css/carousel.css" rel="stylesheet"
	type="text/css">
<style type="text/css">
</style>
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

/* Main marketing message and sign up button */
.jumbotron {
	margin: 80px 0;
	text-align: center;
}

.jumbotron h1 {
	font-size: 70px;
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
	<script type="text/javascript" src="https://www.google.com/jsapi"></script>
	<script>
					google.load('visualization', '1.1', {packages: ['line', 'corechart']});
    google.setOnLoadCallback(drawChart);
    function drawChart() {
      var classicChart;
      var classicDiv = document.getElementById('classic');
      var data = new google.visualization.DataTable();
      data.addColumn('date', 'Month');
      data.addColumn('number', "Raglan Road");
      data.addColumn('number', "GingerMc");
      data.addColumn('number', "Lucayan");

      data.addRows([
                    [new Date(2015, 0),  0,  0, 0],
                    [new Date(2015, 1),  0,  0, 0],
                    [new Date(2015, 2),   532,  0, 0],
                    [new Date(2015, 3),   559,   30, 0],
                    [new Date(2015, 4),  390, 4, 55]
                  ]);
      var classicOptions = {
    	title: 'Strategy Performance Comparison',
        width: 850,
        height: 500,
        backgroundColor: '#f1f8e9',
        // Gives each series an axis that matches the vAxes number below.
        series: {
          0: {targetAxisIndex: 0}
        },
        vAxes: {
          // Adds titles to each axis.
          0: {title: 'Return on Investment'},
        },
        hAxis: {
            ticks: [new Date(2015, 0), new Date(2015, 1), new Date(2015, 2), new Date(2015, 3),
                    new Date(2015, 4),  new Date(2015, 5), new Date(2015, 6), new Date(2015, 7),
                    new Date(2015, 8), new Date(2015, 9), new Date(2015, 10), new Date(2015, 11)
                   ]
          },
        vAxis: {
          viewWindow: {
            max: 600
          }
        }
      };
	  classicChart = new google.visualization.LineChart(classicDiv);
      classicChart.draw(data, classicOptions);
    }
    </script>
</head>

<body>
	<img src="bootstrap/img/Aintree.jpg"
		id="full-screen-background-image" />
	<div class="container">

		<div class="navbar-wrapper">
		<div class="container">

			<nav class="navbar navbar-inverse navbar-fixed-top">
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
							<li ><a href="index.html">Home</a></li>
							<c:if test="${not empty sessionScope.user.firstName}">
								<li><a href="profile.html">My Profile</a></li>
							</c:if>
							<c:if test="${empty sessionScope.user.firstName}">
								<li><a href="loginform.html">Login</a></li>
								<li><a href="register.html">Register</a></li>
							</c:if>
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
			<h1>Invest in a Strategy</h1>
			<p class="lead">Select the strategy or strategies that you wish
				to sign up for. Once you have signed up you will be directed to the
				strategy's main page. The main page will allow you to view the
				strategy's statistical information and invest as much capital as you
				wish.</p>
			<c:if test="${empty sessionScope.user}">
				<p>
					<a class="btn btn-lg btn-primary" href="register.html"
						role="button">Register today</a>
				</p>
			</c:if>
			<br>
			<div id="classic"></div>
		</div>

		<!-- Example row of columns -->
		<div class="jumbotron">
			<div class="row-fluid">
				<div class="span4">
					<h2>Raglan Road</h2>
					<p>Raglan Road is a fully automated, high risk/high reward strategy that is created using
						a mathematical hedging strategy that selects specific types of horses from
						horse racing markets based on market prices.
						</p>
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
			</div>
			</div>
			
			<div class="jumbotron">
			<div class="span4">
				<h2>Lucayan</h2>
				<p>Lucayan is a  fully automated, medium risk strategy which uses a similar mathematical
					hedging algorithm to Raglan Road. What seperates this strategy is that it
					operates on differents horse racing markets and it provides a more sophisticated pricing technique. </p>
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
			
			<div class="jumbotron">
			<div class="span4">
				<h2>GingerMc</h2>
				<p>GingerMc is low risk strategy that executes trades on specific types of horses
					based on a number of attributes such as previous form and current market position. It 
					differs from the other two strategies because it places lose bets rather than win bets.</p>
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
		</div>

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
