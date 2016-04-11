angular.module('formlyApp').service('JsonTranslateService', [ function(){

    var _jsonObject = {};

    function setJsonObject(jsonObject) {
        _jsonObject = jsonObject;
    }

    function getJsonObject() {
        return _jsonObject;
    }

    return {
        setJsonObject: setJsonObject,
        getJsonObject: getJsonObject
    }
}
]);