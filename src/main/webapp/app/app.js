angular.module('trialdirect',[
  'ngResource',
  'spring-data-rest',
  'ui.router'
]).config(['$stateProvider', '$urlRouterProvider',
  function ($stateProvider, $urlRouterProvider) {
    $stateProvider
        .state('item', {
          url: '/item',
          templateUrl: 'views/templates/item.view.htm',
          controller: 'AppController'
        });

    $urlRouterProvider.otherwise('/');
  }]);
