'use strict';

angular.module('lauroxApp')
    .controller('PedidoClienteController', function ($scope, PedidoCliente,PedidoClienteApprove,PedidoClienteReject, ParseLinks,Principal) {
        Principal.identity(true).then(function(account) {
            $scope.settingsAccount = account;
            $scope.isAdmin=account.authorities.indexOf("ROLE_ADMIN")!=-1;
            console.log($scope.settingsAccount);
        });
    	$scope.pedidoClientes = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            PedidoCliente.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.pedidoClientes = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id,action) {
        	PedidoCliente.get({nmPedido: id}, function(result) {
                $scope.pedidoCliente = result;
                if(action=='appr'){
                	$('#approvePedidoClienteConfirmation').modal('show');
                }else{
                	$('#rejectPedidoClienteConfirmation').modal('show');
                }
                
            });
        };
        
        $scope.confirmApprove = function (id) {
        	console.log(id);
        	PedidoClienteApprove.get({nmPedidoApprove: id},
                function () {
                    $scope.loadAll();
                    $('#approvePedidoClienteConfirmation').modal('hide');
                    $scope.clear();
                });
        };
        
        $scope.confirmReject = function (id) {
        	console.log(id);
        	PedidoClienteReject.get({nmPedidoReject: id},
                function () {
                    $scope.loadAll();
                    $('#rejectPedidoClienteConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
        	 $scope.pedidocliente = {nmPedido: null, nmCliente: null, fePedido: null, feCompra: null, feEntrega: null, total: null, detallePedido: {}};
        };
    });
