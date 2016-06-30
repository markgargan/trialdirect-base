angular.module('trialdirect').controller('RegisterController',
    ['$scope', '$http', '$sce',
        function ($scope, $http, $sce) {

            $scope.mapLatitude = 'N/A';
            $scope.mapLongtitude = 'N/A';
            $scope.loadGoogleMap = false;

            $scope.doctorZip = 'IL 60611';

            $scope.getGooglePos = function (zip) {

                // if zip is not set, reset variables to default
                if (!zip) {
                    $scope.mapLatitude = 'N/A';
                    $scope.mapLongtitude = 'N/A';
                    $scope.loadGoogleMap = false;
                    return;
                }


                // run api link, and return result
                $http.get('https://maps.google.com/maps/api/geocode/json?address=' + zip.replace(/\s+/g, '+'))
                    .then(function (response) {

                        var lat = response.data.results[0].geometry.location.lat;
                        var lng = response.data.results[0].geometry.location.lng;

                        $scope.mapLatitude = lat.toFixed(6);
                        $scope.mapLongtitude = lng.toFixed(6);

                        $scope.loadGoogleMap = true; // allow google map to load

                    });

            };


            //
            $scope.getGoogleMap = function (zip) {

                //$scope.cords = getGooglePos();

                /*
                 var url = 'https://maps.google.com/maps/api/geocode/json?address=1600+Amphitheatre+Parkway,+Mountain+View,+CA';
                 $http.get(url).success(function(data){
                 console.log(data);
                 });
                 */

                // build map
                var map =
                    '<iframe width="100%" height="250" frameborder="0" style="border:0"'
                    + ' src="https://www.google.com/maps/embed/v1/place'
                    + '?key=AIzaSyDPnVez_E7pNgjNPxNKWqy8tTsnlFpKJaw'
                    + '&q='
                    + zip.replace(/\s+/g, '+') + ','
                    + '"></iframe>';

                // return info as HTML code
                return $sce.trustAsHtml(map);
            };



            function calcDistance(lat1, lon1, lat2, lon2, unit) {
                var radlat1 = Math.PI * lat1/180;
                var radlat2 = Math.PI * lat2/180;
                var theta = lon1-lon2;
                var radtheta = Math.PI * theta/180;
                var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
                dist = Math.acos(dist);
                dist = dist * 180/Math.PI;
                dist = dist * 60 * 1.1515;
                if (unit=="K") { dist = dist * 1.609344; }
                if (unit=="N") { dist = dist * 0.8684; }
                return dist;
            }

        }
    ]);