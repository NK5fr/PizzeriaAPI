# PIZZALAND Nathan DESMEE Armand SADY

## Ingredients

### Tables

Le gestion des ingrédients se fait grâce à une unique table.
Cette table s'appelle **ingredients**, elle contient la liste des ingrédients.

```sql
create table ingredients(ino serial , inom text unique not null, prix int, constraint pk_ingredients primary key (ino));
```

La table **ingredients** contient 3 attribut :

- ino, c'est l'identifiant de l'ingrédient. Il s'agit d'un numéro 
automatique qui sert également de clé primaire.

- inom, c'est le nom de type text de l'ingrédient. Il est unique et ne peut pas être null.

- prix, c'est le prix de type int de l'ingrédient

### Liste Requêtes

| URI  | Opération | Réponse |
| :--- |:---------:| ----:|
| /ingredients | GET | liste des ingrédients |
| /ingredients/{id} | GET | l'ingrédient ou 404 |
| /ingredients/{id}/name | GET | le nom de l'ingrédient ou 404 |
| /ingredients | POST | l'ingrédient ou 409 |
| /ingredients/{id} | DELETE | rien ou 404 |
| /ingredients/{id} | PUT | l'ingrédient, 404 ou 409 |
| /ingredients/{id} | PATCH | l'ingrédient, 404 ou 409|

### Détail Requêtes

#### GET /ingredients

Cette requête permet de récupérer la liste des ingrédients.

Le retour est de type **application/json**. Elle ne nécessite pas de données en entrée.

Voici un exemple de retour :

```json
[
    {
        "id": 1,
        "nom": "tomate",
        "prix": 1
    },
    {
        "id": 2,
        "nom": "fromage",
        "prix": 2
    }
]
```

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |

#### GET /ingredients/{id}

Cette requête permet de récupérer un ingrédient avec l'id voulu.

