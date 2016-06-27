angular
    .module('trialdirect')
    .controller('LoginController',
        ['$scope', '$state', '$cookieStore', '$window',
            function ($scope, $state, $cookieStore, $window) {

                $scope.loginMessage = null;
                $scope.loginSuccess = false;

                $scope.profile1 =
                {
                    "name": "Doctor",
                    "email": "doctor@iconplc.com",
                    "password": "doctor",
                    "type": "doctor"
                };
                $scope.profile2 =
                {
                    "name": "Trial Owner",
                    "email": "owner@iconplc.com",
                    "password": "owner",
                    "type": "owner"
                };
                $scope.profile3 =
                {
                    "name": "Admin Q&A",
                    "email": "admin@icon.com",
                    "password": "admin",
                    "type": "admin"
                };


                $scope.submitLogin = function (loginEmail, loginPassword) {

                    if (
                        (loginEmail == $scope.profile1.email && loginPassword == $scope.profile1.password)
                        || (loginEmail == $scope.profile2.email && loginPassword == $scope.profile2.password)
                        || (loginEmail == $scope.profile3.email && loginPassword == $scope.profile3.password)
                    ) {
                        $scope.loginSuccess = true;
                        // $scope.login.email = null;
                        // $scope.login.password = null;

                        $cookieStore.remove('userType');

                        if (loginEmail == $scope.profile1.email) {
                            $cookieStore.put('userType', $scope.profile1.type);
                        }
                        else if (loginEmail == $scope.profile2.email) {
                            $cookieStore.put('userType', $scope.profile2.type);
                        }
                        else if (loginEmail == $scope.profile3.email) {
                            $cookieStore.put('userType', $scope.profile3.type);
                        }

                        $scope.loginMessage = 'Login Successful';

                        //alert($cookieStore.get('userType'));

                        $window.location.href = '/';

                    } else {
                        $scope.loginSuccess = false;
                        $scope.loginMessage = 'No matching account details found';
                    }


                    //$cookieStore.put('Name', loginEmail);
                    //alert($cookieStore.get('Name'));
                    //return $scope.loginMessage = 'You Submitted the Login Form '+ loginEmail +' - '+ loginPassword;

                };


                /*				$scope.WriteCookie = function () {
                 $cookieStore.put('Name', $scope.Name);
                 };
                 $scope.ReadCookie = function () {
                 $window.alert($cookieStore.get('Name'));
                 };
                 $scope.RemoveCookie = function () {
                 $cookieStore.remove('Name');
                 };*/

            }
        ]);