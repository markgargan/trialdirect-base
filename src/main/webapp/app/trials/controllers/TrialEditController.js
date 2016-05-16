angular.module('trialdirect').controller('TrialEditController',
    ['$scope', 'Question', 'Answer', 'QuestionnaireEntryResourceService', 'TrialResourceService',
        'trial', 'trialInfo', 'questionnaireEntries', 'trialSelectorQuestionnaireEntries', 'TrialSelectorQuestionnaireEntryResourceService',
        function ($scope, Question, Answer, QuestionnaireEntryResourceService, TrialResourceService, trial, trialInfo,
                  questionnaireEntries, trialSelectorQuestionnaireEntries, TrialSelectorQuestionnaireEntryResourceService) {

            $scope.trial = trial;

            //$scope.currentTrialSite = null;

            $scope.trialInfo = trialInfo;

            // Previously saved TrialInfo objects
            // must have had an image saved along with them
            // or validation wouldn't have passed.
            $scope.trialInfo.hasUploadedImage=true;
            $scope.trialInfo.needsImageUpload=false;

            if (angular.isDefined($scope.trialInfo.trialSites)) {
                angular.forEach($scope.trialInfo.trialSites._embeddedItems, function(trialSite) {
                    trialSite.hasUploadedImage = true;
                    trialSite.needsImageUpload = false;
                });
            }

            $scope.trial.trialInfo = $scope.trialInfo;

            $scope.trialTitle = trial.title;

            $scope.trial.checked = true;

            $scope.questionnaireEntries = questionnaireEntries;

            $scope.isEditing = false;
            $scope.wasSaved = false;

            // Selections loaded upon hitting the page
            $scope.trialSelectorQuestionnaireEntries = trialSelectorQuestionnaireEntries;

            $scope.hidelist = false;

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

            $scope.setEditing = function (isEditing) {
                $scope.trialTitle = $scope.trial.title + (isEditing?' *':'');
                $scope.isEditing = isEditing;
            };

            $scope.resetUploadedImage = function() {
                $scope.trial.trialInfo.trialLogoPic = null;
                $scope.trial.trialInfo.needsImageUpload=true;
                $scope.trial.trialInfo.hasUploadedImage=false;
            };

            $scope.resetUploadedTrialSiteImage = function(trialSite) {
                trialSite.sitePic = null;
                trialSite.needsImageUpload=true;
                trialSite.hasUploadedImage=false;
            };

            $scope.toggleTrialInfoImage = function () {
                $scope.trial.trialInfo.needsImageUpload = !$scope.trial.trialInfo.needsImageUpload;
            };

            $scope.toggleTrialSiteImage = function (trialSite) {
                trialSite.needsImageUpload = !trialSite.needsImageUpload;
            };


            $scope.submitTrialSelections = function () {
                var valid = $scope.validateQuestionnaireEntriesAnswered();

                if (valid) {
                    $scope.saveTrialSelectorQuestionnaireEntries(function() {

                        // Upon successful save turn off editing

                        // Turn off editing
                        $scope.setEditing(false);
                        $scope.wasSaved = true;
                    });
                }
            };

            $scope.saveTrialSelectorQuestionnaireEntries = function (callback) {

                // Delete all the existing trialSelections for the trial.

                // Go through all the trialSelectorQuestionnaireEntries loaded at page load.
                // For each unacceptableAnswer that is no longer represented by the state of the
                // questionnaireEntry/answer combinations we delete it.

                // Then we iterate this time over the questionnaireEntry/answers
                // If we find an unacceptable answer that doesn't exist in the original collection
                // then we know we need to save it and also add it to the original collection.

                // By doing it this way we never encounter conflicts in the db as we'll
                // never add a trialSelectorQuestionnaireEntry duplicate.

                angular.forEach(questionnaireEntries, function(questionnaireEntry){

                    angular.forEach(questionnaireEntry.answers._embeddedItems, function(answer) {

                        var isNewAnswer = true;

                        // If the answer exists in the trialSelectorQuestionnaireEntry collection
                        // and is now considered acceptable then it must be removed
                        angular.forEach(trialSelectorQuestionnaireEntries, function(trialSelectorQuestionnaireEntry){

                            // If the answer was considered unacceptable when the page loaded
                            if (trialSelectorQuestionnaireEntry.answer.id == answer.id) {

                                isNewAnswer = false;
                                // but now is acceptable we need to remove it from the database
                                if (answer.isAcceptable) {
                                    trialSelectorQuestionnaireEntry.remove(function () {
                                        $scope.removeFromTrialSelectorQuestionnaireEntryCollection(trialSelectorQuestionnaireEntry);
                                    });
                                }
                            }
                        }, isNewAnswer);

                        if (isNewAnswer) {
                            if (! answer.isAcceptable ) {
                                new TrialSelectorQuestionnaireEntryResourceService({
                                    question: questionnaireEntry.question.getHrefLink(),
                                    answer: answer.getHrefLink(),
                                    trial: trial.getHrefLink()
                                }).save(function (savedTrialSelectorQuestionnaireEntry) {
                                    // Upon successful persistence
                                    // push into the trialSelectors
                                    $scope.trialSelectorQuestionnaireEntries.unshift(savedTrialSelectorQuestionnaireEntry);
                                });
                            }
                        }
                    });
                });

                callback && callback();
            };


            $scope.validateQuestionnaireEntriesAnswered = function () {

                // Is there at least one answer per question.

                var allQuestionnaireEntriesAnswered = true;
                angular.forEach(questionnaireEntries, function (questionnaireEntry) {
                    var atLeastOneAnswerAcceptable = false;
                    for (var i = 0, len = questionnaireEntry.answers._embeddedItems.length; i < len; i++) {
                        var answer = questionnaireEntry.answers._embeddedItems[i];
                        if (answer.isAcceptable) {
                            atLeastOneAnswerAcceptable = true;
                            break;
                        }
                    }

                    if (!atLeastOneAnswerAcceptable) {
                        questionnaireEntry.errors = 'Please select at least one answer!';
                        allQuestionnaireEntriesAnswered = false;
                    }
                });

                return allQuestionnaireEntriesAnswered;
            };


            $scope.selectAll = function (questionnaireEntry) {

                angular.forEach(questionnaireEntry.answers._embeddedItems, function (answer) {
                    answer.isAcceptable = questionnaireEntry.selectAll;
                });
            };

            $scope.updateQuestionnaireEntrySet = function(questionnaireEntry) {
                $scope.setEditing(true);
                $scope.wasSaved = false;
                questionnaireEntry.selectAll=false;
                questionnaireEntry.errors = null;
            };

            $scope.removeFromTrialSelectorQuestionnaireEntryCollection = function(trialSelectorQuestionnaireEntry) {

                $scope.trialSelectorQuestionnaireEntries.splice(
                    $scope.trialSelectorQuestionnaireEntries.indexOf(trialSelectorQuestionnaireEntry), 1);

            };
        }
    ])
;