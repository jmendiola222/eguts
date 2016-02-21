//var userService = angular.module('userService', ['ngResource']);

app.factory('User', [ '$resource', '$http', function($resource, $http) {
	var service = $service($resource, "user");

	service.unlock = function(id) {
		return $http.post(BASE_URL + "/user/unlock/" + id);
	}

	service.resetPassword = function(userInput) {
		return $http.post(BASE_URL + "/user/resetPassword", {
			params : {
				userInput : userInput
			}
		});
	}

	return service;
} ]);
