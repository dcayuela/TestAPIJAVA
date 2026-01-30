Une application Java Spring Boot qui se connecte à MySQL et expose une API REST utilisée par un front web AngularJS.

Prérequis:
    Applications Vagrant et VirtualBox installées
    Créer un répertoire et y déposer le fichier Vagrantfile
    Pour démarrer l'environnement et  l'application :
        => Tapez: vagrant UP
    Pour supprimer l'environnement
        => Tapez: vagrant destroy -f

    Pour se connecter à la VM docker de l'enrironnement
        => Tapez: vagrant ssh


Structure des fichiers :
------------------------

Créer un répertoire principal pour le projet, puis à l'intérieur, créer un sous-répertoire nommé "app" avec la structure suivante :

.
|-- docker-compose.yml
|__ app/
    |-- Dockerfile
    |-- pom.xml
    |__ src/
        |__ main/
            |-- java/
            |   |__ com/
            |       |__ example/
            |           |__ demo/
            |               |__ config/
            |               |    |-- OpenApiConfig.java      // Pour la génération du Swagger
            |               |    |__ SecurityConfig.java
            |               |__ controller/
            |               |    |-- LoginController.java
            |               |    |-- ProductController.java
            |               |    |__ UserController.java
            |               |__ entity/
            |               |    |-- Product.java
            |               |    |-- Role.java
            |               |    |__ User.java
            |               |__ init/
            |               |    |-- AdminUserInitializer.java
            |               |    |-- ProductTableInitializer.java
            |               |    |__ RoleInitializer.java
            |               |__ repository/
            |               |    |-- ProductRepository.java
            |               |    |-- RoleRepository.java
            |               |    |__ UserRepository.java
            |               |__ service/
            |               |    |-- CustoUserDetailsService.java
            |               |    |-- ProductService.java
            |               |    |__ UserService.java
            |               |__ DemoApplication.java
            |__ resources/
                |__ application.properties

1. Le fichier docker-compose.yml

Ce fichier orchestrera les deux services: db (MySQL) et front-app (l'application Spring Boot)

  - build: ./app : Docker Compose va construire l'image de l'application Java à partir du Dockerfile situé dans e sous-répertoire app
  - ports: - "8080:8080" : L'application Sprint Boot écoutera par défaut sur le port 8080 à l'intérieur du conteneur, et ce port est mappé au port 8080 de la machine hôte.
  - environments: Ces variables sont passées au conteneur de l'application Java. Spring Boot les utilise pour configurer la connexion à la base de données.
      SPRING_DATASOURCE_URL utilise db comme hôte, car c'est le nom du service MySQL dans le réseau Docker Compose.
      SPRING_JPA_HIBERNATE_DDL_AUTO: update permet à Spring Boot de créer automatiquement la table users si elle n'existe pas.

2. Le fichier app/Dockerfile

Ce fichier contient les instructions pour construire l'image Docker de l'application Spring Boot en utilisant une approche multi-stage pour optimiser la taille de l'image finale.

3. Le fichier app/pom.xml

C'est le fichier de configuration Maven pour l'application Spring Boot, listant les dépendances nécessaires.

4. Le fichier app/src/main/java/com/example/demo/DemoApplication.java

C'est la classe principale de l'application Spring Boot. Elle inclut également une logique pour insérer des données de test au démarrage si la table users est vide.

5. Le fichier app/src/min/lava/com/example/demo/User.java

C'est l'entité JPA qui représente la table users dans la base de données.

6. Le fichier app/src/main/java/com/example/demo/UserRepository.java

C'est l'interface du dépôt Spring Data JPA, qui fournit automatiquement les opérations CRUD pour l'entité User.

7. Le fichier app/src/main/java/com/example/demo/UserController.java

C'est le contrôleur REST qui expose les endpoints pour inetragir avec les données des utilisateurs.

8. Le fichier app/src/main/resources/application.properties

Ce fichier contient la configuration de base de l'application Spring Boot. Les détails de connexion à la base de données sont fournis via les variables d'environnement dans docker-compose.yml. Donc ce fichier peut être minimal.


Lancer l'exemple :

- Ouvrir un navigateur ou utiliser un outil comme curl pour tester l'API :

  - Accéder à l'application front-end: http://192.168.50.20:8080/api/products
  - Accéder à l'endpoint des utilisateurs: curl -v http://192.168.50.10:8080/api/users -u admin:admin

Vous devriez voir une réponse JSON avec les données des utilisateurs


- Pour accéder au swagger de l'API : http://192.168.50.10:8080/swagger-ui/index.html


- Pour accéder à l'administration de la base de données MySQL: http://192.168.50.10:8081
    => Login:    rest_user
    => Password: rest_password

- Pour accéder au Front WEB de l'application : http://192.168.50.10:4200
