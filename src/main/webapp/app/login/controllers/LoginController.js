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
                    "email": "admin@iconplc.com",
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

                        $scope.loginMessage = 'Login Successful';

                        $cookieStore.remove('userType');

                        if (loginEmail == $scope.profile1.email) {
                            $cookieStore.put('userType', $scope.profile1.type);
                            //$window.location.href = '/#/users';
                        }
                        else if (loginEmail == $scope.profile2.email) {
                            $cookieStore.put('userType', $scope.profile2.type);
                            //$window.location.href = '/#/trials';
                        }
                        else if (loginEmail == $scope.profile3.email) {
                            $cookieStore.put('userType', $scope.profile3.type);
                            //$window.location.href = '/#/therapeuticareas';
                        }

                        $window.location.href = '/';


                    } else {
                        $scope.loginSuccess = false;
                        $scope.loginMessage = 'No matching account details found';
                    }


                };


                /*
                    // write
                    $cookieStore.put('Name', $scope.Name);
                    // read
                    $cookieStore.get('Name');
                    // remove
                    $cookieStore.remove('Name');
                */
            }
        ]);