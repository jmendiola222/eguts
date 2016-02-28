<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main" />
</head>
<body titulo="${message(code:'subscription.list.label')}" subtitulo="${message(code:'default.administrate.label')}"
	  icono="fa fa-lg fa-fw fa-exchange ">
<div ng-controller="SubscriptionCtrl" class="ng-cloak col-sm-12">
	<div ng-init="editUrl = '${createLink(controller: 'SubscriptionView', action: 'edit' , mapping: 'backendMapping')}/'"></div>
	<section id="page">
		<div class="page-top-bar">
			<div class="form-inline">
				<a class="btn btn-success" href="#" ng-click="newItem();"> <i
						class="fa fa-plus"></i> <g:message code="default.new.f.label" args="${[message(code:'subscription.label')]}" />
				</a>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-12">
				<div grails-alerts error-title="errorTitle"
					 errors="errors" success-title="successTitle"
					 show="showAlerts"></div>
			</div>
		</div>

		<div class="row">
			<div class="col-sm-12">
				<g:form useToken="true" action="delete" controller="subscription" name="form" class="form-horizontal">
				</g:form>
				<div grid columns="columns"
					 no-items-message="<g:message code="default.items.not.found.message" args='["${message(code:'subscription.list.label')}"]'/>"
					 resource="gridResource" client-side-pagination="false"
					 allow-edit="true" allow-delete="true" page-size="20"
					 allow-details="false" filters="filters"
					 on-edit-selected="edit(item);" on-item-deleted="onDeleteOk(item);"
					 on-delete-error="onDeleteError(result, alerts)"
					 get-delete-confirmation-msg="deleteMessage(item)"></div>
			</div>
		</div>
	</section>
</div>
</body>
</html>