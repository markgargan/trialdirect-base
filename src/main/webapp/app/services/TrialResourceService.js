angular.module('trialdirect').factory('TrialResourceService', ['$http', 'SpringDataRestAdapter',
        function ($http, SpringDataRestAdapter) {

            var RESOURCE_URL = './api/trials';

            // initialise the resources object.
            TrialResourceService.resources = null;


            // The load function 
            // Queries the /api/trials url to bring back all available trials
            // It is called during the 'resolve' function of the trial state in the stateController
            TrialResourceService.load = function () {
                // Create promise with the resource url for the top level trials resource
                var deferred = $http.get(RESOURCE_URL);
                
                // The SpringDataRestAdapter's process method takes the promise
                // and makes the call
                return SpringDataRestAdapter.process(deferred).then(function (data) {
                    
                    // Upon successful execution of the promise ( simply a GET to the RESOURCE_URL )
                    // The resources function created by SpringDataRestAdapter
                    // is made available on the TrialResourceService object.
                    // This provides methods like save/update/delete at the '/api/trials' level
                    TrialResourceService.resources = data._resources("self");

                    // From this method we return a map passing in all the embeddedItems that came back from
                    // the call to the top level api 'api/trials'
                    return _.map(data._embeddedItems, function (trial) {

                        // For each trial in the _embeddedItems of the return value
                        // it instantiates a new TrialResource and places it in the map.
                        // Now instead of just having a map with JSON values representing the Trial
                        // we have a map of objects representing each Trial
                        // which also have methods like update/save/delete for convenience.
                        return new TrialResourceService(trial);
                    });
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

                // return the Trial as a interactible representation
                // of the backend entity
                return trial;
            }

            return TrialResourceService;
        }
    ]
);