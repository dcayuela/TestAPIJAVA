angular.module('productApp').controller('ProductListController', ['$scope', 'ProductService', 'SelectedProductService', 'AuthService', '$location',
  function($scope, ProductService, SelectedProductService, AuthService, $location) {


    // Liste des produits
    $scope.products = [];

    // Charger les produits depuis le backend
    // Lecture publique : pas de login nécessaire
    ProductService.getAll().then(function(response) {
        $scope.products = response.data;
        console.log("Produits reçus :", $scope.products); // debug
    }, function(error) {
        console.error("Erreur chargement produits :", error);
    });

    // Fonction appelée lorsqu'on clique sur "Modifier"
    $scope.selectProduct = function(product) {
        SelectedProductService.set(product);
    };


    $scope.isLoggedIn = function() {
        return AuthService.isAuthenticated();
    };

    $scope.logout = function() {
        AuthService.setAuth(null);
        $location.path('/login');
    };
}]);

