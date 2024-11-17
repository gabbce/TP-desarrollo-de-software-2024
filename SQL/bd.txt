CREATE TABLE vendedor(
    id SERIAL PRIMARY KEY,
    nombre VARCHAR(30),
    direccion VARCHAR(50),
    latitud NUMERIC(8, 2),
    longitud NUMERIC(8, 2)
);

CREATE TABLE categoria(
    id SERIAL PRIMARY KEY,
    descripcion VARCHAR(100),
    tipo VARCHAR(20) UNIQUE
);

INSERT INTO categoria (descripcion, tipo) VALUES
('Comida rápida','Fast food'),
('Bebida con gas','Gaseosa'),
('Bebidas con graduación alcoholica','Alcohol'),
('Comida china', 'Comida china');

CREATE TABLE item_menu(
    id SERIAL PRIMARY KEY,
    es_comida BOOLEAN,
    nombre VARCHAR(20),
    descripcion VARCHAR(100),
    id_vendedor INTEGER REFERENCES vendedor (id),
    id_categoria INTEGER REFERENCES categoria (id),
    precio REAL,
    graduacion_alcoholica REAL,
    tam REAL,
    peso REAL,
    calorias REAL,
    apto_celiaco BOOLEAN,
    apto_vegano BOOLEAN 
);

CREATE TABLE cliente(
    id SERIAL PRIMARY KEY,
    cuit VARCHAR(11) UNIQUE,
    mail VARCHAR(50),
    direccion VARCHAR(50),
    latitud NUMERIC(8, 2),
    longitud NUMERIC(8, 2)
);


CREATE TABLE estado_pedido(
    tipo VARCHAR(20) PRIMARY KEY
);

INSERT INTO estado_pedido (tipo) VALUES
('PENDIENTE'),
('RECIBIDO'),
('EN_ENVIO'),
('RECIBIDO_CLIENTE');

CREATE TABLE pago_type(
    tipo VARCHAR(20) PRIMARY KEY
);

INSERT INTO pago_type (tipo) VALUES 
('MERCADO_PAGO'),
('TRANSFERENCIA');

CREATE TABLE pedido(
    id SERIAL PRIMARY KEY,
    id_estado_pedido VARCHAR(20) REFERENCES estado_pedido (tipo),
    id_cliente INTEGER REFERENCES cliente (id)
);

CREATE TABLE pago(
    id SERIAL PRIMARY KEY,
    id_pedido INTEGER REFERENCES pedido (id),
    id_pagoType VARCHAR(20) REFERENCES pago_type (tipo),
    alias_cbu VARCHAR(40),
    fecha_pago DATE
);

CREATE TABLE pedido_detalle(
    id_pedido INTEGER REFERENCES pedido (id),
    id_item_menu INTEGER REFERENCES item_menu (id),
    PRIMARY KEY (id_pedido, id_item_menu),
    cantidad INTEGER NOT NULL
);

INSERT INTO vendedor (nombre, direccion, latitud, longitud) VALUES
('Carlos López', 'Av. Rivadavia 1234, Buenos Aires', -34.6083, -58.3712),
('Ana Fernández', 'Calle Córdoba 567, Rosario', -32.9468, -60.6394),
('Martín Gómez', 'San Martín 890, Mendoza', -32.8908, -68.8272),
('Susana Martínez', 'Mitre 234, Córdoba', -31.4173, -64.1830),
('Juan Pérez', 'Av. Belgrano 345, Salta', -24.7821, -65.4232);

INSERT INTO item_menu 
(es_comida, nombre, descripcion, id_vendedor, id_categoria, precio, graduacion_alcoholica, tam, peso, calorias, apto_celiaco, apto_vegano) VALUES
(TRUE, 'Empanada', 'Empanada de carne', 1, 1, 150.0, 0.0, NULL, 0.15, 200, TRUE, FALSE),
(TRUE, 'Pizza', 'Pizza napolitana', 1, 1, 1200.0, 0.0, 12.0, NULL, 1000, TRUE, TRUE),
(FALSE, 'Coca Cola', 'Lata de Coca Cola', 2, 2, 250.0, 0.0, 0.33, NULL, 150, TRUE, TRUE),
(FALSE, 'Quilmes', 'Cerveza Quilmes', 3, 3, 300.0, 4.5, 0.5, NULL, 180, TRUE, FALSE),
(TRUE, 'Chow Mein', 'Fideos chinos con verduras', 4, 4, 850.0, 0.0, 0.75, NULL, 450, TRUE, TRUE);

INSERT INTO cliente (cuit, mail, direccion, latitud, longitud) VALUES
('20123456789', 'juan.perez@gmail.com', 'Av. Corrientes 1123, Buenos Aires', -34.6037, -58.3816),
('20345678901', 'maria.lopez@yahoo.com', 'Calle Mitre 456, Rosario', -32.9471, -60.6388),
('20987654321', 'carlos.garcia@outlook.com', 'Av. San Martín 789, Córdoba', -31.4135, -64.1811),
('20876543210', 'laura.fernandez@gmail.com', 'San Juan 345, Mendoza', -32.8898, -68.8270),
('20111222333', 'lucia.martinez@hotmail.com', 'Belgrano 890, Salta', -24.7852, -65.4103);

INSERT INTO pedido (id_estado_pedido, id_cliente) VALUES
('PENDIENTE', 1),
('RECIBIDO', 2),
('EN_ENVIO', 3),
('RECIBIDO_CLIENTE', 4),
('PENDIENTE', 5);

INSERT INTO pago (id_pedido, id_pagoType, alias_cbu, fecha_pago) VALUES
(1, 'MERCADO_PAGO', 'mercadopago.juan', '2024-11-10'),
(2, 'TRANSFERENCIA', 'cbu.maria.456', '2024-11-12'),
(3, 'MERCADO_PAGO', 'mercadopago.carlos', '2024-11-14'),
(4, 'TRANSFERENCIA', 'cbu.laura.678', '2024-11-15'),
(5, 'MERCADO_PAGO', 'mercadopago.lucia', '2024-11-16');

INSERT INTO pedido_detalle (id_pedido, id_item_menu, cantidad) VALUES
(1, 1, 12),
(1, 2, 1),
(2, 3, 6),
(3, 4, 10),
(4, 5, 3),
(5, 2, 2);

