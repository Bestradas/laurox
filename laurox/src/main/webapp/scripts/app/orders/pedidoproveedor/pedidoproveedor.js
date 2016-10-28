'use strict';

angular.module('lauroxApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('providerorder', {
                parent: 'order',
                url: '/providerOrders',
                data: {
                    authorities: ["ROLE_ADMIN"],
                    pageTitle: 'lauroxApp.providerorder.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/orders/pedidoproveedor/pedidoproveedor.html',
                        controller: 'PedidoProveedorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pedidoproveedor');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('providerorder.detail', {
                parent: 'order',
                url: '/providerorder/{nmPedido}',
                data: {
                    authorities: ["ROLE_ADMIN"],
                    pageTitle: 'lauroxApp.providerorder.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/orders/pedidoproveedor/pedidoproveedor-detail.html',
                        controller: 'PedidoProveedorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pedidoproveedor');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PedidoProveedor', function($stateParams, PedidoProveedor) {
                        return PedidoProveedor.get({nmPedido : $stateParams.nmPedido});
                    }]
                }
            })
            .state('providerorder.new', {
                parent: 'order',
                url: '/new',
                data: {
                    authorities: ["ROLE_ADMIN"],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                	console.log('OpenPedidoProveedor');
                    $modal.open({
                        templateUrl: 'scripts/app/orders/pedidoproveedor/pedidoproveedor-dialog.html',
                        controller: 'PedidoProveedorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nmPedido: null, nmProveedor: null, fePedido: null, feCompra: null, feEntrega: null,total: null, detallePedido: {}};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('providerorder', null, { reload: true });
                    }, function() {
                        $state.go('providerorder');
                    })
                }]
            })
            .state('pedidoproveedor.edit', {
                parent: 'order',
                url: '/{nmPedido}/edit',
                data: {
                    authorities: ["ROLE_ADMIN"],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                	$modal.open({
                        templateUrl: 'scripts/app/orders/pedidoproveedor/pedidoproveedor-dialog.html',
                        controller: 'PedidoProveedorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['PedidoProveedor', function(PedidoProveedor) {
                                return PedidoProveedor.get({nmPedido : $stateParams.nmPedido});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('pedidoproveedor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
