angular.module('uiRouterSample.trialSelector', [
  'ui.router'
])
.config(
  ['$stateProvider', '$urlRouterProvider',
    function ($stateProvider,  $urlRouterProvider) {
      $stateProvider
        .state('trialSelectors', {

          abstract: true,
          url: '/trialSelectors',
            views :{
                "viewA" : {
                  templateUrl: 'app/trialselectors/views/trialselectors.view.html',
                  resolve: {

                    trialSelectors: function (TrialSelectorResourceService) {

                      // Initialise the query api and return all available TrialSelectors
                      return TrialSelectorResourceService.load();
                    }
                  },

            controller: 'TrialSelectorController'
            }}
        })
        .state('trialSelectors.list', {
            url: '',
            templateUrl: 'app/trialselectors/views/trialselectors.list.html'
        })
        .state('trialSelectors.detail', {
          url: '/{trialSelectorId:[0-9]{1,4}}',
          views: {

              // So this one is targeting the unnamed view within the parent state's template.
              '': {
                  templateUrl: 'app/trialselectors/views/trial.selector.questionnaire.view.htm',
                  controller: 'TrialSelectorEditController',
                  resolve: {

                      trialSelector : function($stateParams, TrialSelectorResourceService) {
                          return TrialSelectorResourceService.loadTrialSelector($stateParams.trialSelectorId);
                      },
                      questionnaireEntries : function ($stateParams, QuestionnaireEntryResourceService, Question, Answer) {
                          //Initialise the answer api
                          Question.query();
                          Answer.query();
                          QuestionnaireEntryResourceService.initialize();

                          // Load all the questions for the particular TrialSelector
                          return QuestionnaireEntryResourceService.loadQuestionnaireEntriesForTherapeuticArea($stateParams.trialSelectorId);
                      }

                      //specificTrialSelector: function ($stateParams, TrialSelectorResourceService, QuestionnaireEntryResourceService, Question, Answer) {
                      //    //Initialise the answer api
                      //    Question.query();
                      //    Answer.query();
                      //    QuestionnaireEntryResourceService.initialize();
                      //    TrialSelectorResourceService.initialize();
                      //
                      //    // Load all the questions for the particular TrialSelector
                      //    return TrialSelectorResourceService.loadTrialSelector($stateParams.trialSelectorId);
                      //}
                  }
              },
            'hint@': {
              template: 'This is trialSelectors.detail populating the "hint" ui-view'
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
                  return '<hr><small class="muted">TrialSelector ID: ' + $stateParams.trialSelectorId + '</small>';
                }]
            }
          }
        });
    }
  ]
);
