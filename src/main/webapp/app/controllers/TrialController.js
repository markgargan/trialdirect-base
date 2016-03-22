angular.module('trialdirect').controller('TrialController',
    ['$scope', 'TrialResourceService', 'trials',
        function ($scope, TrialResourceService, trials) {

            $scope.trials = trials;

            $scope.addTrial = function (newTrial) {
                new TrialResourceService({
                    title: newTrial.title
                }).save(function (savedTrial) {
                    $scope.trials.unshift(savedTrial);
                });
                $scope.newTrial = "";
            };

            $scope.updateTrial = function (trial) {
                trial.save();
            };

            $scope.deleteTrial = function (trial) {
                trial.remove(function () {
                    $scope.trials.splice($scope.trials.indexOf(trial), 1);
                });
            };
        }
    ]);