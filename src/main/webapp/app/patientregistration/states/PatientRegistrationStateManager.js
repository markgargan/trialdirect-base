angular.module('uiRouterSample.patientregistration', [
    'ui.router'
])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {
                $stateProvider
                    .state('patientregistration', {

                        /*abstract: true,*/
                        url: '/patient-registration',
                        views: {
                            "viewA": {
                                templateUrl: 'app/patientregistration/views/patientregistration.view.html'
                            }
                        }

                    });
            }
        ]
    );
