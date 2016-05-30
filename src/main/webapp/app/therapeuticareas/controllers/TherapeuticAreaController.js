angular.module('trialdirect').controller('TherapeuticAreaController',
    ['$scope', '$state', 'TherapeuticAreaResourceService', 'therapeuticAreas',
        function ($scope, $state, TherapeuticAreaResourceService, therapeuticAreas) {

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.addTherapeuticArea = function (therapeuticAreaTitle, therapeuticParentId) {

                console.log(therapeuticAreaTitle +' - id:'+ therapeuticParentId);

                new TherapeuticAreaResourceService({
                    title: therapeuticAreaTitle,
                    therapeuticparent: 'http://localhost:8080/api/therapeuticparent/1'
                }).save(function (newTherapeuticArea) {

                    $scope.therapeuticAreas.unshift(newTherapeuticArea);

                    $state.go("therapeuticAreas.detail", {'therapeuticAreaId': newTherapeuticArea.id});
                });

                $scope.newTherapeuticArea = "";
            };
        }
    ]);