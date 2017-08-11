INSERT INTO category (id, categoryName, categoryURL, status) VALUES (1,'Dolls', 'dolls', true); 
INSERT INTO category (id, categoryName, categoryURL, status) VALUES (2,'Construction', 'construction', true); 
INSERT INTO category (id, categoryName, categoryURL, status, parent_id) VALUES (3,'Monster High', 'monster_high', true, 1);
INSERT INTO category (id, categoryName, categoryURL, status, parent_id) VALUES (4,'LEGO', 'lego', true, 2); 
INSERT INTO category (id, categoryName, categoryURL, status, parent_id) VALUES (5,'Party series', 'monster_high_party', true, 3); 
INSERT INTO category (id, categoryName, categoryURL, status, parent_id) VALUES (6,'Electrified', 'monster_high_electrified', true, 3); 
INSERT INTO category (id, categoryName, categoryURL, status) VALUES (7,'InActiveCategory', 'inactive', false); 