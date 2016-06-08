angular.module('uiRouterSample.login', [
    'ui.router'
])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $stateProvider
                    .state('login', {

                        /*abstract: true,*/
                        url: '/login',
                        views: {
                            "viewA": {
                                templateUrl: 'app/login/views/login.view.html'
                            }
                        }

                    });
            }
        ]
    );
