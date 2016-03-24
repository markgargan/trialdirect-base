angular.module('trialdirect').factory('TrialSelectorResourceService',
    ['$http', 'SpringDataRestAdapter', 'QuestionnaireEntryResourceService',
        function ($http, SpringDataRestAdapter, QuestionnaireEntryResourceService ) {

            var RESOURCE_URL = './api/therapeuticareas';

            TrialSelectorResourceService.resources = null;

            // Load the specific TrialSelector drilling for the questionnaire and the nested questions and answers
            TrialSelectorResourceService.loadTrialSelector = function (trialSelectorId) {
                var deferred = $http.get(RESOURCE_URL + '/' + trialSelectorId);

                return SpringDataRestAdapter.process(deferred).then(function (data) {

                    return new TrialSelectorResourceService(data);
                });
            };

            // Just load the trialSelectors themselves, don't eagerly pull back their questionnaires.
            TrialSelectorResourceService.load = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred ).then(function (data) {

                    TrialSelectorResourceService.resources = data._resources("self");

                    return _.map(data._embeddedItems, function (trialSelector) {

                        return new TrialSelectorResourceService(trialSelector);
                    });
                });
            };

            // Load the specific TrialSelector drilling for the questionnaire and the nested questions and answers
            TrialSelectorResourceService.inflateTrialSelector = function (trialSelectorId) {
                var deferred = $http.get(RESOURCE_URL + '/' + trialSelectorId);
                
                return SpringDataRestAdapter.process(deferred, ['questionnaireentries', ['question', 'answers']]).then(function (data) {
                    
                    TrialSelectorResourceService.resources = data._resources("self");

                    // Inflate all the questionnaireEntries so that their question and corresponding answers
                    // are retrieved
                    var promises = [];

                    var questionnaireentriesList = data.questionnaireentries._embeddedItems;
                    angular.forEach(questionnaireentriesList, function (uninflatedQuestionnaireEntry) {
                            QuestionnaireEntryResourceService.inflateQuestionnaireEntry(uninflatedQuestionnaireEntry)
                                .then(function(inflatedQuestionnaireEntry){
                                    questionnaireentriesList[questionnaireentriesList.indexOf(uninflatedQuestionnaireEntry)] = inflatedQuestionnaireEntry;
                            });
                    });

                    return new TrialSelectorResourceService(data);
                });
            };

            // e.g. '/api/trialSelectors/1' is being initialized
            // as a trialSelectorResource in it's own right.
            function TrialSelectorResourceService(trialSelector) {

                // If the trialSelectorResource is being created and
                if (angular.isUndefined(trialSelector._resources)) {
                    
                    // the trialSelectorResource that we are creating has a save function
                    // placed onto the prototype for the object.
                    trialSelector.save = function (callback) {
                        
                        // TrialSelectorResourceService representing the top-level '/api/trialSelectors'
                        // was initialized during the load call hence it has the 'resources' member with methods
                        // for saving at the top-level

                        // We call the save object with the trialSelector
                        TrialSelectorResourceService.resources.save(trialSelector, function (trialSelector, headers) {

                            // the response doesn't contain the fully saved object i.e. missing the id or perhaps
                            // some fields that are only instantiated upon creation at the backend
                            // We are forced to retrieve the saved object so
                            // GET the url returned in the headers representing the newly saved trialSelector
                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newTrialSelector) {

                                // The resource object representing the newly created entity comes back with the
                                // 'resources' member instantiated
                                callback && callback(new TrialSelectorResourceService(newTrialSelector));
                            });
                        });
                    };
                    

                } else {

                    // Instantiated resources no longer 'save' as such but are updated
                    trialSelector.resources = trialSelector._resources("self", {}, {
                        // hence we initialize the update object's HTTP method to 'PUT'
                        update: {
                            method: 'PUT'
                        }
                    });

                    // now the save method calls the update method on the resource
                    // providing capability for the callback most likely provided by the controller.
                    trialSelector.save = function (callback) {
                        trialSelector.resources.update(trialSelector, function () {
                            callback && callback(trialSelector);
                        });
                    };

                    // Also a remove method is defined on the resource
                    // which simply calls the remove method of the resources
                    // object
                    trialSelector.remove = function (callback) {
                        trialSelector.resources.remove(function () {

                            // and then a provided callback
                            callback && callback(trialSelector);
                        });
                    };
                }

                trialSelector.getHrefLink = function() {
                    return trialSelector._links.self.href;
                };

                // return the TrialSelector as a interactible representation
                // of the backend entity
                return trialSelector;
            }

            return TrialSelectorResourceService;
        }
    ]
);