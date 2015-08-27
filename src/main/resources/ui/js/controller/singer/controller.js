'use strict';

var turboAdventure = angular.module('TurboAdventure');

turboAdventure.controller('SingerController', function ($scope, Restangular) {

    $scope.addNewSinger = false;
    $scope.newSinger = null;

    $scope.editMode = false;
    $scope.actualEditSinger = null;

    $scope.singerListLoaded = false;
    $scope.singerList = {};

    $scope.initSingerList = function(singers) {
        $scope.newSinger = null;
        $scope.addNewSinger = false;

        $scope.editMode = false;
        $scope.actualEditSinger = null;

        $scope.singerList = _.sortBy(singers, 'firstname');
        $scope.singerListLoaded = true;
    };

    Restangular.one('singer').get().then(function (data) {
        $scope.initSingerList(data.singers);
    });

    $scope.addSinger = function() {
        $scope.addNewSinger = true;
    };

    $scope.cancelAddNewSinger = function() {
        $scope.newSinger = null;
        $scope.addNewSinger = false;
    };

    $scope.saveNewSinger = function() {
        $scope.singerListLoaded = false;

        var singerParameter = {
            "firstname": $scope.newSinger.firstname,
            "lastname": $scope.newSinger.lastname
        };
        var encodedFormData = $.param(singerParameter);

        var headers = {
            "Content-Type": "application/x-www-form-urlencoded"
        };

        Restangular.one('singer').customPOST(encodedFormData, null, null, headers).then(function (data) {
            $scope.initSingerList(data.singers);
        });

    };

    $scope.editSinger = function(singer) {
        $scope.editMode = true;
        $scope.actualEditSinger = singer;
    };

    $scope.cancelEditSinger = function() {
        $scope.editMode = false;
        $scope.singerListLoaded = false;

        Restangular.one('singer').get().then(function (data) {
            $scope.initSingerList(data.singer);
        });
    };

    $scope.saveEditedSinger = function(singer) {
        $scope.singerListLoaded = false;

        var singerParameter = {
            "_id": singer._id,
            "firstname": singer.firstname,
            "lastname": singer.lastname
        };
        var encodedFormData = $.param(singerParameter);

        var headers = {
            "Content-Type": "application/x-www-form-urlencoded"
        };

        Restangular.one('singer/' + singer._id).patch(encodedFormData, null, headers).then(function (data) {
            $scope.initSingerList(data.singer);
        });
    };

    $scope.deleteSinger = function(singer) {
        $scope.singerListLoaded = false;

        Restangular.one('singer/' + singer._id).remove().then(function (data) {
            $scope.initSingerList(data.singers);
        });
    };
});