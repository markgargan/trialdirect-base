angular.module('trialdirect').factory('TherapeuticAreaResourceService', ['$http', 'SpringDataRestAdapter',
        function ($http, SpringDataRestAdapter) {

            var RESOURCE_URL = './api/therapeuticareas';

            // initialise the resources object.
            TherapeuticAreaResourceService.resources = null;


            // The load function 
            // Queries the /api/therapeuticareas url to bring back all available therapeuticAreas
            // It is called during the 'resolve' function of the therapeticarea state in the stateController
            TherapeuticAreaResourceService.load = function () {
                // Create promise with the resource url for the top level therapeuticAreas resource
                var deferred = $http.get(RESOURCE_URL);
                
                // The SpringDataRestAdapter's process method takes the promise
                // and makes the call
                return SpringDataRestAdapter.process(deferred).then(function (data) {
                    
                    // Upon successful execution of the promise ( simply a GET to the RESOURCE_URL )
                    // The resources function created by SpringDataRestAdapter
                    // is made available on the TherapeuticAreaResourceService object.
                    // This provides methods like save/update/delete at the '/api/therapeuticareas' level
                    TherapeuticAreaResourceService.resources = data._resources("self");

                    // From this method we return a map passing in all the embeddedItems that came back from
                    // the call to the top level api 'api/therapeuticareas'
                    return _.map(data._embeddedItems, function (therapeuticArea) {

                        // For each therapeuticArea in the _embeddedItems of the return value
                        // it instantiates a new TherapeuticAreaResource and places it in the map.
                        // Now instead of just having a map with JSON values representing the TherapeuticArea
                        // we have a map of objects representing each TherapeuticArea
                        // which also have methods like update/save/delete for convenience.
                        return new TherapeuticAreaResourceService(therapeuticArea);
                    });
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

                // return the TherapeuticArea as a interactible representation
                // of the backend entity
                return therapeuticArea;
            }

            return TherapeuticAreaResourceService;
        }
    ]
);