<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main" />
</head>
<body titulo="EndPoints" subtitulo="${message(code:'default.administrate.label')}"
	  icono="fa fa-lg fa-fw fa-exchange ">
<div ng-controller="EndPointCtrl" class="ng-cloak col-sm-12">
	<div ng-init="editUrl = '${createLink(controller: 'EndPointView', action: 'edit' , mapping: 'backendMapping')}/'"></div>
	<section id="page">
		<div class="page-top-bar">
			<div class="form-inline">
				<input placeholder="${message(code:'endPoint.name.label')}" ng-model="filters.name" type="text"
					   class="form-control" typeahead-min-length="3"
					   typeahead="endPoint.name for endPoint in getNombres($viewValue)" />
				<a class="btn btn-info" ng-click="filter()">
					<i class="fa fa-filter"></i>
					<g:message code="default.button.filter.label"/>
				</a>
				<a class="btn btn-info" ng-click="cleanFilters()">
					<i class="fa fa-times"></i>
					<g:message code="default.button.filter.clean.label"/>
				</a>
				<a class="btn btn-info" ng-click="cleanFilters()">
					<i class="fa fa-refresh"></i>
					<span><g:message code="default.button.filter.refresh.label"/></span>
				</a>
				<a class="btn btn-success" href="#" ng-click="newItem();"> <i
						class="fa fa-plus"></i> <g:message code="default.new.label" args="${[message(code:'endPoint.label')]}" />
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
				<g:form useToken="true" action="delete" controller="endPoint" name="form" class="form-horizontal">
				</g:form>
				<div grid columns="columns"
					 no-items-message="<g:message code="default.items.not.found.message" args='["${message(code:'endPoint.list.label')}"]'/>"
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