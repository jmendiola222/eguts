<!DOCTYPE html>
<html>
<head>
<meta name="layout" content="public" />
</head>

<body class="home">
	<section id="index" class="page_index">
		<div class="front_index container" ng-controller="SubscriptionCtrl" ng-cloak>
			<article>
				<div class="row">
					<div class="col-sm-12" id="message">
						<div ng-show="alert != null" ng-if="alert && alert !== null"
							 class="alert alert-dismissible" ng-class="alertType" role="alert">
							<button type="button" class="close" ng-click="alert = null">
								<span aria-hidden="true">&times;</span><span class="sr-only">Close</span>
							</button>
							<span>{{alert}}</span>
						</div>
					</div>
				</div>
				<g:form useToken="true" action="anonymousSubscribe" controller="front" name="form"
						class="form-horizontal">
					<div class="form-group">
						<label for="inputUserEmail" class="col-md-2 control-label"><g:message code="default.email"/></label>

						<div class="col-md-9" ng-class="{'has-error' : submitted && form.inputUserEmail.$invalid}">
							<input class="form-control" id="inputUserEmail" name="inputUserEmail" type="email" maxlength="30"
								   ng-model="subscription.email" placeholder="${message(code: 'subscription.email.placeholder')}" required>
							<span class="text-error"
								  ng-show="submitted && form.inputUserEmail.$error.required"><g:message code="default.required.message"/></span>
							<span class="text-error"
								  ng-show="submitted && form.inputUserEmail.$error.email"><g:message code="default.invalid.email.message"/></span>
						</div>
					</div>
					<div class="form-group">
						<label for="subscriptionUrl" class="col-md-2 control-label"><g:message code="default.url"/></label>
						<div class="col-md-9" ng-class="{'has-error' : submitted && form.subscriptionUrl.$invalid}">
							<input class="form-control" id="subscriptionUrl" name="subscriptionUrl" placeholder="${message(code: 'subscription.url.placeholder')}" required type="text" ng-model="subscription.url"/>
							<span class="text-error"
								  ng-show="submitted && form.subscriptionUrl.$error.required"><g:message code="default.required.message"/></span>
							<span class="text-error"
								  ng-show="submitted && form.subscriptionUrl.$error.url"><g:message code="default.invalid.url.message"/></span>
						</div>
					</div>
				</g:form>
				<div class="form-group">
					<div class="col-md-12">
						<button type="button" class="btn btn-success pull-right"
								ng-disabled="submitted === true && form.$invalid"
								ng-click="submitted = true; form.$invalid || ok();">
							<g:message code="default.button.subscribe.label"/>
						</button>
					</div>
				</div>
			</article>
		</div>
	</section>
</body>
</html>