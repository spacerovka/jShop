DELETE FROM parcel_cost;
DELETE FROM parcel_size;
DELETE FROM country;

INSERT INTO country(id, name, basetarif) VALUES (1, 'USA', 30.00);

INSERT INTO parcel_size(id, name, description) VALUES (1, 'Regular', 'Regular parcel 30x30 cm');
INSERT INTO parcel_cost(id, country, size, cost) VALUES (1, 1,1, 10.00);

INSERT INTO parcel_size(id, name, description) VALUES (2, 'Medium', 'Medium parcel 60x80 cm');
INSERT INTO parcel_cost(id, country, size, cost) VALUES (2, 1,2, 20.00);
