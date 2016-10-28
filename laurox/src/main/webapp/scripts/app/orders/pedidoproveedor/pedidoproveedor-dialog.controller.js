'use strict';

angular.module('lauroxApp').controller('PedidoProveedorDialogController',
		['$scope', '$stateParams', '$modalInstance', 'entity', 'PedidoProveedor','Proveedor','PedidoProveedorProducto',
		 function($scope, $stateParams, $modalInstance, entity, PedidoProveedor,Proveedor,PedidoProveedorProducto) {

			$scope.pedidoproveedor = entity;
			$scope.productos;
			$scope.productosPedidoList=null;
			$scope.productoSelected;
			$scope.cantidadSelected=0;
			$scope.total=0;
			$scope.proveedors = Proveedor.query();      
			$scope.load = function(nmPedido) {
				PedidoProveedor.get({nmPedido : nmPedido}, function(result) {
					$scope.pedidoproveedor = result;
				});
			};

			var onSaveFinished = function (result) {
				$scope.$emit('lauroxApp:proveedorUpdate', result);
				$modalInstance.close(result);
			};

			$scope.save = function () {
				console.log('save');
				var feCompra=$("#field_feCompra").val().split("/");
				$scope.pedidoproveedor.feCompra=new Date(feCompra[2], feCompra[0], feCompra[1] - 1);
				var feEntrega=$("#field_feEntrega").val();
				if(feEntrega!=null && feEntrega!=""){
					feEntrega=$("#field_feEntrega").val().split("/");
					$scope.pedidoproveedor.feEntrega=new Date(feEntrega[2], feCompra[0], feCompra[1] - 1);
				}else{
					$scope.pedidoproveedor.feEntrega=null;
				}
				var fePedido=$("#field_fePedido").val().split("/");
				$scope.pedidoproveedor.fePedido=new Date(fePedido[2], feCompra[0], feCompra[1] - 1);
				$scope.pedidoproveedor.total=$scope.total;
				$scope.pedidoproveedor.detallePedido=$scope.productosPedidoList;
				if ($scope.pedidoproveedor.nmPedido != null) {
					PedidoProveedor.update($scope.pedidoproveedor, onSaveFinished);
				} else {
					PedidoProveedor.save($scope.pedidoproveedor, onSaveFinished);
				}
			};

			$scope.clear = function() {
				$modalInstance.dismiss('cancel');
			};

			$scope.changeProduct = function() {
				$scope.productosPedidoList=null;
				PedidoProveedorProducto.query({nmProveedor: $scope.pedidoproveedor.nmProveedor.nit}, function(result, headers) {
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
