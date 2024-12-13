-- Insérer des types d'opérations
INSERT INTO type_operation (id) VALUES (1), (2);

-- Insérer des types de biens
INSERT INTO type_bien (id) VALUES (1), (2);

-- Insérer des communes
INSERT INTO commune (id, nom) VALUES (1, 'Casablanca'), (2, 'Rabat');

-- Insérer des citoyens
INSERT INTO app_user (id, login, password, email, telephone, adresse)
VALUES (1, 'citoyen1', 'password', 'email1@example.com', '0612345678', 'Adresse 1');
