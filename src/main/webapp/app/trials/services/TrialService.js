angular.module('trialdirect').service('TrialService', ['$http', function($http){

    var AVAILABLE_TRIALS_COUNT_URL = './availabletrialids';

    var AVAILABLE_TRIALS_URL = './availabletrial';

    function getAvailableTrialsCount(userId, therapeuticAreaId, callback) {
        return $http.get(AVAILABLE_TRIALS_COUNT_URL + '/' + userId + '/therapeuticarea/' + therapeuticAreaId)
            .then (function(trials){

            if (trials == [])
                trials.data = [];

            callback && callback(trials.data);
        })
    }

    function getAvailableTrials(userId, therapeuticAreaId) {
        $http.get(AVAILABLE_TRIALS_URL + '/' + userId).then (function(trials){

            return _.map(trials, function (trial) {

                return new TrialResourceService(trial);
            });
        })
    }

    return {
        getAvailableTrials:getAvailableTrials,
        getAvailableTrialsCount:getAvailableTrialsCount
    };
    //Test for new branch

}]);