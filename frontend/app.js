// Création du module principal 'app' avec ngRoute pour le routage
var app = angular.module('productApp', ['ngRoute']);


// Configuration des routes
app.config(['$routeProvider', function($routeProvider) {
    $routeProvider
	.when('/login', {
            template: `
                <h2>Connexion</h2>

                <form ng-submit="login()">
                    <input ng-model="auth.username" placeholder="Nom d'utilisateur" required /><br/>
                    <input type="password" ng-model="auth.password" placeholder="Mot de passe" required /><br/>
                    <button type="submit">Se connecter</button>
                </form>
		<p style="color:red" ng-if="errorMessage">{{errorMessage}}</p>
            `,
            controller: 'LoginController'
        })
        .when('/products', {
            template: `
	        <h2>Liste des produits</h2>

                <ul>
                    <li ng-repeat="p in products">
                        {{p.name}} - {{p.description}} - {{p.price}} €a
			<!--  mémorise le produit sélectionné avant de naviguer vers /edit -->
			<a href="#!/edit" ng-click="selectProduct(p)">Modifier</a>
                    </li>
                </ul>
		<button ng-if="isLoggedIn()" ng-click="logout()">Déconnexion</button>
            `,
            controller: 'ProductListController'
        })
        .when('/edit', {
            template: `
                <h2>Modifier un produit</h2>

                <form ng-submit="updateProduct()">
                    <input ng-model="product.id" placeholder="ID" required readonly /><br/>
                    <input ng-model="product.name" placeholder="Nom" /><br/>
		    <input ng-model="product.description" placeholder="Description" /><br/>
                    <input type="number" ng-model="product.price" placeholder="Prix" /><br/>
                    <button type="submit">Modifier</button>
                </form>

		<button ng-click="logout()">Déconnexion</button>

                <p style="color:red">{{error}}</p>
                <p style="color:green">{{success}}</p>
            `,
            controller: 'ProductEditController'
        })
        .otherwise({ redirectTo: '/products' }); // redirection par défaut
}]);

