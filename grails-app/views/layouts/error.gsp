<!DOCTYPE html>
<html lang="es">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Eguts</title>

<asset:stylesheet href="public.css" />
<g:render template="/layouts/ie8"></g:render>

</head>
<body>
	<div class="cnt_error">
		<asset:image src="logo-eguts.png" alt="eguts" />
		<article>
			<h2>
				${ pageProperty(name:'body.titulo') }
			</h2>
			<h6>
				${flash.message}
			</h6>
			<h6>
				${ pageProperty(name:'body.subtitulo') }
			</h6>
			<div class="actions">
				<a class="btn btn-primary" onClick="javascript:history.back();">
					<i class="fa fa-reply"></i> Volver
				</a>
				<g:link controller="Front" class="btn btn-default">
					<i class="fa fa-home"></i>
						Ir al inicio
					</g:link>
			</div>
		</article>

		<div id="error-msg" style="display: none;">
			<g:renderException exception="${exception}" />
		</div>
	</div>

	<!--[if IE 7]>

		<h1>Your browser is out of date, please update your browser by going to www.microsoft.com/download</h1>

		<![endif]-->

	<!-- PAGE RELATED PLUGIN(S) -->

	<g:render template="/layouts/ie8_footer"></g:render>

</body>
</html>

