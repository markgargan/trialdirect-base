angular.module('uiRouterSample.patients', [
    'ui.router'
])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $stateProvider
                    .state('patients', {

                        /*abstract: true,*/
                        url: '/patients',
                        views: {
                            "viewA": {
                                templateUrl: 'app/patients/views/patients.view.html'
                            }
                        }

                    });
            }
        ]
    );
