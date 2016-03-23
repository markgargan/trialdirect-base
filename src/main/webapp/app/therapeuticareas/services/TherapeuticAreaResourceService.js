angular.module('trialdirect').factory('TherapeuticAreaResourceService',
    ['$http', 'SpringDataRestAdapter', 'QuestionnaireEntryResourceService',
        function ($http, SpringDataRestAdapter, QuestionnaireEntryResourceService ) {

            var RESOURCE_URL = './api/therapeuticareas';

            TherapeuticAreaResourceService.resources = null;

            // Load the specific TherapeuticArea drilling for the questionnaire and the nested questions and answers
            TherapeuticAreaResourceService.loadTherapeuticArea = function (therapeuticAreaId) {
                var deferred = $http.get(RESOURCE_URL + '/' + therapeuticAreaId);

                return SpringDataRestAdapter.process(deferred).then(function (data) {

                    return new TherapeuticAreaResourceService(data);
                });
            };

            // Just load the therapeuticAreas themselves, don't eagerly pull back their questionnaires.
            TherapeuticAreaResourceService.load = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred ).then(function (data) {

                    TherapeuticAreaResourceService.resources = data._resources("self");

                    return _.map(data._embeddedItems, function (therapeuticArea) {

                        return new TherapeuticAreaResourceService(therapeuticArea);
                    });
                });
            };

            // Load the specific TherapeuticArea drilling for the questionnaire and the nested questions and answers
            TherapeuticAreaResourceService.inflateTherapeuticArea = function (therapeuticAreaId) {
                var deferred = $http.get(RESOURCE_URL + '/' + therapeuticAreaId);
                
                return SpringDataRestAdapter.process(deferred, ['questionnaireentries', ['question', 'answers']]).then(function (data) {
                    
                    TherapeuticAreaResourceService.resources = data._resources("self");

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

                    return new TherapeuticAreaResourceService(data);
                });
            };

            // e.g. '/api/therapeuticareas/1' is being initialized
            // as a therapeuticAreaResource in it's own right.
            function TherapeuticAreaResourceService(therapeuticArea) {

                // If the therapeuticAreaResource is being created and
                if (angular.isUndefined(therapeuticArea._resources)) {
                    
                    // the therapeuticAreaResource that we are creating has a save function
                    // placed onto the prototype for the object.
                    therapeuticArea.save = function (callback) {
                        
                        // TherapeuticAreaResourceService representing the top-level '/api/therapeuticareas'
                        // was initialized during the load call hence it has the 'resources' member with methods
                        // for saving at the top-level

                        // We call the save object with the therapeuticArea
                        TherapeuticAreaResourceService.resources.save(therapeuticArea, function (therapeuticArea, headers) {

                            // the response doesn't contain the fully saved object i.e. missing the id or perhaps
                            // some fields that are only instantiated upon creation at the backend
                            // We are forced to retrieve the saved object so
                            // GET the url returned in the headers representing the newly saved therapeuticArea
                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newTherapeuticArea) {

                                // The resource object representing the newly created entity comes back with the
                                // 'resources' member instantiated
                                callback && callback(new TherapeuticAreaResourceService(newTherapeuticArea));
                            });
                        });
                    };
                    

                } else {

                    // Instantiated resources no longer 'save' as such but are updated
                    therapeuticArea.resources = therapeuticArea._resources("self", {}, {
                        // hence we initialize the update object's HTTP method to 'PUT'
                        update: {
                            method: 'PUT'
                        }
                    });

                    // now the save method calls the update method on the resource
                    // providing capability for the callback most likely provided by the controller.
                    therapeuticArea.save = function (callback) {
                        therapeuticArea.resources.update(therapeuticArea, function () {
                            callback && callback(therapeuticArea);
                        });
                    };

                    // Also a remove method is defined on the resource
                    // which simply calls the remove method of the resources
                    // object
                    therapeuticArea.remove = function (callback) {
                        therapeuticArea.resources.remove(function () {

                            // and then a provided callback
                            callback && callback(therapeuticArea);
                        });
                    };
                }

                therapeuticArea.getHrefLink = function() {
                    return therapeuticArea._links.self.href;
                };

                // return the TherapeuticArea as a interactible representation
                // of the backend entity
                return therapeuticArea;
            }

            return TherapeuticAreaResourceService;
        }
    ]
);