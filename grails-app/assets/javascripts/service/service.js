var BASE_URL = URL + '/api';

// TODO Maybe move this to other *.js
function $service($resource, $name, customActions) {

	var actions = {};

	if (angular.isDefined(customActions)) {
		actions = angular.copy(customActions);
	}

	actions['update'] = {
		method : 'PUT'
	};

	return $resource(BASE_URL + '/' + $name + '/:id', {
		id : '@id'
	}, actions);
}
