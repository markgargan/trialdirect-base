angular.module('trialdirect').factory('TrialResourceService',
    ['$http', 'SpringDataRestAdapter', 'QuestionnaireEntryResourceService',
        function ($http, SpringDataRestAdapter, QuestionnaireEntryResourceService ) {

            var RESOURCE_URL = './api/trials';

            TrialResourceService.resources = null;

            // Load the specific Trial drilling for the questionnaire and the nested questions and answers
            TrialResourceService.loadTrial = function (trialId) {
                var deferred = $http.get(RESOURCE_URL + '/' + trialId);

                return SpringDataRestAdapter.process(deferred, 'therapeuticArea').then(function (data) {

                    return new TrialResourceService(data);
                });
            };

            // Just load the trials themselves, don't eagerly pull back their questionnaires.
            TrialResourceService.load = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred ).then(function (data) {

                    TrialResourceService.resources = data._resources("self");

                    return _.map(data._embeddedItems, function (trial) {

                        return new TrialResourceService(trial);
                    });
                });
            };

            // Load the specific Trial drilling for the questionnaire and the nested questions and answers
            TrialResourceService.inflateTrial = function (trialId) {
                var deferred = $http.get(RESOURCE_URL + '/' + trialId);
                
                return SpringDataRestAdapter.process(deferred, ['trialselectorquestionnaireentries']).then(function (data) {
                    
                    TrialResourceService.resources = data._resources("self");

                    // Inflate all the questionnaireEntries so that their question and corresponding answers
                    // are retrieved
                    var promises = [];

                    var trialselectorquestionentriesList = data.trialselectorquestionnaireentries._embeddedItems;
                    angular.forEach(questionnaireentriesList, function (uninflatedQuestionnaireEntry) {
                            QuestionnaireEntryResourceService.inflateQuestionnaireEntry(uninflatedQuestionnaireEntry)
                                .then(function(inflatedQuestionnaireEntry){
                                    questionnaireentriesList[questionnaireentriesList.indexOf(uninflatedQuestionnaireEntry)] = inflatedQuestionnaireEntry;
                            });
                    });

                    return new TrialResourceService(data);
                });
            };

            // e.g. '/api/trials/1' is being initialized
            // as a trialResource in it's own right.
            function TrialResourceService(trial) {

                // If the trialResource is being created and
                if (angular.isUndefined(trial._resources)) {
                    
                    // the trialResource that we are creating has a save function
                    // placed onto the prototype for the object.
                    trial.save = function (callback) {
                        
                        // TrialResourceService representing the top-level '/api/trials'
                        // was initialized during the load call hence it has the 'resources' member with methods
                        // for saving at the top-level

                        // We call the save object with the trial
                        TrialResourceService.resources.save(trial, function (trial, headers) {

                            // the response doesn't contain the fully saved object i.e. missing the id or perhaps
                            // some fields that are only instantiated upon creation at the backend
                            // We are forced to retrieve the saved object so
                            // GET the url returned in the headers representing the newly saved trial
                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newTrial) {

                                // The resource object representing the newly created entity comes back with the
                                // 'resources' member instantiated
                                callback && callback(new TrialResourceService(newTrial));
                            });
                        });
                    };
                    

                } else {

                    // Instantiated resources no longer 'save' as such but are updated
                    trial.resources = trial._resources("self", {}, {
                        // hence we initialize the update object's HTTP method to 'PUT'
                        update: {
                            method: 'PUT'
                        }
                    });

                    // now the save method calls the update method on the resource
                    // providing capability for the callback most likely provided by the controller.
                    trial.save = function (callback) {
                        trial.resources.update(trial, function () {
                            callback && callback(trial);
                        });
                    };

                    // Also a remove method is defined on the resource
                    // which simply calls the remove method of the resources
                    // object
                    trial.remove = function (callback) {
                        trial.resources.remove(function () {

                            // and then a provided callback
                            callback && callback(trial);
                        });
                    };
                }

                trial.getHrefLink = function() {
                    return trial._links.self.href;
                };

                // return the Trial as a interactible representation
                // of the backend entity
                return trial;
            }

            return TrialResourceService;
        }
    ]
);