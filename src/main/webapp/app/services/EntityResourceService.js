angular.module('trialdirect').factory('EntityResourceService', ['$http', 'SpringDataRestAdapter',
        function ($http, SpringDataRestAdapter) {

            var RESOURCE_URL = ''; // e.g. '/api/questions'

            // initialise the resources object.
            EntityResourceService.resources = null;


            // The load function 
            // Queries the entity's '/api/' url e.g. '/api/questions' to bring back all available entities
            // It is called during the 'resolve' function of the state where the Service is being initialized
            EntityResourceService.load = function (_RESOURCE_URL) {

                RESOURCE_URL = _RESOURCE_URL;

                // Create promise retrieving the resource url for the top level entity's resource
                var deferred = $http.get(RESOURCE_URL);
                
                // The SpringDataRestAdapter's process method takes the promise
                // and makes the call
                return SpringDataRestAdapter.process(deferred).then(function (data) {
                    
                    // Upon successful execution of the promise ( simply a GET to the RESOURCE_URL )
                    // The resources function created by SpringDataRestAdapter
                    // is made available on the EntityResourceService object.
                    // This provides methods like save/update/delete at for example the '/api/questions' level
                    EntityResourceService.resources = data._resources("self");

                    // From this method we return a map passing in all the embeddedItems that came back from
                    // the call to the top level api url.
                    return _.map(data._embeddedItems, function (entity) {

                        // For each entity in the _embeddedItems of the return value
                        // it instantiates a new EntityResource and places it in the map.
                        // Now instead of just having a map with JSON values representing the Entity's db state
                        // we have a map of resource objects representing each Entity
                        // which allow us to save/update & deletefor convenience.
                        return new EntityResourceService(entity);
                    });
                });
            };


            // For example, the '/api/questions/1' is being initialized
            // as a entityResource in it's own right.
            function EntityResourceService(entity) {

                // If the entityResource is being created and
                if (angular.isUndefined(entity._resources)) {
                    
                    // the entityResource that we are creating has a save function
                    // placed onto the prototype for the object.
                    entity.save = function (callback) {
                        
                        // EntityResourceService representing the top-level '/api/entitys'
                        // was initialized during the load call hence it has the 'resources' member with methods
                        // for saving at the top-level

                        // We call the save object with the entity
                        EntityResourceService.resources.save(entity, function (entity, headers) {

                            // the response doesn't contain the fully saved object i.e. missing the id or perhaps
                            // some fields that are only instantiated upon creation at the backend
                            // We are forced to retrieve the saved object so
                            // GET the url returned in the headers representing the newly saved entity
                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newEntity) {

                                // The resource object representing the newly created entity comes back with the
                                // 'resources' member instantiated
                                callback && callback(new EntityResourceService(newEntity));
                            });
                        });
                    };
                    

                } else {

                    // Instantiated resources no longer 'save' as such but are updated
                    entity.resources = entity._resources("self", {}, {
                        // hence we initialize the update object's HTTP method to 'PUT'
                        update: {
                            method: 'PUT'
                        }
                    });

                    // now the save method calls the update method on the resource
                    // providing capability for the callback most likely provided by the controller.
                    entity.save = function (callback) {
                        entity.resources.update(entity, function () {
                            callback && callback(entity);
                        });
                    };

                    // Also a remove method is defined on the resource
                    // which simply calls the remove method of the resources
                    // object
                    entity.remove = function (callback) {
                        entity.resources.remove(function () {

                            // and then a provided callback
                            callback && callback(entity);
                        });
                    };
                }

                // return the Entity as a interactible representation
                // of the backend entity
                return entity;
            }

            return EntityResourceService;
        }
    ]
);