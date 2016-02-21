app.factory('Subscription', [ '$resource', '$http', function($resource, $http) {
	var service = $service($resource, "subscription");

	service.subscribeAnonymous = function(subscription) {
		return $http.post(BASE_URL + "/subscription/subscribeAnonymous", subscription);
	}

	return service;
} ]);
