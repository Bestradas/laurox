'use strict';

angular.module('lauroxApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('pedidocliente', {
                parent: 'order',
                url: '/pedidoclientes',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lauroxApp.customerorder.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/orders/pedidocliente/pedidocliente.html',
                        controller: 'PedidoClienteController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pedidocliente');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            }).state('pedidocliente.detail', {
                parent: 'order',
                url: '/customerorder/{nmPedido}',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lauroxApp.customerorder.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/orders/pedidocliente/pedidocliente-detail.html',
                        controller: 'PedidoClienteDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('pedidocliente');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'PedidoCliente', function($stateParams, PedidoCliente) {
                        return PedidoCliente.get({nmPedido : $stateParams.nmPedido});
                    }]
                }
            }).state('pedidocliente.new', {
                parent: 'order',
                url: '/newCustomerOrder',
                data: {
                    authorities: ['ROLE_USER'],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    console.log('openCliente');
                	$modal.open({
                        templateUrl: 'scripts/app/orders/pedidocliente/pedidocliente-dialog.html',
                        controller: 'PedidoClienteDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nmPedido: null,total: null, detallePedido: {}};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('pedidocliente', null, { reload: true });
                    }, function() {
                        $state.go('pedidocliente');
                    })
                }]
            });
    });
