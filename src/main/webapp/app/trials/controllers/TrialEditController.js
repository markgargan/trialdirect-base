angular.module('trialdirect').controller('TrialEditController',
    ['$scope', 'Question', 'Answer', 'QuestionnaireEntryResourceService', 'TrialResourceService',
        'trial', 'questionnaireEntries', 'trialSelectorQuestionnaireEntries', 'TrialSelectorQuestionnaireEntryResourceService',
        function ($scope, Question, Answer, QuestionnaireEntryResourceService, TrialResourceService, trial,
                  questionnaireEntries, trialSelectorQuestionnaireEntries, TrialSelectorQuestionnaireEntryResourceService) {

            $scope.trial = trial;

            $scope.questionnaireEntries = questionnaireEntries;

            $scope.trialSelectorQuestionnaireEntries = trialSelectorQuestionnaireEntries;

            // Iterate over the trialSelectors setting
            // 'answer.isAcceptable=true' on the answers that correspond

            // Find the corresponding question
            angular.forEach($scope.questionnaireEntries, function (questionnaireEntry) {


                // Set each question in the trial to be an unacceptable answer
                // i.e. all unchecked
                angular.forEach(questionnaireEntry.answers._embeddedItems, function (answer) {
                    answer.isAcceptable = true;
                });

                // Then iterate over the
                angular.forEach($scope.trialSelectorQuestionnaireEntries, function (trialSelectorEntry) {

                    if (trialSelectorEntry.question.id == questionnaireEntry.question.id) {
                        angular.forEach(questionnaireEntry.answers._embeddedItems, function (answer) {
                            if (answer.id == trialSelectorEntry.answer.id) {
                                answer.isAcceptable = false;
                            }
                        });
                    }
                });
            });

            $scope.chooseTherapeuticArea = function(therapeuticArea) {
                newTrial.therapeuticArea = therapeuticArea;
                //therapeuticArea.isSelected=true;
            };

            $scope.updateTrialSelectorQuestionnaireEntry = function (questionnaireEntry, answer) {

                // Is it to be considered an unacceptable Answer
                // i.e. requires a new TrialSelectorQuestionnaireEntry
                if (!answer.isAcceptable) {
                    // Then create the trialSelectorQuestionnaireEntry in the database
                    new TrialSelectorQuestionnaireEntryResourceService({
                        question: questionnaireEntry.question.getHrefLink(),
                        answer: answer.getHrefLink(),
                        trial: trial.getHrefLink()
                    }).save(function (savedTrialSelectorQuestionnaireEntry) {
                        // Upon successful persistence
                        // push into the trialSelectors
                        // Done for consistency only as the trialSelectorQuestionnaireEntries aren't bound to anything
                        $scope.trialSelectorQuestionnaireEntries.unshift(savedTrialSelectorQuestionnaireEntry);
                    });
                } else {
                    // Otherwise the answer is considered acceptable
                    angular.forEach($scope.trialSelectorQuestionnaireEntries, function (trialSelectorQuestionnaireEntry) {

                        // therefore the trialSelectorQuestionnaireEntry must be removed from the database.
                        if (answer.id == trialSelectorQuestionnaireEntry.answer.id) {
                            trialSelectorQuestionnaireEntry.remove(function () {
                                $scope.trialSelectorQuestionnaireEntries.splice(
                                    $scope.trialSelectorQuestionnaireEntries.indexOf(trialSelectorQuestionnaireEntry), 1);
                            });
                        }
                    });
                }
            };
        }
    ]);