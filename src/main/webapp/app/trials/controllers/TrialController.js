angular.module('trialdirect').controller('TrialController',
    ['$scope', '$state', '$timeout', 'Upload', 'TrialResourceService', 'TrialInfo', 'trials',
        'therapeuticAreas',
        function ($scope, $state, $timeout, Upload, TrialResourceService, TrialInfo, trials, therapeuticAreas ) {

            $scope.trials = trials;

            $scope.newTrial={};

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.wasSaved = false;

            $scope.editingTrialInfo = true;

            $scope.addTrial = function (newTrial, callback) {
                new TrialResourceService({
                    title: newTrial.title,
                    therapeuticArea: newTrial.therapeuticArea.getHrefLink()
                }).save(function (savedTrial) {
                    // Goto the new instance on the far side
                    $scope.trials.unshift(savedTrial);

                    // Update the savedTrial with the trialInfo to be saved.
                    savedTrial.trialInfo = $scope.newTrial.trialInfo;
                    savedTrial.therapeuticArea= $scope.newTrial.therapeuticArea;
                    $scope.newTrial = savedTrial;

                    callback && callback($scope.newTrial);

                    $scope.wasSaved = true;
                });

                $scope.reset();
            };

            $scope.initializeTrialSite = function() {

                var trialInfo = $scope.newTrial.trialInfo;

                if (!trialInfo.trialSites) {
                    trialInfo.trialSites = [];
                }

                var newTrialSite = {
                    isEditing : true
                };

                trialInfo.trialSites.unshift(newTrialSite);
            };

            $scope.reset = function() {

                // Reset the radio buttons
                angular.forEach($scope.therapeuticAreas, function(therapeuticArea) {
                    therapeuticArea.checked=false;
                });

                $scope.wasSaved = false;
            };

            $scope.reset();

            $scope.chooseTherapeuticArea = function(therapeuticArea) {
                $scope.newTrial.therapeuticArea = therapeuticArea;
            };

            $scope.createTrialInfo = function(trial) {

                var data = {
                    description: trial.trialInfo.description,
                    trialId:trial.id,
                    trialInfoLogo: trial.trialInfo.trialLogoPic
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

                // Firstly create the Trial Object
                $scope.addTrial(trial, function(savedTrial) {

                    file.upload = Upload.upload({
                        url: '/uploadTrialInfo',
                        data: $scope.createTrialInfo(savedTrial),
                        objectKey: '.k',
                        arrayKey: '[i]'
                    });

                    file.upload.then(function (response) {
                        // timeout prevents this from running within the digest cycle
                        $timeout(function () {
                            file.result = response.data;
                        });
                    }, function (response) {
                        if (response.status > 0)
                            $scope.errorMsg = response.status + ': ' + response.data;
                    });
                });
            };


            $scope.toggleTrialInfoDisplay = function () {
                $scope.editingTrialInfo = angular.element(document.querySelectorAll("[data-td-id='trialInformationEditor']")).hasClass('ng-hide');
            };

            $scope.toggleTrialSiteDisplay = function (trialSite) {
                trialSite.isEditing = !trialSite.isEditing;
            };

        }
    ])
    /*.filter('highlight', function($sce) {
        return function(text, phrase) {
            if (phrase) text = text.replace(new RegExp('('+phrase+')', 'gi'),
                '<span class="highlighted">$1</span>')

            return $sce.trustAsHtml(text)
        }
    })*/;