angular.module('traildirect').controller('UserController',
    ['$scope', '$state', 'UserResourceService', 'users', 'therapeuticAreas',
        function ($scope, $state, UserResourceService, users, therapeuticAreas ) {

            $scope.users = users;

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.newUser={};

            $scope.color = {
                name: 'blue'
            };

            $scope.addUser = function (newUser) {
                new UserResourceService({
                    title: newUser.title,
                    therapeuticArea: newUser.therapeuticArea.getHrefLink()
                }).save(function (newUser) {
                    // Goto the new instance on the far side
                    $scope.users.unshift(newUser);
                    $state.go("users.detail", { 'userId': newUser.id});
                });

                $scope.newUser= {};
                $scope.newUser.checked = false;
            };

            $scope.chooseTherapeuticArea = function(therapeuticArea, $event) {
                $scope.newUser.therapeuticArea = therapeuticArea;

            }
        }
    ]);