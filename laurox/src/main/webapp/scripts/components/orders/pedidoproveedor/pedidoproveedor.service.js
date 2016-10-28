'use strict';

angular.module('lauroxApp')
    .factory('PedidoProveedor', function ($resource, DateUtils) {
        return $resource('api/providerOrders/:nmPedido', {}, {
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
    }).factory('PedidoProveedorProducto', function ($resource, DateUtils) {
        return $resource('api/providerOrders/productsbyprov/:nmProveedor', {}, {
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
