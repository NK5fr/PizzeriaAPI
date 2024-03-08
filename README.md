# PIZZALAND Nathan DESMEE Armand SADY

## TABLES

La table de gestion des ingredient s'appelle **ingredients**.
Elle a pour clé primaire **id**.

```sql
create table ingredients(ino serial , inom text, prix int, constraint pk_ingredients primary key (ino));
```

La table de gestion des pizzas s'appelle **pizzas**.
Elle a pour clé primaire **id**.

```sql
create table pizzas(pno serial, pnom text not null unique, pate text, prixBase int, constraint pk_pizzas primary key (pno));
```

La table de gestion des ingrédients dans les pizzas s'appelle **compose**.
Elle a pour clé primaire la paire **(pno, ino)**.
Elle a pour clés étrangères **pno** qui fait référence à **pizzas(pno)** et **ino** qui fait référence à **ingredients(ino)**.

```sql
create table compose(pno int , ino int, constraint pk_compose primary key (pno, ino), 
constraint fk_pno foreign key (pno) references pizzas(pno) ON DELETE CASCADE ON UPDATE CASCADE, constraint fk_ino foreign key (ino) references ingredients(ino) ON DELETE CASCADE ON UPDATE CASCADE);
```

La table de gestion des utilisateurs s'appelle **utilisateurs**.
Elle a pour clé primaire **unom**.

```sql
create table utilisateurs(unom text, mdp text, constraints pk_utilisateurs primary key (nom));
```

La table de gestion des commandes s'appelle **commandes**
Elle a pour clé primaire **cno**

## REQUETES

### Ingredients

Voici l'ensemble des requêtes possibles sur les ingrédients :


- curl -i -X GET http://localhost:8080/pizzaland/ingredients **permet de récupérer la liste des ingrédients**

```
Cette requête appelle le méthode doGet du controleur ingredient. Dans cette méthode on récupère le pathInfo qui dans notre cas est vide ou null.
On récupère alors 
```



- curl -i -X GET http://localhost:8080/pizzaland/ingredients/{id} **permet de récupérer un ingrédient avec l'id voulu**



- curl -i -X GET http://localhost:8080/pizzaland/ingredients/{id}/name **permet de récupérer le nom d'un ingrédient avec l'id voulu**



- curl -i -H 'Content-Type: application/json' -d '{"nom":"{nom}","prix":{prix}}' -X PUT http://localhost:8080/pizzaland/ingredients **permet d'ajouter un ingrédient avec un nom et prix voulu**


- curl -i -X DELETE http://localhost:8080/pizzaland/ingredients/{id} **permet de supprimer un ingrédient avec l'id voulu**

## Ingredients

### Tables

Le gestion des ingrédients se fait grâce à une unique table.
Cette table s'appelle **ingredients**.

```sql
create table ingredients(ino serial , inom text, prix int, constraint pk_ingredients primary key (ino));
```

La table **ingredients** contient 3 attribut :

- ino, c'est l'identifiant de l'ingrédient. Il s'agit d'un numéro 
automatique qui sert également de clé primaire.

- inom, c'est le nom de type text de l'ingrédient.

- prix, c'est le prix de type int de l'ingrédient

### Liste Requêtes

| URI  | Opération | Réponse |
| :--- |:---------:| ----:|
| /ingredients | GET | liste des ingrédients |
| /ingredients/{id} | GET | l'ingrédient ou 404 |
| /ingredients/{id}/name | GET | le nom de l'ingrédient ou 404 |
| /ingredients | POST | l'ingrédient ou 409 |
| /ingredients/{id} | DELETE | rien ou 404 |
| /ingredients/{id} | PUT | l'ingrédient ou 404 |
| /ingredients/{id} | PATCH | l'ingrédient ou 404 |

### Détail Requêtes

#### GET /ingredients

#### GET /ingredients/{id}

#### GET /ingredients/{id}/name

#### POST /ingredients

#### DELETE /ingredients/{id}

#### PUT /ingredients/{id}

#### PATCH /ingredients/{id}
