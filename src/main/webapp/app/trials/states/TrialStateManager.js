angular
    .module('uiRouterSample.trial', ['ui.router'])
    .config(
        ['$stateProvider', '$urlRouterProvider',
            function ($stateProvider, $urlRouterProvider) {

                $stateProvider

                    .state('trials', {

                        abstract: true,
                        url: '/trials',
                        views: {
                            "viewA": {
                                templateUrl: 'app/trials/views/trials.view.html',
                                resolve: {

                                    trials: function (TrialResourceService, TrialInfo) {

                                        TrialResourceService.initialize();
                                        TrialInfo.initialize();
                                        // Initialise the query api and return all available Trials
                                        return TrialResourceService.load();
                                    },
                                    therapeuticAreas: function (TherapeuticAreaResourceService) {

                                        // Initialise the query api and return all available Therapeutic Areas
                                        return TherapeuticAreaResourceService.load();
                                    }
                                },

                                controller: 'TrialController'
                            }
                        }
                    })


                    .state('trials.list', {
                        url: '',
                        templateUrl: 'app/trials/views/trials.list.html'
                    })

                    
                    /*
                    .state('trials.oldcreate', {
                        url: '/oldcreate',
                        templateUrl: 'app/trials/views/trials.create.html'
                    })
                    */


                    .state('trials.create', {
                        url: '/create',
                        views: {
                            // So this one is targeting the unnamed view within the parent state's template.
                            '': {
                                templateUrl: 'app/trials/views/trials.edit.html'
                            }
                        }
                    })


                    .state('trials.edit', {
                        url: '/edit/{trialId:[0-9]{1,4}}',
                        views: {

                            // So this one is targeting the unnamed view within the parent state's template.
                            '': {
                                templateUrl: 'app/trials/views/trials.edit.html',
                                controller: 'TrialEditController',
                                resolve: {

                                    trialInfo: function ($stateParams, TrialResourceService) {
                                        return TrialResourceService.loadTrialInfo($stateParams.trialId);
                                    },

                                    trial: function ($stateParams, TrialResourceService) {
                                        return TrialResourceService.loadTrial($stateParams.trialId);
                                    },

                                    questionnaireEntries: function ($stateParams, QuestionnaireEntryResourceService, Question, Answer) {
                                        //Initialise the answer api
                                        Question.initialize();
                                        Answer.initialize();
                                        QuestionnaireEntryResourceService.initialize();

                                        // Load all the questions for the particular Trial
                                        return QuestionnaireEntryResourceService.loadQuestionnaireEntriesForTherapeuticLink('trials', $stateParams.trialId);
                                    },
                                    
                                    trialSelectorQuestionnaireEntries: function ($stateParams, TrialSelectorQuestionnaireEntryResourceService, Question, Answer) {
                                        //Initialise the answer api
                                        Question.initialize();
                                        Answer.initialize();
                                        TrialSelectorQuestionnaireEntryResourceService.initialize();

                                        // Load all the questions for the particular Trial
                                        return TrialSelectorQuestionnaireEntryResourceService.loadTrialSelectorQuestionnaireEntriesForTrial($stateParams.trialId);
                                    }
                                    
                                }
                            }
                        }
                    })


                    .state('trials.detail', {
                        url: '/{trialId:[0-9]{1,4}}',
                        views: {

                            // So this one is targeting the unnamed view within the parent state's template.
                            '': {
                                templateUrl: 'app/trials/views/trial.questionnaire.view.htm',
                                controller: 'TrialEditController',
                                resolve: {

                                    trialInfo: function ($stateParams, TrialResourceService) {
                                        return TrialResourceService.loadTrialInfo($stateParams.trialId);
                                    },

                                    trial: function ($stateParams, TrialResourceService) {
                                        return TrialResourceService.loadTrial($stateParams.trialId);
                                    },

                                    questionnaireEntries: function ($stateParams, QuestionnaireEntryResourceService, Question, Answer) {
                                        //Initialise the answer api
                                        Question.initialize();
                                        Answer.initialize();
                                        QuestionnaireEntryResourceService.initialize();

                                        // Load all the questions for the particular Trial
                                        return QuestionnaireEntryResourceService.loadQuestionnaireEntriesForTherapeuticLink('trials', $stateParams.trialId);
                                    },
                                    
                                    trialSelectorQuestionnaireEntries: function ($stateParams, TrialSelectorQuestionnaireEntryResourceService, Question, Answer) {
                                        //Initialise the answer api
                                        Question.initialize();
                                        Answer.initialize();
                                        TrialSelectorQuestionnaireEntryResourceService.initialize();

                                        // Load all the questions for the particular Trial
                                        return TrialSelectorQuestionnaireEntryResourceService.loadTrialSelectorQuestionnaireEntriesForTrial($stateParams.trialId);
                                    }
                                }
                            },

                            //'hint@': {
                            //  template: 'This is trials.detail populating the "hint" ui-view'
                            //},
                            
                            // This one is targeting the ui-view="menuTip" within the parent state's template.
                            'menuTip': {
                                // templateProvider is the final method for supplying a template.
                                // There is: template, templateUrl, and templateProvider.
                                templateProvider: ['$stateParams',
                                    function ($stateParams) {
                                        // This is just to demonstrate that $stateParams injection works for templateProvider.
                                        // $stateParams are the parameters for the new state we're transitioning to, even
                                        // though the global '$stateParams' has not been updated yet.
                                        return '<hr><small class="muted">Trial ID: ' + $stateParams.trialId + '</small>';
                                    }]
                            }
                        }
                    });
            }
        ]
    );
