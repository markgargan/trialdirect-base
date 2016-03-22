angular.module('trialdirect').factory('QuestionnaireEntryResourceService',
    ['$http', 'SpringDataRestAdapter', 'Question', 'Answer',
        function ($http, SpringDataRestAdapter, Question, Answer) {

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

                        // Wrap the question with the extra functions
                        questionnaireEntry.question = new Question(questionnaireEntry.question);

                        // Wrap the answers with the extra functions.
                        var answerList = questionnaireEntry.answers._embeddedItems;
                        angular.forEach(answerList, function(unwrappedAnswer) {
                            answerList[answerList.indexOf(unwrappedAnswer)] = new Answer(unwrappedAnswer);
                        });

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

                    questionnaireEntry.createAssociation = function(associationName, associatedEntity, callback) {

                        var deferred = $http({
                            method: 'PATCH',
                            headers: { 'Content-Type': 'text/uri-list'},
                            data: associatedEntity._links.self.href,
                            url: questionnaireEntry._links.self.href + '/' + associationName
                        });

                        // no response bar 204 from an association creation.
                        return SpringDataRestAdapter.process(deferred).then(function () {

                            callback && callback();
                        });
                    };

                    questionnaireEntry.removeAssociation = function(associationName, associatedEntity, callback) {

                        var deferred = $http.delete(questionnaireEntry._links.self.href + '/' + associationName + '/' + associatedEntity.id);

                        // no response bar 204 from an association deletion.
                        return SpringDataRestAdapter.process(deferred).then(function () {

                            callback && callback();
                        });
                    };
                }

                return questionnaireEntry;
            }

            return QuestionnaireEntryResourceService;
        }
    ]
);