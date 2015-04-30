<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">

<!-- Optional theme -->
<script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.0/jquery.min.js"></script>
<!-- Latest compiled and minified JavaScript -->
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.1/js/bootstrap.min.js"></script>
<link href="bootstrap/dist/css/bootstrap.css" rel="stylesheet" type="text/css">
<link href="bootstrap/dist/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="bootstrap/dist/js/bootstrap.min.js"></script>

<title>Catch The Monkey</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
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
.errors {
        color: red; font-weight: bold;
    }
</style>
<script>  
function validateform(){  
var userEmail=document.myform.userEmail.value;  
var userPassword=document.myform.userPassword.value;  
  
if (userEmail==null || userEmail==""){  
  alert("Name can't be blank");  
  return false;  
}else if(userPassword==null || userPassword==""){  
  alert("Password must be at least 6 characters long.");  
  return false;  
  }  
}  
</script>  
</head>
<body>
<img src="bootstrap/img/epsom.jpg"
		id="full-screen-background-image" />
	<div class="container">
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
				<a class="navbar-brand" href="${pageContext.request.contextPath}/index.html">Catch the Monkey</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="active"><a href="#">Login</a></li>
					<li><a href="register.html">Register</a></li>
					<li><a href="contact.html">Contact</a></li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">

		<form:form method="POST" commandName="user" name="myForm" class="form-signin" onsubmit="return validateform()">
			<h2 class="form-signin-heading">Log In</h2>
			<form:input path="userEmail" name="userEmail"  class="input-block-level" placeholder="Email address"/>
			<form:password path="userPassword" name="userPassword" class="input-block-level" placeholder="Password" />
			<input type="submit" value="Login" class="btn btn-large btn-success"/>
			<br><font color="#ff0000">${loginError}</font>
		</form:form>

	</div>
</div>
</body>
</html>