angular.module('trialdirect').controller('TrialController',
    ['$scope', '$state', 'TrialResourceService', 'trials',
        'therapeuticAreas',
        function ($scope, $state, TrialResourceService, trials, therapeuticAreas ) {

            $scope.trials = trials;

            $scope.newTrial={};

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.addTrial = function (newTrial) {
                new TrialResourceService({
                    title: newTrial.title,
                    therapeuticArea: newTrial.therapeuticArea.getHrefLink()
                }).save(function (newTrial) {
                    // Goto the new instance on the far side
                    $scope.trials.unshift(newTrial);
                    $state.go("trials.detail", { 'trialId': newTrial.id});
                });

                $scope.newTrial= {};
                $scope.reset();
            };

            $scope.reset = function() {

                // Reset the radio buttons
                angular.forEach($scope.therapeuticAreas, function(therapeuticArea) {
                    therapeuticArea.checked=false;
                });
            };

            $scope.reset();

            $scope.chooseTherapeuticArea = function(therapeuticArea) {
                $scope.newTrial.therapeuticArea = therapeuticArea;
            };
        }
    ]);