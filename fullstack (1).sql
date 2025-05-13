INSERT INTO almacen(almacennombre, direccion) VALUES ('Almacén Central', 'Av. Principal 123');
INSERT INTO almacen (almacennombre, direccion) VALUES ('Bodega Norte', 'Calle Secundaria 456');
INSERT INTO almacen (almacennombre, direccion) VALUES ('Depósito Sur', 'Pasaje Industrial 789');
INSERT INTO almacen (almacennombre, direccion) VALUES ('Centro Logístico', 'Ruta Comercial 321');
INSERT INTO almacen (almacennombre, direccion) VALUES ('Almacén Express', 'Boulevard Rápido 654');
COMMIT;

INSERT INTO producto (producto_nombre, stock) VALUES ('Bolsa reutilizable', 50);
INSERT INTO producto (producto_nombre, stock) VALUES ('Cepillo de bambú', 100);
INSERT INTO producto (producto_nombre, stock) VALUES ('Botella de acero inoxidable', 75);
INSERT INTO producto (producto_nombre, stock) VALUES ('Pajillas de metal', 60);
INSERT INTO producto (producto_nombre, stock) VALUES ('Cuaderno de papel reciclado', 40);
INSERT INTO producto (producto_nombre, stock) VALUES ('Detergente ecológico', 55);
INSERT INTO producto (producto_nombre, stock) VALUES ('Shampoo sólido', 30);
INSERT INTO producto (producto_nombre, stock) VALUES ('Jabón biodegradable', 80);
INSERT INTO producto (producto_nombre, stock) VALUES ('Panel solar portátil', 20);
INSERT INTO producto (producto_nombre, stock) VALUES ('Compostera doméstica', 15);
COMMIT;



INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (1, 1); -- EcoStore 1 tiene Bolsa reutilizable
INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (1, 3); -- EcoStore 1 tiene Botella de acero inoxidable

INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (2, 2); -- EcoStore 2 tiene Cepillo de bambú
INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (2, 4); -- EcoStore 2 tiene Pajillas de metal

INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (3, 5); -- EcoStore 3 tiene Cuaderno de papel reciclado
INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (3, 6); -- EcoStore 3 tiene Detergente ecológico

INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (4, 7); -- EcoStore 4 tiene Shampoo sólido
INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (4, 8); -- EcoStore 4 tiene Jabón biodegradable

INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (5, 9); -- EcoStore 5 tiene Panel solar portátil
INSERT INTO almacen_producto (almacen_id, producto_id) VALUES (5, 10); -- EcoStore 5 tiene Compostera doméstica
COMMIT;
