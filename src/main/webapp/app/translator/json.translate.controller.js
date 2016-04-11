angular.module('formlyApp')
    .controller('JsonTranslateController', ['$scope', '$http', 'JsonTranslateService',
        function ($scope, $http, JsonTranslateService) {

            // The model object that we reference
            // on the  element in index.html

            $scope.model= {};
            $scope.json = {};

            $scope.flattenedJsonMap = [{
                key: 'first_name',
                type: 'input',
                templateOptions: {
                    type: 'text',
                    label: 'First Name',
                    placeholder: 'Enter your first name',
                    required: true
                }
            }];

            $scope.$watch(function(){
                return JsonTranslateService.getJsonObject();
            }, function (newVal, oldVal) {
                if (newVal !== oldVal) {
                    try {
                        $scope.error = null;
                        $scope.flattenedJsonMap = $scope.flattenJson(newVal, '', []);

                        angular.forEach($scope.flattenedJsonMap, function(value, key) {
                            console.log(key +' : '+ value);
                        });
                    } catch (e) {
                        console.log(e);
                        alert(e);
                        $scope.error = {
                            message: e.message,
                            show: true
                        }
                    }
                }
            }, true);

            $scope.flattenJson = function (node, currentK, flattenedMap) {

                angular.forEach(node, function (value, key) {
                    var currentKey = currentK == '' ? ''+ key : currentK + '_' + key;

                    // Is an object
                    if (angular.isObject(value)) {
                        $scope.flattenJson(value, currentKey, this);
                    }
                    // is a string
                    else if (angular.isString(value)){
                        this.push({
                            key: currentKey,
                            type: 'input',
                            templateOptions: {
                                type: 'text',
                                //label: currentKey,
                                placeholder: currentKey,
                                required: true
                            }
                        });
                    }
                }, flattenedMap);

                return flattenedMap;
            };

        }]);
