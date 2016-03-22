angular.module('trialdirect').factory('Answer', ['$http', 'SpringDataRestAdapter',
        function ($http, SpringDataRestAdapter) {
            var ANSWER_URL = '/api/answers';

            function Answer(answer) {

                if (answer._resources) {
                    answer.resources = answer._resources("self", {}, {
                        update: {
                            method: 'PUT'
                        }
                    });
                    answer.save = function (callback) {
                        answer.resources.update(answer, function () {
                            callback && callback(answer);
                        });
                    };

                    answer.remove = function (callback) {
                        answer.resources.remove(function () {
                            callback && callback(answer);
                        });
                    };

                } else {
                    answer.save = function (callback) {
                        Answer.resources.save(answer, function (answer, headers) {
                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newAnswer) {
                                callback && callback(new Answer(newAnswer));
                            });
                        });
                    };
                }

                answer.getHrefLink = function() {
                    return answer._links.self.href;
                };

                return answer;
            }

            Answer.query = function () {
                var deferred = $http.get(ANSWER_URL);
                return SpringDataRestAdapter.process(deferred ).then(function (data) {
                    Answer.resources = data._resources("self");

                });
            };

            Answer.resources = null;

            return Answer;
        }
    ]
);