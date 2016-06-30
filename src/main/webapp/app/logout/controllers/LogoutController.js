angular
    .module('trialdirect')
    .controller('LogoutController',
        ['$scope', '$state', '$cookieStore', '$window',
            function ($scope, $state, $cookieStore, $window) {

                $cookieStore.remove('userType');
                $window.location.href = '/';

            }
        ]);