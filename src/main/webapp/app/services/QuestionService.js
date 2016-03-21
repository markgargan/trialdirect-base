angular.module('trialdirect').factory('Question', ['$http', 'SpringDataRestAdapter', 'Answer',
        function ($http, SpringDataRestAdapter, Answer) {
            var HATEOAS_URL = './api/questions';

            function Question(question) {

                if (question._resources) {
                    question.resources = question._resources("self", {}, {
                        update: {
                            method: 'PUT'
                        }
                    });
                    question.save = function (callback) {
                        question.resources.update(question, function () {
                            callback && callback(question);
                        });
                    };

                    question.remove = function (callback) {
                        question.resources.remove(function () {
                            callback && callback(question);
                        });
                    };

                } else {
                    question.save = function (callback) {
                        Question.resources.save(question, function (question, headers) {
                            var deferred = $http.get(headers().location);
                            return SpringDataRestAdapter.process(deferred).then(function (newQuestion) {
                                callback && callback(new Question(newQuestion));
                            });
                        });
                    };
                }

                return question;
            }

            Question.query = function () {
                var deferred = $http.get(HATEOAS_URL);
                return SpringDataRestAdapter.process( deferred ).then(function (data) {
                    Question.resources = data._resources("self");

                    return _.map(data._embeddedItems, function (question) {

                        //var i=0;
                        //angular.forEach(question.answers._embeddedItems, function(answer) {
                        //    question.answers._embeddedItems[i++] = new Answer(answer);
                        //});

                        return new Question(question);
                    });
                });
            };

            Question.resources = null;

            return Question;
        }
    ]
);