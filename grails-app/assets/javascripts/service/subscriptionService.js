app.factory('Subscription', [ '$resource', function($resource) {
	return $service($resource, "subscription");
} ]);
