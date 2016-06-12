angular.module('trialdirect').controller('UserController',
    ['$scope', '$state', 'UserResourceService', 'users', 'therapeuticAreas', 'TherapeuticParentResourceService', 'therapeuticParents',
        function ($scope, $state, UserResourceService, users, therapeuticAreas, TherapeuticParentResourceService, therapeuticParents) {

            $scope.users = users;

            $scope.therapeuticAreas = therapeuticAreas;
            //$scope.therapeuticParents = therapeuticParents;

            //console.log(therapeuticAreas);

            $scope.newUser = {};

            $scope.addUser = function (newUser) {
                new UserResourceService({
                    pseudonym: newUser.pseudonym,
                    therapeuticArea: newUser.therapeuticArea.getHrefLink()
                }).save(function (newUser) {
                    // Goto the new instance on the far side
                    $scope.users.unshift(newUser);
                    $state.go("users.detail", {'userId': newUser.id});
                });

                $scope.newUser = {};

                $scope.reset();
            };

            $scope.reset = function () {

                // Reset the radio buttons
                angular.forEach($scope.therapeuticAreas, function (therapeuticArea) {
                    therapeuticArea.checked = false;
                });
            };

            $scope.reset();

            $scope.chooseTherapeuticArea = function (therapeuticArea) {
                $scope.newUser.therapeuticArea = therapeuticArea;
            };

        }
    ]);