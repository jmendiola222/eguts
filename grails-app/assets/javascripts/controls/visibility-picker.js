var VisibilityPickerModule = angular.module('visibility-picker', [ 'ngResource',
		'ui.bootstrap' ]);

VisibilityPickerModule.directive("visibilityPicker", [ function() {
	return {
		restrict : 'A',
		scope : {
			isRequired : '@',
			selectedCategories: '='
		},
		replace : true,
		templateUrl : URL + '/controls/visibility-picker',
		controller : 'VisibilityPickerController',
		link : function(scope, tEl, tAtr) {
			if(scope.selectedCategories) {
				scope.visibility = scope.selectedCategories.split(",");
			} else {
				scope.visibility = "";
			}
		}
	};
} ]);

VisibilityPickerModule.controller('VisibilityPickerController', ['$scope', 'UserCategory', function($scope, UserCategory) {
	
	var _empty = [" "];
	
	$scope.userCategories = UserCategory.query({
		sort : "category",
		fields : "category",
		order : "asc"
	}, function(categories) {
		$scope.userCategories = _empty;
		for (var i = 0; i < categories.length; i++) {
			$scope.userCategories
					.push(categories[i].category);
		}
	});
	
	$scope.$watch("visibility", function() {
		if($scope.visibility != "") {
				$scope.selectedCategories = $scope.visibility.join();
		}
		else
			$scope.selectedCategories = "";
	});
}]);


