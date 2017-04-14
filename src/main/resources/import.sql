#CATEGORY
INSERT INTO category (categoryName, categoryURL, status) VALUES ('Dolls', 'dolls', true); #1
INSERT INTO category (categoryName, categoryURL, status) VALUES ('Construction', 'construction', true); #2
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('Monster High', 'monster_high', true, 1); #3
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('LEGO', 'lego', true, 2); #4
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('Party series', 'monster_high_party', true, 3); #5
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('Electrified', 'monster_high_electrified', true, 3); #6
INSERT INTO category (categoryName, categoryURL, status) VALUES ('InActiveCategory', 'inactive', true); #7inactive

#PRODUCTS
INSERT INTO product (NAME, PRICE, instock, SKU, url, category_id,status, featured) VALUES ('FRANKIE STEIN', '59.99', '6', 'PI90800', 'frankie_stein', 5,1,1);
INSERT INTO product (NAME, PRICE, instock, SKU, url, category_id,status, featured) VALUES ('DRACULAURA', '99.99', '6', 'PI90870','draculaura', 6,1,1);
INSERT INTO product (NAME, PRICE, instock, SKU, url, category_id,status, featured) VALUES ('Lego Star', '9.99', '20', 'PI90899','lego_star', 4,1,1);
INSERT INTO product (NAME, PRICE, instock, SKU, url, category_id,status, featured) VALUES ('Inactive Product', '9.99', '20', 'PI90899','inactive_product', 4,0,1);
INSERT INTO product (NAME, PRICE, instock, SKU, url, category_id,status, featured) VALUES ('Category INACTIVEProduct', '9.99', '20', 'PI90899','categ_inactive_product', 7,1,1);