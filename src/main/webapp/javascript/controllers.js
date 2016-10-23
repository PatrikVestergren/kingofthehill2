angular.module("KingApp", ['angular-websocket', 'ngAnimate'])
    .constant("baseUrl", 'http://localhost:8080/king/laps/current')
    .controller("lapsController", function ($scope, $http, $websocket, baseUrl) {
        $scope.nameFilter = null;
        $scope.laps = [];

        $scope.listLaps = function () {
            $http.get(baseUrl).success(function (data) {
                console.log(data);
                $scope.laps = data;
            });
        }

        var ws = $websocket('ws://localhost:8080/WSking');

        ws.onMessage(function(message) {
            console.log('something incoming from the server: ' + message.data);
            var obj = JSON.parse(message.data);
            $scope.$apply(function() {
                var set = false;
                for (var i in $scope.laps) {
                    if($scope.laps[i].transponder == obj.transponder){
                        $scope.laps[i] = obj;
                        set = true;
                    }
                }
                if (!set) {
                    $scope.laps.push(obj);
                }
            });
            //ws.push(JSON.parse(message.data));
        });


        $scope.listLaps();
    });
