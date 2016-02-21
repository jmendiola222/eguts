app.directive('placeholder', function() {
	  return {
	    restrict: 'A',
	    require: 'ngModel',
	    link: function(scope, element, attr, ctrl) {      

	      var value;

	      var placeholder = function () {
	          element.val(attr.placeholder)
	      };
	      var unplaceholder = function () {
	          element.val('');
	      };

	      scope.$watch(attr.ngModel, function (val) {
	        value = val || '';
	      });

	      element.bind('focus', function () {
	         if(value == '') unplaceholder();
	      });

	      element.bind('blur', function () {
	         if (element.val() == '') placeholder();
	      });

	      ctrl.$formatters.unshift(function (val) {
	        if (!val) {
	          placeholder();
	          value = '';
	          return attr.placeholder;
	        }
	        return val;
	      });
	    }
	  };
	});