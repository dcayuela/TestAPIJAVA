angular.module('productApp').controller('LoginController', ['$scope', '$location', 'AuthService', '$http',
  function($scope, $location, AuthService, $http) {

    $scope.auth = { username: '', password: '' };
    $scope.errorMessage = '';

    $scope.login = function() {
        $http.post("http://192.168.50.10:8080/api/login/check", $scope.auth)
            .then(function() {
                // Login OK
                AuthService.setAuth($scope.auth);
                $location.path('/products');
            })
            .catch(function(error) {
                if (error.status === 401) {
                    $scope.errorMessage = "Login inconnu ou mot de passe incorrect !";
		} else {
		    $scope.errorMessage = "Erreur serveur";
                }
            });
    };
}]);

