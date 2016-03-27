angular.module('trialdirect').controller('UserEditController',
    ['$scope', 'Question', 'Answer', 'QuestionnaireEntryResourceService', 'UserResourceService',
        'user', 'therapeuticArea', 'questionnaireEntries', 'userSelectorQuestionnaireEntries',
        'UserSelectorQuestionnaireEntryResourceService',
        function ($scope, Question, Answer, QuestionnaireEntryResourceService, UserResourceService,
                  user, therapeuticArea, questionnaireEntries, userSelectorQuestionnaireEntries,
                  UserSelectorQuestionnaireEntryResourceService) {

            $scope.user = user;

            $scope.therapeuticArea = therapeuticArea;

            $scope.questionnaireEntries = questionnaireEntries;

            $scope.userSelectorQuestionnaireEntries = userSelectorQuestionnaireEntries;

            // Iterate over the userSelectors setting
            // 'answer.isSelected=true' on the answers that correspond

            // Find the corresponding question
            angular.forEach($scope.questionnaireEntries, function (questionnaireEntry) {


                // Set each question in the user to be an unacceptable answer
                // i.e. all unchecked
                angular.forEach(questionnaireEntry.answers._embeddedItems, function (answer) {
                    answer.isSelected = false;
                });

                // Then iterate over the
                angular.forEach($scope.userSelectorQuestionnaireEntries, function (userSelectorEntry) {

                    if (userSelectorEntry.question.id == questionnaireEntry.question.id) {
                        angular.forEach(questionnaireEntry.answers._embeddedItems, function (answer) {
                            if (answer.id == userSelectorEntry.answer.id) {
                                answer.isSelected = true;
                            }
                        });
                    }
                });
            });

            $scope.chooseTherapeuticArea = function(therapeuticArea) {
                newUser.therapeuticArea = therapeuticArea;
            };

            $scope.updateUserSelectorQuestionnaireEntry = function (questionnaireEntry, answer) {

                // Is it to be considered an unacceptable Answer
                // i.e. requires a new UserSelectorQuestionnaireEntry
                if (answer.isSelected) {
                    // Then create the userSelectorQuestionnaireEntry in the database
                    new UserSelectorQuestionnaireEntryResourceService({
                        question: questionnaireEntry.question.getHrefLink(),
                        answer: answer.getHrefLink(),
                        user: user.getHrefLink(),
                        therapeuticArea:therapeuticArea.getHrefLink()
                    }).save(function (savedUserSelectorQuestionnaireEntry) {
                        // Upon successful persistence
                        // push into the userSelectors
                        // Done for consistency only as the userSelectorQuestionnaireEntries aren't bound to anything
                        $scope.userSelectorQuestionnaireEntries.unshift(savedUserSelectorQuestionnaireEntry);
                    });
                } else {
                    // Otherwise the answer is considered acceptable
                    angular.forEach($scope.userSelectorQuestionnaireEntries, function (userSelectorQuestionnaireEntry) {

                        // therefore the userSelectorQuestionnaireEntry must be removed from the database.
                        if (answer.id == userSelectorQuestionnaireEntry.answer.id) {
                            userSelectorQuestionnaireEntry.remove(function () {
                                $scope.userSelectorQuestionnaireEntries.splice(
                                    $scope.userSelectorQuestionnaireEntries.indexOf(userSelectorQuestionnaireEntry), 1);
                            });
                        }
                    });
                }
            };
        }
    ]);