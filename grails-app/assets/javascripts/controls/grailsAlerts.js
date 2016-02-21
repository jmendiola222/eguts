var GrailsAlertModule = angular.module('grails-alerts', [ 'ngResource',
		'ui.bootstrap' ]);

GrailsAlertModule.directive("grailsAlerts", [ function() {
	return {
		restrict : 'A',
		scope : {
			errors : '=',
			errorTitle : '=',
			successTitle : '=',
			show: '='
		},
		replace : true,
		templateUrl :  URL + '/controls/grails-alerts',
		controller : 'AlertController',
		link : function(scope, tEl, tAtr) {
			scope.show = false;
		}
	};
} ]);
