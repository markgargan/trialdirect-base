angular.module('trialdirect').controller('UserEditController',
    ['$scope', '$http', '$sce', 'Question', 'Answer', 'QuestionnaireEntryResourceService', 'UserResourceService',
        'user', 'questionnaireEntries', 'userSelectorQuestionnaireEntries',
        'UserSelectorQuestionnaireEntryResourceService', 'TrialService', 'TrialResourceService',
        function ($scope, $http, $sce, Question, Answer, QuestionnaireEntryResourceService, UserResourceService,
                  user, questionnaireEntries, userSelectorQuestionnaireEntries,
                  UserSelectorQuestionnaireEntryResourceService, TrialService, TrialResourceService) {

            $scope.user = user;

            $scope.questionnaireEntries = questionnaireEntries;

            $scope.userSelectorQuestionnaireEntries = userSelectorQuestionnaireEntries;

            $scope.trialInfos = [];

            //
            $scope.getGoogleMap = function (name, loc) {

                /*
                var url = 'http://maps.google.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA';
                $http.get(url).success(function(data){
                    console.log(data);
                });
                */

                // build map
                var map =
                    '<iframe width="100%" height="250" frameborder="0" style="border:0"'
                    + ' src="https://www.google.com/maps/embed/v1/place'
                    + '?key=AIzaSyDPnVez_E7pNgjNPxNKWqy8tTsnlFpKJaw'
                    + '&q='
                    + name.replace(/\s+/g, '+') + ','
                    + loc.address1.replace(/\s+/g, '+') + ','
                    + loc.address5.replace(/\s+/g, '+') + ','
                    + loc.country.replace(/\s+/g, '+')
                    + '"></iframe>';

                //console.log(name, loc);

                // return info as HTML code
                return $sce.trustAsHtml(map);
            }

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
                                // Set the users's selection for this questionnaireEntry
                                questionnaireEntry.userSelection = userSelectorEntry;
                            }
                        });
                    }
                });
            });

            $scope.chooseTherapeuticArea = function (therapeuticArea) {
                newUser.therapeuticArea = therapeuticArea;
            };

            $scope.updateAvailableTrials = function () {
                TrialService.getAvailableTrialsCount($scope.user.id, $scope.user.therapeuticArea.id, function (availableTrialIds) {
                    $scope.availableTrialIds = availableTrialIds;

                    $scope.trialInfos = [];

                    if (availableTrialIds.length < 50) {
                        angular.forEach(availableTrialIds, function (availableTrialId) {
                            TrialResourceService.loadTrialInfo(availableTrialId).then(function (trialInfos) {
                                if (!angular.isUndefined(trialInfos)) {
                                    $scope.trialInfos.push(trialInfos);
                                }
                            });
                        });
                    }
                });
            };


            // Initialise the trial count
            $scope.updateAvailableTrials();


            $scope.chooseTrialData = function (trialData) {
                $scope.trialData = trialData;
            };


            $scope.updateUserSelectorQuestionnaireEntry = function (questionnaireEntry, answer) {

                // User clicks a button
                // Need to remove previous selection
                // Need to save new selection.
                if (!questionnaireEntry.userSelection) {
                    console.log("No previous selection");
                    // Then remove the old selection
                    new UserSelectorQuestionnaireEntryResourceService({
                        question: questionnaireEntry.question.getHrefLink(),
                        answer: answer.getHrefLink(),
                        user: user.getHrefLink(),
                        therapeuticArea: user.therapeuticArea.getHrefLink()
                    }).save(function (savedUserSelectorQuestionnaireEntry) {
                        $scope.userSelectorQuestionnaireEntries.unshift(savedUserSelectorQuestionnaireEntry);
                        questionnaireEntry.userSelection = savedUserSelectorQuestionnaireEntry;
                        answer.isSelected = true;
                        $scope.updateAvailableTrials();
                    });
                } else {

                    //console.log("Was a previous selection");
                    questionnaireEntry.userSelection.remove(function () {

                        //console.log("Removed old selection");

                        // Unselecting the same button
                        if (questionnaireEntry.userSelection.answer.id != answer.id) {
                            //console.log("Different new selection");

                            // This answer is being unselected so don't resave
                            new UserSelectorQuestionnaireEntryResourceService({
                                question: questionnaireEntry.question.getHrefLink(),
                                answer: answer.getHrefLink(),
                                user: user.getHrefLink(),
                                therapeuticArea: user.therapeuticArea.getHrefLink()
                            }).save(function (savedUserSelectorQuestionnaireEntry) {

                                // Update the avialableTrials
                                $scope.updateAvailableTrials(user.id);

                                $scope.userSelectorQuestionnaireEntries.unshift(savedUserSelectorQuestionnaireEntry);
                                $scope.userSelectorQuestionnaireEntries.splice(
                                    $scope.userSelectorQuestionnaireEntries.indexOf(questionnaireEntry.userSelection), 1);

                                // unclick the previously selected answer
                                // not performed with angular.forEach as break; currently
                                // doesn't work correctly.
                                for (var i = 0, len = questionnaireEntry.answers._embeddedItems.length; i < len; i++) {
                                    var previouslySelectedAnswer = questionnaireEntry.answers._embeddedItems[i];
                                    if (questionnaireEntry.userSelection.answer.id == previouslySelectedAnswer.id) {
                                        previouslySelectedAnswer.isSelected = false;
                                        questionnaireEntry.userSelection = savedUserSelectorQuestionnaireEntry;
                                        break;
                                    }
                                }
                            });
                        } else {
                            //console.log("Unselecting old selection");
                            questionnaireEntry.userSelection = null;
                            $scope.updateAvailableTrials();
                        }
                    });
                }
            };

            $scope.toggleBio = function (site, trialData) {
                var bioIsShowing = site.showBio;

                if (bioIsShowing) {
                    site.showBio = !site.showBio;
                } else {
                    site.showBio = true;
                    angular.forEach(trialData.trialSites._embeddedItems, function (otherSite) {
                        if (otherSite.id != site.id) {
                            otherSite.showBio = false;
                        }
                    });
                }
            };
        }
    ]).filter('newlines', function () {
    return function (text) {
        if (!text)
            return '';

        return text.split('\n');
    }
});