Le retour est de type **application/json**. Elle ne nécessite pas de données en entrée.

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "nom": "tomate",
    "prix": 1
}
```

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | L'ingrédient n'existe pas |

#### GET /ingredients/{id}/name

Cette requête permet de récupérer le nom d'un ingrédient avec l'id voulu.

Le retour est de type **application/json**. Elle ne nécessite pas de données en entrée.

Voici un exemple de retour avec **id = 1** et **nom = tomate** :

```json
{
    "nom": "tomate"
}
```

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | L'ingrédient n'existe pas |

#### POST /ingredients

Cette requête permet d'ajouter un ingrédient.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici un exemple de données en entrée :

```json
{
    "nom": "tomate",
    "prix": 1
}
```

Voici un exemple de retour :

```json
{
    "id": 1,
    "nom": "tomate",
    "prix": 1
}
```

La valeur de **id** est automatiquement générée donc inutile de la mettre en entrée. De plus il faut faire attention à ne pas mettre un **nom** d'ingrédient déjà existant ou null. Si le prix n'est pas rentré, il est mis à 0.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 409 Conflict | Le nom de l'ingrédient existe déjà ou est null |

#### DELETE /ingredients/{id}

Cette requête permet de supprimer un ingrédient avec l'id voulu.

Il n'y a pas de retour de données. Elle ne nécessite pas de données en entrée.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | L'ingrédient n'existe pas |

#### PUT /ingredients/{id}

Cette requête permet de modifier un ingrédient avec l'id voulu en supprimant les données non rentrée.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici l'ingrédient avec **id = 1** avant la modification :

```json
{
    "id": 1,
    "nom": "tomate",
    "prix": 1
}
```

Voici un exemple de données en entrée avec **id = 1** :

```json
{
    "nom": "tomate cerise"
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "nom": "tomate cerise",
    "prix": 0
}
```
L'**id** n'est pas modifiable, seul le nom et le prix le sont. Impossible de le mettre en données d'entrée. Le **prix** est mis à 0 car il n'a pas été rentré (0 est la valeur par défaut d'un entier). De plus il faut faire attention à ne pas mettre un **nom** d'ingrédient déjà existant ou null (le nom est null si on le met pas dans les données entrée).

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | L'ingrédient n'existe pas |
| 409 Conflict | Le nom de l'ingrédient existe déjà ou est null |

#### PATCH /ingredients/{id}

Cette requête permet de modifier un ingrédient avec l'id voulu en modifiant uniquement les données rentrée.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici l'ingrédient avec **id = 1** avant la modification :

```json
{
    "id": 1,
    "nom": "tomate",
    "prix": 1
}
```

Voici un exemple de données en entrée avec **id = 1** :

```json
{
    "prix": 10
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "nom": "tomate",
    "prix": 10
}
```

L'**id** n'est pas modifiable, seul le nom et le prix le sont. Impossible de le mettre en données d'entrée. De plus il faut faire attention à ne pas mettre un **nom** d'ingrédient déjà existant ou null. Il est impossible également de mettre le prix à 0 de cette manière.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | L'ingrédient n'existe pas |
| 409 Conflict | Le nom de l'ingrédient existe déjà ou est null |

## Pizzas

### Tables

La gestion des pizzas se fait grâce à une deux tables.

La première table s'appelle **pizzas**, elle contient la liste des pizzas.

```sql
create table pizzas(pno serial, pnom text unique not null , pate text, prixBase int, constraint pk_pizzas primary key (pno));
```

La table **pizzas** contient 4 attribut :

- pno, c'est l'identifiant de la pizza. Il s'agit d'un numéro 
automatique qui sert également de clé primaire.

- pnom, c'est le nom de type text de la pizza. Il est unique et ne peut pas être null.

- pate, c'est la pâte de type text de la pizza.

- prixBase, c'est le prix de base de type int de la pizza

La deuxième table s'appelle **compose**, elle contient l'ensemble des associations entre pizzas et ingrédients.

```sql
create table compose(pno int , ino int, constraint pk_compose primary key (pno, ino), 
constraint fk_pno foreign key (pno) references pizzas(pno) ON DELETE CASCADE ON UPDATE CASCADE, constraint fk_ino foreign key (ino) references ingredients(ino) ON DELETE CASCADE ON UPDATE CASCADE);
```

La table **compose** contient 2 attribut :

- pno, c'est l'identifiant de la pizza. Il s'agit d'une clé étrangère qui fait référence à **pizzas(pno)**.

- ino, c'est l'identifiant de l'ingrédient. Il s'agit d'une clé étrangère qui fait référence à **ingredients(ino)**.

- Le coupe **(pno, ino)** représente une assciation pizza-ingredient et sert de clé primaire.

En cas de suppréssion ou modification d'un ingrédient ou d'une pizza, le référence dans compose sera supprimée ou mise à jour.

### Liste Requêtes

| URI  | Opération | Réponse |
| :--- |:---------:| ----:|
| /pizzas | GET | liste des pizzas |
| /pizzas/{id} | GET | la pizza ou 404 |
| /pizzas/{id}/prixfinal | GET | le nom de la pizza ou 404 |
| /pizzas | POST | la pizza ou 409 |
| /pizzas/{id} | POST | la pizza ou 404 |
| /pizzas/{id} | DELETE | rien ou 404 |
| /pizzas/{id}/{idIngredient} | DELETE | la pizza ou 404 |
| /pizzas/{id} | PUT | la pizza, 404 ou 409|
| /pizzas/{id} | PATCH | la pizza, 404 ou 409|

### Détail Requêtes

#### GET /pizzas

Cette requête permet de récupérer la liste des pizzas.

Le retour est de type **application/json**. Elle ne nécessite pas de données en entrée.

Voici un exemple de retour :

```json
[
    {
        "id": 1,
        "nom": "regina",
        "pate": "pate à pizza",
        "prixBase": 50,
        "ingredients": [
            {
                "id": 1,
                "nom": "tomate",
                "prix": 1
            },
            {
                "id": 2,
                "nom": "fromage",
                "prix": 2
            }
        ]
    },
    {
        "id": 2,
        "nom": "4 fromages",
        "pate": "pate à pizza",
        "prixBase": 60,
        "ingredients": [
            {
                "id": 2,
                "nom": "fromage",
                "prix": 2
            },
            {
                "id": 3,
                "nom": "emmental",
                "prix": 3
            
            }
        ]
    }
]
```

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |

#### GET /pizzas/{id}

Cette requête permet de récupérer une pizza avec l'id voulu.

Le retour est de type **application/json**. Elle ne nécessite pas de données en entrée.

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "nom": "regina",
    "pate": "pate à pizza",
    "prixBase": 50,
    "ingredients": [
        {
            "id": 1,
            "nom": "tomate",
            "prix": 1
        },
        {
            "id": 2,
            "nom": "fromage",
            "prix": 2
        }
    ]
}
```

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La pizza n'existe pas |

