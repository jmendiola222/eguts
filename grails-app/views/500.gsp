<!DOCTYPE html>
<html lang="en-us">

<head>
<meta charset="utf-8">
<!--<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">-->

<title>Eguts</title>
<meta
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"
	name="viewport">
<!-- Basic Styles -->
<asset:stylesheet href="public.css" />

<g:render template="/layouts/ie8"></g:render>
</head>
<body titulo="${message(code: 'front.500.title')}" subtitulo="${message(code: 'front.500.subtitle')}">
	<div style="display: none;">
		<g:renderException exception="${exception}" />
	</div>
</body>
</html>
