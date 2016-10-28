'use strict';

angular.module('lauroxApp')
    .controller('LogoutController', function (Auth) {
        Auth.logout();
    });
