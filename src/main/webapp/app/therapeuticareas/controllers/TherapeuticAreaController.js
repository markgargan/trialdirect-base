angular.module('trialdirect').controller('TherapeuticAreaController',
    ['$scope', '$state', 'TherapeuticAreaResourceService', 'therapeuticAreas', 'TherapeuticParentResourceService', 'therapeuticParents',
        function ($scope, $state, TherapeuticAreaResourceService, therapeuticAreas, TherapeuticParentResourceService, therapeuticParents) {

            $scope.therapeuticAreas = therapeuticAreas;
            $scope.therapeuticParents = therapeuticParents;

            $scope.addTherapeuticArea = function (therapeuticAreaTitle, therapeuticParentId) {

                console.log(therapeuticAreaTitle +' - id:'+ therapeuticParentId);

                new TherapeuticAreaResourceService({
                    title: therapeuticAreaTitle,
                    therapeuticparent: 'http://localhost:8080/api/therapeuticparent/'+ therapeuticParentId
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