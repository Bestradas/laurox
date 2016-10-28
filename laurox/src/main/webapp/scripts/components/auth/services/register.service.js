'use strict';

angular.module('lauroxApp')
    .factory('Register', function ($resource) {
        return $resource('api/register', {}, {
        });
    });


