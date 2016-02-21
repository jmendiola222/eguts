<!DOCTYPE html>
<html>
<head>
	<meta name="layout" content="main" />
</head>
<body titulo="EndPoints" subtitulo="Administrar"
	  icono="fa fa-lg fa-fw fa-exchange ">
<div ng-controller="EndPointCtrl" class="ng-cloak col-sm-12">
	<section id="page">
		<div class="page-top-bar">
			<div class="form-inline">
				<span>EndPoint:</span> <select class="form-control"
											   ng-model="filters['endPoint.id']" title="EndPoint"
											   ng-options="endPoint.id as endPoint.name for endPoint in endPoints" required></select>
				<input placeholder="Nombre" ng-model="filters.name" type="text"
					   class="form-control" typeahead-min-length="3"
					   typeahead="endPoint.name for endPoint in getNombres($viewValue)" /> <a
					class="btn btn-info" ng-click="filter()"> <i
						class="fa fa-filter"></i> Filtrar
			</a> <a class="btn btn-info" ng-click="cleanFilters()"> <i
					class="fa fa-times"></i> Limpiar Filtros
			</a> <a class="btn btn-info" ng-click="cleanFilters()"> <i
					class="fa fa-refresh"></i> <span>Actualizar Datos</span>
			</a> <a class="btn btn-success" href="#" ng-click="newItem();"> <i
					class="fa fa-plus"></i> Nuevo EndPoint
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
					 no-items-message="No se encontraron endPointos"
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

<script type="text/ng-template" id="newEndPoint-template" >
<div class="modal-content">
	<div class="modal-header">
		<button type="button" class="close" ng-click="cancel();">
			<span aria-hidden="true">&times;</span> <span class="sr-only">Close</span>
		</button>
		<h4 ng-if="isNew" class="modal-title">Agregar EndPointo</h4>
		<h4 ng-if="!isNew" class="modal-title">Editar EndPointo</h4>
	</div>
<div class="modal-body">
	<g:form useToken="true" action="update" controller="endPoint" name="form" id="endPointView" class="form-horizontal">
		<div class="form-group"
			 ng-class="{'has-error' : submitted && form.inputEndPoint.$invalid}">
			<label class="col-md-2 control-label">EndPoint</label>
			<div class="col-md-10">
				<div class="input-group col-md-12">
					<select class="form-control" ng-model="itemToEdit.endPoint.id"
							name="inputEndPoint"
							ng-options="endPoint.id as endPoint.name for endPoint in endPoints"
							required></select> <span
						ng-show="submitted && form.inputEndPoint.$error.required"
						class="input-group-addon" tooltip-append-to-body="true"
						tooltip="Este campo es requerido"> <i
							class="glyphicon glyphicon-remove-circle"></i>
				</span>
				</div>
			</div>
		</div>
		<div class="form-group"
			 ng-class="{'has-error' : submitted && form.inputEndPointName.$invalid}">
			<label class="col-md-2 control-label">Nombre</label>
			<div class="col-md-10">
				<div class="input-group col-md-12">
					<input class="form-control" name="inputEndPointName" type="text"
						   maxlength="150" ng-model="itemToEdit.name" required> <span
						ng-show="submitted && form.inputEndPointName.$error.required"
						class="input-group-addon" tooltip-append-to-body="true"
						tooltip="Este campo es requerido"> <i
							class="glyphicon glyphicon-remove-circle"></i>
				</span>
				</div>
			</div>
		</div>
		</div>
	</g:form>
	<div class="modal-footer">
		<button class="btn btn-success"
				ng-disabled="submitted === true && form.inputEndPointName.$invalid"
				ng-click="submitted = true; form.inputEndPointName.$invalid || ok();">Guardar</button>
		<button class="btn btn-danger" ng-click="cancel()">Cancelar</button>
	</div>
</div>
</script>
</body>
</html>