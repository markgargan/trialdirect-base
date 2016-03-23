angular.module('uiRouterSample.therapeuticarea', [
  'ui.router'
])
.config(
  [          '$stateProvider', '$urlRouterProvider',
    function ($stateProvider,  $urlRouterProvider) {
      $stateProvider
        .state('therapeuticAreas', {

          abstract: true,
          url: '/therapeuticareas',
            views :{
                "viewA" : {
                  templateUrl: 'app/therapeuticareas/views/therapeuticareas.view.html',
                  resolve: {

                    therapeuticAreas: function (TherapeuticAreaResourceService) {

                      // Initialise the query api and return all available Therapeutic Areas
                      return TherapeuticAreaResourceService.load();
                    }
                  },

            controller: ['$scope', '$state', 'therapeuticAreas',
                function (  $scope,   $state,   therapeuticAreas ) {
                  $scope.therapeuticAreas = therapeuticAreas;
                }]
            }}
        })
        .state('therapeuticAreas.list', {
            url: '',
            templateUrl: 'app/therapeuticareas/views/therapeuticareas.list.html'
        })
        .state('therapeuticAreas.detail', {
          url: '/{therapeuticAreaId:[0-9]{1,4}}',
          views: {

              // So this one is targeting the unnamed view within the parent state's template.
              '': {
                  templateUrl: 'views/templates/therapeutic.area.questionnaire.view.htm',
                  controller: 'TherapeuticAreaEditController',
                  resolve: {

                      therapeuticArea : function($stateParams, TherapeuticAreaResourceService) {
                          return TherapeuticAreaResourceService.loadTherapeuticArea($stateParams.therapeuticAreaId);
                      },
                      questionnaireEntries : function ($stateParams, QuestionnaireEntryResourceService, Question, Answer) {
                          //Initialise the answer api
                          Question.query();
                          Answer.query();
                          QuestionnaireEntryResourceService.initialize();

                          // Load all the questions for the particular TherapeuticArea
                          return QuestionnaireEntryResourceService.loadQuestionnaireEntriesForTherapeuticArea($stateParams.therapeuticAreaId);
                      }

                      //specificTherapeuticArea: function ($stateParams, TherapeuticAreaResourceService, QuestionnaireEntryResourceService, Question, Answer) {
                      //    //Initialise the answer api
                      //    Question.query();
                      //    Answer.query();
                      //    QuestionnaireEntryResourceService.initialize();
                      //    TherapeuticAreaResourceService.initialize();
                      //
                      //    // Load all the questions for the particular TherapeuticArea
                      //    return TherapeuticAreaResourceService.loadTherapeuticArea($stateParams.therapeuticAreaId);
                      //}
                  }
              },
            'hint@': {
              template: 'This is therapeuticareas.detail populating the "hint" ui-view'
            },
            // This one is targeting the ui-view="menuTip" within the parent state's template.
            'menuTip': {
              // templateProvider is the final method for supplying a template.
              // There is: template, templateUrl, and templateProvider.
              templateProvider: ['$stateParams',
                function (        $stateParams) {
                  // This is just to demonstrate that $stateParams injection works for templateProvider.
                  // $stateParams are the parameters for the new state we're transitioning to, even
                  // though the global '$stateParams' has not been updated yet.
                  return '<hr><small class="muted">TherapeuticArea ID: ' + $stateParams.therapeuticAreaId + '</small>';
                }]
            }
          }
        });
    }
  ]
);
