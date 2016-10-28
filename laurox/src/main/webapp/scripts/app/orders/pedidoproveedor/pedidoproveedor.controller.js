'use strict';

angular.module('lauroxApp')
    .controller('PedidoProveedorController', function ($scope, PedidoProveedor, ParseLinks) {
        $scope.pedidoproveedors = [];
        $scope.page = 0;
        $scope.loadAll = function() {
        	PedidoProveedor.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pedidoproveedors = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
        	PedidoProveedor.get({id: id}, function(result) {
                $scope.pedidoproveedor = result;
                $('#deletePedidoProveedorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
        	PedidoProveedor.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deletePedidoProveedorConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.pedidoproveedor = {nmPedido: null, nmProveedor: null, fePedido: null, feCompra: null, feEntrega: null, total: null, detallePedido: {}};
        };
    });
