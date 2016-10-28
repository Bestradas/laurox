'use strict';

angular.module('lauroxApp')
    .factory('AcercaDe', function ($resource, DateUtils) {
        return $resource('api/acercade/:acercade', {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    });
