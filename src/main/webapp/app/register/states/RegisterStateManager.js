angular.module('uiRouterSample.register', [
    'ui.router'
])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $stateProvider
                    .state('register', {

                        /*abstract: true,*/
                        url: '/register',
                        views: {
                            "viewA": {
                                templateUrl: 'app/register/views/register.view.html'
                            }
                        }

                    });
            }
        ]
    );
