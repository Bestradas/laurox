'use strict';

angular.module('lauroxApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('order', {
                abstract: true,
                parent: 'site'
            });
    });