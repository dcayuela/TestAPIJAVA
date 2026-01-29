// Service pour accéder à l'API produits
angular.module('productApp').service('ProductService', ['$http', function($http) {

    const API_URL = "http://192.168.50.10:8080/api/products";  // URL du service Sprint Boot

    // Lecture publique des produits
    this.getAll = function() {
        return $http.get(API_URL);
    };

    // Modification (authentifiée)
    this.update = function(product, auth) {
        return $http.put(API_URL + "/" + product.id, product, {
            headers: {
                "Authorization": "Basic " + btoa(auth.username + ":" + auth.password)
            }
        });
    };
}]);

