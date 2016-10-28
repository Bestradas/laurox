'use strict';

angular.module('lauroxApp')
    .controller('ProveedorController', function ($scope, Proveedor, ParseLinks) {
        $scope.proveedors = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Proveedor.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.proveedors = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (nit) {
            Proveedor.get({nit: nit}, function(result) {
                $scope.proveedor = result;
                $('#deleteProveedorConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (nit) {
            Proveedor.delete({nit: nit},
                function () {
                    $scope.loadAll();
                    $('#deleteProveedorConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.proveedor = {nit: null, razonSocial: null, nombreContacto: null, telefono: null, celular: null, direccion: null};
        };
    });