#### GET /pizzas/{id}/prixfinal

Cette requête permet de récupérer le prix final d'une pizza avec l'id voulu.

Le retour est de type **application/json**. Elle ne nécessite pas de données en entrée.

Le prix final est le prix de base de la pizza additionné au prix de chaque ingrédient.

Voici la pizza avec **id = 1** :

```json
{
    "id": 1,
    "nom": "regina",
    "pate": "pate à pizza",
    "prixBase": 50,
    "ingredients": [
        {
            "id": 1,
            "nom": "tomate",
            "prix": 1
        },
        {
            "id": 2,
            "nom": "fromage",
            "prix": 2
        }
    ]
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "prixfinal": 53
}
```

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La pizza n'existe pas |

#### POST /pizzas

Cette requête permet d'ajouter une pizza.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici un exemple de données en entrée :

```json
{
    "nom": "regina",
    "pate": "pate à pizza",
    "prixBase": 50,
    "ingredients": [
        {
            "id": 1
        },
        {
            "id": 2
        }
    ]
}
```

Voici un exemple de retour :

```json
{
    "id": 1,
    "nom": "regina",
    "pate": "pate à pizza",
    "prixBase": 50,
    "ingredients": [
        {
            "id": 1,
            "nom": "tomate",
            "prix": 1
        },
        {
            "id": 2,
            "nom": "fromage",
            "prix": 2
        }
    ]
}
```

L'**id** de la pizza est automatiquement généré donc inutile de la mettre en entrée. Pour éviter de réecrire entièrement les ingrédients à chaque fois, il suffit de mettre les **id** des ingrédients dans le tableau **ingredients**. De plus il faut faire attention à ne pas mettre un **nom** de pizza déjà existant ou null (le nom est null si il n'est pas mis dans les données d'entrée). Si le prix de base n'est pas rentré, il est mis à 0.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 409 Conflict | Le nom de la pizza est null ou déjà existant ou l'un des ingrédient n'existe pas|

#### POST /pizzas/{id}

Cette requête permet d'ajouter un ingrédient à une pizza avec l'id voulu.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici les données de la pizza avec **id = 1** avant l'ajout :

```json
{
    "id": 1,
    "nom": "regina",
    "pate": "pate à pizza",
    "prixBase": 50,
    "ingredients": [
        {
            "id": 1,
            "nom": "tomate",
            "prix": 1
        },
        {
            "id": 2,
            "nom": "fromage",
            "prix": 2
        }
    ]
}
```

Voici un exemple de données en entrée avec **id = 1** :

```json
{
    "id": 3
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "nom": "regina",
    "pate": "pate à pizza",
    "prixBase": 50,
    "ingredients": [
        {
            "id": 1,
            "nom": "tomate",
            "prix": 1
        },
        {
            "id": 2,
            "nom": "fromage",
            "prix": 2
        },
        {
            "id": 3,
            "nom": "emmental",
            "prix": 3
        }
    ]
}
```

Pour éviter de réecrire entièrement les ingrédients à chaque fois, il suffit de mettre les **id** des ingrédients dans le tableau **ingredients**.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La pizza ou l'ingrédient n'existe pas |

#### DELETE /pizzas/{id}

Cette requête permet de supprimer une pizza avec l'id voulu.

Il n'y a pas de retour de données. Elle ne nécessite pas de données en entrée.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La pizza n'existe pas |


