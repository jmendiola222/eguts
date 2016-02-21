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
<body ng-app="app">
<div ng-controller="LoginCtrl">
	<g:form useToken="true" method='POST' id='loginForm' class='cnt_login' autocomplete='off' url="${postUrl}">
		<asset:image src="logo-eguts-large.png" alt="Eguts" />
		<article class="col-sm-12 col-md-12">
			<h6><g:message code="user.label" /></h6>
			<input type="text" name='j_username' id='username'>
			<h6>
				<g:message code="springSecurity.login.password.label" />
			</h6>
			<input name='j_password' id='password' type="password" value="">
			<div class="row">
				<div class="col-md-6">
					<label> <input type="checkbox"> <g:message
							code="springSecurity.login.remember.me.label" />
					</label>
				</div>
				<div class="forgot_password col-sm-6">
					<a href="#" ng-click="showForgotPassword();">
						<g:message code="springSecurity.forgot_password" />
					</a>
				</div>
			</div>
			<g:if test='${flash.message}'>
				<div class="alert alert-danger">
					<p>
						${flash.message}
					</p>
				</div>
			</g:if>
			<input id="login-submit" class="btn btn-primary btn-block"
				   type="submit" value="Ingresar">
		</article>
	</g:form>
</div>

<g:render template="/layouts/ie8_footer"></g:render>
<!--================================================== -->

<asset:javascript src="angular.js" />
<asset:javascript src="application.js" />
<g:renderIfExists template="jsIncludes" />

<!--================================================== -->

</body>
</html>

<script type="text/ng-template" id="forgotPassword-template">
<div class="mdl_contact">
	<div class="modal-content">
		<div class="modal-header">
			<button type="button" class="close" ng-click="cancel()">
				<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
			</button>
			<h4 class="modal-title"><g:message code="forgot_password.modal.title" /></h4>
		</div>

		<div class="modal-body">
			<p><g:message code="forgot_password.modal.description" /></p>

			<g:form useToken="true" method='POST' id='pwdResetForm' autocomplete='off' url="${postUrl}">
				<section>

					<h6><g:message code="user.label" /> / <g:message code="email.label" /></h6>
					<input type="text" name='j_username' id='userInput'>

					<g:if test='${flash.message}'>
						<div class="alert alert-danger">
							<p>
								${flash.message}
							</p>
						</div>
					</g:if>


					<div class="modal-footer">
						<button type="button" class="btn btn-success"
								ng-disabled="submitted === true "
								ng-click="submitted = true; ok();">
							<g:message code="forgot_password.modal.confirm.label" />
						</button>
						<button type="button" class="btn btn-primary" ng-click="cancel()">
							<g:message code="default.button.cancel.label" />
						</button>
					</div>
				</section>
			</g:form>
		</div>
	</div>
</div>
</script>