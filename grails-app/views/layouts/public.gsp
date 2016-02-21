<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Eguts</title>
<asset:stylesheet href="public.css" />
<link rel="shortcut icon" href="${assetPath(src: 'favicon.ico')}"
	type="image/x-icon">

<g:render template="/layouts/ie8"></g:render>

<script type="text/javascript">var URL = "${request.contextPath}";
</script>

<g:layoutHead />

</head>
<body ng-app="app" id="egutsapp">

	<g:render template="/layouts/front/navbar" />

	<g:layoutBody />

	<g:render template="/layouts/front/footer" />

	<!--SCRIPTS-->
	<g:renderIfExists template="customAngularModules" />
	<asset:javascript src="public.js" />
	<g:renderIfExists template="jsIncludes" />

	<script type="text/javascript">
		$(function() {
			$(".btn_nav").click(function() {
				$("header nav").toggleClass("mobile_hide");
			});
		});
	</script>

	<asset:javascript src="pace/pace.min.js" />

	<!--[if IE 7]>

		<h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>

		<![endif]-->

	<!-- PAGE RELATED PLUGIN(S) -->

	<g:render template="/layouts/ie8_footer"></g:render>
	<!--end SCRIPTS-->

</body>
</html>