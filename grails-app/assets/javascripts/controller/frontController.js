app.controller('NavbarCtrl', [ '$scope', '$rootScope', '$modal',
		'$location', function($scope, $rootScope, $modal, $location) {

			var clearLocation = function() {
				$location.search("selectInstance", "false");
			};

			$scope.showMyInstances = function() {
				var modalInstance = $modal.open({
					templateUrl : 'instanceSelector-template',
					controller : "SelectMyInstanceCtrl"
				});

				modalInstance.result.then(clearLocation, clearLocation);
			}

			$rootScope.$on('$locationChangeSuccess', function(event) {
				if ($location.search().selectInstance === "true") {
					$scope.showMyInstances();
				}
			});
		} ]);

app.controller('FooterCtrl', [ '$scope', '$rootScope', '$modal',
	'$location', function($scope, $rootScope, $modal) {

		$scope.showContact = function() {
			var modalInstance = $modal.open({
				templateUrl : 'contact-template',
				controller : "ContactCtrl"
			});
		}

	}
]);

app.controller('SubscriptionCtrl', [ '$scope', 'Subscription',
	function($scope, Subscription) {
		$scope.subscription = new Subscription();

		$scope.ok = function () {
			Subscription.subscribeAnonymous($scope.subscription)
			.success(
				function(response) {
					$scope.alert = 'Subscripcion guardada correctamente.' + response.data;
					$scope.alertType = "alert-success";
				}
			).error(
				function(response) {
					$scope.alert = 'Error al guardar la subscripcion. ';
					if (angular.isObject(response.errors)){
						$scope.alert = response.errors[0].message;
					}
					$scope.alertType = "alert-danger";
				});
		}
	}
]);

app.controller('FooterCtrl', [ '$scope', '$rootScope', '$modal',
	'$location', function($scope, $rootScope, $modal) {

		$scope.showContact = function() {
			var modalInstance = $modal.open({
				templateUrl : 'contact-template',
				controller : "ContactCtrl"
			});
		}

	}
]);

app.controller('ContactCtrl', [ '$scope', '$modalInstance',
	function($scope, $modalInstance) {
		$scope.cancel = function() {
			$modalInstance.dismiss("cancel");
		}
	} ]);
