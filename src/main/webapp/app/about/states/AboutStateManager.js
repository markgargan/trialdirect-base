angular.module('uiRouterSample.about', [
    'ui.router'
])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $stateProvider
                    .state('about', {

                        /*abstract: true,*/
                        url: '/about',
                        views: {
                            "viewA": {
                                templateUrl: 'app/about/views/about.view.html'
                            }
                        }

                    });
            }
        ]
    );
