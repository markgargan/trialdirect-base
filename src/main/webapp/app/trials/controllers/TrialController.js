angular.module('trialdirect').controller('TrialController',
    ['$scope', '$state', 'TrialResourceService', 'trials', 'therapeuticAreas',
        function ($scope, $state, TrialResourceService, trials, therapeuticAreas ) {

            $scope.trials = trials;

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.newTrial={};

            $scope.color = {
                name: 'blue'
            };

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
                $scope.newTrial.checked = false;
            };

            $scope.chooseTherapeuticArea = function(therapeuticArea, $event) {
                $scope.newTrial.therapeuticArea = therapeuticArea;

                //if ($scope.newTrial.checked == event.target.value)
                //    $scope.newTrial.checked = true;

            }
        }
    ]);