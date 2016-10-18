angular.module("KingApp", [])
    .constant("baseUrl", 'http://localhost:8080/king/laps/current')
    .controller("lapsController", function ($scope, $http, baseUrl) {
        $scope.nameFilter = null;
        $scope.laps = [];

        $scope.listLaps = function () {
            $http.get(baseUrl).success(function (data) {
                console.log(data);
                $scope.laps = data;
            });
        }

        $scope.listLaps();
    });
