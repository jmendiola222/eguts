var FormatUtilsModule = angular.module('pathUtils', []);

FormatUtilsModule.factory('PathUtils', function($location, $window) {

	var splitt = function() {
		return $location.absUrl().split('/');
	}
	
	var doParse = function(params) {
		var splitted = splitt();
		var itemIndex = splitted.length - params;
		//Ugly
		if(params == 2) {
			var subItemIndex = splitted.length - 1
		}
		var parsed = parseInt(splitted[itemIndex]);
		return {
			isEdit : !isNaN(parsed),
			id : parsed,
			subItemId : parseInt(splitted[subItemIndex])
		};
	}
	
	return {
		parseEdit : function() {
			return doParse(1);
		},
		
		//FIXME we should do this as generic code
		parseParamsWithSubItem : function() {
			return doParse(2);
		},
		
		clearId : function() {
			$location.path( "" );
			$location.replace();

		},
		
		goTo : function(url) {
			$window.location = url;
		},
		
		goToUrlWithId : function(url, resource) {
			$window.location = url + resource.id;
		},

		goToNewTab : function(url) {
			$window.open(url);
		},
		
		updateId : function(id) {
			var edit = doParse(1);
			if(!edit.isEdit) {
				$location.path( id + "" );
				$location.replace();
			}
		}

	}
});

