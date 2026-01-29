// Service pour partager le produit sélectionné entre controller
angular.module('productApp').service('SelectedProductService', function() {
    let selectedProduct = null;

    // Stocke une copie du produit pour éviter de modifier la liste originale
    this.set = function(product) {
        // on fait une copie pour ne pas modifier l'objet de la liste directement
        selectedProduct = angular.copy(product);
    };

    // Retourne le produit sélectionné
    this.get = function() {
        return selectedProduct;
    };
});

