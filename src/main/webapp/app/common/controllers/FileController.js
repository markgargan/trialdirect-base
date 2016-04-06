angular.module('fileUpload', ['ngFileUpload']).controller('FileController',
    ['$scope', 'Upload', '$timeout', function ($scope, Upload, $timeout) {
        $scope.uploadPic = function (file, trial) {
            file.upload = Upload.upload({
                url: '/uploadTrialLogo',
                data: {
                    description: trial.trialInfo.description,
                    trialId:trial.id,
                    file: file
                },
            });

            file.upload.then(function (response) {
                $timeout(function () {
                    file.result = response.data;
                });
            }, function (response) {
                if (response.status > 0)
                    $scope.errorMsg = response.status + ': ' + response.data;
            }, function (evt) {
                // Math.min is to fix IE which reports 200% sometimes
                file.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
            });
        };

        $scope.calculateThumbnailSize = function(file) {

            if (!file)
                return;
            var width = file.getWidth();
            var height = file.getHeight();


            return 'width:'+ width + ';height' + height;
        };

    }]);