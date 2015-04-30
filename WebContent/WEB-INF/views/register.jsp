<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap.min.css">

<!-- Optional theme -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/css/bootstrap-theme.min.css">
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<title>Catch The Monkey</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">

<meta charset="utf-8">
<title>Register</title>
<!-- Le styles -->
<link href="bootstrap/dist/css/bootstrap.css" rel="stylesheet"
	type="text/css">
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet"
	type="text/css" />
<script type="text/javascript" src="bootstrap/dist/js/bootstrap.min.js"></script>
<style type="text/css">
body {
	padding-top: 70px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
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

.form-signin {
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 20px;
	background-color: #fff;
	border: 1px solid #e5e5e5;
	-webkit-border-radius: 5px;
	-moz-border-radius: 5px;
	border-radius: 5px;
	-webkit-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	-moz-box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
	box-shadow: 0 1px 2px rgba(0, 0, 0, .05);
}

.form-signin .form-signin-heading, .form-signin .checkbox {
	margin-bottom: 10px;
}

.form-signin input[type="text"], .form-signin input[type="password"] {
	font-size: 16px;
	height: auto;
	margin-bottom: 15px;
	padding: 7px 9px;
}
</style>
<style>
.error {
	color: red;
}
</style>
</head>

<body>
<img src="bootstrap/img/balla.jpg"
		id="full-screen-background-image" />
	<nav class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed"
					data-toggle="collapse" data-target="#navbar" aria-expanded="false"
					aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span> <span
						class="icon-bar"></span> <span class="icon-bar"></span> <span
						class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="index.html">Catch the Monkey</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Register</a></li>
					<li><a href="loginform.html">Login</a></li>
					<li><a href="contact.html">Contact</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">

		<form:form method="POST" action="/CTM/save.html" class="form-signin">
			<h2 class="form-signin-heading">Please Register</h2>
			<form:input path="firstName" class="input-block-level"
				placeholder="First Name" value="${user.firstName}" />
			<form:input path="lastName" class="input-block-level"
				placeholder="Last Name" value="${user.lastName}" />
			<form:input path="email" class="input-block-level"
				placeholder="Email address" value="${user.userEmail}" />
			<form:input path="age" class="input-block-level" placeholder="Age"
				value="${user.userAge}" />
			<form:input path="password" type="password" class="input-block-level"
				placeholder="Password" value="${user.userPassword}" />
			<input type="submit" value="Register"
				class="btn btn-large btn-success" />
			<br>
			<font color="#ff0000">${registerError}</font>
		</form:form>

	</div>

</body>
</html>
