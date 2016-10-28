'use strict';

angular.module('lauroxApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('producto', {
                parent: 'entity',
                url: '/productos',
                data: {
                    authorities: ["ROLE_USER", "ROLE_ADMIN"],
                    pageTitle: 'lauroxApp.producto.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/producto/productos.html',
                        controller: 'ProductoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('producto');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('producto.detail', {
                parent: 'entity',
                url: '/producto/{idproducto}',
                data: {
                    authorities: ["ROLE_USER", "ROLE_ADMIN"],
                    pageTitle: 'lauroxApp.producto.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/producto/producto-detail.html',
                        controller: 'ProductoDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('producto');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Producto', function($stateParams, Producto) {
                        return Producto.get({idproducto : $stateParams.idproducto});
                    }]
                }
            })
            .state('producto.new', {
                parent: 'producto',
                url: '/new',
                data: {
                    authorities: ["ROLE_ADMIN"],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/producto/producto-dialog.html',
                        controller: 'ProductoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {idproducto: null, nombreProducto: null, idproveedor: null, descripcion: null, numeroLote: null, precioUnitario: null, foto: null, id: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('producto', null, { reload: true });
                    }, function() {
                        $state.go('producto');
                    })
                }]
            })
            .state('producto.edit', {
                parent: 'producto',
                url: '/{idproducto}/edit',
                data: {
                    authorities: ["ROLE_ADMIN"],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/producto/producto-dialog.html',
                        controller: 'ProductoDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Producto', function(Producto) {
                                return Producto.get({idproducto : $stateParams.idproducto});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('producto', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
