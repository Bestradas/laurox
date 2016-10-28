'use strict';

angular.module('lauroxApp').controller('ProductoDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'Producto', 'Proveedor',
        function($scope, $stateParams, $modalInstance, entity, Producto, Proveedor) {
    	

    	
        $scope.producto = entity;
        $scope.proveedors = Proveedor.query();
        $scope.load = function(id) {
            Producto.get({id : id}, function(result) {
                $scope.producto = result;
            });
        };

        var onSaveFinished = function (result) {
            $scope.$emit('lauroxApp:productoUpdate', result);
            $modalInstance.close(result);
        };

        $scope.save = function () {
            if ($scope.producto.id != null) {
                Producto.update($scope.producto, onSaveFinished);
            } else {
                Producto.save($scope.producto, onSaveFinished);
            }
        };
        
        $scope.loadImage = function (input) {
        	if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#image')
                        .attr('src', e.target.result)
                        .width(200)
                        .height(400);
                    alert(e.target.result);
                };
                
                reader.readAsDataURL(input.files[0]);
                $scope.producto.foto=input.files[0].name;
            }
        };
        
        $scope.init = function () {
        	if (input.files && input.files[0]) {
                var reader = new FileReader();

                reader.onload = function (e) {
                    $('#image')
                        .attr('src', e.target.result)
                        .width(200)
                        .height(400);
                };
                
                reader.readAsDataURL(input.files[0]);
                $scope.producto.foto=input.files[0].name;
            }
        };

        $scope.clear = function() {
            $modalInstance.dismiss('cancel');
        };
}]);

