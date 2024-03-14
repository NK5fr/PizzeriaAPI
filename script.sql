TRUNCATE TABLE ingredients RESTART IDENTITY CASCADE;
TRUNCATE TABLE pizzas RESTART IDENTITY CASCADE;
TRUNCATE TABLE commandes RESTART IDENTITY CASCADE;

INSERT INTO ingredients (inom, prix)
VALUES ('tomate', 1);

INSERT INTO ingredients (inom, prix)
VALUES ('mozzarella', 2);

INSERT INTO ingredients (inom, prix)
VALUES ('jambon', 2);

INSERT INTO ingredients (inom, prix)
VALUES ('caviar', 50);

INSERT INTO ingredients (inom, prix)
VALUES ('pomme de terre', 1);

INSERT INTO ingredients (inom, prix)
VALUES ('pomme', 1);

INSERT INTO ingredients (inom, prix)
VALUES ('poulet', 4);

INSERT INTO ingredients (inom, prix)
VALUES ('oeuf', 5);

INSERT INTO pizzas (pnom, pate, prixbase)
VALUES ('roubaissienne', 'pate a pizza', 50);
INSERT INTO compose VALUES (1, 1);
INSERT INTO compose VALUES (1, 2);
INSERT INTO compose VALUES (1, 3);

INSERT INTO pizzas (pnom, pate, prixbase)
VALUES ('margegaritta', 'pate a pizza', 40);
INSERT INTO compose VALUES (2, 7);
INSERT INTO compose VALUES (2, 8);
INSERT INTO compose VALUES (2, 1);

INSERT INTO pizzas (pnom, pate, prixbase)
VALUES ('salmonella', 'pate au saumon', 80);
INSERT INTO compose VALUES (3, 1);
INSERT INTO compose VALUES (3, 4);
INSERT INTO compose VALUES (3, 6);

INSERT INTO commandes (cnom, date)
VALUES ('nathan', '2024-03-13');

INSERT INTO commandes (cnom, date)
VALUES ('armand', '2024-03-13');