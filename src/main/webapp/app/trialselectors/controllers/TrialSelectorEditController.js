angular.module('trialdirect').controller('TrialSelectorEditController',
    ['$scope', 'Question', 'Answer', 'QuestionnaireEntryResourceService', 'trialSelector', 'questionnaireEntries', 'TrialSelectorResourceService',
        function ($scope, Question, Answer, QuestionnaireEntryResourceService, trialSelector, questionnaireEntries, TrialSelectorResourceService ) {

            $scope.trialSelector = trialSelector;

            $scope.questionnaireEntries = questionnaireEntries;

            $scope.addQuestion = function (questionText) {
                new Question({
                    questionText: questionText
                }).save(function (question) {
                    new QuestionnaireEntryResourceService({
                        question: question.getHrefLink(),
                        trialSelector:trialSelector.getHrefLink()
                    }).save(function (questionnaireEntry) {
                        questionnaireEntry.question = question;
                        $scope.questionnaireEntries.unshift(questionnaireEntry);
                    })
                });
                $scope.newQuestion = "";
            };

            $scope.updateQuestion = function (question) {
                question.save();
            };

            $scope.deleteQuestionnaireEntry = function (questionnaireEntry) {

                questionnaireEntry.removeQEAssociation($scope.trialSelector.id, function () {
                   questionnaireEntry.remove(function () {
                       $scope.questionnaireEntries.splice($scope.questionnaireEntries.indexOf(questionnaireEntry), 1);
                   });
                });
            };

            $scope.addAnswer = function (questionnaireEntry, answerText) {

                //Should the question not have any answers create a place for the _embeddedItems object.
                if (questionnaireEntry.answers && questionnaireEntry.answers._embeddedItems) {

                } else {
                    questionnaireEntry.answers = {};
                    questionnaireEntry.answers._embeddedItems = [];
                }

                new Answer({
                    answerText: answerText
                }).save(function (answer) {

                    // No content returned from link creation hence empty function arguments
                    questionnaireEntry.createAssociation('answers', answer, function () {
                        questionnaireEntry.answers._embeddedItems.unshift(answer);
                    });
                });
                $scope.newAnswer = "";
            };

            $scope.updateAnswer = function (answer) {
                answer.save();
            };

            $scope.deleteAnswer = function (questionnaireEntry, answer) {

                questionnaireEntry.removeAssociation('answers', answer, function () {
                    answer.remove(function () {
                        var answerList = questionnaireEntry.answers._embeddedItems;
                        answerList.splice(answerList.indexOf(answer), 1);
                    });

                });
            };

        }
    ]);