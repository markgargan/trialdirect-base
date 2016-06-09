angular.module('trialdirect').factory('TherapeuticParentResourceService',
    ['$http', 'SpringDataRestAdapter', 'QuestionnaireEntryResourceService',
        function ($http, SpringDataRestAdapter, QuestionnaireEntryResourceService ) {

            var RESOURCE_URL = './api/therapeuticparent';


            TherapeuticParentResourceService.resources = null;

            // Load the specific TherapeuticParent drilling for the questionnaire and the nested questions and answers
            TherapeuticParentResourceService.loadTherapeuticParent = function (therapeuticParentId) {
                var deferred = $http.get(RESOURCE_URL + '/' + therapeuticParentId);

                return SpringDataRestAdapter.process(deferred).then(function (data) {

                    return new TherapeuticParentResourceService(data);
                });
            };

            // Just load the therapeuticParent themselves, don't eagerly pull back their questionnaires.
            TherapeuticParentResourceService.load = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred ).then(function (data) {

                    TherapeuticParentResourceService.resources = data._resources("self");

                    return _.map(data._embeddedItems, function (therapeuticParent) {

                        return new TherapeuticParentResourceService(therapeuticParent);
                    });
                });
            };

            // Load the specific TherapeuticParent drilling for the questionnaire and the nested questions and answers
            TherapeuticParentResourceService.inflateTherapeuticParent = function (therapeuticParentId) {
                var deferred = $http.get(RESOURCE_URL + '/' + therapeuticParentId);
                
                return SpringDataRestAdapter.process(deferred, ['questionnaireentries', ['question', 'answers']]).then(function (data) {
                    
                    TherapeuticParentResourceService.resources = data._resources("self");

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

                    return new TherapeuticParentResourceService(data);
                });
            };

            // e.g. './api/therapeuticparent/1' is being initialized
            // as a therapeuticParentResource in it's own right.
            function TherapeuticParentResourceService(therapeuticParent) {

                // If the therapeuticParentResource is being created and
                if (angular.isUndefined(therapeuticParent._resources)) {
                    
                    // the therapeuticParentResource that we are creating has a save function
                    // placed onto the prototype for the object.
                    therapeuticParent.save = function (callback) {
                        
                        // TherapeuticParentResourceService representing the top-level './api/therapeuticparent'
                        // was initialized during the load call hence it has the 'resources' member with methods
                        // for saving at the top-level

                        // We call the save object with the therapeuticParent
                        TherapeuticParentResourceService.resources.save(therapeuticParent, function (therapeuticParent, headers) {

                            // the response doesn't contain the fully saved object i.e. missing the id or perhaps
                            // some fields that are only instantiated upon creation at the backend
                            // We are forced to retrieve the saved object so
                            // GET the url returned in the headers representing the newly saved therapeuticParent
                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newTherapeuticParent) {

                                // The resource object representing the newly created entity comes back with the
                                // 'resources' member instantiated
                                callback && callback(new TherapeuticParentResourceService(newTherapeuticParent));
                            });
                        });
                    };
                    

                } else {

                    // Instantiated resources no longer 'save' as such but are updated
                    therapeuticParent.resources = therapeuticParent._resources("self", {}, {
                        // hence we initialize the update object's HTTP method to 'PUT'
                        update: {
                            method: 'PUT'
                        }
                    });

                    // now the save method calls the update method on the resource
                    // providing capability for the callback most likely provided by the controller.
                    therapeuticParent.save = function (callback) {
                        therapeuticParent.resources.update(therapeuticParent, function () {
                            callback && callback(therapeuticParent);
                        });
                    };

                    // Also a remove method is defined on the resource
                    // which simply calls the remove method of the resources
                    // object
                    therapeuticParent.remove = function (callback) {
                        therapeuticParent.resources.remove(function () {

                            // and then a provided callback
                            callback && callback(therapeuticParent);
                        });
                    };
                }

                therapeuticParent.getHrefLink = function() {
                    return therapeuticParent._links.self.href;
                };

                // return the TherapeuticParent as a interactible representation
                // of the backend entity
                return therapeuticParent;
            }

            return TherapeuticParentResourceService;
        }
    ]
);