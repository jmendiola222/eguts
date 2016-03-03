app.controller('SubscriptionCtrl', ['$scope', '$modal', 'Subscription', 'BootstrapGridAPI', 'PathUtils',
    function($scope, $modal, Subscription, BootstrapGridAPI, PathUtils) {

    $scope.columns = [{
        field : "id",
        name : "Id"
    },{
        field : "url",
        name : "Url"
    },{
        field : "status",
        name : "Estado",
        customFilter : function(value, item) { return angular.isDefined(value.name) ? value.name: value; }
    } ];

    var getFilters = function() {
        return {
            sort : "url",
            order : "asc",
            fields: "id,url,status"
        };
    };

    $scope.filters = getFilters();

    $scope.gridResource = Subscription;

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
        $scope.errorTitle = "Error al borrar el Subscription"
        $scope.showAlerts = true;
    }

    $scope.onDeleteOk = function(item) {
        $scope.errors = null;
        $scope.successTitle =  "Subscription '" + item.name + "' borrado."
        $scope.showAlerts = true;
    }

    $scope.deleteMessage = function(item) {
        return "¿Seguro que desea borrar la Subscription '" + item.name + "'?";
    }

}]);

app.controller('EditSubscriptionController', [
        '$scope','$filter',	'PathUtils','Subscription','$modal','BootstrapGridAPI',
        function($scope, $filter, PathUtils, Subscription, $modal, BootstrapGridAPI) {

            var path = PathUtils.parseEdit();
            $scope.isNewSubscription = !path.isEdit;
            $scope.itemToEdit = {
                'endpoint' : {},
                'subscriber' : {}
            };

            $scope.saving = false;
            $scope.isEditable = true;


            if (path.isEdit) {
                Subscription.get(
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

                var functionToCall = $scope.isNewSubscription ? "save" : "update";
                Subscription[functionToCall]
                (
                    $scope.itemToEdit,
                    function(response) {

                        $scope.errors = null;
                        $scope.successTitle = 'Subscription guardado correctamente.';
                        $scope.showAlerts = true;

                        PathUtils.updateId(response.id);
                        $scope.itemToEdit.id = response.id;
                        $scope.saving = false;
                        $scope.isNewSubscription = false;
                    },
                    function(response) {
                        $scope.errors = [];
                        if (angular.isObject(response.data) && angular.isObject(response.data.errors)){
                            $scope.errors = response.data.errors;
                        }

                        $scope.errorTitle = 'Error al guardar la Subscripcion.';
                        $scope.showAlerts = true;
                        $scope.saving = false;

                    });
            };
        }
    ]);
