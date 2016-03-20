angular.module('trialdirect').controller('QuestionController',
    ['$scope', 'Question', 'Answer', 'quests',  function ($scope, Question, Answer, quests) {

        $scope.questions = quests;

        $scope.addQuestion = function (questionText) {
            new Question({
                questionText: questionText
            }).save(function (question) {
                question.answers= {};
                question.answers._embeddedItems= [];
                $scope.questions.unshift(question);
            });
            $scope.newQuestion = "";
        };

        $scope.updateQuestion = function (question) {
            question.save();
        };

        $scope.deleteQuestion = function (question) {
            question.remove(function () {
                $scope.questions.splice($scope.questions.indexOf(question), 1);
            });
        };

        $scope.addAnswer =  function (question, answerText) {
            new Answer({
                answerText: answerText,
                question: question._links.self.href
            }).save(function (answer) {
                question.answers._embeddedItems.unshift(answer);
            });
            $scope.newAnswer = "";
        };

        $scope.updateAnswer = function (answer) {
            answer.save();
        };

        $scope.deleteAnswer= function (question, answer) {
            answer.remove(function () {
                var answerList = question.answers._embeddedItems;
                answerList.splice(answerList.indexOf(answer), 1);
            });
        };

        $scope.getOrder = function(entity, entityType) {

        }

    }
]);