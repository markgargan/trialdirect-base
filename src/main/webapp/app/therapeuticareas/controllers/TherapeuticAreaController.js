angular.module('trialdirect').controller('TherapeuticAreaController',
    ['$scope', '$state', 'TherapeuticAreaResourceService', 'therapeuticAreas',
        function ($scope, $state, TherapeuticAreaResourceService, therapeuticAreas ) {

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.addTherapeuticArea = function (therapeuticAreaTitle) {
                new TherapeuticAreaResourceService({
                    title: therapeuticAreaTitle
                }).save(function (newTherapeuticArea) {
                    // Goto the new instance on the far side
                    $state.go("therapeuticAreas.detail", { 'therapeuticAreaId': newTherapeuticArea.id});
                });

                $scope.newTherapeuticArea= "";
            };
        }
    ]);