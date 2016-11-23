angular.module("KingApp", ['angular-websocket', 'ngAnimate'])
    .constant("baseUrl", 'http://localhost:8080/king')
    .controller("lapsController", function ($scope, $http, $websocket, baseUrl) {
        $scope.nameFilter = null;
        $scope.laps = [];

        $scope.listLaps = function () {
            $http.get(baseUrl + "/laps/current").success(function (data) {
                console.log(data);
                $scope.laps = data;
            });
        }

        var ws = $websocket('ws://localhost:8080/WSking');

        ws.onMessage(function(message) {

            var lap = JSON.parse(message.data);
            $scope.$apply(function() {
                var set = false;
                for (var i in $scope.laps) {
                    if($scope.laps[i].transponder === lap.transponder){
                        $scope.laps[i] = lap;
                        set = true;
                    }
                }
                if (!set) {
                    $scope.laps.push(obj);
                }
            });
        });


        $scope.listLaps();
    })
    .controller("todayfor", function($scope, $http, $routeParams, baseUrl) {
        $scope.todays = [];
        $scope.transponder = $routeParams.transponder;
        $scope.todaysLaps = function () {
                $http.get(baseUrl + "/laps/todays/",{
                    params:{transponder: $scope.transponder}
                }).success(function (data) {
                    console.log(data);
                    $scope.todays = data;
                }).error(function (error, status){
                   $scope.data.error = { message: error, status: status};
                   console.log($scope.data.error.status);
                });
            }

        $scope.todaysLaps();
        console.log($scope.todays);
    });
