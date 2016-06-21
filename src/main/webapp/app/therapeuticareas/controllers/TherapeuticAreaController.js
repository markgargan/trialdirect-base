angular.module('trialdirect').controller('TherapeuticAreaController',
    ['$scope', '$state', 'TherapeuticAreaResourceService', 'therapeuticAreas', 'TherapeuticParentResourceService', 'therapeuticParents',
        function ($scope, $state, TherapeuticAreaResourceService, therapeuticAreas, TherapeuticParentResourceService, therapeuticParents) {

            $scope.therapeuticAreas = therapeuticAreas;
            $scope.therapeuticParents = therapeuticParents;
            $scope.newTherapeuticParent = {};

            $scope.addTherapeuticArea = function (therapeuticAreaTitle, therapeuticParentId) {

                console.log(therapeuticAreaTitle +' - id:'+ therapeuticParentId);

                new TherapeuticAreaResourceService({
                    title: therapeuticAreaTitle,
                    therapeuticparent: './api/therapeuticparent/'+ therapeuticParentId
                }).save(function (newTherapeuticArea) {

                    $scope.therapeuticAreas.unshift(newTherapeuticArea);

                    $state.go("therapeuticAreas.detail", {'therapeuticAreaId': newTherapeuticArea.id});
                });

                $scope.newTherapeuticArea = "";
            };


            $scope.addTherapeuticParent = function (therapeuticParentTitle) {

                new TherapeuticParentResourceService({
                    title: therapeuticParentTitle
                }).save(function (newTherapeuticParent) {

                    $scope.therapeuticParents.unshift(newTherapeuticParent);

                    //$state.go("newTherapeuticParents.detail", {'therapeuticParentId': newTherapeuticParent.id});

                });

                $scope.newTherapeuticParent = "";
            };

        }
    ]);