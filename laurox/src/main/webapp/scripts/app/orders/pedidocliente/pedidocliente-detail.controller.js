'use strict';

angular.module('lauroxApp')
    .controller('PedidoClienteDetailController', function ($scope, $rootScope, $stateParams, entity, PedidoCliente, Proveedor) {
        $scope.pedidocliente = entity;
        $scope.load = function (id) {
        	PedidoCliente.get({nmPedido: id}, function(result) {
                $scope.pedidocliente = result;
            });
        };
        $rootScope.$on('lauroxApp:pedidoclienteUpdate', function(event, result) {
            $scope.pedidocliente = result;
        });
    });
