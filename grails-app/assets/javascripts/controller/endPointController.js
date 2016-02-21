app.controller('EndPointCtrl', ['$scope', '$modal', 'EndPoint', 'BootstrapGridAPI', function($scope, $modal, EndPoint, BootstrapGridAPI) {

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
        $scope.edit();
    }

    $scope.edit = function(item) {
        $scope.alert = null;

        var itemToEdit = new EndPoint();
        var isNew = true;

        if(angular.isDefined(item)) {
            itemToEdit = EndPoint.get({id : item.id})
            isNew = false;
        }

        var modalInstance = $modal
            .open({
                templateUrl : 'newEndPoint-template',
                controller : "EditEndPointController",
                resolve : {
                    itemToEdit : function() { return itemToEdit; },
                    isNew : function() { return isNew; }
                }
            });

        modalInstance.result
            .then(function(editedItem) {

                var functionToCall = isNew ? "$save" : "$update";

                editedItem[functionToCall](function() {
                    $scope.errors = null;
                    $scope.successTitle = "EndPoint guardado correctamente";
                    $scope.showAlerts = true;
                    BootstrapGridAPI.doRefresh();
                }, function(response) {
                    $scope.errors = response.data.errors;
                    $scope.errorTitle = "Error al guardar el EndPoint"
                    $scope.showAlerts = true;
                });

            });
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

app.controller('EditEndPointController', ['$scope', '$modalInstance', 'isNew', 'itemToEdit', function($scope, $modalInstance, isNew, itemToEdit) {

    $scope.isNew = isNew;
    $scope.itemToEdit = itemToEdit;

    $scope.ok = function() {
        $modalInstance.close($scope.itemToEdit);
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };

}]);