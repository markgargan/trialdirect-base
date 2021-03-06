angular.module(
    'trialdirect', [
        'uiRouterSample.about',
        'uiRouterSample.help',
        'uiRouterSample.home',
        'uiRouterSample.therapeuticarea',
        'uiRouterSample.trial',
        'uiRouterSample.user',
        'uiRouterSample.login',
        'uiRouterSample.logout',
        'uiRouterSample.register',
        'uiRouterSample.profile',
        'uiRouterSample.patients',
        'uiRouterSample.patientregistration',
        'ngResource',
        'ngCookies',
        'spring-data-rest',
        'ui.router',
        'ngFileUpload',
        'as.sortable'
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
                console.log('This line was added on the develop branch');

            }
        ]
    )
    .config(['$stateProvider', '$urlRouterProvider',
        function ($stateProvider, $urlRouterProvider) {
            $urlRouterProvider.otherwise('/about');
        }
    ])

    .filter('reverse', function () {
        return function (items) {
            return items.slice().reverse();
        };
    })

    .controller("NavController", function ($scope, $cookieStore) {
        $scope.IsHidden = !1;

        $scope.ShowHide = function () {
            $scope.IsHidden = !$scope.IsHidden
        };

        if ($cookieStore.get('userType')) {
            $scope.navUserType = $cookieStore.get('userType');
        } else {
            $scope.navUserType = 'reg';
        }
    });


angular.isEmpty = function (obj) {
    return angular.isUndefined(obj) || obj == '';
};