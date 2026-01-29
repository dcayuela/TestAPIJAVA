angular.module('productApp').service('AuthService', function() {
    let auth = null;

    this.setAuth = function(credentials) {
        auth = credentials;
    };

    this.getAuth = function() {
        return auth;
    };

    this.isAuthenticated = function() {
        return auth !== null;
    };
});

