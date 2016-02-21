<!DOCTYPE html>
<html lang="en-us" data-placeholder-live="false">

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">

	<title>Eguts</title>
	<meta content="Web Alert Service" name="description">
	<meta content="@jmendiola222" name="author">
	<meta
			content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
			name="viewport">
	<!-- Basic Styles -->

	<asset:stylesheet href="application.css" />
	<g:render template="/layouts/ie8"></g:render>

	<!--[if lt IE 9]>
<style type="text/css">
.fa {
	display: none;
}
</style>

<![endif]-->

	<g:layoutHead />


	<!-- FAVICONS -->
	<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}"
		  type="image/x-icon">

	<script type="text/javascript">
		var URL = "${request.contextPath}";
	</script>

</head>

<body class="smart-style-0" ng-app="app" id="eguts">
<div class="ie8">
	<header id="header">
		<div id="logo-group">

			<!-- PLACE YOUR LOGO HERE -->
			<span id="logo" class="page-title" style="margin-top: 0;">
				<h2>eguts</h2>
			</span>
			<!-- END LOGO PLACEHOLDER -->
		</div>

		<!-- pulled right: nav area -->
		<div class="pull-right"></div>
		<!-- pulled right: nav area -->
		<div class="pull-right">

			<!-- collapse menu button -->
			<div id="hide-menu" class="btn-header pull-right">
				<span> <a href="javascript:void(0);" title="Collapse Menu">
					<i class="glyphicon glyphicon-align-justify"></i>
				</a>
				</span>
			</div>
			<!-- end collapse menu -->

			<!-- logout button -->
			<div id="logout" class="btn-header transparent pull-right">
				<span> <g:link controller='logout'>
					<i class="glyphicon glyphicon-log-out"></i>
				</g:link>
				</span>
			</div>
		</div>
		<!-- end logout button -->
		<!-- end pulled right: nav area -->
	</header>
	<!-- END HEADER -->
	<aside id="left-panel">

		<g:render template="/menu" />

		<span class="minifyme"> <i
				class="glyphicon glyphicon-circle-arrow-left hit hit"
				style="top: 3px"></i>
		</span>
	</aside>

	<!-- MAIN PANEL -->
	<div id="main">
		<!-- RIBBON -->
		<div id="ribbon">

			<!-- breadcrumb -->
			<ol class="breadcrumb">
			</ol>
			<!-- end breadcrumb -->

		</div>
		<div id="content">
			<div class="row">
				<div class="col-sm-12">
					<h1 class="page-title txt-color-blueDark">
						<i class="${ pageProperty(name:'body.icono') }"></i>
						${ pageProperty(name:'body.titulo') }
						<span> &gt; ${ pageProperty(name:'body.subtitulo') }
						</span>
					</h1>
				</div>
			</div>
			<div class="row">
				<g:layoutBody />
			</div>
		</div>
	</div>
</div>

<!-- END SHORTCUT AREA -->

<!--================================================== -->

<asset:javascript src="angular.js" />
<g:renderIfExists template="customAngularModules" />

<asset:javascript src="application.js" />
<!--[if lt IE 10]>
<asset:javascript src="/directives/placeholderfix.js" />
<![endif]-->

<g:renderIfExists template="jsIncludes" />

<!--================================================== -->

<!--[if IE 7]>

		<h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>

		<![endif]-->

<!-- PAGE RELATED PLUGIN(S) -->
<script>
	$(document).ready(function() {
		// DO NOT REMOVE : GLOBAL FUNCTIONS!
		pageSetUp();
		drawBreadCrumb();
	});
</script>

<!--[if lt IE 9]>
		<script type="text/javascript">
			if($.isReady) {
    			// DOM is ready
			}
		</script>
		<![endif]-->

<g:render template="/layouts/ie8_footer"></g:render>
</body>
</html>

