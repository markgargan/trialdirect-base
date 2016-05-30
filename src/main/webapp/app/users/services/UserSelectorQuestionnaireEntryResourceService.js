angular.module('trialdirect').factory('UserSelectorQuestionnaireEntryResourceService',
    ['$http', 'SpringDataRestAdapter', 'Question', 'Answer',
        function ($http, SpringDataRestAdapter, Question, Answer) {

            var THERAPEUTIC_AREA_PARENT_URL_PREFIX = './api/therapeuticareas';
            var RESOURCE_URL = './api/userselectorquestionnaireentries';

            // initialise the resources object.
            UserSelectorQuestionnaireEntryResourceService.resources = null;

            UserSelectorQuestionnaireEntryResourceService.initialize = function () {
                var deferred = $http.get(RESOURCE_URL);

                return SpringDataRestAdapter.process(deferred).then(function (data) {
                    UserSelectorQuestionnaireEntryResourceService.resources = data._resources("self");
                });
            };

            // Load the specific User drilling for the userSelectorQuestionnaireEntries, the questions and answers
            UserSelectorQuestionnaireEntryResourceService.loadUserSelectorQuestionnaireEntriesForUser = function (userId) {
                var deferred = $http.get('/api/users/' + userId + '/userselectorquestionnaireentries');

                return SpringDataRestAdapter.process(deferred, ['question', 'answer']).then(function (data) {

                    return _.map(data._embeddedItems, function (userSelectorQuestionnaireEntry) {

                        // Wrap the question with the extra functions
                        userSelectorQuestionnaireEntry.question = new Question(userSelectorQuestionnaireEntry.question);
                        userSelectorQuestionnaireEntry.answer = new Answer(userSelectorQuestionnaireEntry.answer);

                        return new UserSelectorQuestionnaireEntryResourceService(userSelectorQuestionnaireEntry);
                    });
                });
            };

            UserSelectorQuestionnaireEntryResourceService.inflateUserSelectorQuestionnaireEntry = function (userSelectorQuestionnaireEntry) {

                var deferred = $http.get(RESOURCE_URL + '/' + userSelectorQuestionnaireEntry.id);

                return SpringDataRestAdapter.process(deferred, ['question', 'answer']).then(function (data) {

                    UserSelectorQuestionnaireEntryResourceService.resources = data._resources("self");

                    // Wrap the question with the extra functions
                    data.question = new Question(data.question);
                    data.answer = new Answer(data.answer);

                    return new UserSelectorQuestionnaireEntryResourceService(data);
                });
            };

            function UserSelectorQuestionnaireEntryResourceService(userSelectorQuestionnaireEntry) {

                if (angular.isUndefined(userSelectorQuestionnaireEntry._resources)) {

                    userSelectorQuestionnaireEntry.save = function (callback) {

                        UserSelectorQuestionnaireEntryResourceService.resources.save(userSelectorQuestionnaireEntry, function (userSelectorQuestionnaireEntry, headers) {

                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred, ['question', 'answer']).then(function (newUserSelectorQuestionnaireEntry) {

                                callback && callback(new UserSelectorQuestionnaireEntryResourceService(newUserSelectorQuestionnaireEntry));
                            });
                        });
                    };


                } else {
                    userSelectorQuestionnaireEntry.resources = userSelectorQuestionnaireEntry._resources("self", {}, {
                        update: {
                            method: 'PUT'
                        }
                    });

                    userSelectorQuestionnaireEntry.save = function (callback) {
                        userSelectorQuestionnaireEntry.resources.update(userSelectorQuestionnaireEntry, function () {
                            callback && callback(userSelectorQuestionnaireEntry);
                        });
                    };

                    userSelectorQuestionnaireEntry.remove = function (callback) {
                        userSelectorQuestionnaireEntry.resources.remove(function () {
                            callback && callback(userSelectorQuestionnaireEntry);
                        });
                    };

                    userSelectorQuestionnaireEntry.createAssociation = function (associationName, associatedEntity, callback) {

                        var deferred = $http({
                            method: 'PATCH',
                            headers: {'Content-Type': 'text/uri-list'},
                            data: associatedEntity._links.self.href,
                            url: userSelectorQuestionnaireEntry._links.self.href + '/' + associationName
                        });

                        // no response bar 204 from an association creation.
                        return SpringDataRestAdapter.process(deferred).then(function () {

                            callback && callback();
                        });
                    };

                    userSelectorQuestionnaireEntry.removeAssociation = function (associationName, associatedEntity, callback) {

                        var deferred = $http.delete(userSelectorQuestionnaireEntry._links.self.href + '/' + associationName + '/' + associatedEntity.id);

                        // no response bar 204 from an association deletion.
                        return SpringDataRestAdapter.process(deferred).then(function () {

                            callback && callback();
                        });
                    };

                    // Must remove the Question and the User from the UserSelectorQuestionnaireEntry row before
                    // we may delete the UserSelectorQuestionnaireEntry.
                    userSelectorQuestionnaireEntry.removeQEAssociation = function (userId, callback) {

                        var removeQuestionAssociationPromise
                            = $http.delete(userSelectorQuestionnaireEntry._links.self.href + '/question/' + userSelectorQuestionnaireEntry.question.id);
                        return SpringDataRestAdapter.process(removeQuestionAssociationPromise).then(function () {

                            var removeUserAssociationPromise
                                = $http.delete(userSelectorQuestionnaireEntry._links.self.href + '/user/' + userId);
                            return SpringDataRestAdapter.process(removeUserAssociationPromise).then(function () {
                                callback && callback();
                            });
                        });
                    };
                }

                return userSelectorQuestionnaireEntry;
            }

            return UserSelectorQuestionnaireEntryResourceService;
        }
    ]
);