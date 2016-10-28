'use strict';

angular.module('lauroxApp')
    .controller('ProveedorDetailController', function ($scope, $rootScope, $stateParams, entity, Proveedor) {
        $scope.proveedor = entity;
        $scope.load = function (nit) {
            Proveedor.get({nit: nit}, function(result) {
                $scope.proveedor = result;
            });
        };
        $rootScope.$on('lauroxApp:proveedorUpdate', function(event, result) {
            $scope.proveedor = result;
        });
    });
