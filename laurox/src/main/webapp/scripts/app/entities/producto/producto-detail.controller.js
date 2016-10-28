'use strict';

angular.module('lauroxApp')
    .controller('ProductoDetailController', function ($scope, $rootScope, $stateParams, entity, Producto, Proveedor) {
        $scope.producto = entity;
        $scope.load = function (idproducto) {
            Producto.get({idproducto: idproducto}, function(result) {
                $scope.producto = result;
            });
        };
        $rootScope.$on('lauroxApp:productoUpdate', function(event, result) {
            $scope.producto = result;
        });
    });
