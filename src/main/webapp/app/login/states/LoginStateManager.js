angular.module('uiRouterSample.login', [
    'ui.router'
])
    .config(
        ['$stateProvider',
            function ($stateProvider) {
                $stateProvider
                    .state('login', {

                        /*abstract: true,*/
                        url: '/login',
                        views: {
                            "viewA": {
                                templateUrl: 'app/login/views/login.view.html',
                                controller: 'LoginController'
                            }
                        }

                    });
            }
        ]
    );
