'use strict';

angular.module('lauroxApp')
    .controller('ProductoController', function ($scope, Producto, ParseLinks) {
        $scope.productos = [];
        $scope.page = 0;
        $scope.loadAll = function() {
            Producto.query({page: $scope.page, size: 20}, function(result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.productos = result;
            });
        };
        $scope.loadPage = function(page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (idproducto) {
            Producto.get({idproducto: idproducto}, function(result) {
                $scope.producto = result;
                $('#deleteProductoConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (idproducto) {
            Producto.delete({idproducto: idproducto},
                function () {
                    $scope.loadAll();
                    $('#deleteProductoConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.producto = {idproducto: null, nombreProducto: null, idproveedor: null, descripcion: null, numeroLote: null, precioUnitario: null, id: null};
        };
    });
