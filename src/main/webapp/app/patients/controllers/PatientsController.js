angular.module('trialdirect').controller('MyPatientsController', function ($scope, $http){

    $http.get('./assets/json/myPatients.json').success(function(data) {
        $scope.d = data;
    });

});