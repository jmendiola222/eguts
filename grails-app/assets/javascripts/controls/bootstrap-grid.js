'use strict';

var GridModule = angular.module('bootstrap-grid', [ 'ngResource',
		'ui.bootstrap' ]);

GridModule.factory('BootstrapGridAPI', function() {
	return {
		refresh : {},
		resetPagination : {},
		
		/*currentPage : function() {
			return $scope.currentPage;
		},*/
		doRefresh : function(gridName) {
			if(!angular.isDefined(gridName)) {
				this.refresh[""] = true;
			} else {
				this.refresh[gridName] = true;
			}
		},
		
		doResetPagination : function(gridName) {
			if(!angular.isDefined(gridName)) {
				this.resetPagination[""] = true;
			} else {
				this.resetPagination[gridName] = true;
			}
		}
		/*itemsCount : function() {
			return $scope.itemsCout;
		}*/
	}
});

GridModule.factory('PaginationUtils', function() {

	return {
		/**
		 * Returns itemsCountHeader based on AbstractCrudController definition
		 */
		getItemsCount : function(headers) {
			return parseInt(headers("itemsCount"));
		},

		/**
		 * Returns an object to be sent as queryString to do serverSide
		 * pagination based on AbstractCrudController definition
		 */
		getPaginationQuery : function(pageNumber, pageSize) {
			return {
				'page' : pageNumber,
				'pageSize' : pageSize
			};

		}

	}
});

