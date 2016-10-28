'use strict';

angular.module('lauroxApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('acercade', {
                parent: 'ayuda',
                url: '/acercade',
                data: {
                    authorities: ['ROLE_USER'],
                    pageTitle: 'lauroxApp.producto.home.title'
                },
                views: {
                    'content@': {
                        templateUrl: 'scripts/app/ayuda/acercade/acercade.html',
                        controller: 'ProductoController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('acercade');
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
            
    });
