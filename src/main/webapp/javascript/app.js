'use strict';

var app = angular.module("MyApp", [
    "ngRoute",
    "KingApp"
]);


app.config(function ($routeProvider) {
    $routeProvider
        .when('/', {
            templateUrl: "partials/home.html",
            controller: "lapsController"
        }).otherwise({
        redirectTo: '/'
    });
});
