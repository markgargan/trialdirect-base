angular.module('uiRouterSample.logout', [
    'ui.router'
])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $stateProvider
                    .state('logout', {

                        /*abstract: true,*/
                        url: '/logout',
                        views: {
                            "viewA": {
                                templateUrl: 'app/logout/views/logout.view.html'
                            }
                        }

                    });
            }
        ]
    );
