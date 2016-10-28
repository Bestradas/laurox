'use strict';

angular.module('lauroxApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('ayuda', {
                abstract: true,
                parent: 'site'
            });
    });
