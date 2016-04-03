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

                $rootScope.$on("$stateChangeError", console.log.bind(console));

            }
        ]
    ).config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {

        $urlRouterProvider.otherwise('/therapeuticareas');
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