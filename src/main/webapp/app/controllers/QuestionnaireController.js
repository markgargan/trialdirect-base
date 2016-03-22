angular.module('trialdirect').controller('QuestionnaireController',
    ['$scope', 'Question', 'Answer', 'QuestionnaireEntryResourceService', 'questionnaireEntries',
        function ($scope, Question, Answer, QuestionnaireEntryResourceService, questionnaireEntries) {

        $scope.questionnaireEntries = questionnaireEntries;

        $scope.addQuestion = function (questionText) {
            new Question({
                questionText: questionText
            }).save(function (question) {
                new QuestionnaireEntryResourceService({
                    question:question.getHrefLink()
                }).save(function(questionnaireEntry){
                    $scope.questionnaireEntries.unshift(questionnaireEntry);
                })
            });
            $scope.newQuestion = "";
        };

        $scope.updateQuestion = function (question) {
            question.save();
        };

        $scope.deleteQuestion = function (questionnaireEntry) {
            questionnaireEntry.remove(function () {
                questionnaireEntry.question.remove(function(){
                    $scope.questionnaireEntries.splice($scope.questionnaireEntries.indexOf(questionnaireEntry), 1);
                });
            });
        };

        $scope.addAnswer = function (questionnaireEntry, answerText) {

            //Should the question not have any answers create a place for the _embeddedItems object.
            if (questionnaireEntry.answers && questionnaireEntry.answers._embeddedItems) {

            } else {
                questionnaireEntry.answers={};
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

        $scope.getOrder = function(entity, entityType) {

        }

    }
]);