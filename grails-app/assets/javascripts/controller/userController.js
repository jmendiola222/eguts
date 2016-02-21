app.controller('UserCtrl', ['$scope', '$modal','User','Role', 'BootstrapGridAPI', function($scope, $modal, User,Role, BootstrapGridAPI) {

	$scope.columns = [
		{field : "region", name : "Región"},
		{field : "code" , name: "Código"},		
		{field : "name", name : "Nombre"},
		{field : "username", name : "Usuario"},
		{field : "email", name : "Correo"},
		{field : "category.category", name : "Categoria"},
		{field : "activo", 
	      name : "Estado",
	      customFilter : function(value, item) {return item.enabled? "Activo" : "Inactivo";}
		}
    ];

	$scope.customActions = [ {
		icon : "fa fa-unlock",
		text : "Desbloquear",
		action : "unlock",
		visibleWhen : function(item) {
			return item.accountLocked;
		}
	} ];

	$scope.$on("unlock", function(event, params) {
		User.unlock(params.item.id)
			.success(
				function(data) {

					$scope.errors = null;
					$scope.successTitle =  'Usuario desbloqueado correctamente.'
					$scope.showAlerts = true;

					BootstrapGridAPI.doRefresh();
				})
			.error(function(response) {
				$scope.errors = [];
				if (angular.isObject(response.data) && angular.isObject(response.data.errors)){
					$scope.errors = response.data.errors;
				}

				$scope.errorTitle =  'Error al desbloqueado el usuario. ' + errores;
				$scope.showAlerts = true;
			})
	});

	$scope.getNombres = function(val) {
		var result = User.query({
			name : val
		}, function(data) {
			return data;
		});

		return result.$promise;
	}

	var getFilters = function() {
		return {
			sort : "name",
			order : "asc",
			fields : "id,region,code,name,username,email,category.category,enabled,accountLocked"
		};
	}

	$scope.roles = Role.query({
		order : "asc",
		sort : "description"
	});

	$scope.filters = getFilters();

	$scope.gridResource = User;

	$scope.cleanFilters = function() {
		$scope.filters = getFilters();
		$scope.filter();
	}

	$scope.filter = function() {
		$scope.alert = null;
		BootstrapGridAPI.doResetPagination();
	}

	$scope.deleteConfirm = function(item) {
		return "¿Seguro desea eliminar el usuario " + item.name + "?";
	}
	
	$scope.newItem = function() {
		$scope.edit();
	}
	
	$scope.onDeleteError = function(result, alerts) {

		$scope.errors = [];
		if (angular.isObject(result.data) && angular.isObject(result.data.errors)){
			$scope.errors = result.data.errors;
		}

		$scope.errorTitle =  'Error al eliminar el usuario.';
		$scope.showAlerts = true;

	}
	

	$scope.edit = function(item) {
		$scope.alert = null;

		var itemToEdit = new User();
		var isNew = true;

		if (angular.isDefined(item)) {
			
			itemToEdit = User.get({id: item.id});
			isNew = false;
		}

		var modalInstance = $modal.open({
			templateUrl : 'newUser-template',
			windowClass : "big-modal",
			controller : "EditUserController",
			resolve : {
				itemToEdit : function() {
					return itemToEdit;
				},
				isNew : function() {
					return isNew;
				},
				roles : function() {
					return $scope.roles;
				}
			}
		});

		// FIXME Aca hay funcionalidad que tiene pinta que se va a repetir
		modalInstance.result.then(function(editedItem) {
			editedItem.state = "Active";
			var functionToCall = isNew ? "$save" : "$update";

			editedItem[functionToCall](function() {
				$scope.errors = null;
				$scope.successTitle = 'Usuario guardado correctamente.';
				$scope.showAlerts = true;
				BootstrapGridAPI.doRefresh();
			}, function(response) {
				$scope.errors = [];
				if (angular.isObject(response.data) && angular.isObject(response.data.errors)){
					$scope.errors = response.data.errors;
				}

				$scope.errorTitle =  'Error al guardar el usuario. ';
				$scope.showAlerts = true;
			});

		});
	}

}]);

app.controller('EditUserController',
		[
				'$scope',
				'$modalInstance',
				'roles',
				'UserCategory',
				'isNew',
				'itemToEdit',
				function($scope, $modalInstance, roles, UserCategory, isNew,
						itemToEdit) {

					$scope.categorias = UserCategory.query();

					$scope.roles = roles;

					$scope.isNew = isNew;
					$scope.itemToEdit = itemToEdit;

					$scope.ok = function() {
						$modalInstance.close($scope.itemToEdit);
					};

					$scope.cancel = function() {
						$modalInstance.dismiss('cancel');
					};

				} ]);
