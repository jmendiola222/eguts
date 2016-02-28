<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main" />
</head>
<body titulo="${message(code:"subscription.list.label")}" subtitulo="${message(code:"default.administrate.label")}"
	  icono="fa fa-lg fa-fw fa-list ">

<div class="ng-cloak col-sm-12" ng-controller="EditSubscriptionController">
	<section id="page">
		<div class="page-top-bar" ng-show="loaded || isNewSubscription">

		</div>
		<div ng-show="!loaded && !isNewSubscription"
			 class="text-center margin-top-100">
			<label><g:message code="default.loading.message" args="${[message(code:'subscription.list.label')]}"/></label>
			<label>
				<i class="fa fa-spinner fa-spin"></i>
			</label>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<div grails-alerts error-title="errorTitle" errors="errors"
					 success-title="successTitle" show="showAlerts"></div>
			</div>
		</div>

		<div ng-show="loaded || isNewSubscription">
			<g:form useToken="true" action="update" controller="front" name="form" id="subscriptionView" class="form-horizontal">

				<div class="form-group"
					 ng-class="{'has-error' : submitted && form.inputSubscriptionName.$invalid}">
					<label class="col-md-2 control-label"><g:message code="subscription.name.label"/></label>
					<div class="col-md-6">
						<div class="input-group col-md-12">
							<input class="form-control" name="inputSubscriptionName"
								   type="text" ng-model="itemToEdit.name"
								   required>
							<span
									ng-show="submitted && form.inputSubscriptionName.$error.required"
									class="input-group-addon" tooltip-append-to-body="true"
									tooltip="${message(code:'default.required.message')}">
								<i class="glyphicon glyphicon-remove-circle"></i>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group"
					 ng-class="{'has-error' : submitted && form.inputSubscriptionTarget.$invalid}">
					<label class="col-md-2 control-label"><g:message code="subscription.target.label"/></label>
					<div class="col-md-6">
						<div class="input-group col-md-12">
							<input class="form-control" name="inputSubscriptionTarget"
								   type="text" maxlength="150" ng-model="itemToEdit.target"
								   required>
							<span
									ng-show="submitted && form.inputSubscriptionTarget.$error.required"
									class="input-group-addon" tooltip-append-to-body="true"
									tooltip="${message(code:'default.required.message')}">
								<i class="glyphicon glyphicon-remove-circle"></i>
							</span>
						</div>
					</div>
				</div>
			</g:form>
			<div class="row">
				<article class="col-sm-12">
					<div class="">
						<h3><g:message code="subscription.subscriptionElements.label" /></h3>
					</div>
					<div class="">
						<div class="grid-container">
							<div grid columns="subscriptionElementColumns" bind-once="false"
								 no-items-message="<g:message code="default.items.not.found.message" args='["${message(code:'subscription.subscriptionElements.label')}"]'/>"
								 resource="itemToEdit.subscriptionElements"
								 on-delete-selected="deleteSubscriptionElement(item)"
								 client-side-pagination="true" allow-edit="true"
								 allow-delete="true" page-size="20" allow-details="false"
								 get-delete-confirmation-msg="'${message(code:'subscription.subscriptionElements.delete.message')}'"></div>
						</div>
					</div>
				</article>
			</div>
			<div class="row">
				<article class="col-sm-12">
					<div class="">
						<h3><g:message code="subscription.urlMatchs.label" /></h3>
					</div>
					<div class="">
						<div class="grid-container">
							<div grid columns="subscriptionUrlMatchsColumns" bind-once="false"
								 no-items-message="<g:message code="default.items.not.found.message" args='["${message(code:'subscription.urlMatchs.label')}"]'/>"
								 resource="itemToEdit.urlMatchs"
								 on-delete-selected="deleteSubscriptionUrlMatch(item)"
								 client-side-pagination="true" allow-edit="true"
								 allow-delete="true" page-size="20" allow-details="false"
								 get-delete-confirmation-msg="'${message(code:'subscription.urlMatchs.delete.message')}'"></div>
						</div>
					</div>
				</article>
			</div>
			<div class="page-footer margin-top-10">
				<g:link class="btn btn-info" controller="SubscriptionView"
						mapping="backendMapping"><g:message code="default.button.back.label" /></g:link>
				<button class="btn btn-success"
						ng-disabled="(submitted === true && form.$invalid) || saving"
						ng-click="submitted = true; form.$invalid || save();"><g:message code="default.button.save.label" /></button>
				<g:link class="btn btn-danger" controller="SubscriptionView"
						mapping="backendMapping"><g:message code="default.button.cancel.label" /></g:link>
			</div>
		</div>
	</section>
</div>
</body>
</html>