angular.module('trialdirect').factory('UserResourceService',
    ['$http', 'SpringDataRestAdapter', 'QuestionnaireEntryResourceService', 'TherapeuticAreaResourceService', 'TherapeuticParentResourceService',
        function ($http, SpringDataRestAdapter, QuestionnaireEntryResourceService, TherapeuticAreaResourceService, TherapeuticParentResourceService) {

            var RESOURCE_URL = './api/users';

            UserResourceService.resources = null;

            // Load the specific User drilling for the questionnaire and the nested questions and answers
            UserResourceService.loadUser = function (userId) {
                var deferred = $http.get(RESOURCE_URL + '/' + userId);

                return SpringDataRestAdapter.process(deferred, ['therapeuticArea']).then(function (data) {

                    // Inflate the therapeuticArea
                    data.therapeuticArea = new TherapeuticAreaResourceService(data.therapeuticArea);

                    return new UserResourceService(data);
                });
            };

            // Load the specific User drilling for the questionnaire and the nested questions and answers
            UserResourceService.loadTherapeuticAreaForUser = function (userId) {
                var deferred = $http.get(RESOURCE_URL + '/' + userId + '/therapeuticArea');

                return SpringDataRestAdapter.process(deferred).then(function (data) {

                    return new TherapeuticAreaResourceService(data);
                });
            };

            // Just load the users themselves, don't eagerly pull back their questionnaires.
            UserResourceService.load = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred).then(function (data) {

                    UserResourceService.resources = data._resources("self");

                    return _.map(data._embeddedItems, function (user) {

                        return new UserResourceService(user);
                    });
                });
            };

            // Load the specific User drilling for the questionnaire and the nested questions and answers
            UserResourceService.inflateUser = function (userId) {
                var deferred = $http.get(RESOURCE_URL + '/' + userId);

                return SpringDataRestAdapter.process(deferred, ['userSelectorquestionnaireentries']).then(function (data) {

                    UserResourceService.resources = data._resources("self");

                    // Inflate all the questionnaireEntries so that their question and corresponding answers
                    // are retrieved
                    var promises = [];

                    var userSelectorquestionentriesList = data.userSelectorquestionnaireentries._embeddedItems;
                    angular.forEach(questionnaireentriesList, function (uninflatedQuestionnaireEntry) {
                        QuestionnaireEntryResourceService.inflateQuestionnaireEntry(uninflatedQuestionnaireEntry)
                            .then(function (inflatedQuestionnaireEntry) {
                                questionnaireentriesList[questionnaireentriesList.indexOf(uninflatedQuestionnaireEntry)] = inflatedQuestionnaireEntry;
                            });
                    });

                    return new UserResourceService(data);
                });
            };

            // e.g. './api/users/1' is being initialized
            // as a userResource in it's own right.
            function UserResourceService(user) {

                // If the userResource is being created and
                if (angular.isUndefined(user._resources)) {

                    // the userResource that we are creating has a save function
                    // placed onto the prototype for the object.
                    user.save = function (callback) {

                        // UserResourceService representing the top-level './api/users'
                        // was initialized during the load call hence it has the 'resources' member with methods
                        // for saving at the top-level

                        // We call the save object with the user
                        UserResourceService.resources.save(user, function (user, headers) {

                            // the response doesn't contain the fully saved object i.e. missing the id or perhaps
                            // some fields that are only instantiated upon creation at the backend
                            // We are forced to retrieve the saved object so
                            // GET the url returned in the headers representing the newly saved user
                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newUser) {

                                // The resource object representing the newly created entity comes back with the
                                // 'resources' member instantiated
                                callback && callback(new UserResourceService(newUser));
                            });
                        });
                    };


                } else {

                    // Instantiated resources no longer 'save' as such but are updated
                    user.resources = user._resources("self", {}, {
                        // hence we initialize the update object's HTTP method to 'PUT'
                        update: {
                            method: 'PUT'
                        }
                    });

                    // now the save method calls the update method on the resource
                    // providing capability for the callback most likely provided by the controller.
                    user.save = function (callback) {
                        user.resources.update(user, function () {
                            callback && callback(user);
                        });
                    };

                    // Also a remove method is defined on the resource
                    // which simply calls the remove method of the resources
                    // object
                    user.remove = function (callback) {
                        user.resources.remove(function () {

                            // and then a provided callback
                            callback && callback(user);
                        });
                    };
                }

                user.getHrefLink = function () {
                    return user._links.self.href;
                };

                // return the User as a interactible representation
                // of the backend entity
                return user;
            }

            return UserResourceService;
        }
    ]
);