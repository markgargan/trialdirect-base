angular.module('trialdirect', [
        'uiRouterSample.therapeuticarea',
        'uiRouterSample.trial',
        'uiRouterSample.user',
        'ngResource',
        'spring-data-rest',
        'ui.router'
    ])
    .run(
        ['$rootScope', '$state', '$stateParams',
            function ($rootScope, $state, $stateParams) {

                // It's very handy to add references to $state and $stateParams to the $rootScope
                // so that you can access them from any scope within your applications.For example,
                // <li ng-class="{ active: $state.includes('contacts.list') }"> will set the <li>
                // to active whenever 'contacts.list' or one of its decendents is active.
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;

                //$rootScope.$on("$stateChangeError", console.log('error'));
                //$rootScope.$on("$stateChangeSuccess", console.log('Change success'));
                //$rootScope.$on("$stateChangeEvent", console.log('Event'));

                $rootScope.$on("$stateChangeError",
                    function (event, toState, toParams, fromState, fromParams, error) {
                        console.log('Changing state frm :-');
                        console.log(fromState.name);
                        console.log('to state :-');
                        console.log(toState.name);


                    });

                $rootScope.$on('$stateChangeSuccess',
                    function (event, toState, toParams, fromState, fromParams) {
                        console.log('Changing state from :-');
                        console.log(fromState.name);
                        console.log('to state :-');
                        console.log(toState.name);

                    })

            }
        ]
    ).config(['$stateProvider', '$urlRouterProvider', '$httpProvider',
    function ($stateProvider, $urlRouterProvider, $httpProvider) {

        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
        $httpProvider.defaults.headers.common['X-CSRF-Token'] = $('meta[name=_csrf]').attr('content');


        $stateProvider.state('logout', {
            url: '/logout',
            views: {
                "viewA": {
                    template: '<h1 ng-bind="logoutMessage"></h1>',
                    controller: function($scope) {
                        $scope.logoutMessage = 'Loggin\' out';

                    },
                }
            }
        });

        $urlRouterProvider.otherwise('/login');
    }]).filter('reverse', function () {
    return function (items) {
        return items.slice().reverse();
    };
}).controller("NavController", function($scope) {
        $scope.IsHidden = !1,
            $scope.ShowHide = function() {
                $scope.IsHidden = !$scope.IsHidden
            }
    });