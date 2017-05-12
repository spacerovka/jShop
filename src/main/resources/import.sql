#CATEGORY
INSERT INTO category (categoryName, categoryURL, status) VALUES ('Dolls', 'dolls', true); #1
INSERT INTO category (categoryName, categoryURL, status) VALUES ('Construction', 'construction', true); #2
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('Monster High', 'monster_high', true, 1); #3
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('LEGO', 'lego', true, 2); #4
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('Party series', 'monster_high_party', true, 3); #5
INSERT INTO category (categoryName, categoryURL, status, parent_id) VALUES ('Electrified', 'monster_high_electrified', true, 3); #6
INSERT INTO category (categoryName, categoryURL, status) VALUES ('InActiveCategory', 'inactive', false); #7inactive

#PRODUCTS
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured, longDesc, shortDesc, meta_title, meta_description) VALUES ('FRANKIE STEIN', '59.99', '6', 'PI90800', 'frankie_stein', 5,1,1, "<p>See more snippets like these online store reviews at <a target='_blank' href='http://bootsnipp.com'>Bootsnipp - http://bootsnipp.com</a>.</p><p>Want to make these reviews work? Check out <strong><a href='http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/'>this building a review system tutorial</a></strong>over at maxoffsky.com!</p><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum</p>", "Frankie Stein is a wholehearted girl, and pretty-in-personality kind of girl. She is optimistic, positive, kindhearted and always gets along with others. Frankie is aware of her flaws and embraces them.", "JShop Draculaura Doll", "She is optimistic, positive, kindhearted and always gets along with others.");
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured, longDesc, shortDesc, rating) VALUES ('DRACULAURA', '99.99', '6', 'PI90870','draculaura', 6,1,1, "<p>See more snippets like these online store reviews at <a target='_blank' href='http://bootsnipp.com'>Bootsnipp - http://bootsnipp.com</a>.</p><p>Want to make these reviews work? Check out <strong><a href='http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/'>this building a review system tutorial</a></strong>over at maxoffsky.com!</p><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum</p>", "Draculaura is used to being pampered. She is prone to crying when she has to deal with obstacles, which on a bad day includes not being able to check her apperance in the mirror, though over the past years she has grown to follow that up with action. ", 3);
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured, longDesc, shortDesc) VALUES ('Lego Star', '9.99', '20', 'PI90899','lego_star', 4,1,1, "<p>See more snippets like these online store reviews at <a target='_blank' href='http://bootsnipp.com'>Bootsnipp - http://bootsnipp.com</a>.</p><p>Want to make these reviews work? Check out <strong><a href='http://maxoffsky.com/code-blog/laravel-shop-tutorial-1-building-a-review-system/'>this building a review system tutorial</a></strong>over at maxoffsky.com!</p><p>Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum</p>", "Win the battle for the Empire with the awesome Death Star!");


INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured) VALUES ('Inactive Product', '9.99', '20', 'PI90891','inactive_product', 4,0,1);
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured) VALUES ('Category INACTIVEProduct', '9.99', '20', 'PI90892','categ_inactive_product', 7,1,1);
INSERT INTO product (NAME, PRICE, instock, SKU, url, category,status, featured) VALUES ('Lego Star 2', '9.99', '20', 'PI90893','lego_star2', 4,1,1);

#REVIEWS
INSERT INTO review (comment, rating, created, useremail, username, product) VALUES ("Good price", 5, NOW(), "nina@mail.ru", "Nina", 2);
INSERT INTO review (comment, rating, created, useremail, username, product) VALUES ("Very fast delivery", 4, NOW(), "nina@mail.ru", "Lilu", 2);
INSERT INTO review (comment, rating, created, useremail, username, product) VALUES ("Shipping is expencuve", 3, NOW(), "nina@mail.ru", "Margory", 2);
INSERT INTO review (comment, rating, created, useremail, username, product) VALUES ("My box was damaged", 2, NOW(), "nina@mail.ru", "Irma", 2);
INSERT INTO review (comment, rating, created, useremail, username, product) VALUES ("High price", 1, NOW(), "nina@mail.ru", "Tommy", 2);

#Options
INSERT INTO optionGroup (optionGroupName) VALUES ("Color");
INSERT INTO optionGroup (optionGroupName) VALUES ("Size");

INSERT INTO option_entity (optionName, optionGroup) VALUES ("Black", 1);
INSERT INTO option_entity (optionName, optionGroup) VALUES ("Blue", 1);
INSERT INTO option_entity (optionName, optionGroup) VALUES ("Pink", 1);


INSERT INTO option_entity (optionName, optionGroup) VALUES ("Small", 2);
INSERT INTO option_entity (optionName, optionGroup) VALUES ("Medium", 2);
INSERT INTO option_entity (optionName, optionGroup) VALUES ("Large", 2);

INSERT INTO productOption (option_entity, optiongroup, product) VALUES (2,1, 1);
INSERT INTO productOption (option_entity, optiongroup, product) VALUES (3,1, 2);
INSERT INTO productOption (option_entity, optiongroup, product) VALUES (1,1, 3);
INSERT INTO productOption (option_entity, optiongroup, product) VALUES (2,1, 6);

INSERT INTO productOption (option_entity, optiongroup, product) VALUES (5,2, 1);
INSERT INTO productOption (option_entity, optiongroup, product) VALUES (5,2, 2);
INSERT INTO productOption (option_entity, optiongroup, product) VALUES (6,2, 3);

INSERT INTO productOption (option_entity, optiongroup, product) VALUES (1,1, 4);
INSERT INTO productOption (option_entity, optiongroup, product) VALUES (1,1, 5);

INSERT INTO categoryOption (option_entity, category) VALUES (1,4);
INSERT INTO categoryOption (option_entity, category) VALUES (2,4);
INSERT INTO categoryOption (option_entity, category) VALUES (3,4);
INSERT INTO categoryOption (option_entity, category) VALUES (1,6);
INSERT INTO categoryOption (option_entity, category) VALUES (2,6);
INSERT INTO categoryOption (option_entity, category) VALUES (3,6);
INSERT INTO categoryOption (option_entity, category) VALUES (4,5);
INSERT INTO categoryOption (option_entity, category) VALUES (5,5);
INSERT INTO categoryOption (option_entity, category) VALUES (6,5);
