angular.module('uiRouterSample.home', [
    'ui.router'
])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $stateProvider
                    .state('home', {

                        /*abstract: true,*/
                        url: '/home',
                        views: {
                            "viewA": {
                                templateUrl: 'app/home/views/home.view.html'
                            }
                        }

                    });
            }
        ]
    );
