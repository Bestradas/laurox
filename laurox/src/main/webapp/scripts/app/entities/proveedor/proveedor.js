'use strict';

angular.module('lauroxApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('proveedor', {
                parent: 'entity',
                url: '/proveedors',
                data: {
                    authorities: ["ROLE_ADMIN"],
                    pageTitle: 'lauroxApp.proveedor.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proveedor/proveedors.html',
                        controller: 'ProveedorController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proveedor');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            })
            .state('proveedor.detail', {
                parent: 'entity',
                url: '/proveedor/{nit}',
                data: {
                    authorities: ["ROLE_ADMIN"],
                    pageTitle: 'lauroxApp.proveedor.detail.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/entities/proveedor/proveedor-detail.html',
                        controller: 'ProveedorDetailController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('proveedor');
                        return $translate.refresh();
                    }],
                    entity: ['$stateParams', 'Proveedor', function($stateParams, Proveedor) {
                        return Proveedor.get({nit : $stateParams.nit});
                    }]
                }
            })
            .state('proveedor.new', {
                parent: 'proveedor',
                url: '/new',
                data: {
                    authorities: ["ROLE_ADMIN"],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/proveedor/proveedor-dialog.html',
                        controller: 'ProveedorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: function () {
                                return {nit: null, razonSocial: null, nombreContacto: null, telefono: null, celular: null, direccion: null};
                            }
                        }
                    }).result.then(function(result) {
                        $state.go('proveedor', null, { reload: true });
                    }, function() {
                        $state.go('proveedor');
                    })
                }]
            })
            .state('proveedor.edit', {
                parent: 'proveedor',
                url: '/{nit}/edit',
                data: {
                    authorities: ["ROLE_ADMIN"],
                },
                onEnter: ['$stateParams', '$state', '$modal', function($stateParams, $state, $modal) {
                    $modal.open({
                        templateUrl: 'scripts/app/entities/proveedor/proveedor-dialog.html',
                        controller: 'ProveedorDialogController',
                        size: 'lg',
                        resolve: {
                            entity: ['Proveedor', function(Proveedor) {
                                return Proveedor.get({nit : $stateParams.nit});
                            }]
                        }
                    }).result.then(function(result) {
                        $state.go('proveedor', null, { reload: true });
                    }, function() {
                        $state.go('^');
                    })
                }]
            });
    });
