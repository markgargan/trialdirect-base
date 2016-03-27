angular.module('uiRouterSample.user', [
  'ui.router'
])
.config(
  ['$stateProvider', '$urlRouterProvider',
    function ($stateProvider,  $urlRouterProvider) {
      $stateProvider
        .state('users', {

          abstract: true,
          url: '/users',
            views :{
                "viewA" : {
                  templateUrl: 'app/users/views/users.view.html',
                  resolve: {

                    users: function (UserResourceService) {

                      // Initialise the query api and return all available Users
                      return UserResourceService.load();
                    },
                      therapeuticAreas: function (TherapeuticAreaResourceService) {

                          // Initialise the query api and return all available Therapeutic Areas
                          return TherapeuticAreaResourceService.load();
                      }
                  },

            controller: 'UserController'
            }}
        })
        .state('users.list', {
            url: '',
            templateUrl: 'app/users/views/users.list.html'
        })
        .state('users.detail', {
          url: '/{userId:[0-9]{1,4}}',
          views: {

              // So this one is targeting the unnamed view within the parent state's template.
              '': {
                  templateUrl: 'app/users/views/user.questionnaire.view.htm',
                  controller: 'UserEditController',
                  resolve: {

                      user : function($stateParams, UserResourceService) {
                          return UserResourceService.loadUser($stateParams.userId);
                      },
                      therapeuticArea: function($stateParams, UserResourceService) {
                          return UserResourceService.loadTherapeuticAreaForUser($stateParams.userId);
                      },
                      questionnaireEntries : function ($stateParams, QuestionnaireEntryResourceService, Question, Answer) {
                          //Initialise the answer api
                          Question.query();
                          Answer.query();
                          QuestionnaireEntryResourceService.initialize();

                          // Load all the questions for the particular User
                          return QuestionnaireEntryResourceService.loadQuestionnaireEntriesForTherapeuticLink('users', $stateParams.userId);
                      }
                      ,
                      userSelectorQuestionnaireEntries : function ($stateParams, UserSelectorQuestionnaireEntryResourceService, Question, Answer) {
                          //Initialise the answer api
                          Question.query();
                          Answer.query();
                          UserSelectorQuestionnaireEntryResourceService.initialize();

                          // Load all the questions for the particular User
                          return UserSelectorQuestionnaireEntryResourceService.loadUserSelectorQuestionnaireEntriesForUser($stateParams.userId);
                      }
                  }
              },
            //'hint@': {
            //  template: 'This is users.detail populating the "hint" ui-view'
            //},
            // This one is targeting the ui-view="menuTip" within the parent state's template.
            'menuTip': {
              // templateProvider is the final method for supplying a template.
              // There is: template, templateUrl, and templateProvider.
              templateProvider: ['$stateParams',
                function (        $stateParams) {
                  // This is just to demonstrate that $stateParams injection works for templateProvider.
                  // $stateParams are the parameters for the new state we're transitioning to, even
                  // though the global '$stateParams' has not been updated yet.
                  return '<hr><small class="muted">User ID: ' + $stateParams.userId + '</small>';
                }]
            }
          }
        });
    }
  ]
);
