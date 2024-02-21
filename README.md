# PIZZALAND Nathan DESMEE Armand SADY

## TABLES

La table de gestion des ingredient s'appelle **ingredients**.
Elle a pour clé primaire **id**.

```sql
create table ingredients(id serial , nom text, prix int, constraint pk_ingredients primary key (id));
```

La table de gestion des pizzas s'appelle **pizzas**.
Elle a pour clé primaire **nom**.

```sql
create table pizzas(nom text , pate text, prixBase int, constraint pk_pizzas primary key (nom));
```

La table de gestion des ingrédients dans les pizzas s'appelle **compose**.
Elle a pour clé primaire la paire **(nom, id)**.
Elle a pour clés étrangères **nom** qui fait référence à **pizzas.nom** et **id** qui fait référence à **ingredients.id**.

```sql
create table compose(nom text , id int, constraint pk_compose primary key (nom, id), 
constraint fk_nom foreign key (nom) references pizzas(nom) ON DELETE CASCADE ON UPDATE CASCADE, constraint fk_id foreign key (id) references ingredients(id) ON DELETE CASCADE ON UPDATE CASCADE);
```

La table de gestion des utilisateurs s'appelle **utilisateurs**.
Elle a pour clés primaire **id**.

```sql
create table utilisateurs(id serial, nom text, mdp text, token text, constraints pk_utilisateurs primary key (id));
```

## REQUETES

### Ingredients

Voici l'ensemble des requêtes possibles sur les ingrédients :

- curl -i -X GET http://localhost:8080/pizzaland/ingredients **permet de récupérer la liste des ingrédients**
- curl -i -X GET http://localhost:8080/pizzaland/ingredients/{id} **permet de récupérer un ingrédient avec l'id voulu**
- curl -i -X GET http://localhost:8080/pizzaland/ingredients/{id}/name **permet de récupérer le nom d'un ingrédient avec l'id voulu**
- curl -i -H 'Content-Type: application/json' -d '{"nom":"{nom}","prix":{prix}}' -X PUT http://localhost:8080/pizzaland/ingredients **permet d'ajouter un ingrédient avec un nom et prix voulu**
- curl -i -X DELETE http://localhost:8080/pizzaland/ingredients/{id} **permet de supprimer un ingrédient avec l'id voulu**