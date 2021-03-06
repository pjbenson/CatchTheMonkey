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

<title>Catch The Monkey</title>

<!-- Bootstrap Core CSS -->
<link
	href="bootstrap/bower_components/bootstrap/dist/css/bootstrap.min.css"
	rel="stylesheet" type="text/css">

<!-- MetisMenu CSS -->
<link href="bootstrap/bower_components/metisMenu/dist/metisMenu.min.css"
	rel="stylesheet" type="text/css">

<!-- Timeline CSS -->
<link href="bootstrap/dist/css/timeline.css" rel="stylesheet"
	type="text/css">

<!-- Custom CSS -->
<link href="bootstrap/dist/css/sb-admin-2.css" rel="stylesheet"
	type="text/css">

<!-- Morris Charts CSS -->
<link href="bootstrap/bower_components/morrisjs/morris.css"
	rel="stylesheet" type="text/css">

<!-- Custom Fonts -->
<link
	href="bootstrap/bower_components/font-awesome/css/font-awesome.min.css"
	rel="stylesheet" type="text/css">
<link href="bootstrap/dist/css/xcharts.min.css" rel="stylesheet"
	type="text/css">

<script type="text/javascript" src="bootstrap/dist/js/bootstrap.min.js"></script>
<script src="http://d3js.org/d3.v3.min.js" charset="utf-8"></script>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script>
	$(document).ready(function() {
		$('addCash').formValidation({
			message : 'This value is not valid',
			excluded : [ ':disabled' ],
			framework : 'bootstrap',
			icon : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				amount : {
					validators : {
						notEmpty : {
							message : 'Amount is required'
						}
					}
				}
			}
		});
	});
</script>
<script>
	$(document).ready(function() {
		$('removeCash').formValidation({
			message : 'This value is not valid',
			excluded : [ ':disabled' ],
			framework : 'bootstrap',
			icon : {
				valid : 'glyphicon glyphicon-ok',
				invalid : 'glyphicon glyphicon-remove',
				validating : 'glyphicon glyphicon-refresh'
			},
			fields : {
				amount : {
					validators : {
						notEmpty : {
							message : 'Amount is required'
						}
					}
				}
			}
		});
	});
</script>
</head>

