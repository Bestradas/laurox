'use strict';

angular.module('lauroxApp')
    .factory('Proveedor', function ($resource, DateUtils) {
        return $resource('api/proveedors/:nit', {}, {
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
