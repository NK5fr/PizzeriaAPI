# PIZZALAND Nathan DESMEE Armand SADY

## TABLES

La table de gestion des ingredient s'appelle **ingredients**.
Elle a pour clé primaire **id**.

```sql
create table ingredients(ino serial , nom text, prix int, constraint pk_ingredients primary key (ino));
```

La table de gestion des pizzas s'appelle **pizzas**.
Elle a pour clé primaire **id**.

```sql
create table pizzas(pno serial, nom text not null unique, pate text, prixBase int, constraint pk_pizzas primary key (pno));
```

La table de gestion des ingrédients dans les pizzas s'appelle **compose**.
Elle a pour clé primaire la paire **(pno, ino)**.
Elle a pour clés étrangères **pno** qui fait référence à **pizzas(pno)** et **ino** qui fait référence à **ingredients(ino)**.

```sql
create table compose(pno int , ino int, constraint pk_compose primary key (pno, ino), 
constraint fk_pno foreign key (pno) references pizzas(pno) ON DELETE CASCADE ON UPDATE CASCADE, constraint fk_ino foreign key (ino) references ingredients(ino) ON DELETE CASCADE ON UPDATE CASCADE);
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