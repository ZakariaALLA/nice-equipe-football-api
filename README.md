# Nice Equipe Football API

Une API RESTful pour la gestion des équipes de football, construite avec Spring Boot et les technologies associées.

---

## Technologies Utilisées

- **Java :** 21
- **Spring Boot :** 3.4.4
    - Spring Boot Starter Data JPA
    - Spring Boot Starter Validation
    - Spring Boot Starter Web
    - Spring Boot Starter Security
    - Spring Boot Starter Test
- **Springdoc OpenAPI Starter WebMVC UI :** 2.8.5
- **Driver JDBC PostgreSQL :** (dépendance runtime ; version gérée par Spring Boot)
- **JSON Web Token (JWT) :** 0.11.5 (pour `jjwt-api`, `jjwt-impl` et `jjwt-jackson`)
- **Lombok :** 1.18.36
- **H2 Database :** (pour les tests ; version gérée par Spring Boot)

---

## Exécuter l'Application avec l'image Docker
- Pour executer l'aplication avec Docker il suffit de lancer la commande suivante :
```bash
docker-compose up
```
---
Sinon vous pouvez telecharger le code source et lancer locallement l'application en suivant les étapes suivantes :
## Guide d'Installation

### Prérequis

- **Java JDK 21 :** Assurez-vous que le JDK 21 est installé et correctement configuré.
- **Git :** Pour cloner le repo.
- **IntelliJ IDEA**.
- **Maven**.
- **Postgresql**.

### Clonage du Dépôt

Ouvrez votre terminal et exécutez la commande suivante :

```bash
git clone https://github.com/ZakariaALLA/nice-equipe-football-api.git
```

## Importer dans IntelliJ IDEA

- **Ouvrez IntelliJ IDEA.**

- **Importer le projet :**
    - Cliquez sur **File > Open** et naviguez jusqu'au dossier du dépôt cloné.
    - Sélectionnez le fichier `pom.xml` lorsque cela est demandé.
    - IntelliJ détectera le projet Maven et importera automatiquement toutes les dépendances.

- **Attendez l'indexation :**  
  Laissez IntelliJ indexer et télécharger toutes les dépendances Maven requises.

---

## Configuration de la Base de Données

- **Créer une Base de Données :**  
  Connectez-vous à votre serveur PostgreSQL et créez une nouvelle base de données:
  ```sql
  CREATE DATABASE football_nice_db;
  ```

---

## Exécuter l'Application avec le Profil Dev

- **Localisez la classe principale :**  
  Trouvez la classe annotée avec `@SpringBootApplication` c'est `NiceEquipeFootballApiApplication`

- **Créer une configuration d'exécution :**
    - Allez dans **Run > Edit Configurations...**.
    - Cliquez sur le bouton **+** et sélectionnez **Spring Boot**.
    - Définissez la classe **NiceEquipeFootballApiApplication**.

    - **Activer le Profil Dev :**  
      Dans le champ **Active profiles**, entrez : `dev`
    - **Mettre à Jour les Informations d'Identification :**
      Mettez à jour les variables d'environnement suivantes avec vos informations de base de données :
      Sur `Modify Options` > `Environment Variables` :
      ```bash 
      SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/football_nice_db
      SPRING_DATASOURCE_USERNAME=postgres
      SPRING_DATASOURCE_PASSWORD=postgres
      ```
    - **Exécuter l'Application :**  
      Cliquez sur le bouton **Run** pour démarrer l'application avec le profil de développement.
    - **Accéder à l'API :**
      Pour alimenter la base de données au démarrage, le parametre d'initalisation des données
      ```yml
      data:
        initialize:
          enabled: true
      ```
      est activé par défaut, vous pouvez le désactiver en mettant `false`

      Acceder à le URL
      `http://localhost:8080/nice-foot-api/swagger-ui/index.html#/` pour voir swagger UI.

      Vous allez trouver une `collection postman` à la racine de projet; importer la sur postman et tester les WS

      Spring Security est acctivé; donc il faut tout dabord s'inscrire sur l'application en utilisant le web
      service `POST: /nice-foot-api/auth/signup` avec ce json
      ```json
      {
        "username": "admin",
        "password": "admin",
        "roles": [
          "ADMIN",
          "USER"
        ]
      }
      ```
      L'application va vous fournir un token, qu'il faut utiliser pour consomer les autres Web Services.

      Il faut le mettre au niveau de postman sur `Autorization > Type: Bearer Token` et coller le token dans le
      champ `Token`.

  Les deux endpoints sont publiques :
  ```
  GET /nice-foot-api/equipes/**
  GET /nice-foot-api/positions/**
  ```

