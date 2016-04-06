angular.module('trialdirect').controller('TrialController',
    ['$scope', '$state', 'TrialResourceService', 'TrialInfo', 'trials',
        'therapeuticAreas',
        function ($scope, $state, TrialResourceService, TrialInfo, trials, therapeuticAreas ) {

            $scope.trials = trials;

            $scope.newTrial={};

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.wasSaved = false;

            $scope.addTrial = function (newTrial) {
                new TrialResourceService({
                    title: newTrial.title,
                    therapeuticArea: newTrial.therapeuticArea.getHrefLink()
                }).save(function (savedTrial) {
                    // Goto the new instance on the far side
                    $scope.trials.unshift(savedTrial);
                    $scope.newTrial = savedTrial;

                    //new TrialInfo({
                    //    trial: $scope.newTrial.getHrefLink()
                    //}).save(function(savedTrialInfo) {
                    //    console.log("Saved Trial Info " + savedTrialInfo.id);
                    //
                    //    savedTrialInfo.picFile={};
                    //    $scope.newTrial.trialInfo = new TrialInfo(savedTrialInfo);
                    //});

                    $scope.wasSaved = true;
                });

                $scope.reset();
            };

            $scope.initializeTrialSite = function() {

                var trialInfo = $scope.newTrial.trialInfo;

                if (!trialInfo.trialSites) {
                    trialInfo.trialSites = [];
                }

                var newTrialSite = {};
                trialInfo.trialSites.unshift(newTrialSite);
            };

            $scope.reset = function() {

                // Reset the radio buttons
                angular.forEach($scope.therapeuticAreas, function(therapeuticArea) {
                    therapeuticArea.checked=false;
                });

                $scope.wasSaved = false;
            };

            $scope.reset();

            $scope.chooseTherapeuticArea = function(therapeuticArea) {
                $scope.newTrial.therapeuticArea = therapeuticArea;
            };
        }
    ]);