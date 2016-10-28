'use strict';

angular.module('lauroxApp').controller('PedidoClienteDialogController',
		['$scope', '$stateParams', '$modalInstance', 'entity', 'PedidoCliente','Proveedor','PedidoClienteProducto','Principal',
		 function($scope, $stateParams, $modalInstance, entity, PedidoCliente,Proveedor,PedidoClienteProducto,Principal) {

	        Principal.identity(true).then(function(account) {
	            $scope.settingsAccount = account;
	            console.log($scope.settingsAccount);
	        });
			$scope.pedidocliente = entity;
			$scope.productos;
			$scope.nmProveedorSelected;
			$scope.productosPedidoList=null;
			$scope.productoSelected;
			$scope.cantidadSelected=0;
			$scope.total=0;
			$scope.proveedors = Proveedor.query();      
			$scope.load = function(nmPedido) {
				PedidoCliente.get({nmPedido : nmPedido}, function(result) {
					$scope.pedidocliente = result;
				});
			};

			var onSaveFinished = function (result) {
				$scope.$emit('lauroxApp:clienteUpdate', result);
				$modalInstance.close(result);
			};

			$scope.save = function () {
				$scope.pedidocliente.nmCliente=$scope.settingsAccount;
				$scope.pedidocliente.total=$scope.total;
				$scope.pedidocliente.fePedido=null;
				$scope.pedidocliente.feEntrega=null;
				$scope.pedidocliente.status=0;
				$scope.pedidocliente.detallePedido=$scope.productosPedidoList;
				console.log($scope.pedidocliente);
				if ($scope.pedidocliente.nmPedido != null) {
					PedidoCliente.update($scope.pedidocliente, onSaveFinished);
				} else {
					PedidoCliente.save($scope.pedidocliente, onSaveFinished);
				}
			};

			$scope.clear = function() {
				$modalInstance.dismiss('cancel');
			};

			$scope.changeProduct = function() {
				PedidoClienteProducto.query({nmProveedor: $scope.nmProveedorSelected.nit}, function(result, headers) {
					$scope.productos = result;
				});
			};

			$scope.addProduct = function()
			{
				var newProduct = new function() {
					this.id = 0;
					this.nmPedido = 0;
					this.nmProducto = $scope.productoSelected;
					this.nmCantidad    = $scope.cantidadSelected;
					this.precioUnitario = $scope.productoSelected.precioUnitario;
					this.subTotal = $scope.productoSelected.precioUnitario*$scope.cantidadSelected;
				}
//				$scope.productosPedidoList[$scope.productoSelected.id]=newProduct;
				if($scope.productosPedidoList==null){
					$scope.productosPedidoList=[newProduct];
				}else{
					var exist=false;
					for (var i = 0; i < $scope.productosPedidoList.length; i++) {
						if($scope.productosPedidoList[i].nmProducto!=undefined){
							if($scope.productosPedidoList[i].nmProducto.id==newProduct.nmProducto.id){
								$scope.productosPedidoList[i].nmCantidad=newProduct.nmCantidad;
								exist=true;
								break;
							}
						}
					}
					if(!exist){
						$scope.productosPedidoList.push(newProduct);	
					}
				}
				$scope.total=0;
				for (var i = 0; i < $scope.productosPedidoList.length; i++) {
					if($scope.productosPedidoList[i].nmProducto!=undefined){

						var precio=$scope.productosPedidoList[i].nmProducto.precioUnitario;
						var cantidad=$scope.productosPedidoList[i].nmCantidad;
						var subtotal=$scope.total;
						$scope.total=subtotal+(precio*cantidad);
					}
				}				
			}

		}]);
