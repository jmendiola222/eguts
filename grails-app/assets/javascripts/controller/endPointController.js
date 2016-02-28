app.controller('EndPointCtrl', ['$scope', '$modal', 'EndPoint', 'BootstrapGridAPI', 'PathUtils',
    function($scope, $modal, EndPoint, BootstrapGridAPI, PathUtils) {

    $scope.columns = [{
        field : "name",
        name : "Nombre"
    } ];

    var getFilters = function() {
        return {
            sort : "name",
            order : "asc",
            fields: "id,name"
        };
    }

    $scope.getNombres = function(val) {
        var result = EndPoint.query({
            name : val
        },function(data) {
            return data;
        });

        return result.$promise;
    }

    $scope.filters = getFilters();

    $scope.gridResource = EndPoint;

    $scope.cleanFilters = function() {
        $scope.filters = getFilters();
        $scope.filter();
    }

    $scope.filter = function() {
        $scope.alert = null;
        BootstrapGridAPI.doResetPagination();
    }

    $scope.newItem = function() {
        PathUtils.goTo($scope.editUrl);
    }

    $scope.edit = function(item) {
        PathUtils.goToUrlWithId($scope.editUrl, item);
    }

    $scope.onDeleteError = function(result, alerts) {
        $scope.errors = result.data.errors;
        $scope.errorTitle = "Error al borrar el EndPoint"
        $scope.showAlerts = true;
    }

    $scope.onDeleteOk = function(item) {
        $scope.errors = null;
        $scope.successTitle =  "EndPoint '" + item.name + "' borrado."
        $scope.showAlerts = true;
    }

    $scope.deleteMessage = function(item) {
        return "¿Seguro que desea borrar el EndPoint '" + item.name + "'?";
    }

}]);

app.controller('EditEndPointController', [
        '$scope','$filter',	'PathUtils','EndPoint','$modal','BootstrapGridAPI',
        function($scope, $filter, PathUtils, EndPoint, $modal, BootstrapGridAPI) {

            var path = PathUtils.parseEdit();
            $scope.isNewEndPoint = !path.isEdit;
            $scope.itemToEdit = {
                'endPointElements' : [],
                'urlMatchs' : []
            };

            $scope.saving = false;
            $scope.isEditable = true;

            $scope.endPointElementColumns = [{
                field : "name",
                name : "Nombre"
            },{
                field : "description",
                name : "Descripcion"
            },{
                field : "type",
                name : "Tipo",
                customFilter : function(value, item) { return angular.isDefined(value.name) ? value.name: value; }
            }
            ];

            $scope.endPointUrlMatchsColumns = [{
                field : "value",
                name : "Valor"
            }];

            if (path.isEdit) {
                EndPoint.get(
                    {
                        id : path.id
                    },
                    function(response) {
                        $scope.itemToEdit = response;
                        $scope.loaded = true;
                    },
                    function(response) {
                        //TODO
                    }
                );
            } else {
                $scope.loaded = true;
            }

            var getFilters = function() {
                return {
                    sort : "name",
                    order : "asc"
                };
            };

            $scope.filters = getFilters();

            $scope.cleanFilters = function() {
                $scope.filters = getFilters();
                $scope.filter();
            };

            $scope.filter = function() {
                $scope.alert = null;
                BootstrapGridAPI.doResetPagination();
            };

            $scope.save = function() {
                $scope.saving = true;
                $scope.showAlerts = false;

                var functionToCall = $scope.isNewEndPoint ? "save" : "update";
                EndPoint[functionToCall]
                (
                    $scope.itemToEdit,
                    function(response) {

                        $scope.errors = null;
                        $scope.successTitle = 'EndPoint guardado correctamente.';
                        $scope.showAlerts = true;

                        PathUtils.updateId(response.id);
                        $scope.itemToEdit.id = response.id;
                        $scope.saving = false;
                        $scope.isNewEndPoint = false;
                    },
                    function(response) {
                        $scope.errors = [];
                        if (angular.isObject(response.data) && angular.isObject(response.data.errors)){
                            $scope.errors = response.data.errors;
                        }

                        $scope.errorTitle = 'Error al guardar el EndPoint.';
                        $scope.showAlerts = true;
                        $scope.saving = false;

                    });
            };

            $scope.deleteEndPointElement = function(item){
                //TODO
            };

            $scope.deleteEndPointUrlMatch = function(item){
                //TODO
            };

            $scope.addEndPointElement = function(item) {
                $scope.alert = null;

                var itemToEdit = { type : { name: ''}};

                var isNew = true;

                if (angular.isDefined(item)) {
                    angular.copy(item, itemToEdit);
                    isNew = false;
                }

                var modalInstance = $modal
                    .open({
                        templateUrl : 'endPointElement-template',
                        controller : "EditEndPointElementController",
                        resolve : {
                            itemToEdit : function() {
                                return itemToEdit;
                            },
                            isNew : function() {
                                return isNew;
                            }
                        }
                    });

                modalInstance.result.then(function(editedItem) {
                    if (isNew)
                        $scope.itemToEdit.endPointElements.push(editedItem);
                    else
                        item = angular.copy(editedItem, item);
                });
            };

            $scope.addUrlMatch = function(item) {
                $scope.alert = null;

                var itemToEdit = { value : ''};

                var isNew = true;

                if (angular.isDefined(item)) {
                    angular.copy(item, itemToEdit);
                    isNew = false;
                }

                var modalInstance = $modal
                    .open({
                        templateUrl : 'endPointUrlMatch-template',
                        controller : "EditEndPointElementController",
                        resolve : {
                            itemToEdit : function() {
                                return itemToEdit;
                            },
                            isNew : function() {
                                return isNew;
                            }
                        }
                    });

                modalInstance.result.then(function(editedItem) {
                    if (isNew)
                        $scope.itemToEdit.urlMatchs.push(editedItem);
                    else
                        item = angular.copy(editedItem, item);
                });


            };
        }
    ]);

app.controller('EditEndPointElementController',
    [
        '$scope',
        '$filter',
        '$modalInstance',
        'isNew',
        'itemToEdit',
        function($scope, $filter, $modalInstance, isNew,
                 itemToEdit) {

            $scope.isNew = isNew;
            if (isNew) {
                $scope.itemToEdit = { };
            } else {
                $scope.itemToEdit = itemToEdit;
            }

            $scope.ok = function() {
                $modalInstance.close($scope.itemToEdit);
            };

            $scope.cancel = function() {
                $modalInstance.dismiss('cancel');
            };
        }
    ]
);