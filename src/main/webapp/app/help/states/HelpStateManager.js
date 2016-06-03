angular.module('uiRouterSample.help', [
    'ui.router'
])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $stateProvider
                    .state('help', {

                        /*abstract: true,*/
                        url: '/help',
                        views: {
                            "viewA": {
                                templateUrl: 'app/help/views/help.view.html'
                            }
                        }

                    });
            }
        ]
    );