GridModule
		.controller(
				'BootstrapGridController',
				function($scope, PaginationUtils, BootstrapGridAPI, $modal) {
					
					$scope.api = BootstrapGridAPI;
					$scope.alerts = [];
					$scope.currentPage = 1;

					$scope.internalPageChanged = function(pageNumber) {
						if (pageNumber <= 0 || !angular.isDefined(pageNumber)) {
							pageNumber = 1;
							$scope.currentPage = 1;
						}
						var paginationQuery = PaginationUtils
								.getPaginationQuery(pageNumber, $scope.pageSize);

						$scope.strategy.doQuery($scope, paginationQuery,
								PaginationUtils);

						$scope.onPageChanged({
							newPage : pageNumber
						});
					};

					$scope.internalDelete = function(item, index) {
						$scope.alerts = [];

						var msg = $scope.getDeleteConfirmationMsg({
							item : item
						});

						if (!angular.isDefined(msg)) {
							$scope.strategy.doDelete($scope, item, index);
						} else {
							var modal = $scope.openModal("AtenciÃ³n", msg);
							modal.result.then(function() {
								$scope.strategy.doDelete($scope, item, index);
							});
						}

					};

					$scope.internalEdit = function(item) {
						$scope.onEditSelected({
							item : item
						});
					};

					$scope.internalView = function(item) {
						$scope.onViewSelected({
							item : item
						});
					};

					$scope.execCustomAction = function(customAction, item,
							index, e) {
						e.preventDefault();
						$scope.alerts = [];
						$scope.$emit(customAction.action, {
							item : item,
							index : index,
							items : $scope.resource
						});
					}

					$scope.mustShowCustomAction = function(customAction, item) {
						if (!angular.isDefined(customAction.visibleWhen))
							return true;

						return customAction.visibleWhen(item);
					};

					$scope.$watch("currentPage", function(){
						$scope.internalPageChanged($scope.currentPage);
					}, true);
					
					$scope.$watch('api.refresh', function() {
						if($scope.api.refresh[$scope.name] === true) $scope.internalPageChanged($scope.currentPage);
						//FIXME enters here twice
						$scope.api.refresh[$scope.name] = false;
					}, true);

					$scope.$watch('api.resetPagination', function() {
						if($scope.api.resetPagination[$scope.name] === true)	$scope.internalPageChanged(0);
						//FIXME enters here twice
						$scope.api.resetPagination[$scope.name] = false;
					}, true);
					
					$scope.closeAlerts = function(index) {
						$scope.alerts.splice(index, 1);
					}

					$scope.openModal = function(modalTitle, modalDescription) {

						$scope.modalTitle = modalTitle;
						$scope.modalDescription = modalDescription;

						var modalInstance = $modal.open({
							templateUrl : 'myModalContent',
							controller : ModalInstanceCtrl,
							resolve : {
								title : function() {
									return $scope.modalTitle;
								},
								description : function() {
									return $scope.modalDescription;
								}
							}
						});

						return modalInstance;
					};

					if ($scope.clientSidePagination) {
						$scope.$watch('resource.length', function() {
							$scope.itemsCount = $scope.resource.length;
							if ($scope.itemsCount) {
								$scope.noOfPages = Math.ceil($scope.itemsCount
										/ $scope.pageSize);
							}

							$scope.internalPageChanged($scope.currentPage);
						});
					}
				})
		.directive(
				'grid',

				function() {
					/**
					 * @columns An array with columns to be bound. A column is
					 *          an object with this format: { 'name' : "A Column
					 *          Label", // Label to be put on table header
					 *          'field' : "myField", // Field from object to be
					 *          bound. It accepts nested fields, ie:
					 *          obj.myField, obj.myField.mySubField. If no field
					 *          is given, the object is bound. 'template': "<span>{{itemToBind.myField}}"</span">, //
					 *          Inline template. itemToBind is a reference to
					 *          the current item to bind 'templateUrl':
					 *          "templates/myTemplate.html", // Template fetch
					 *          from the template cache. It should have
					 *          itemToBind as template field. 'customFilter' :
					 *          function(value) { return value ? "Yes" : "No" }, //
					 *          If no template is given, plain text is rendered
					 *          inside span. Custom filter is used to change de
					 *          value insted of using an angular filter. For
					 *          example, changing a true/false into yes/no }
					 * @resource $resource or an array of object to be bound. If
					 *           resource, delete is called directly on the
					 *           restService
					 * @clientSidePagination If true, resource should be an
					 *                       array. Because all items are
					 *                       handled clientside, it works on the
					 *                       resource array
					 * @customActions An array of object to add to the actions
					 *                column as anchors. Each object should be
					 *                like: { icon : "fa fa-play", // Class to
					 *                be applied to anchor text : "Execute", //
					 *                Text to be displayed action : "execute", //
					 *                An event is thrown with emit when action
					 *                is clicked. Action item and index are sent
					 *                as params visibleWhen : function(item) {
					 *                ... }, // Function that returns a boolean
					 *                value. If true, the action will be shown.
					 *                getLink : function(item) {return
					 *                "/anUrl/aValue/" + item.id;}, // If this
					 *                function is given, instead of an ngClick,
					 *                action uses anchor href }
					 * @filters an object to be sent as query string if it has
					 *          server side pagination
					 * @pageSize and int to determine de pageSize
					 * @allowEdit if true, edit button is shown. When edit is
					 *            clicked, onEditSelected function is called
					 * @allowDelete if true, delete button is shown.
					 * 
					 * TODO Finish this doc
					 */
					return {
						restrict : 'A',
						scope : {
							name : '@',
							tableClass : '@',
							columns : '=',
							resource : '=',
							clientSidePagination : '=',
							customActions : '=',
							filters : '=',
							pageSize : '=',
							// currentPage : '=',
							allowEdit : '=',
							editIcon : '@',
							editText : '@',
							editClass : '@',
							allowDelete : '=',
							deleteIcon : '@',
							deleteText : '@',
							deleteClass : '@',
							allowDetails : '=',
							bindOnce : "@",
							allowCheck : '@',
							noItemsMessage : '@',
							onPageChanged : '&',
							onItemDeleted : '&',
							onDeleteError : '&',
							getDeleteConfirmationMsg : '&',
							onEditSelected : '&',
							onViewSelected : '&',
							paginationSize : "=",
						},
						templateUrl : URL + '/controls/bootstrap-grid',
						controller : 'BootstrapGridController',
						replace : true,
						compile : function(element, attrs) {
							if (!attrs.name) {
								attrs.name = '';
							}
							
							if (!attrs.tableClass) {
								attrs.tableClass = "table table-striped table-bordered table-hover adataTable table-condensed";
							}
							
							if (!attrs.paginationSize) {
								attrs.paginationSize = '10';
							}
							if (!attrs.allowCheck) {
								attrs.allowCheck = 'false';
							}

							if (!attrs.bindOnce) {
								attrs.bindOnce = 'true';
							}

							if (! angular.isDefined(attrs.editIcon)) {
								attrs.editIcon = 'fa fa-edit';
							}

							if (! angular.isDefined(attrs.deleteIcon)) {
								attrs.deleteIcon = 'fa fa-trash-o';
							}

							if (! angular.isDefined(attrs.deleteClass)) {
								attrs.deleteClass = 'btn btn-default';
							}

							if (! angular.isDefined(attrs.editText)) {
								attrs.editText = 'Editar';
							}

							if (! angular.isDefined(attrs.deleteText)) {
								attrs.deleteText = 'Eliminar';
							}

							if (! angular.isDefined(attrs.editClass)) {
								attrs.editClass = 'btn btn-default';
							}

							return function(scope, container, attrs) {

								var hasCustomActions = false;
								if (angular.isDefined(scope.customActions)) {
									hasCustomActions = scope.customActions.length > 0;
								}

								scope.bindOnce = attrs.bindOnce;

								scope.showActions = scope.allowDelete
										|| scope.allowEdit
										|| scope.allowDetails
										|| hasCustomActions;

								if (scope.clientSidePagination) {
									scope.strategy = clientSide;
								} else {
									scope.strategy = serverSide;
								}
							}
						}
					};
				})
		.directive(
				'bindModel',
				function($compile) {
					return {
						link : function(scope, tEl, tAtr) {
							tEl[0].removeAttribute('bind-model')

							tEl[0].setAttribute('ng-model', 'item.'
									+ scope.$eval(tAtr.bindModel))
							$compile(tEl[0])(scope)

						}
					}
				})
		.directive(
				'bindItem',
				function($compile) {
					var textTemplate = "<span>{{valueToBind}}</span>";
					var updateableTemplate = "<span>{{customFilter($model, itemToBind)}}</span>";
					var editableTextTemplate = '<input type="text" ng-model="$model">'
					var optionsColumnTemplate = '<select ng-model="$model" ng-options="option.value as option.text for option in column.options"></select>'

					return {
						scope : {
							itemToBind : '=',
							column : "=",
							bindOnce : "="
						},
						link : function(scope, element, attrs) {
							var column = scope.column;

							var field;
							if (angular.isDefined(column.field)) {
								field = scope.$eval("column.field");
								field = "itemToBind." + field;
							} else {
								field = scope.itemToBind;
							}

							scope.customFilter = function(value, item) {
								return value;
							}

							if (angular.isDefined(column.customFilter)) {
								scope.customFilter = column.customFilter;
							}

							var replaceModel = function(template, value) {
								return template.replace("$model", value);
							}

							var template;
							// FIXME Move this to enum or something

							if (column.options) {
								template = replaceModel(optionsColumnTemplate,
										field);
							} else {
								if (column.isEditable) {
									template = replaceModel(
											editableTextTemplate, field);
								} else {
									if (scope.bindOnce == "true") {
										if (angular.isDefined(column.field)) {
											scope.valueToBind = scope
													.customFilter(scope
															.$eval(field),
															scope.itemToBind);
										} else {
											scope.valueToBind = field;
										}
										template = textTemplate;
									} else {
										template = replaceModel(
												updateableTemplate, field);
									}
								}
							}

							element.html(template).show();

							$compile(element.contents())(scope);
						}
					}

				}).directive(
				'bindTemplate',
				function($compile, $templateCache) {
					return {
						scope : {
							itemToBind : '=',
							column : "=",
						},
						link : function(scope, element, attrs) {
							if (angular.isDefined(scope.column.templateUrl)) {
								var template = $templateCache
										.get(scope.column.templateUrl);
							} else {
								var template = scope.column.template;
							}
							element.html(template).show();
							$compile(element.contents())(scope);
						}
					}

				});

