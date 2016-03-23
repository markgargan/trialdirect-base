angular.module('trialdirect').controller('TherapeuticAreaEditController',
    ['$scope', 'TherapeuticAreaResourceService',
        function ($scope, TherapeuticAreaResourceService ) {

            $scope.therapeuticArea = therapeuticArea;

            $scope.questionnaireEntries = questionnaireEntries;

            $scope.addTherapeuticArea = function (therapeuticAreaTitle) {
                new TherapeuticAreaResourceService({
                    title: therapeuticAreaTitle
                }).save(function (question) {
                    // Goto the new instance on the far side
                });
                $scope.newTherapeuticArea= "";
            };
        }
    ]);