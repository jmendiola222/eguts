'use strict';

var modules = ['ngResource', 'ngRoute', 'ngSanitize', 'ui.bootstrap', 'bootstrap-grid', 'ui.validate', 'grails-alerts', 'ui.bootstrap.datepicker', 'pathUtils'];

if (typeof customModules !== 'undefined') {
    angular.forEach(customModules, function (customModule) {
        modules.push(customModule);
    });
}

var app = angular.module('app', modules);

app.config(['$httpProvider', function ($httpProvider) {

    function appendTransform(defaults, transform) {

        // We can't guarantee that the default transformation is an array
        defaults = angular.isArray(defaults) ? defaults : [defaults];

        // Append the new transformation to the defaults
        return defaults.concat(transform);
    }

    function parseTokensFromHeader(response) {
        var token = response.headers('SYNCHRONIZER_TOKEN');
        if (token != null) {
            var tokenElem = document.getElementById("SYNCHRONIZER_TOKEN");
            if (tokenElem != null) {
                tokenElem.value = token;
            }
        }
    }

    $httpProvider.defaults.transformRequest = appendTransform($httpProvider.defaults.transformRequest,
        function (data) {
            if (data === undefined) {
                return data;
            }
            var toReturn = angular.fromJson(data);
            //var token = document.getElementById("SYNCHRONIZER_TOKEN");
            //var uriToken = document.getElementById("SYNCHRONIZER_URI");
            //if (token != null && uriToken != null) {
            //    toReturn.SYNCHRONIZER_TOKEN = token.value;
            //    toReturn.SYNCHRONIZER_URI = uriToken.value;
            //}
            return angular.toJson(toReturn);
        });

    $httpProvider.interceptors.push(function ($q) {
        return {
            'request': function (config) {

                var token = document.getElementById("SYNCHRONIZER_TOKEN");
                if (token != null) {
                    var uriToken = document.getElementById("SYNCHRONIZER_URI");
                    config.headers["SYNCHRONIZER_TOKEN"] = token.value;
                    config.headers["SYNCHRONIZER_URI"] = uriToken.value;
                }

                return config;
            },
            'response': function (response) {
                parseTokensFromHeader(response);
                return response;
            },

            'responseError': function (rejection) {
                if (rejection.status == 401) {
                    window.location.reload(true);
                    return;
                }
                parseTokensFromHeader(rejection);
                return $q.reject(rejection);
            }
        };

    });
}]);

// Select fix
app.directive("select", function () {
    return {
        restrict: "E",
        require: "?ngModel",
        scope: false,
        link: function (scope, element, attrs, ngModel) {
            if (!ngModel) {
                return;
            }
            element.bind("keyup", function () {
                element.triggerHandler("change");
            })
        }
    }
})

app.directive("passwordVerify", function () {
    return {
        require: "ngModel",
        scope: {
            passwordVerify: '='
        },
        link: function (scope, element, attrs, ctrl) {
            scope.$watch(function () {
                var combined;

                if (scope.passwordVerify || ctrl.$viewValue) {
                    combined = scope.passwordVerify + '_' + ctrl.$viewValue;
                }
                return combined;
            }, function (value) {
                if (value) {
                    ctrl.$parsers.unshift(function (viewValue) {
                        var origin = scope.passwordVerify;
                        if (origin !== viewValue) {
                            ctrl.$setValidity("passwordVerify", false);
                            return undefined;
                        } else {
                            ctrl.$setValidity("passwordVerify", true);
                            return viewValue;
                        }
                    });
                }
            });
        }
    };
});


app.filter('saltoLinea', function () {
    return function (text) {
        return text.replace(/\n/g, '<br/>');
    }
})


app.directive('ngConfirmClick', [
    function () {
        return {
            priority: -100,
            restrict: 'A',
            //terminal: true,
            link: function (scope, element, attrs) {
                element.bind('click', function (e) {
                    var message = attrs.ngConfirmClick;
                    if (message && !confirm(message)) {
                        e.stopImmediatePropagation();
                        e.preventDefault();
                    }
                });
            }
        }
    }]);

app.directive('scrollToItem', function () {
    return {
        restrict: 'A',
        scope: {
            scrollTo: "@"
        },
        link: function (scope, $elm, attr) {

            $elm.on('click', function () {
                $('html,body').animate({scrollTop: $(scope.scrollTo).offset().top}, "medium");
            });
        }
    }
});

app.filter('range', function () {
    return function (input, min, max) {
        min = parseInt(min);
        max = parseInt(max);
        for (var i = min; i <= max; i++)
            input.push(i);
        return input;
    };
});

app.filter('yearRange', ['rangeFilter', function (rangeFilter) {
    return function (input, min) {

        var d = new Date();

        return rangeFilter(input, min, d.getFullYear());
    };


}]);