var clientSide = {
	doQuery : function($scope, paginationQuery, PaginationUtils) {
		if ($scope.resource) {
			var start = (paginationQuery.page - 1) * paginationQuery.pageSize;
			var end = start + paginationQuery.pageSize;
			$scope.items = $scope.resource.slice(start, end);
		}
	},

	doDelete : function($scope, item, index) {
		// Remove from clientSide copy
		$scope.resource.splice(index, 1);

		$scope.onItemDeleted({
			item : item
		});
	}
};

var serverSide = {
	doQuery : function($scope, paginationQuery, PaginationUtils) {
		// Merge query with filters query
		if (!angular.isDefined($scope.resource)) {
			$scope.items = [];
			$scope.itemsCount = 0;
			return;
		}

		var query = angular.extend({}, paginationQuery, $scope.filters);
		$scope.resource.query(query, function(data, headers) {
			$scope.items = data;
			$scope.itemsCount = PaginationUtils.getItemsCount(headers);
			if ($scope.itemsCount) {
				$scope.noOfPages = Math.ceil($scope.itemsCount
						/ $scope.pageSize);
			}
		});
	},
	doDelete : function($scope, item, index) {
		item.$delete(function() {

			$scope.items.splice(index, 1);
			// reload page
			$scope.internalPageChanged($scope.currentPage);

			$scope.onItemDeleted({
				item : item
			});
		}, function(result) {
			var handled = $scope.onDeleteError({
				result : result,
				alerts : $scope.alerts
			});

			if (!angular.isDefined(handled)) {
				$scope.alerts = [ {
					type : 'danger',
					msg : result.data.message
				} ];
			}
		});
	}
};

var ModalInstanceCtrl = function($scope, $modalInstance, title, description) {

	$scope.title = title;
	$scope.description = description;

	$scope.ok = function() {
		$modalInstance.close("ok");
	};

	$scope.cancel = function() {
		$modalInstance.dismiss('cancel');
	};
};
