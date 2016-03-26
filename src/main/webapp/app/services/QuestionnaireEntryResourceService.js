angular.module('trialdirect').factory('QuestionnaireEntryResourceService',
    ['$http', 'SpringDataRestAdapter', 'Question', 'Answer', '$q',
        function ($http, SpringDataRestAdapter, Question, Answer, $q) {

            var TRIAL_PARENT_URL_PREFIX = './api/trials';
            var THERAPEUTIC_AREA_PARENT_URL_PREFIX = './api/therapeuticareas';
            var RESOURCE_URL = './api/questionnaireentries';

            // initialise the resources object.
            QuestionnaireEntryResourceService.resources = null;

            QuestionnaireEntryResourceService.initialize = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred).then(function (data) {
                    QuestionnaireEntryResourceService.resources = data._resources("self");
                });
            };

            // Load the specific TherapeuticArea drilling for the questionnaire and the nested questions and answers
            QuestionnaireEntryResourceService.loadQuestionnaireEntriesForTherapeuticArea = function (therapeuticAreaId) {
                var deferred = $http.get(THERAPEUTIC_AREA_PARENT_URL_PREFIX + '/' + therapeuticAreaId + '/questionnaireentries');

                return SpringDataRestAdapter.process(deferred, ['question', 'answers']).then(function (data) {

                    return _.map(data._embeddedItems, function (questionnaireEntry) {

                        // Wrap the question with the extra functions
                        questionnaireEntry.question = new Question(questionnaireEntry.question);

                        // Wrap the answers with the extra functions.
                        var answerList = questionnaireEntry.answers._embeddedItems;
                        angular.forEach(answerList, function (unwrappedAnswer) {
                            answerList[answerList.indexOf(unwrappedAnswer)] = new Answer(unwrappedAnswer);
                        });

                        return new QuestionnaireEntryResourceService(questionnaireEntry);
                    });
                });
            };

            // Load the specific TherapeuticArea drilling for the questionnaire and the nested questions and answers
            QuestionnaireEntryResourceService.loadQuestionnaireEntriesForTrial = function (trialId) {

                var deferred1 = $http.get(TRIAL_PARENT_URL_PREFIX + '/' + trialId + '/therapeuticArea');

                return SpringDataRestAdapter.process(deferred1).then(function (therapeuticArea) {

                    var deferred = $http.get(THERAPEUTIC_AREA_PARENT_URL_PREFIX + '/' + therapeuticArea.id + '/questionnaireentries');

                    return SpringDataRestAdapter.process(deferred, ['question', 'answers']).then(function (data) {

                        return _.map(data._embeddedItems, function (questionnaireEntry) {

                            // Wrap the question with the extra functions
                            questionnaireEntry.question = new Question(questionnaireEntry.question);

                            // Wrap the answers with the extra functions.
                            var answerList = questionnaireEntry.answers._embeddedItems;
                            angular.forEach(answerList, function (unwrappedAnswer) {
                                answerList[answerList.indexOf(unwrappedAnswer)] = new Answer(unwrappedAnswer);
                            });

                            return new QuestionnaireEntryResourceService(questionnaireEntry);
                        });
                    });
                });
            };

            QuestionnaireEntryResourceService.inflateQuestionnaireEntry = function (questionnaireEntry) {

                var deferred = $http.get(RESOURCE_URL + '/' + questionnaireEntry.id);

                return SpringDataRestAdapter.process(deferred, ['question', 'answers']).then(function (data) {

                    QuestionnaireEntryResourceService.resources = data._resources("self");

                    // Wrap the question with the extra functions
                    data.question = new Question(data.question);

                    // Wrap the answers with the extra functions.
                    var answerList = data.answers._embeddedItems;
                    angular.forEach(answerList, function (unwrappedAnswer) {
                        answerList[answerList.indexOf(unwrappedAnswer)] = new Answer(unwrappedAnswer);
                    });

                    return new QuestionnaireEntryResourceService(data);
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

                    questionnaireEntry.createAssociation = function (associationName, associatedEntity, callback) {

                        var deferred = $http({
                            method: 'PATCH',
                            headers: {'Content-Type': 'text/uri-list'},
                            data: associatedEntity._links.self.href,
                            url: questionnaireEntry._links.self.href + '/' + associationName
                        });

                        // no response bar 204 from an association creation.
                        return SpringDataRestAdapter.process(deferred).then(function () {

                            callback && callback();
                        });
                    };

                    questionnaireEntry.removeAssociation = function (associationName, associatedEntity, callback) {

                        var deferred = $http.delete(questionnaireEntry._links.self.href + '/' + associationName + '/' + associatedEntity.id);

                        // no response bar 204 from an association deletion.
                        return SpringDataRestAdapter.process(deferred).then(function () {

                            callback && callback();
                        });
                    };

                    // Must remove the Question and the TherapeuticArea from the QuestionnaireEntry row before
                    // we may delete the QuestionnaireEntry.
                    questionnaireEntry.removeQEAssociation = function (therapeuticAreaId, callback) {

                        var removeQuestionAssociationPromise
                            = $http.delete(questionnaireEntry._links.self.href + '/question/' + questionnaireEntry.question.id);
                        return SpringDataRestAdapter.process(removeQuestionAssociationPromise).then(function () {

                            var removeTherapeuticAreaAssociationPromise
                                = $http.delete(questionnaireEntry._links.self.href + '/therapeuticArea/' + therapeuticAreaId);
                            return SpringDataRestAdapter.process(removeTherapeuticAreaAssociationPromise).then(function () {
                                callback && callback();
                            });
                        });
                    };
                }

                return questionnaireEntry;
            }

            return QuestionnaireEntryResourceService;
        }
    ]
);