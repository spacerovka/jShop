
INSERT INTO user (username, id, enabled, password, email, emailVerified) VALUES ("default", 1, true, "$2a$10$nrOeQ8DX22NStYUuCeIvMeYA6fWfTczFvuynZVTq.7k0bkqGxPpyW", "default@gmail.com", true);


INSERT INTO user_roles (user_role_id, username, role) VALUES (1,"default", "USER");
