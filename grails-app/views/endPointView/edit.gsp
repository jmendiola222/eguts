<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main" />
</head>
<body titulo="${message(code:"endPoint.list.label")}" subtitulo="${message(code:"default.administrate.label")}"
	  icono="fa fa-lg fa-fw fa-list ">

<div class="ng-cloak col-sm-12" ng-controller="EditEndPointController">
	<section id="page">
		<div class="page-top-bar" ng-show="loaded || isNewEndPoint">
			<div class="form-inline">
				<a class="btn btn-success" ng-click="addEndPointElement();" ng-show="isEditable">
					<i class="fa fa-plus"></i>
					<g:message code="default.new.label" args="${[message(code:'endPointElement.label')]}"/>
				</a>
				<a class="btn btn-success" ng-click="addUrlMatch();" ng-show="isEditable">
					<i class="fa fa-plus"></i>
					<g:message code="default.new.label" args="${[message(code:'endPoint.urlMatchs.label')]}"/>
				</a>
			</div>
		</div>
		<div ng-show="!loaded && !isNewEndPoint"
			 class="text-center margin-top-100">
			<label><g:message code="default.loading.message" args="${[message(code:'endPoint.list.label')]}"/></label>
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

		<div ng-show="loaded || isNewEndPoint">
			<g:form useToken="true" action="update" controller="front" name="form" id="endPointView" class="form-horizontal">

				<div class="form-group"
					 ng-class="{'has-error' : submitted && form.inputEndPointName.$invalid}">
					<label class="col-md-2 control-label"><g:message code="endPoint.name.label"/></label>
					<div class="col-md-6">
						<div class="input-group col-md-12">
							<input class="form-control" name="inputEndPointName"
								   type="text" ng-model="itemToEdit.name"
								   required>
							<span
									ng-show="submitted && form.inputEndPointName.$error.required"
									class="input-group-addon" tooltip-append-to-body="true"
									tooltip="${message(code:'default.required.message')}">
								<i class="glyphicon glyphicon-remove-circle"></i>
							</span>
						</div>
					</div>
				</div>
				<div class="form-group"
					 ng-class="{'has-error' : submitted && form.inputEndPointTarget.$invalid}">
					<label class="col-md-2 control-label"><g:message code="endPoint.target.label"/></label>
					<div class="col-md-6">
						<div class="input-group col-md-12">
							<input class="form-control" name="inputEndPointTarget"
								   type="text" maxlength="150" ng-model="itemToEdit.target"
								   required>
							<span
									ng-show="submitted && form.inputEndPointTarget.$error.required"
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
						<h3><g:message code="endPoint.endPointElements.label" /></h3>
					</div>
					<div class="">
						<div class="grid-container">
							<div grid columns="endPointElementColumns" bind-once="false"
								 no-items-message="<g:message code="default.items.not.found.message" args='["${message(code:'endPoint.endPointElements.label')}"]'/>"
								 resource="itemToEdit.endPointElements"
								 on-delete-selected="deleteEndPointElement(item)"
								 client-side-pagination="true" allow-edit="true"
								 allow-delete="true" page-size="20" allow-details="false"
								 get-delete-confirmation-msg="'${message(code:'endPoint.endPointElements.delete.message')}'"></div>
						</div>
					</div>
				</article>
			</div>
			<div class="page-footer margin-top-10">
				<g:link class="btn btn-info" controller="EndPointView"
						mapping="backendMapping"><g:message code="default.button.back.label" /></g:link>
				<button class="btn btn-success"
						ng-disabled="(submitted === true && form.$invalid) || saving"
						ng-click="submitted = true; form.$invalid || save();"><g:message code="default.button.save.label" /></button>
				<g:link class="btn btn-danger" controller="EndPointView"
						mapping="backendMapping"><g:message code="default.button.cancel.label" /></g:link>
			</div>
		</div>
	</section>
</div>

<script type="text/ng-template" id="endPointElement-template">
<div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" ng-click="cancel()">
			<span aria-hidden="true">&times;</span>
			<span class="sr-only">Close</span>
		</button>
		<h4 ng-if="isNew" class="modal-title"><g:message code="default.add.label" args="${[message(code:'endPointElement.label')]}" /></h4>
		<h4 ng-if="!isNew" class="modal-title"><g:message code="default.edit.label" args="${[message(code:'endPointElement.label')]}" /></h4>
	</div>
	<div class="modal-body">
		<g:form useToken="true" action="update" controller="brand" name="form" id="brandView" class="form-horizontal">

			<div class="form-group" ng-class="{'has-error' : submitted && form.inputName.$invalid}">
				<label class="col-md-2 control-label"><g:message code="endPointElement.name.label" /></label>
				<div class="col-md-10">
					<div class="input-group col-md-12">
						<input class="form-control" name="inputName" type="text" ng-model="itemToEdit.name" required>
						<span ng-show="submitted && form.inputName.$error.required"
							  class="input-group-addon" tooltip-append-to-body="true"
							  tooltip="${message(code:'default.required.message')}">
							<i class="glyphicon glyphicon-remove-circle"></i>
						</span>
					</div>
				</div>
			</div>
			<div class="form-group" ng-class="{'has-error' : submitted && form.inputDescription.$invalid}">
				<label class="col-md-2 control-label"><g:message code="endPointElement.description.label" /></label>
				<div class="col-md-10">
					<div class="input-group col-md-12">
						<input class="form-control" name="inputDescription" type="text" ng-model="itemToEdit.description">
					</div>
				</div>
			</div>
			<div class="form-group"
				 ng-class="{'has-error' : submitted && form.selectEvaluationCategory.$invalid}">
				<label class="col-md-2 control-label"><g:message code="endPointElement.type.label" /></label>
				<div class="col-md-10">
					<div class="input-group col-md-12">
						<g:select class="form-control"
								  name="selectEvaluationCategory"
								  from="${models.config.EndPointElementType.values()}"
								  valueMessagePrefix="ENUM.EndPointElementType"
								  ng-model="itemToEdit.type" required="required"
						/>
						<span
								ng-show="submitted && form.selectEvaluationCategory.$error.required"
								class="input-group-addon" tooltip-append-to-body="true"
								tooltip="${message(code:'default.required.message')}">
							<i class="glyphicon glyphicon-remove-circle"></i>
						</span>
					</div>
				</div>
			</div>
		</g:form>
		<div class="modal-footer">
			<button class="btn btn-success" ng-disabled="submitted === true && form.inputName.$invalid" ng-click="submitted = true; form.inputName.$invalid || ok();">
				<g:message code="default.button.save.label" />
			</button>
			<button class="btn btn-danger" ng-click="cancel()"><g:message code="default.button.cancel.label" /></button>
		</div>
	</div>
</script>
</body>
</html>