<body>

	<div id="wrapper">

		<!-- Navigation -->
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
					<a class="navbar-brand"
						href="${pageContext.request.contextPath}/index.html">Catch the
						Monkey</a>
				</div>
				<ul class="nav navbar-nav">
					<li><a href="raglanroad.html">Raglan Road</a></li>
					<li><a href="lucayan.html">Lucayan</a></li>
					<li class="active"><a href="gingermc.html"><font color="yellow">GingerMc</font></a></li>
				</ul>
				<div id="navbar" class="navbar-collapse collapse">
					<c:if test="${not empty sessionScope.user.firstName}">
						<ul class="nav navbar-nav navbar-right">
							<li class="dropdown"><a class="dropdown-toggle"
								role="button" data-toggle="dropdown"><i
									class="glyphicon glyphicon-user"></i>${sessionScope.user.firstName}
									<span class="caret"></span></a>
								<ul class="dropdown-menu" role="menu">
									<li><a href="profile.html">My Profile</a></li>
									<li><a href="updateBalance.html">Add To Wallet</a></li>
									<li><a href="" data-target="#addCash" data-toggle="modal">Invest in GingerMc</a></li>
									<li><a href="" data-target="#removeCash" data-toggle="modal">Remove Investment</a></li>
								</ul></li>
							<li><a href="#">Wallet: <i
									class="glyphicon glyphicon-euro"></i>${sessionScope.user.account.balance}</a></li>
							<li><a href="${pageContext.request.contextPath}/logout.html"><i
									class="glyphicon glyphicon-lock"></i>Logout</a></li>
						</ul>
					</c:if>
				</div>
			</div>
		</nav>
	</div>
	<div class="container-fluid">
		<div class="row">
			<div class="col-lg-6">
				<h3>
					<i class="glyphicon glyphicon-briefcase"></i> Toolbox
				</h3>
				<hr>
				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="fa fa-line-chart fa-fw"></i> GingerMc ROI
						<div class="pull-right">
							<div class="btn-group">
								<button type="button"
									class="btn btn-default btn-xs dropdown-toggle"
									data-toggle="dropdown">
									Month <span class="caret"></span>
								</button>
								<ul class="dropdown-menu pull-right" role="menu">
									<li><a
										href="<c:url value="/updateGingerMcLineChart/0.html" />">January</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/1.html" />">February</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/2.html" />">March</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/3.html" />">April</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/4.html" />">May</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/5.html" />">June</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/6.html" />">July</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/7.html" />">August</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/8.html" />">September</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/9.html" />">October</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/10.html" />">November</a></li>
									<li><a
										href="<c:url value="/updateGingerMcLineChart/11.html" />">December</a></li>
								</ul>
							</div>
						</div>
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<img align="bottom" alt="Google Pie Chart" src="${gmLineChart}"
							width="610px" />
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="fa fa-bar-chart-o fa-fw"></i> Todays runners
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="row">
							<div class="col-lg-12">
								<table class="table table-bordered table-hover table-striped">
									<thead>
										<tr>
											<th>Race Name</th>
											<th>Horse Name</th>
											<th>Date & Time</th>
											<th>Price</th>
											<th>Side</th>
											<th>Liability</th>
											<th>Return per €100</th>
										</tr>
									</thead>
									<c:forEach items="${gMcList}" var="list">
										<tbody>
											<c:if test="${sessionScope != null}">

												<tr>
													<td><a target="_blank"
														href="https://www.betfair.com/exchange/plus/#/horse-racing/market/${list.marketId }"><c:out
																value="${list.raceName}" /></a></td>
													<td><c:out value="${list.horseName}" /></td>
													<td><c:out value="${list.date}" /></td>
													<td><c:out value="${list.price}" /></td>
													<td><c:out value="${list.side}" /></td>
													<td><c:out value="${list.liability}" /></td>
													<td><c:out value="${list.expWinnings}" /></td>
												</tr>

											</c:if>
										</tbody>
									</c:forEach>
								</table>
							</div>
							<!-- /.table-responsive -->
							<!-- /.col-lg-4 (nested) -->
							<div class="col-lg-8">
								<div id="morris-bar-chart"></div>
							</div>
							<!-- /.col-lg-8 (nested) -->
						</div>
						<!-- /.row -->
					</div>
					<!-- /.panel-body -->
				</div>

				<div class="modal fade" id="addCash" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Investment Amount</h4>
							</div>

							<div class="modal-body">
								<!-- The form is placed inside the body of modal -->
								<form:form id="addCash" method="POST"
									action="/CTM/addGmcCash.html" class="form-horizontal">
									<div class="form-group">
										<label class="col-xs-3 control-label">Amount</label>
										<div class="col-xs-5">
											<input type="text" class="form-control" name="amount" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-5 col-xs-offset-3">
											<button type="submit" class="btn btn-default">Add</button>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade" id="removeCash" tabindex="-1" role="dialog"
					aria-labelledby="myModalLabel" aria-hidden="true">
					<div class="modal-dialog">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal"
									aria-hidden="true">&times;</button>
								<h4 class="modal-title">Investment Amount</h4>
							</div>

							<div class="modal-body">
								<!-- The form is placed inside the body of modal -->
								<form:form id="removeCash" method="POST"
									action="/CTM/removeGmcCash.html" class="form-horizontal">
									<div class="form-group">
										<label class="col-xs-3 control-label">Amount</label>
										<div class="col-xs-5">
											<input type="text" class="form-control" name="amount" />
										</div>
									</div>
									<div class="form-group">
										<div class="col-xs-5 col-xs-offset-3">
											<button type="submit" class="btn btn-default">Remove</button>
										</div>
									</div>
								</form:form>
							</div>
						</div>
					</div>
				</div>

				<!-- /.panel -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="fa fa-bar-chart-o fa-fw"></i> Runner Pie Chart
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<img alt="Return Pie Chart" src="${pieChart}" width="610px" />
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
			</div>
			<!-- /.col-lg-8 -->

			<div class="col-lg-6">
				<h3>
					<i class="glyphicon glyphicon-briefcase"></i> Toolbox
				</h3>
				<hr>
				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="fa fa-bar-chart fa-fw"></i> GingerMc Information
					</div>
					<!-- /.panel-heading -->
					<div class="panel-body">
						<div class="list-group">
							<a href="#" class="list-group-item"> <i
								class="fa fa-user fa-fw"></i>Amount you have invested: <span
								class="pull-right text-muted small">
									€${sessionScope.user.account.gingermc}</span>
							</a> <a href="#" class="list-group-item"> <i
								class="fa fa-user fa-fw"></i>Date you invested: <span
								class="pull-right text-muted small"> ${signUpDate}</span>
							</a><a href="#" class="list-group-item"> <i
								class="fa fa-user fa-fw"></i> Your total return to date: <span
								class="pull-right text-muted small">€${totalUserReturn}</span>
							</a> <a href="#" class="list-group-item"> <i
								class="fa fa-users fa-fw"></i> Total number of investors: <span
								class="pull-right text-muted small">${gingerMcSubscribers }
							</span>
							</a> <a href="#" class="list-group-item"> <i
								class="fa fa-money fa-fw"></i> Total pool: <span
								class="pull-right text-muted small">
									€${sessionScope.strategy.pool}</span>
							</a> <a href="#" class="list-group-item"> <i
								class="fa fa-money fa-fw"></i> Total return to date: <span
								class="pull-right text-muted small">€${totalReturn}</span>
							</a>
						</div>
						<!-- /.list-group -->
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="fa fa-bar-chart-o fa-fw"></i>Your Monthly returns
						<div class="pull-right">
							<div class="btn-group">
								<button type="button"
									class="btn btn-default btn-xs dropdown-toggle"
									data-toggle="dropdown">
									Month <span class="caret"></span>
								</button>
								<ul class="dropdown-menu pull-right" role="menu">
									<li><a href="<c:url value="/gMcBarChartMonth/0.html" />">January</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/1.html" />">February</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/2.html" />">March</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/3.html" />">April</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/4.html" />">May</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/5.html" />">June</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/6.html" />">July</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/7.html" />">August</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/8.html" />">September</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/9.html" />">October</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/10.html" />">November</a></li>
									<li><a href="<c:url value="/gMcBarChartMonth/11s.html" />">December</a></li>
								</ul>
							</div>
						</div>
					</div>
					<div class="panel-body">
						<div id="morris-donut-chart"></div>
						<img alt="Google Pie Chart" src="${gMcBarChart}" width="610px" />
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel -->
				<div class="panel panel-default">
					<div class="panel-heading">
						<i class="fa fa-line-chart fa-fw"></i>Strategy Performance
						Comparison
					</div>
					<div class="panel-body">
						<div id="classic"></div>
					</div>
					<!-- /.panel-body -->
				</div>
				<!-- /.panel .chat-panel -->
			</div>
			<script>
				google.load('visualization', '1.1', {
					packages : [ 'line', 'corechart' ]
				});
				google.setOnLoadCallback(drawChart);
				function drawChart() {
					var classicChart;
					var classicDiv = document.getElementById('classic');
					var data = new google.visualization.DataTable();
					data.addColumn('date', 'Month');
					data.addColumn('number', "Raglan Road");
					data.addColumn('number', "GingerMc");
					data.addColumn('number', "Lucayan");

					data.addRows([ [ new Date(2015, 0), 0, 0, 0 ],
							[ new Date(2015, 1), 532, 0, 0 ],
							[ new Date(2015, 2), 559, 30, 0 ],
							[ new Date(2015, 3), 390, 4, 55 ] ]);
					var classicOptions = {
						width : 600,
						height : 400,
						backgroundColor : '#f1f8e9',
						// Gives each series an axis that matches the vAxes number below.
						series : {
							0 : {
								targetAxisIndex : 0
							}
						},
						vAxes : {
							// Adds titles to each axis.
							0 : {
								title : 'ROI'
							},
						},
						hAxis : {
							ticks : [ new Date(2015, 0), new Date(2015, 1),
									new Date(2015, 2), new Date(2015, 3),
									new Date(2015, 4), new Date(2015, 5),
									new Date(2015, 6), new Date(2015, 7),
									new Date(2015, 8), new Date(2015, 9),
									new Date(2015, 10), new Date(2015, 11) ]
						},
						vAxis : {
							viewWindow : {
								max : 600
							}
						}
					};
					classicChart = new google.visualization.LineChart(
							classicDiv);
					classicChart.draw(data, classicOptions);
				}
			</script>
			<!-- /.col-lg-4 -->
		</div>

	</div>

	<!-- jQuery -->
	<script src="bootstrap/bower_components/jquery/dist/jquery.min.js"></script>

	<!-- Bootstrap Core JavaScript -->
	<script
		src="bootstrap/bower_components/bootstrap/dist/js/bootstrap.min.js"></script>

	<!-- Metis Menu Plugin JavaScript -->
	<script
		src="bootstrap/bower_components/metisMenu/dist/metisMenu.min.js"></script>

	<!-- Morris Charts JavaScript -->
	<script src="bootstrap/bower_components/raphael/raphael-min.js"></script>
	<script src="bootstrap/bower_components/morrisjs/morris.min.js"></script>
	<script src="bootstrap/js/morris-data.js"></script>

	<!-- Custom Theme JavaScript -->
	<script src="bootstrap/dist/js/sb-admin-2.js"></script>

</body>

</html>
