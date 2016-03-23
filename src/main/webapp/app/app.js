angular.module('trialdirect', [
        'uiRouterSample.therapeuticarea',
        'uiRouterSample.contacts',
        'uiRouterSample.contacts.service',
        'uiRouterSample.utils.service',
        'ngResource',
        'spring-data-rest',
        'ui.router'
    ])
    .run(
        ['$rootScope', '$state', '$stateParams',
            function ($rootScope, $state, $stateParams) {

                // It's very handy to add references to $state and $stateParams to the $rootScope
                // so that you can access them from any scope within your applications.For example,
                // <li ng-class="{ active: $state.includes('contacts.list') }"> will set the <li>
                // to active whenever 'contacts.list' or one of its decendents is active.
                $rootScope.$state = $state;
                $rootScope.$stateParams = $stateParams;

                $rootScope.$on("$stateChangeError", console.log.bind(console));

                //$rootScope.$on('$stateChangeSuccess',
                //    function (event, toState, toParams, fromState, fromParams) {
                //        console.log('Changing state from :-');
                //        console.log(fromState);
                //        console.log('to state :-');
                //        console.log(toState);
                //
                //    })
            }
        ]
    ).config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {

        var trial = {
            templateUrl: 'views/templates/trial.view.htm',
            controller: 'TrialController',
            resolve: {
                trials: function (TrialResourceService) {
                    return TrialResourceService.load();
                }
            }
        };


        var therapeuticAreaState = {
            templateUrl: 'views/templates/therapeutic.area.view.htm',
            controller: 'TherapeuticAreaEditController',

            // We use the 'resolve' below to prime the page with the therapeutic area
            // so that they're available before the page loads preventing the flickering
            // as the bound questions hit the scope
            resolve: {

                therapeuticAreas: function (TherapeuticAreaResourceService) {

                    // Initialise the query api and return all available Therapeutic Areas
                    return TherapeuticAreaResourceService.load();
                }
            }
        };

        var questionnaireState = {
            templateUrl: 'views/templates/questionnaire.view.htm',
            controller: 'QuestionnaireController',

            // We use the 'resolve' below to prime the page with the questions
            // so that they're available before the page loads preventing the flickering
            // as the bound questions hit the scope
            resolve: {

                questionnaireEntries: function (QuestionnaireEntryResourceService, Answer) {
                    //Initialise the answer api
                    Answer.query();

                    // Initialise the query api and return all available questions
                    return QuestionnaireEntryResourceService.load();
                }
            }
        };

        $stateProvider
            .state('home', {
                url: "/",
                templateUrl: 'views/templates/home.view.htm',
                views: {
                    "viewA": therapeuticAreaState
                    ,
                    "viewB": questionnaireState
                }
            })
            .state('trial', {
                url: '/trial',
                views: {
                    "viewA": trial
                }
            })
            .state('therapeuticarea', {
                url: '/therapeuticarea',
                views: {
                    "viewA": therapeuticAreaState
                }
            })
            .state('item', {
                url: '/item',
                templateUrl: 'views/templates/item.view.htm',
                controller: 'AppController'
            }).state('questionnaire', {
            url: '/questionnaire',
            views: {
                "viewA": questionnaireState
            }
        });

        $urlRouterProvider.otherwise('/therapeuticareas');
    }]).filter('reverse', function () {
    return function (items) {
        return items.slice().reverse();
    };
    // Debugging for the UI router
});