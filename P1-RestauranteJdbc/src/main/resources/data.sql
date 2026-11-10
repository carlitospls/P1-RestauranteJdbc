-- Categorías (Menú infantil no tiene platos)
INSERT INTO categoria (nombre) VALUES ('Entrantes');     -- 1
INSERT INTO categoria (nombre) VALUES ('Principales');   -- 2
INSERT INTO categoria (nombre) VALUES ('Postres');       -- 3
INSERT INTO categoria (nombre) VALUES ('Bebidas');       -- 4
INSERT INTO categoria (nombre) VALUES ('Menú infantil'); -- 5

-- Platos
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Croquetas de jamón', 8.50, 1, 600);
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Ensalada César', 9.90, 1, 100);
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Patatas bravas', 6.00, 1, 500);
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Paella mixta', 16.50, 2, 650);
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Cachopo asturiano', 19.90, 2,100);
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Lubina a la sal', 18.00, 2,400);
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Tarta de queso', 6.50, 3, 450);
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Arroz con leche', 5.00, 3, 400);
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Agua mineral', 1.80, 4, 0);
INSERT INTO plato (nombre, precio, codigo_categoria, calorias) VALUES ('Sidra natural', 4.50, 4, 130);
