angular.module('trialdirect').controller('TherapeuticAreaController',
    ['$scope', 'TherapeuticAreaResourceService', 'therapeuticAreas',
        function ($scope, TherapeuticAreaResourceService, therapeuticAreas) {

        $scope.therapeuticAreas = therapeuticAreas;

        $scope.addTherapeuticArea = function (newTherapeuticArea) {
            new TherapeuticAreaResourceService({
                name: newTherapeuticArea.name
            }).save(function (savedTherapeuticArea) {
                $scope.therapeuticAreas.unshift(savedTherapeuticArea);
            });
            $scope.newTherapeuticArea = "";
        };

        $scope.updateTherapeuticArea = function (therapeuticArea) {
            therapeuticArea.save();
        };

        $scope.deleteTherapeuticArea = function (therapeuticArea) {
            therapeuticArea.remove(function () {
                $scope.therapeuticAreas.splice($scope.therapeuticAreas.indexOf(therapeuticArea), 1);
            });
        };
    }
]);