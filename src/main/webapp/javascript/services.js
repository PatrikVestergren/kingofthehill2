angular.module('RCFeederApp.services', []).
factory('ergastAPIservice', function($http) {

    var ergastAPI = {};

   // ergastAPI.getDrivers = function() {
   //     return $http({
   //         method: 'JSONP',
   //         url: 'http://localhost:8080/king/laps/'
   //     });
   // }

    return ergastAPI;
});