angular.module('trialdirect').factory('TrialSelectorQuestionnaireEntryResourceService',
    ['$http', 'SpringDataRestAdapter', 'Question', 'Answer', '$q',
        function ($http, SpringDataRestAdapter, Question, Answer, $q) {

            var TRIAL_PARENT_URL_PREFIX = './api/trials';
            var RESOURCE_URL = './api/trialselectorquestionnaireentries';

            // initialise the resources object.
            TrialSelectorQuestionnaireEntryResourceService.resources = null;

            TrialSelectorQuestionnaireEntryResourceService.initialize = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred).then(function (data) {
                    TrialSelectorQuestionnaireEntryResourceService.resources = data._resources("self");
                });
            };

            // Load the specific Trial drilling for the trialSelectorQuestionnaireEntries, the questions and answers
            TrialSelectorQuestionnaireEntryResourceService.loadTrialSelectorQuestionnaireEntriesForTrial = function (trialId) {
                var deferred = $http.get(TRIAL_PARENT_URL_PREFIX + '/' + trialId + '/trialselectorquestionnaireentries');

                return SpringDataRestAdapter.process(deferred, ['question', 'answer']).then(function (data) {

                    return _.map(data._embeddedItems, function (trialSelectorQuestionnaireEntry) {

                        // Wrap the question with the extra functions
                        trialSelectorQuestionnaireEntry.question = new Question(trialSelectorQuestionnaireEntry.question);
                        trialSelectorQuestionnaireEntry.answer = new Answer(trialSelectorQuestionnaireEntry.answer);

                        return new TrialSelectorQuestionnaireEntryResourceService(trialSelectorQuestionnaireEntry);
                    });
                });
            };

            TrialSelectorQuestionnaireEntryResourceService.inflateTrialSelectorQuestionnaireEntry = function (trialSelectorQuestionnaireEntry) {

                var deferred = $http.get(RESOURCE_URL + '/' + trialSelectorQuestionnaireEntry.id);

                return SpringDataRestAdapter.process(deferred, ['question', 'answer']).then(function (data) {

                    TrialSelectorQuestionnaireEntryResourceService.resources = data._resources("self");

                    // Wrap the question with the extra functions
                    data.question = new Question(data.question);
                    data.answer = new Answer(data.answer);

                    return new TrialSelectorQuestionnaireEntryResourceService(data);
                });
            };

            function TrialSelectorQuestionnaireEntryResourceService(trialSelectorQuestionnaireEntry) {

                if (angular.isUndefined(trialSelectorQuestionnaireEntry._resources)) {

                    trialSelectorQuestionnaireEntry.save = function (callback) {

                        TrialSelectorQuestionnaireEntryResourceService.resources.save(trialSelectorQuestionnaireEntry, function (trialSelectorQuestionnaireEntry, headers) {

                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred, ['question', 'answer']).then(function (newTrialSelectorQuestionnaireEntry) {

                                callback && callback(new TrialSelectorQuestionnaireEntryResourceService(newTrialSelectorQuestionnaireEntry));
                            });
                        });
                    };


                } else {
                    trialSelectorQuestionnaireEntry.resources = trialSelectorQuestionnaireEntry._resources("self", {}, {
                        update: {
                            method: 'PUT'
                        }
                    });

                    trialSelectorQuestionnaireEntry.save = function (callback) {
                        trialSelectorQuestionnaireEntry.resources.update(trialSelectorQuestionnaireEntry, function () {
                            callback && callback(trialSelectorQuestionnaireEntry);
                        });
                    };

                    trialSelectorQuestionnaireEntry.remove = function (callback) {
                        trialSelectorQuestionnaireEntry.resources.remove(function () {
                            callback && callback(trialSelectorQuestionnaireEntry);
                        });
                    };

                    trialSelectorQuestionnaireEntry.createAssociation = function (associationName, associatedEntity, callback) {

                        var deferred = $http({
                            method: 'PATCH',
                            headers: {'Content-Type': 'text/uri-list'},
                            data: associatedEntity._links.self.href,
                            url: trialSelectorQuestionnaireEntry._links.self.href + '/' + associationName
                        });

                        // no response bar 204 from an association creation.
                        return SpringDataRestAdapter.process(deferred).then(function () {

                            callback && callback();
                        });
                    };

                    trialSelectorQuestionnaireEntry.removeAssociation = function (associationName, associatedEntity, callback) {

                        var deferred = $http.delete(trialSelectorQuestionnaireEntry._links.self.href + '/' + associationName + '/' + associatedEntity.id);

                        // no response bar 204 from an association deletion.
                        return SpringDataRestAdapter.process(deferred).then(function () {

                            callback && callback();
                        });
                    };

                    // Must remove the Question and the Trial from the TrialSelectorQuestionnaireEntry row before
                    // we may delete the TrialSelectorQuestionnaireEntry.
                    trialSelectorQuestionnaireEntry.removeQEAssociation = function (trialId, callback) {

                        var removeQuestionAssociationPromise
                            = $http.delete(trialSelectorQuestionnaireEntry._links.self.href + '/question/' + trialSelectorQuestionnaireEntry.question.id);
                        return SpringDataRestAdapter.process(removeQuestionAssociationPromise).then(function () {

                            var removeTrialAssociationPromise
                                = $http.delete(trialSelectorQuestionnaireEntry._links.self.href + '/trial/' + trialId);
                            return SpringDataRestAdapter.process(removeTrialAssociationPromise).then(function () {
                                callback && callback();
                            });
                        });
                    };
                }

                return trialSelectorQuestionnaireEntry;
            }

            TrialSelectorQuestionnaireEntryResourceService.deleteAllTrialsSelectors = function (trial, callback) {

                // Retrieve all the trialSelectorQuestionnaireEntries
                var deferred = $http.get(TRIAL_PARENT_URL_PREFIX + '/' + trial.id + '/trialselectorquestionnaireentries');

                return SpringDataRestAdapter.process(deferred).then(function (trialSelectorQuestionnaireEntries) {

                    var promises = [];

                    for (var i = 0; i < trialSelectorQuestionnaireEntries.length; i++) {
                        var trialSelectorQuestionnaireEntry = trialSelectorQuestionnaireEntries[i];
                        // Delete each TrialSelectorQuestionnaireEntry.
                        promises.push(new TrialSelectorQuestionnaireEntryResourceService(trialSelectorQuestionnaireEntry).remove());
                    }
                    $q.all(promises);

                    callback && callback();
                });
            };


            return TrialSelectorQuestionnaireEntryResourceService;
        }
    ]
);