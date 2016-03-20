angular.module('trialdirect', [
    'ngResource',
    'spring-data-rest',
    'ui.router'
]).config(['$stateProvider', '$urlRouterProvider',
    function ($stateProvider, $urlRouterProvider) {

        var therapeuticAreaState = {
            //url: '/therapeuticarea',
            templateUrl: 'views/templates/therapeutic.area.view.htm',
            controller: 'TherapeuticAreaController',

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

        var questionState = {
            //url: '/question',
            templateUrl: 'views/templates/question.view.htm',
            controller: 'QuestionController',

            // We use the 'resolve' below to prime the page with the questions
            // so that they're available before the page loads preventing the flickering
            // as the bound questions hit the scope
            resolve: {

                quests: function (Question, Answer) {
                    //Initialise the answer api
                    Answer.query();

                    // Initialise the query api and return all available questions
                    return Question.query();
                }
            }
        };

        $stateProvider
            //.state('home', {
            //    url: "/",
            //    //templateUrl: 'views/templates/home.view.htm',
            //    views: {
            //        "viewA": therapeuticAreaState
            //        ,
            //        "viewB": questionState
            //    }
            //})
            //.state('therapeuticarea', {
            //    url: '/therapeuticarea',
            //    views: {
            //        "viewA": therapeuticAreaState
            //    }
            //})
            .state('item', {
                url: '/item',
                templateUrl: 'views/templates/item.view.htm',
                controller: 'AppController'
            }).state('question', {
                url: '/question',
                views: {
                    "viewA": questionState
                }
            });

        $urlRouterProvider.otherwise('/question');
    }]).filter('reverse', function () {
    return function (items) {
        return items.slice().reverse();
    };
    // Debugging for the UI router
}).run(function ($rootScope) {
    $rootScope.$on("$stateChangeError", console.log.bind(console));
});