#### DELETE /pizzas/{id}/{idIngredient}

Cette requête permet de supprimer un ingrédient d'une pizza avec l'id voulu.

Il n'y a pas de retour de données. Elle ne nécessite pas de données en entrée.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | Le couple pizza-ingrédient n'existe pas |

#### PUT /pizzas/{id}

Cette requête permet de modifier une pizza avec l'id voulu en supprimant les données non rentrée.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici la pizza avec **id = 1** avant la modification :

```json
{
    "id": 1,
    "nom": "regina",
    "pate": "pate à pizza",
    "prixBase": 50,
    "ingredients": [
        {
            "id": 1,
            "nom": "tomate",
            "prix": 1
        },
        {
            "id": 2,
            "nom": "fromage",
            "prix": 2
        }
    ]
}
```

Voici un exemple de données en entrée avec **id = 1** :

```json
{
    "nom": "regina",
    "prixBase": 150,
    "ingredients": [
        {
            "id": 1
        }
    ]
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "nom": "regina",
    "pate": null,
    "prixBase": 150,
    "ingredients": [
        {
            "id": 1,
            "nom": "tomate",
            "prix": 1
        }
    ]
}
```

L'**id** n'est pas modifiable. Impossible de le mettre en données d'entrée. De plus il faut faire attention à ne pas mettre un **nom** de pizza déjà existant ou null (le nom est null si il n'est pas mis dans les données d'entrée). Si le prix de base n'est pas rentré, il est mis à 0. Pour éviter de réecrire entièrement les ingrédients à chaque fois, il suffit de mettre les **id** des ingrédients dans le tableau **ingredients**.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La pizza n'existe pas |
| 409 Conflict | Le nom de la pizza est null ou déjà existant ou un des ingrédients n'existe pas |


#### PATCH /pizzas/{id}

Cette requête permet de modifier une pizza avec l'id voulu en modifiant uniquement les données rentrée.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici la pizza avec **id = 1** avant la modification :

```json
{
    "id": 1,
    "nom": "regina",
    "pate": "pate à pizza",
    "prixBase": 50,
    "ingredients": [
        {
            "id": 1,
            "nom": "tomate",
            "prix": 1
        },
        {
            "id": 2,
            "nom": "fromage",
            "prix": 2
        }
    ]
}
```

Voici un exemple de données en entrée avec **id = 1** :

```json
{
    "prixBase": 150
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "nom": "regina",
    "pate": "pate à pizza",
    "prixBase": 150,
    "ingredients": [
        {
            "id": 1,
            "nom": "tomate",
            "prix": 1
        },
        {
            "id": 2,
            "nom": "fromage",
            "prix": 2
        }
    ]
}
```

L'**id** n'est pas modifiable. Impossible de le mettre en données d'entrée. De plus il faut faire attention à ne pas mettre un **nom** de pizza déjà existant ou null (le nom est null si il n'est pas mis dans les données d'entrée). Il est impossible également de mettre le prix de base à 0 de cette manière. Pour éviter de réecrire entièrement les ingrédients à chaque fois, il suffit de mettre les **id** des ingrédients dans le tableau **ingredients**.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La pizza n'existe pas |
| 409 Conflict | Le nom de la pizza est null ou déjà existant ou un des ingrédients n'existe pas |

## Commandes

### Tables

La gestion des commandes se fait grâce à une trois tables.

La première table s'appelle **clients**, elle contient la liste des clients.

```sql
create table clients(cnom text, mdp text not null, constraint pk_clients primary key (cnom));
```

La table **clients** contient 3 attribut :

- cnom, c'est le nom de type text du client. Il sert de clé primaire.

- mdp, c'est le mot de passe de type text du client crypté en MD5. Il ne peut pas être null.


La deuxième table s'appelle **commandes**, elle contient la liste des commandes.

```sql
create table commandes(cno serial, cnom text, date date, constraint pk_commandes primary key (cno), constraint fk_cnom foreign key (cnom) references clients(cnom) ON DELETE CASCADE ON UPDATE CASCADE);
```

