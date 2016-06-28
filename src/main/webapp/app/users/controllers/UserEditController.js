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


            /*$scope.getGooglePos = function() {
             $http.get('http://maps.google.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA')
             .then(function (response) {
             console.log(response.data);
             });
             };*/


            $scope.mapLatitude = 'Lat';
            $scope.mapLongtitude = 'Lng';

            // sample doctor zip code and  (for test)
            $scope.docZip = 'IL 60611';
            $scope.docLongtitude = -87.61616959999999;
            $scope.docLatitude = 41.8925085;

            $scope.getGooglePos = function (name, loc) {

                var q =
                    name.replace(/\s+/g, '+') + ','
                    + loc.address1.replace(/\s+/g, '+') + ','
                    + loc.address5.replace(/\s+/g, '+') + ','
                    + loc.country.replace(/\s+/g, '+');

                $http.get('http://maps.google.com/maps/api/geocode/json?address=' + q)
                    .then(function(response) {

                        if (response) {
                            $scope.mapLatitude = response.data.results[0].geometry.location.lat;
                            $scope.mapLongtitude = response.data.results[0].geometry.location.lng;

                            console.log(response.data.results[0].geometry.location);
                        }

                    });
            };

            //
            $scope.getGoogleMap = function (name, loc) {

                //$scope.cords = getGooglePos();

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
            };



            //:::       Definitions:                                                           :::
            //:::    South latitudes are negative, east longitudes are positive           :::
            //:::                                                                         :::
            //:::  Passed to function:                                                    :::
            //:::    lat1, lon1 = Latitude and Longitude of point 1 (in decimal degrees)  :::
            //:::    lat2, lon2 = Latitude and Longitude of point 2 (in decimal degrees)  :::
            //:::    unit = the unit you desire for results                               :::
            //:::           where: 'M' is statute miles (default)                         :::
            //:::                  'K' is kilometers                                      :::
            //:::                  'N' is nautical miles                                  :::

            $scope.calcDistance = function(lat1, lon1, lat2, lon2, unit) {

                //console.log(lat1, lon1, lat2, lon2, unit);

                var radlat1 = Math.PI * lat1/180;
                var radlat2 = Math.PI * lat2/180;
                var theta = lon1-lon2;
                var radtheta = Math.PI * theta/180;
                var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
                dist = Math.acos(dist);
                dist = dist * 180/Math.PI;
                dist = dist * 60 * 1.1515;
                if (unit=="K") { dist = dist * 1.609344 }
                if (unit=="N") { dist = dist * 0.8684 }

                //console.log(dist.toFixed(0));

                return dist.toFixed(0);
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