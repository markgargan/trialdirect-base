angular.module('trialdirect').controller('TrialController',
    ['$scope', '$state', '$timeout', 'Upload', 'TrialResourceService', 'TrialInfo', 'trials',
        'therapeuticAreas',
        function ($scope, $state, $timeout, Upload, TrialResourceService, TrialInfo, trials, therapeuticAreas ) {

            // Initialize the model
            $scope.trial={
                trialInfo:{
                    trialSites:{
                        _embeddedItems:[]
                    }
                }
            };

            $scope.trials = trials;

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.wasSaved = false;

            $scope.editingTrialInfo = true;

            $scope.editingTrialSite = false;

            $scope.therapeuticAreaSet= false;

            $scope.errorFields=[];

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

            $scope.validateField = function (trial, fieldName) {
                if(angular.isEmpty(trial[fieldName])){
                    $scope.errorFields.push(fieldName);
                    return false;
                } else {
                     $scope.errorFields.splice(
                        $scope.errorFields.indexOf(fieldName),1
                    );
                }

                return true;
            };

            $scope.validTrial = function (trial) {
                var validTrial = true;
                validTrial &= $scope.validateField(trial, 'title', 'Title');
                validTrial &= $scope.validateField(trial, 'therapeuticArea', 'Therapeutic Area');
                validTrial &= $scope.validateField(trial.trialInfo.summary, 'summary', 'Summary');
                validTrial &= $scope.validateField(trial.trialInfo, 'fullDescription', 'Full Description');
                validTrial &= $scope.validateField(trial.trialInfo, 'trialInfoLogo', 'Trial Image');
                validTrial &= $scope.validateField(trial.trialInfo, 'trialSites', 'Trial Sites');

                for (var i =0; i<trial.trialInfo.trialSites.length; i++ ) {
                    var validSite = true;
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'facilityName');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'facilityDescription');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'PrincipalInvestigator');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'siteMap');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'address1');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'address2');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'address3');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'address4');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'address5');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'country');
                    validSite &= $scope.validateField(trial.trialInfo.trialSites[i], 'sitePic');

                    trial.trialInfo.trialSites[i].validSite = validSite;

                    validTrial &= validSite;
                }

                return validTrial;
            };

            $scope.validateTrialInfo = function (trial) {
                if(angular.isEmpty(trial.title)){
                    trial.title={};
                    return false;
                }

                return true;
            };

            $scope.initializeTrialSite = function() {

                var trialInfo = $scope.trial.trialInfo;

                if (!trialInfo.trialSites) {
                    trialInfo.trialSites = {
                        _embeddedItems:[]
                    };
                }

                var newTrialSite = {
                    // temporaryId solely used for view purposes
                    // stripped out before persisting to the backend.O
                    id:trialInfo.trialSites._embeddedItems.length + 1
                };

                $scope.editingTrialSite = true;

                trialInfo.trialSites._embeddedItems.unshift(newTrialSite);

                $scope.showSiteForm(trialInfo.trialSites._embeddedItems.length);
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
                $scope.therapeuticAreaSet=true;
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

                angular.forEach(trial.trialInfo.trialSites._embeddedItems, function(trialSite){

                    var trialSite = {
                        description:trialSite.fullDescription,
                        trialSiteFile:trialSite.sitePic,
                        siteDirector:trialSite.siteDirector,
                        siteSummary:trialSite.siteSummary,
                        siteDescription:trialSite.siteDescription,
                        siteMap:trialSite.siteMap
                    };

                    data.trialSites.unshift(trialSite);
                }, data);

            };

            $scope.clearErrors = function(){
                $scope.errors = '';
                $scope.errorFields = [];
            };

            $scope.uploadTrial = function ( trial, file) {

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