La table **commandes** contient 4 attribut :

- cno, c'est l'identifiant de la commande. Il s'agit d'un numéro automatique qui sert également de clé primaire.

- cnom, c'est le nom de type text du client. C'est une clé étrangère qui fait référence à **clients(cnom)**.

- date, c'est la date de type date de la commande.

En cas de suppréssion ou modification d'un client, le référence dans commandes sera supprimée ou mise à jour.

La troisième table s'appelle **contient**, elle contient l'ensemble des associations entre commandes et pizzas.

```sql
create table contient(cno int , pno int, qte int, constraint pk_contient primary key (cno, pno),
constraint fk_cno foreign key (cno) references commandes(cno) ON DELETE CASCADE ON UPDATE CASCADE, constraint fk_pno foreign key (pno) references pizzas(pno) ON DELETE CASCADE ON UPDATE CASCADE);
```


La table **contient** contient 3 attribut :

- cno, c'est l'identifiant de la commande. Il s'agit d'une clé étrangère qui fait référence à **commandes(cno)**.

- pno, c'est l'identifiant de la pizza. Il s'agit d'une clé étrangère qui fait référence à **pizzas(pno)**.

- qte, c'est la quantité de type int de la pizza.

En cas de suppréssion ou modification d'une commande ou d'une pizza, le référence dans contient sera supprimée ou mise à jour.

### Liste Requêtes

| URI  | Opération | Réponse |
| :--- |:---------:| ----:|
| /commandes | GET | liste des commandes |
| /commandes/{id} | GET | la commande ou 404 |
| /commandes/{id}/prixfinal | GET | le prix final de la commande ou 404 |
| /commandes | POST | la commande ou 409 |
| /commandes/{id} | POST | la commande ou 404 |
| /commandes/{id} | DELETE | rien ou 404 |
| /commandes/{id}/{idPizza} | DELETE | la commande ou 404 |
| /commandes/{id} | PUT | la commande, 404 ou 409|
| /commandes/{id} | PATCH | la commande, 404 ou 409|

### Détail Requêtes

#### GET /commandes

Cette requête permet de récupérer la liste des commandes.

Le retour est de type **application/json**. Elle ne nécessite pas de données en entrée.

Voici un exemple de retour :

```json
[
    {
        "id": 1,
        "client": "nathan",
        "date": "2021-01-01",
        "pizzas": [
            {
                "id": 1,
                "nom": "regina",
                "pate": "pate à pizza",
                "prixBase": 50,
                "ingredients": [
                    {
                        "id": 1,
                        "nom": "tomate",
                        "prix": 1
                    },
                    {
                        "id": 2,
                        "nom": "fromage",
                        "prix": 2
                    }
                ],
                "qte": 2
            }
        ]
    },
    {
        "id": 2,
        "client": "armand",
        "date": "2021-01-02",
        "pizzas": [
            {
                "id": 2,
                "nom": "4 fromages",
                "pate": "pate à pizza",
                "prixBase": 60,
                "ingredients": [
                    {
                        "id": 2,
                        "nom": "fromage",
                        "prix": 2
                    },
                    {
                        "id": 3,
                        "nom": "emmental",
                        "prix": 3
                    }
                ],
                "qte": 1
            }
        ]
    }
]
```

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |

#### GET /commandes/{id}

Cette requête permet de récupérer une commande avec l'id voulu.

