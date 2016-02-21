app.controller('UserProfileCtrl', ['$scope','User','$window',
	function($scope, User, $window) {
		$scope.alert = null;

		$scope.$watch('userId', function () {
			$scope.bindItem();
		});

		$scope.$watch('flashError', function () {
			if($scope.flashError && $scope.flashError != 'null') {
				$scope.alert = $scope.flashError;
				$scope.alertType = "alert-danger";
			}
		});

		$scope.$watch('flashMessage', function () {
			if($scope.flashMessage && $scope.flashMessage != 'null') {
				$scope.alert = $scope.flashMessage;
				$scope.alertType = "alert-info";
			}
		});

		$scope.bindItem  = function() {
			User.get({id: $scope.userId},
				function (user) {
					$scope.itemToEdit = user;
				}, function (error) {
					$scope.alerts = [{
						type: 'danger',
						msg: 'El objeto a editar no existe.'
					}];
					$scope.itemToEdit = new User();
				});
		}

		$scope.ok  = function() {

			var editedItem = $scope.itemToEdit;
			editedItem.state = "Active";
			var functionToCall = "$update";

			editedItem[functionToCall](function() {
				$scope.alert = 'Perfil de usuario guardado correctamente.';
				$scope.alertType = "alert-success";
				$scope.form.$dirty = false;
			}, function(response) {
				// FIXME El error deberia ser mas claro
				var errores = "";
				if (angular.isObject(response.data) && angular.isObject(response.data.errors)){
					response.data.errors.forEach(function(unerror){
						errores+= unerror.message + ". "
					});
				}

				$scope.alert = 'Error al guardar el usuario. ' + errores;
				$scope.alertType = "alert-danger";
			});
		}

		$window.onbeforeunload = function(event) {
			if ($scope.form.$dirty) {
				var message = 'Si sale perderá los cambios.';
				if (typeof event == 'undefined') {
					event = window.event;
				}
				if (event) {
					event.returnValue = message;
				}
				return message;
			}
		}
	}
]);
