angular.module('productApp').controller('ProductEditController', ['$scope', 'ProductService', 'SelectedProductService', 'AuthService', '$location',
  function($scope, ProductService, SelectedProductService, AuthService, $location) {


    // Vérifie que le service est bien injecté
    console.log("AuthService =", AuthService);

    if (!AuthService.isAuthenticated()) {
        $location.path('/login');
        return;
    }

    // Produit prérempli depuis la sélection
    $scope.product = SelectedProductService.get() || {};

    // Authentification (à remplacer par la gestion réelle des comptes)
    $scope.auth = AuthService.getAuth();
    //$scope.auth = { username: 'admin', password: 'admin' };

    
    // Fonction appelée lors de la soumission du formulaire
    $scope.updateProduct = function() {
        ProductService.update($scope.product, $scope.auth).then(
            function(response) {
                alert("Produit modifié !");
                $location.path('/products');
            },
            function(error) {
                if (error.status === 401) {
                    alert("Login ou mot de passe incorrect !");
                } else if (error.status === 403) {
                    alert("Vous n'avez pas le droit de modifier ce produit !");
                } else {
                    alert("Erreur lors de la modification du produit.");
                }
            }
	);
    };
    
    $scope.logout = function() {
        AuthService.setAuth(null);
        $location.path('/login');
    };
}]);