Le retour est de type **application/json**. Elle ne nécessite pas de données en entrée.


Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "client": "nathan",
    "date": "2021-01-01",
    "pizzas": [
        {
            "id": 1,
            "nom": "regina",
            "pate": "pate à pizza",
            "prixBase": 50,
            "ingredients": [
                {
                    "id": 1,
                    "nom": "tomate",
                    "prix": 1
                },
                {
                    "id": 2,
                    "nom": "fromage",
                    "prix": 2
                }
            ],
            "qte": 2
        }
    ]
}
```

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La commande n'existe pas |


#### GET /commandes/{id}/prixfinal

Cette requête permet de récupérer le prix final d'une commande avec l'id voulu.

Le retour est de type **application/json**. Elle ne nécessite pas de données en entrée.

Le prix final est l'addition des prix finaux de chaque pizza.

Voici la commande avec **id = 1** :

```json
{
    "id": 1,
    "client": "nathan",
    "date": "2021-01-01",
    "pizzas": [
        {
            "id": 1,
            "nom": "regina",
            "pate": "pate à pizza",
            "prixBase": 50,
            "ingredients": [
                {
                    "id": 1,
                    "nom": "tomate",
                    "prix": 1
                },
                {
                    "id": 2,
                    "nom": "fromage",
                    "prix": 2
                }
            ],
            "qte": 2
        }
    ]
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "prixfinal": 106
}
```

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La commande n'existe pas |

#### POST /commandes

Cette requête permet d'ajouter une commande.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici un exemple de données en entrée :

```json
{
    "client": "nathan",
    "pizzas": [
        {
            "id": 1,
            "qte": 2
        }
    ]
}
```

Voici un exemple de retour :

```json
{
    "id": 1,
    "client": "nathan",
    "date": "2021-01-01",
    "pizzas": [
        {
            "id": 1,
            "nom": "regina",
            "pate": "pate à pizza",
            "prixBase": 50,
            "ingredients": [
                {
                    "id": 1,
                    "nom": "tomate",
                    "prix": 1
                },
                {
                    "id": 2,
                    "nom": "fromage",
                    "prix": 2
                }
            ],
            "qte": 2
        }
    ]
}
```

L'**id** de la commande est automatiquement généré donc inutile de la mettre en entrée. La date est automatiquement celle au lancement de la requête docn inutile de la mettre en entrée. Pour éviter de réecrire entièrement les pizzas à chaque fois, il suffit de mettre les **id** des pizzas avec les **qte** dans le tableau **pizzas**. De plus il faut faire attention à mettre un **client** existant (le client est null si il n'est pas mis en entrée).

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 409 Conflict | Le nom du client est incorrect |

#### POST /commandes/{id}

Cette requête permet d'ajouter une pizza à une commande avec l'id voulu.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici les données de la commande avec **id = 1** avant l'ajout :

```json
{
    "id": 1,
    "client": "nathan",
    "date": "2021-01-01",
    "pizzas": [
        {
            "id": 1,
            "nom": "regina",
            "pate": "pate à pizza",
            "prixBase": 50,
            "ingredients": [
                {
                    "id": 1,
                    "nom": "tomate",
                    "prix": 1
                },
                {
                    "id": 2,
                    "nom": "fromage",
                    "prix": 2
                }
            ],
            "qte": 2
        }
    ]
}
```

Voici un exemple de données en entrée avec **id = 1** :

```json
{
    "id": 1,
    "qte": 1
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "client": "nathan",
    "date": "2021-01-01",
    "pizzas": [
        {
            "id": 1,
            "nom": "regina",
            "pate": "pate à pizza",
            "prixBase": 50,
            "ingredients": [
                {
                    "id": 1,
                    "nom": "tomate",
                    "prix": 1
                },
                {
                    "id": 2,
                    "nom": "fromage",
                    "prix": 2
                }
            ],
            "qte": 3
        }
    ]
}
```

Pour éviter de réecrire entièrement les pizzas à chaque fois, il suffit de mettre les **id** des pizzas avec les **qte** dans le tableau **pizzas**. Dans le cas où la pizza est déjà dans la commande, la **qte** sera augmentée de la **qte** rentrée sinon la pizza sera ajoutée à la commande avec la **qte** rentrée.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La commande ou la pizza n'existe pas |

#### DELETE /commandes/{id}

Cette requête permet de supprimer une commande avec l'id voulu.

Il n'y a pas de retour de données. Elle ne nécessite pas de données en entrée.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La commande n'existe pas |

#### DELETE /commandes/{id}/{idPizza}

Cette requête permet de supprimer une pizza d'une commande avec l'id voulu.

Il n'y a pas de retour de données. Elle ne nécessite pas de données en entrée.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La commande ou la pizza n'existe pas |

#### PUT /commandes/{id}

Cette requête permet de modifier une commande avec l'id voulu en supprimant les données non rentrée.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici la commande avec **id = 1** avant la modification :

```json
{
    "id": 1,
    "client": "nathan",
    "date": "2021-01-01",
    "pizzas": [
        {
            "id": 1,
            "nom": "regina",
            "pate": "pate à pizza",
            "prixBase": 50,
            "ingredients": [
                {
                    "id": 1,
                    "nom": "tomate",
                    "prix": 1
                },
                {
                    "id": 2,
                    "nom": "fromage",
                    "prix": 2
                }
            ],
            "qte": 2
        }
    ]
}
```

Voici un exemple de données en entrée avec **id = 1** :

```json
{
    "client": "armand"
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "client": "armand",
    "date": "2021-01-01",
    "pizzas": []
}
```

L'**id** n'est pas modifiable. Impossible de le mettre en données d'entrée. Il également pas possible de changer la **date** qui garde celle à la création de la commande. De plus il faut faire attention à mettre un **client** existant (le client est null si il n'est pas mis en entrée). Pour éviter de réecrire entièrement les pizzas à chaque fois, il suffit de mettre les **id** des pizzas avec les **qte** dans le tableau **pizzas**.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La commande n'existe pas |
| 409 Conflict | Le nom du client est incorrect |

#### PATCH /commandes/{id}

Cette requête permet de modifier une commande avec l'id voulu en modifiant uniquement les données rentrée.

Le retour est de type **application/json**. Elle nécessite des données en entrée de type **application/json**.

Voici la commande avec **id = 1** avant la modification :

```json
{
    "id": 1,
    "client": "nathan",
    "date": "2021-01-01",
    "pizzas": [
        {
            "id": 1,
            "nom": "regina",
            "pate": "pate à pizza",
            "prixBase": 50,
            "ingredients": [
                {
                    "id": 1,
                    "nom": "tomate",
                    "prix": 1
                },
                {
                    "id": 2,
                    "nom": "fromage",
                    "prix": 2
                }
            ],
            "qte": 2
        }
    ]
}
```

Voici un exemple de données en entrée avec **id = 1** :

```json
{
    "client": "armand"
}
```

Voici un exemple de retour avec **id = 1** :

```json
{
    "id": 1,
    "client": "armand",
    "date": "2021-01-01",
    "pizzas": [
        {
            "id": 1,
            "nom": "regina",
            "pate": "pate à pizza",
            "prixBase": 50,
            "ingredients": [
                {
                    "id": 1,
                    "nom": "tomate",
                    "prix": 1
                },
                {
                    "id": 2,
                    "nom": "fromage",
                    "prix": 2
                }
            ],
            "qte": 2
        }
    ]
}
```

L'**id** n'est pas modifiable. Impossible de le mettre en données d'entrée. Il également pas possible de changer la **date** qui garde celle à la création de la commande. De plus il faut faire attention à mettre un **client** existant (le client est null si il n'est pas mis en entrée). Pour éviter de réecrire entièrement les pizzas à chaque fois, il suffit de mettre les **id** des pizzas avec les **qte** dans le tableau **pizzas**.

Codes de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | La requête s'est bien effectuée |
| 404 Not Found | La commande n'existe pas |
| 409 Conflict | Le nom du client est incorrect |


## Token

### Récupération du token

Il est possible de récupérer un token à l'ENDPOINT **/users/token** avec son **nom** et **mdp**. Comme cela :

>/users/token?nom=<nom>&mdp=<mdp>

Le Token est alors retouné dans le **body** sous la forme d'un string. Il est aussi trouvable dans le **header** :

>Token : <token>

Code de retour :

| Code  | Description |
| :--- | ---------:|
| 200 OK | L'utilisateur existe et le token est renvoyé |
| 401 Unauthorized | L'utilisateur est incorrect |

### Utilisation du token

Le header est nécesaire pour toutes les requêtes **hors GET**. Pour utiliser le token il faut le placer dans le header avec le mot clé **Bearer** :

>Authorization:Bearer <token>