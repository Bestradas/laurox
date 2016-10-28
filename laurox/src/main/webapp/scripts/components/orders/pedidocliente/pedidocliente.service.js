'use strict';

angular.module('lauroxApp')
    .factory('PedidoCliente', function ($resource, DateUtils) {
        return $resource('api/customerOrders/:nmPedido', {}, {
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
    }).factory('PedidoClienteApprove', function ($resource, DateUtils) {
    return $resource('api/customerOrders/approve/:nmPedidoApprove', {}, {
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
}).factory('PedidoClienteReject', function ($resource, DateUtils) {
    return $resource('api/customerOrders/reject/:nmPedidoReject', {}, {
        'query': { method: 'GET', isArray: true},
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
            }
        },
        'update': { method:'PUT' }
    })
}).factory('PedidoClienteProducto', function ($resource, DateUtils) {
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
