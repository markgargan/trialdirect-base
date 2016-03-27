angular.module('trialdirect').controller('TherapeuticAreaController',
    ['$scope', '$state', 'TherapeuticAreaResourceService', 'therapeuticAreas',
        function ($scope, $state, TherapeuticAreaResourceService, therapeuticAreas ) {

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.addTherapeuticArea = function (therapeuticAreaTitle) {
                new TherapeuticAreaResourceService({
                    title: therapeuticAreaTitle
                }).save(function (newTherapeuticArea) {
                    $scope.therapeuticAreas.unshift(newTherapeuticArea);
                    
                    $state.go("therapeuticAreas.detail", { 'therapeuticAreaId': newTherapeuticArea.id});
                });

                $scope.newTherapeuticArea= "";
            };
        }
    ]);