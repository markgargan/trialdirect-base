angular.module('trialdirect').controller('TrialController',
    ['$scope', '$state', '$timeout', 'Upload', 'TrialResourceService', 'TrialInfo', 'trials',
        'therapeuticAreas',
        function ($scope, $state, $timeout, Upload, TrialResourceService, TrialInfo, trials, therapeuticAreas ) {

            // Initialize the model
            $scope.trial={
                trialInfo:{
                    trialFullDescription:{},
                    trialSites:{
                        _embeddedItems:[]
                    },
                    needsImageUpload:true,
                    hasUploadedImage:false
                }
            };

            $scope.trials = trials;

            $scope.therapeuticAreas = therapeuticAreas;

            $scope.wasSaved = false;

            $scope.errorFields=[];

            $scope.updateTrial = function (newTrial, callback) {

                if (angular.isDefined(newTrial.id)) {
                    // Means the trial object is being updated
                    // so just call save and it will call into the update
                    // version of the save method.
                    // no 'new' here...
                    TrialResourceService({
                        id:newTrial.id,
                        title:newTrial.title,
                        therapeuticArea: newTrial.therapeuticArea.getHrefLink()
                    }).save(function (savedTrial) {

                        // Update the savedTrial with the trialInfo to be saved.
                        savedTrial.trialInfo = $scope.trial.trialInfo;
                        savedTrial.therapeuticArea= $scope.trial.therapeuticArea;
                        $scope.trial = savedTrial;
                        callback && callback($scope.trial);
                        $scope.wasSaved = true;

                    });
                } else {

                    new TrialResourceService({
                        title: newTrial.title,
                        therapeuticArea: newTrial.therapeuticArea.getHrefLink()
                    }).save(function (savedTrial) {
                        // Goto the new instance on the far side
                        $scope.trials.unshift(savedTrial);

                        // Update the savedTrial with the trialInfo to be saved.
                        savedTrial.trialInfo = $scope.trial.trialInfo;
                        savedTrial.therapeuticArea = $scope.trial.therapeuticArea;
                        $scope.trial = savedTrial;

                        callback && callback($scope.trial);

                        $scope.wasSaved = true;
                    });
                }
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

            // Most of the validation is taken care of by the form
            // Things like the
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


            $scope.validateTrial = function (trial) {
                if(angular.isEmpty(trial.therapeuticArea)){
                    trial.unselectedTherapeuticArea = true;
                    return false;
                }

                return true;
            };


            $scope.initializeTrialSite = function() {

                var trialInfo = $scope.trial.trialInfo;

                var newTrialSite = {
                    // temporaryId solely used for view purposes
                    // stripped out before persisting to the backend.O
                    sortOrder:trialInfo.trialSites._embeddedItems.length + 1,
                    needsImageUpload:true,
                    hasUploadedImage:false
                };

                newTrialSite.trialDirectAddress={};

                trialInfo.trialSites._embeddedItems.unshift(newTrialSite);

                $scope.showSiteForm(newTrialSite.sortOrder);
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
                $scope.trial.unselectedTherapeuticArea= false;
            };

            $scope.createTrialInfo = function(trial) {

                var data = {
                    trialInfoId:trial.trialInfo.id,
                    trialId:trial.id,
                    summary: trial.trialInfo.summary,
                    trialFullDescription: trial.trialInfo.trialFullDescription.fullDescription,
                    trialInfoLogo: trial.trialInfo.trialLogoPic
                };

                $scope.createTrialSites(trial, data);

                return data;
            };

            $scope.createTrialSites = function(trial, data) {

                data.trialSites = [];

                angular.forEach(trial.trialInfo.trialSites._embeddedItems, function(trialSite){

                    var trialSite = {
                        id:trialSite.id,
                        facilityName:trialSite.facilityName,
                        facilityDescription:trialSite.facilityDescription,
                        trialSiteFile:trialSite.sitePic,
                        principalInvestigator:trialSite.principalInvestigator,
                        siteSummary:trialSite.siteSummary,
                        siteDescription:trialSite.siteDescription,
                        siteAddress1:trialSite.trialDirectAddress.address1,
                        siteAddress2:trialSite.trialDirectAddress.address2,
                        siteAddress3:trialSite.trialDirectAddress.address3,
                        siteAddress4:trialSite.trialDirectAddress.address4,
                        siteAddress5:trialSite.trialDirectAddress.address5,
                        siteMap:trialSite.siteMap,
                        sortOrder:trialSite.sortOrder
                    };

                    data.trialSites.unshift(trialSite);
                }, data);

            };

            $scope.clearErrors = function(){
                $scope.errors = '';
                $scope.errorFields = [];
            };

            $scope.uploadTrial = function ( trial, file) {

                if (!$scope.validateTrial(trial)) {
                    return;
                }

                // Firstly create the Trial Object
                $scope.updateTrial(trial, function(savedTrial) {

                    var upload = Upload.upload({
                        url: '/uploadTrialInfo',
                        data: $scope.createTrialInfo(savedTrial),
                        objectKey: '.k',
                        arrayKey: '[i]'
                    });

                    upload.then(function (response) {
                        // timeout prevents this from running within the digest cycle
                        $timeout(function () {
                            // Update TrialInfo and TrialSites
                            var savedTrialInfo = response.data;

                            TrialInfo.loadTrialInfoWithCallback(savedTrialInfo.id, function(trialInfo){
                                $scope.trial.trialInfo = trialInfo;
                                if (file) {
                                    file.result = response.data;
                                }
                            });


                        });
                    }, function (response) {
                        if (response.status > 0)
                            $scope.errorMsg = response.status + ': ' + response.data;
                    });
                });
            };


            $scope.toggleTrialInfoImage = function () {
                $scope.trial.trialInfo.needsImageUpload = !$scope.trial.trialInfo.needsImageUpload;
            };

            $scope.toggleTrialSiteImage = function (trialSite) {
                trialSite.needsImageUpload = !trialSite.needsImageUpload;
            };

            $scope.resetUploadedImage = function() {
                $scope.trial.trialInfo.trialLogoPic = null;
                $scope.trial.trialInfo.needsImageUpload=true;
                $scope.trial.trialInfo.hasUploadedImage=false;
            };

            $scope.resetUploadedTrialSiteImage = function(trialSite) {
                trialSite.sitePic = null;
                trialSite.needsImageUpload=true;
                trialSite.hasUploadedImage=false;
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