angular.module('trialdirect').factory('TrialInfo',
    ['$http', 'SpringDataRestAdapter', 'QuestionnaireEntryResourceService',
        function ($http, SpringDataRestAdapter, QuestionnaireEntryResourceService ) {

            var RESOURCE_URL = './api/trialinfos';

            TrialInfo.resources = null;

            // Just load the trials themselves, don't eagerly pull back their questionnaires.
            TrialInfo.initialize = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred ).then(function (data) {

                    TrialInfo.resources = data._resources("self");
                });
            };

            TrialInfo.loadTrialInfo = function (trialInfoId) {
                var deferred = $http.get(RESOURCE_URL + '/' + trialInfoId);

                return SpringDataRestAdapter.process(deferred, 'trialSites').then(function (data) {

                    return new TrialInfo(data);
                });
            };

            // Just load the trials themselves, don't eagerly pull back their questionnaires.
            TrialInfo.load = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred ).then(function (data) {

                    TrialInfo.resources = data._resources("self");

                    return _.map(data._embeddedItems, function (trialInfo) {

                        return new TrialInfo(trialInfo);
                    });
                });
            };

            // e.g. '/api/trialInfos/1' is being initialized
            // as a trialInfoResource in it's own right.
            function TrialInfo(trialInfo) {

                // If the trialInfoResource is being created and
                if (angular.isUndefined(trialInfo._resources)) {
                    
                    // the trialInfoResource that we are creating has a save function
                    // placed onto the prototype for the object.
                    trialInfo.save = function (callback) {
                        
                        // TrialInfo representing the top-level '/api/trialinfos'
                        // was initialized during the load call hence it has the 'resources' member with methods
                        // for saving at the top-level

                        // We call the save object with the trialInfo
                        TrialInfo.resources.save(trialInfo, function (trialInfo, headers) {

                            // the response doesn't contain the fully saved object i.e. missing the id or perhaps
                            // some fields that are only instantiated upon creation at the backend
                            // We are forced to retrieve the saved object so
                            // GET the url returned in the headers representing the newly saved trialInfo
                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newTrialInfo) {

                                // The resource object representing the newly created entity comes back with the
                                // 'resources' member instantiated
                                callback && callback(new TrialInfo(newTrialInfo));
                            });
                        });
                    };
                    

                } else {

                    // Instantiated resources no longer 'save' as such but are updated
                    trialInfo.resources = trialInfo._resources("self", {}, {
                        // hence we initialize the update object's HTTP method to 'PUT'
                        update: {
                            method: 'PUT'
                        }
                    });

                    // now the save method calls the update method on the resource
                    // providing capability for the callback most likely provided by the controller.
                    trialInfo.save = function (callback) {
                        trialInfo.resources.update(trialInfo, function () {
                            callback && callback(trialInfo);
                        });
                    };

                    // Also a remove method is defined on the resource
                    // which simply calls the remove method of the resources
                    // object
                    trialInfo.remove = function (callback) {
                        trialInfo.resources.remove(function () {

                            // and then a provided callback
                            callback && callback(trialInfo);
                        });
                    };
                }

                trialInfo.getHrefLink = function() {
                    return trialInfo._links.self.href;
                };

                // return the Trial as a interactible representation
                // of the backend entity
                return trialInfo;
            }

            return TrialInfo;
        }
    ]
);