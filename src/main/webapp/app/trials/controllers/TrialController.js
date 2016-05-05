angular.module('trialdirect').controller('TrialController',
    ['$scope', '$state', '$timeout', 'Upload', 'TrialResourceService', 'TrialInfo', 'trials',
        'therapeuticAreas',
        function ($scope, $state, $timeout, Upload, TrialResourceService, TrialInfo, trials, therapeuticAreas ) {

            // Initialize the model
            $scope.trial={
                trialInfo:{
                    trialSites:[]
                }
            };

            $scope.trials = trials;

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.wasSaved = false;

            $scope.editingTrialInfo = true;

            $scope.editingTrialSite = false;

            $scope.addTrial = function (newTrial, callback) {
                new TrialResourceService({
                    title: newTrial.title,
                    therapeuticArea: newTrial.therapeuticArea.getHrefLink()
                }).save(function (savedTrial) {
                    // Goto the new instance on the far side
                    $scope.trials.unshift(savedTrial);

                    // Update the savedTrial with the trialInfo to be saved.
                    savedTrial.trialInfo = $scope.trial.trialInfo;
                    savedTrial.therapeuticArea= $scope.trial.therapeuticArea;
                    $scope.trial = savedTrial;

                    callback && callback($scope.trial);

                    $scope.wasSaved = true;
                });

                //$scope.reset();
            };

            $scope.initializeTrialSite = function() {

                if (!$scope.trial.trialInfo) {
                    $scope.trial.trialInfo={};
                }

                var trialInfo = $scope.trial.trialInfo;

                if (!trialInfo.trialSites) {
                    trialInfo.trialSites = [];
                }

                var newTrialSite = {
                    // temporaryId solely used for view purposes
                    // stripped out before persisting to the backend.
                    id:trialInfo.trialSites.length + 1
                };

                $scope.editingTrialSite = true;

                trialInfo.trialSites.unshift(newTrialSite);

                $scope.showSiteForm(trialInfo.trialSites.length);
            };

            $scope.showSiteForm = function (trialSiteId) {
                $scope.trialSiteId = trialSiteId;
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
                $scope.trial.therapeuticArea = therapeuticArea;
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

            $scope.uploadTrial = function ( trial) {

                file = trial.trialInfo.trialLogoPic;

                // Firstly create the Trial Object
                $scope.addTrial(trial, function(savedTrial, file) {

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

            /**
             * If the trial has an id then it is a previously saved trial
             * therefore
             */
            $scope.isLogoAssociatedWithTrialInfo = function() {
                if ($scope.trial.id) {
                    // then it's a previous trial
                    // has the user clicked to upload a different image
                    return $scope.trial.trialInfo.trialLogoPic;
                } else {
                    // It's a new trial
                }

            };

        }
    ]);