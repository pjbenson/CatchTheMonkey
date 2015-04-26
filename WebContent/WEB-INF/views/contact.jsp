<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">
<link rel="icon" href="../../favicon.ico" type="icon">

<title>Catch The Monkey</title>

<!-- Bootstrap core CSS -->
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"
	type="text/css">
<script src="bootstrap/docs/assets/js/ie-emulation-modes-warning.js"
	type="text/javascript"></script>
<link href="bootstrap/dist/css/carousel.css" rel="stylesheet"
	type="text/css">
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
<!-- NAVBAR
================================================== -->
<body>
	<img src="bootstrap/img/balla.jpg" id="full-screen-background-image" /> 
	<div class="container">
	<div class="navbar-wrapper">
		

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
							<li class="active"><a href="#">Home</a></li>
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
	
</body>
</html>