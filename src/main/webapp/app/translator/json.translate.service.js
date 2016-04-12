angular.module('formlyApp').service('JsonTranslateService', [ function(){

    var _jsonObject = {};
    var updated = true;

    function setJsonObject(jsonObject) {
        _jsonObject = jsonObject;
        updated = !updated;
    }

    function getJsonObject() {
        return _jsonObject;
    }

    function isUpdated() {
        return updated;
    }

    return {
        setJsonObject: setJsonObject,
        getJsonObject: getJsonObject,
        isUpdated: isUpdated
    }
}
]);