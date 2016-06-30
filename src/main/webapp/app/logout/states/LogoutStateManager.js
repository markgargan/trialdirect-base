angular.module('uiRouterSample.logout', [
    'ui.router'
])
    .config(
        ['$stateProvider',
            function ($stateProvider) {
                $stateProvider
                    .state('logout', {

                        /*abstract: true,*/
                        url: '/logout',
                        views: {
                            "viewA": {
                                templateUrl: 'app/logout/views/logout.view.html',
                                controller: 'LogoutController',
                            }
                        }

                    });
            }
        ]
    );
