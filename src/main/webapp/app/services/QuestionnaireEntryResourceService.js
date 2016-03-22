angular.module('trialdirect').factory('QuestionnaireEntryResourceService', ['$http', 'SpringDataRestAdapter',
        function ($http, SpringDataRestAdapter) {

            var RESOURCE_URL = './api/questionnaireentrys';

            // initialise the resources object.
            QuestionnaireEntryResourceService.resources = null;

            QuestionnaireEntryResourceService.load = function () {
                var deferred = $http.get(RESOURCE_URL);

                // As we're loading the QuestionnaireEntries,
                // load both the corresponding question resource
                // and the answer resource and supply them in a
                // fashion that's consumable by the view.
                return SpringDataRestAdapter.process(deferred, ['question', 'answers']).then(function (data) {
                    QuestionnaireEntryResourceService.resources = data._resources("self");

                    return _.map(data._embeddedItems, function (questionnaireEntry) {

                        return new QuestionnaireEntryResourceService(questionnaireEntry);
                    });
                });
            };


            function QuestionnaireEntryResourceService(questionnaireEntry) {

                if (angular.isUndefined(questionnaireEntry._resources)) {
                    
                    questionnaireEntry.save = function (callback) {
                        
                        QuestionnaireEntryResourceService.resources.save(questionnaireEntry, function (questionnaireEntry, headers) {

                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newQuestionnaireEntry) {

                                callback && callback(new QuestionnaireEntryResourceService(newQuestionnaireEntry));
                            });
                        });
                    };
                    

                } else {
                    questionnaireEntry.resources = questionnaireEntry._resources("self", {}, {
                        update: {
                            method: 'PUT'
                        }
                    });

                    questionnaireEntry.save = function (callback) {
                        questionnaireEntry.resources.update(questionnaireEntry, function () {
                            callback && callback(questionnaireEntry);
                        });
                    };

                    questionnaireEntry.remove = function (callback) {
                        questionnaireEntry.resources.remove(function () {
                            callback && callback(questionnaireEntry);
                        });
                    };
                }

                return questionnaireEntry;
            }

            return QuestionnaireEntryResourceService;
        }
    ]
);