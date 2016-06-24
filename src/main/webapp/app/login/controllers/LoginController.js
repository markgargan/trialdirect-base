angular
	.module('trialdirect')
	.controller('LoginController',
    	['$scope', '$state', 'LoginResourceService', 
        	function ($scope, $state, LoginResourceService) {

        		$scope.message = null;

	        	$scope.submitLogin = function (loginEmail, loginPassword) {
	        		return $scope.message = 'You Submitted the Login Form '+ loginEmail +' - '+ loginPassword;
	        	};

        	}
        ]);