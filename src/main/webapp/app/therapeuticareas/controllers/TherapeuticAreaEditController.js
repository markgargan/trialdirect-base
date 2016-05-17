angular.module('trialdirect').controller('TherapeuticAreaEditController',
    ['$scope', 'Question', 'Answer', 'QuestionnaireEntryResourceService', 'therapeuticArea', 'questionnaireEntries',
        function ($scope, Question, Answer, QuestionnaireEntryResourceService, therapeuticArea, questionnaireEntries) {


            $scope.sortableOptions = {
                containment: '#sortable-container',
                accept: function (sourceItemHandleScope, destSortableScope) {
                    return sourceItemHandleScope.itemScope.sortableScope.$id === destSortableScope.$id;
                },
                orderChanged: function(event) {
                    console.log('Change');
                }
            };

            $scope.sortableOptions = {
                containment: '#sortable-container',
                accept: function (sourceItemHandleScope, destSortableScope) {
                    return sourceItemHandleScope.itemScope.sortableScope.$id === destSortableScope.$id;
                },
                orderChanged: function(event) {
                    //console.log('Change');
                    console.log('Change Question: ' + event.source.index);

                    var sortableItems = event.source.sortableScope.modelValue;
                    angular.forEach(sortableItems, function(item, key){
                        console.log(key +' / '+ item.question.id +' / '+ item.question.questionText +' ::'+ item.question._links.self.href);
                    });

                }
            };

            $scope.sortableOptionsHorizontal = {
                containment: '#horizontal-container',
                // restrict movement to within containment div
                accept: function (sourceItemHandleScope, destSortableScope) {
                    return sourceItemHandleScope.itemScope.sortableScope.$id === destSortableScope.$id;
                },
                orderChanged: function(event) {
                    console.log('Change answers: ' + event.source.index);

                    var sortableItems = event.source.sortableScope.modelValue;
                    angular.forEach(sortableItems, function(item, key){
                        console.log(key +' / '+ item.id +' / '+ item.answerText +' ::'+ item._links.self.href);
                    });

                }
            };

            $scope.therapeuticArea = therapeuticArea;

            $scope.questionnaireEntries = questionnaireEntries;


            /*$scope.updateSortOrder = function (sortOrderData, sortOrderNo) {
                new QuestionnaireEntryResourceService(
                    question: sortOrderData.getHrefLink(),
                ).save(function (question) {

                    new QuestionnaireEntryResourceService({
                        question: question.getHrefLink(),
                        therapeuticArea: therapeuticArea.getHrefLink()
                    }).save(function (questionnaireEntry) {
                        questionnaireEntry.question = question;
                        $scope.questionnaireEntries.unshift(questionnaireEntry);
                    })

                })
            };
            */


            $scope.updateSortOrder = function (question, sortOrderNo) {
               console.log($scope.questionnaireEntries);
            };


            $scope.addQuestion = function (questionText) {
                new Question({
                    questionText: questionText
                }).save(function (question) {
                    new QuestionnaireEntryResourceService({
                        question: question.getHrefLink(),
                        therapeuticArea: therapeuticArea.getHrefLink()
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

                questionnaireEntry.removeQEAssociation($scope.therapeuticArea.id, function () {
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
                    // We don't remove the answer from the database
                    // but we need to propagate the delete from the trialSelector & userSelector tables
                    // when the services are in place.
                    var answerList = questionnaireEntry.answers._embeddedItems;
                    answerList.splice(answerList.indexOf(answer), 1);

                });
            };

        }
    ]);