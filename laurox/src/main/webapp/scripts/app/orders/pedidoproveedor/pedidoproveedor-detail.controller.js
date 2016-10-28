'use strict';

angular.module('lauroxApp')
    .controller('PedidoProveedorDetailController', function ($scope, $rootScope, $stateParams, entity, PedidoProveedor, Proveedor) {
        $scope.pedidoproveedor = entity;
        $scope.load = function (id) {
        	PedidoProveedor.get({id: id}, function(result) {
                $scope.pedidoproveedor = result;
            });
        };
        $rootScope.$on('lauroxApp:pedidoproveedorUpdate', function(event, result) {
            $scope.pedidoproveedor = result;
        });
    });
