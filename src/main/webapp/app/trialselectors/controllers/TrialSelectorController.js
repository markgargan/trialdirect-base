angular.module('trialdirect').controller('TrialSelectorController',
    ['$scope', '$state', 'TrialSelectorResourceService', 'trialSelectors',
        function ($scope, $state, TrialSelectorResourceService, trialSelectors ) {

            $scope.trialSelectors = trialSelectors;

            $scope.addTrialSelector = function (trialSelectorTitle) {
                new TrialSelectorResourceService({
                    title: trialSelectorTitle
                }).save(function (newTrialSelector) {
                    // Goto the new instance on the far side
                    $state.go("trialSelectors.detail", { 'trialSelectorId': newTrialSelector.id});
                });

                $scope.newTrialSelector= "";
            };
        }
    ]);