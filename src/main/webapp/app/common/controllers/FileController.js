angular.module('fileUpload', ['ngFileUpload']).controller('FileController',
    ['$scope', 'Upload', '$timeout', function ($scope, Upload, $timeout) {

        $scope.editingTrialInfo = true;

        $scope.createData = function(trial) {

            var data = {
                description: trial.trialInfo.description,
                    trialId:trial.id,
                file: trial.trialInfo.trialLogoPic
            };

            $scope.createTrialSites(trial, data);

            return data;
        };

        $scope.createTrialSites = function(trial, data) {

            data.trialSites = [];

            angular.forEach(trial.trialInfo.trialSites, function(trialSite){

                var trialSite = {
                    description:trialSite.description,
                    trialSiteFile:trialSite.sitePic,
                    siteDirector:trialSite.siteDirector,
                    siteSummary:trialSite.siteSummary,
                    siteDescription:trialSite.siteDescription,
                    siteMap:trialSite.siteMap
                };

                data.trialSites.unshift(trialSite);
            }, data);

        };

        $scope.uploadTrial = function (file, trial) {

            file.upload = Upload.upload({
                url: '/uploadTrialInfo',
                data: $scope.createData(trial),
                objectKey: '.k',
                arrayKey : '[i]'
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


        $scope.toggleTrialInfoDisplay = function () {
            $scope.editingTrialInfo = angular.element(document.querySelectorAll("[data-td-id='trialInformationEditor']")).hasClass('ng-hide');
        };

        //$scope.toggleTrialSiteDisplay = function (trialSiteEditorIndex) {
        //    trialSite.isEditing = angular.element(
        //        document.querySelectorAll("[data-td-id='trialSiteEditor"+trialSiteEditorIndex+"']")).hasClass('ng-hide');
        //};

        $scope.toggleTrialSiteDisplay = function (trialSite) {
            trialSite.isEditing = !trialSite.isEditing;
        };
    }]);