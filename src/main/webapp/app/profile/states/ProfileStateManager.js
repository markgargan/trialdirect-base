angular.module('uiRouterSample.profile', [
    'ui.router'
])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $stateProvider
                    .state('profile', {

                        /*abstract: true,*/
                        url: '/profile',
                        views: {
                            "viewA": {
                                templateUrl: 'app/profile/views/profile.view.html'
                            }
                        }

                    });
            }
        ]
    );
