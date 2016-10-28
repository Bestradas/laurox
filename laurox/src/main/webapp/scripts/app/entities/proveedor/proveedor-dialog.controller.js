'use strict';

angular.module('lauroxApp').controller('ProveedorDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Proveedor',
        function($scope, $stateParams, $modalInstance, entity, Proveedor) {

        $scope.proveedor = entity;
        $scope.load = function(nit) {
            Proveedor.get({nit : nit}, function(result) {
                $scope.proveedor = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('lauroxApp:proveedorUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.proveedor.nit != null) {
                Proveedor.update($scope.proveedor, onSaveFinished);
            } else {
                Proveedor.save($scope.proveedor, onSaveFinished);
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);
