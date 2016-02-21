app.controller('LoginCtrl', ['$scope','$modal', 'User',
	function($scope, $modal, User) {

		$scope.userInput = '';

		$scope.showForgotPassword = function() {
			var modalInstance = $modal.open({
				templateUrl : 'forgotPassword-template',
				controller : "ForgotPasswordCtrl"
			});
		}

		$scope.cancel = function () {
			if($scope.onbeforeunload())
				$modalInstance.dismiss('cancel');
		};
	}
]);

app.controller('ForgotPasswordCtrl', [ '$scope', '$modalInstance', 'User',
	function($scope, $modalInstance, User) {
		$scope.cancel = function() {
			$modalInstance.dismiss("cancel");
		}

		$scope.ok = function () {

			if($scope.userInput == ''){
				$scope.alert =  "El usuario / email no puede ser vacio";
				$scope.alertType = "alert-danger";
				return false;
			}

			User.resetPassword($scope.userInput)
				.success(function(data) {

					$scope.errors = null;
					$scope.successTitle =  'La contrasenia fur cambiada, por favor verifique su mail.'
					$scope.showAlerts = true;

				})
				.error(function(response) {
					var errores = "";
					if (angular.isObject(response.data) && angular.isObject(response.data.errors)) {
						response.data.errors.forEach(function (unerror) {
							errores += unerror.message + ". "
						});
					}
					$scope.alert = 'Error al guardar. ' + errores;
					$scope.alertType = "alert-danger";
				});
		};


	} ]);
