<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Latest compiled and minified CSS -->

<!-- Optional theme -->
<title>Catch The Monkey</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="txt/css">
<link href="bootstrap/dist/css/styles.css" rel="stylesheet">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta name="description" content="">
<meta name="author" content="">

<style type="text/css">
body {
	padding-top: 70px;
	padding-bottom: 40px;
	background-color: #f5f5f5;
}

.form-signin {
	max-width: 300px;
	padding: 19px 29px 29px;
	margin: 0 auto 100px;
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
</head>
<body>
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
									<li><a href="updateBalance.html">Add Cash</a></li>
									<li><a href="strategyChoice.html">Add Strategy</a></li>
									<li><a href="creditcard.html">Add Card</a></li>
									<li><a href="${pageContext.request.contextPath}/logout.html">Logout</a></li>
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

	<div class="container">
		<form:form method="POST" commandName="account" class="form-signin">
			<h2 class="form-signin-heading">Add Cash</h2>
			<form:input path="balance"  class="form-signin" placeholder="Amount"/>
			<input type="submit" value="Add" class="btn btn-large btn-success" />
		</form:form>


	</div>

</body>
</html>