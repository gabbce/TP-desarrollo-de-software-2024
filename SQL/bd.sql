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
    id_cliente INTEGER REFERENCES cliente (id),
    precio_final REAL
);

CREATE TABLE pago(
    id SERIAL PRIMARY KEY,
    id_pedido INTEGER REFERENCES pedido (id) UNIQUE,
    pagoType VARCHAR(20) REFERENCES pago_type (tipo),
    alias_cbu VARCHAR(40),
    cuit VARCHAR(11),
    fecha_pago TIMESTAMP
);

CREATE TABLE pedido_detalle(
    id_pedido INTEGER REFERENCES pedido (id),
    id_item_menu INTEGER REFERENCES item_menu (id),
    PRIMARY KEY (id_pedido, id_item_menu),
    cantidad INTEGER NOT NULL
);

-- Inserciones para 'vendedor'
INSERT INTO vendedor (nombre, direccion, latitud, longitud) VALUES
('Carlos López', 'Av Rivadavia 1234', -34.60, -58.37),
('Ana Fernández', 'Calle Córdoba 567', -32.94, -60.64),
('Martín Gómez', 'San Martín 890', -32.89, -68.82),
('Susana Martínez', 'Mitre 234', -31.43, -64.10),
('Juan Pérez', 'Av Belgrano 345', -24.71, -65.42);

-- Inserciones para 'categoria'
INSERT INTO categoria (descripcion, tipo) VALUES
('Comida rápida','Fast food'),
('Bebida con gas','Gaseosa'),
('Bebidas con graduación alcoholica','Alcohol'),
('Comida china', 'Comida china');

-- Inserciones para 'item_menu'
INSERT INTO item_menu 
(es_comida, nombre, descripcion, id_vendedor, id_categoria, precio, graduacion_alcoholica, tam, peso, calorias, apto_celiaco, apto_vegano) VALUES
(TRUE, 'Empanada', 'Empanada de carne', 1, 1, 150.0, 0.0, NULL, 0.15, 200, TRUE, FALSE),
(TRUE, 'Pizza', 'Pizza napolitana', 1, 1, 1200.0, 0.0, 12.0, NULL, 1000, TRUE, TRUE),
(FALSE, 'Coca Cola', 'Lata de Coca Cola', 2, 2, 250.0, 0.0, 0.33, NULL, 150, TRUE, TRUE),
(FALSE, 'Quilmes', 'Cerveza Quilmes', 3, 3, 300.0, 4.5, 0.5, NULL, 180, TRUE, FALSE),
(TRUE, 'Chow Mein', 'Fideos chinos con verduras', 4, 4, 850.0, 0.0, 0.75, NULL, 450, TRUE, TRUE);

-- Inserciones para 'cliente'
INSERT INTO cliente (cuit, mail, direccion, latitud, longitud) VALUES
('20123456789', 'juan.perez@gmail.com', 'Av Corrientes 1123', -34.60, -58.36),
('20345678901', 'maria.lopez@yahoo.com', 'Calle Mitre 456', -32.91, -60.63),
('20987654321', 'carlos.garcia@outlook.com', 'Av San Martín 789', -31.35, -64.11),
('20876543210', 'laura.fernandez@gmail.com', 'San Juan 345', -32.88, -68.80),
('20111222333', 'lucia.martinez@hotmail.com', 'Belgrano 890', -24.72, -65.43);

-- Inserciones para 'pedido' 
INSERT INTO pedido (id_estado_pedido, id_cliente, precio_final) VALUES
('PENDIENTE', 1, 3000),
('RECIBIDO', 2, 1500),
('EN_ENVIO', 3, 3000),
('RECIBIDO_CLIENTE', 4, 2550),
('PENDIENTE', 5, 2400);


-- Inserciones para 'pago' (MERCADO_PAGO y TRANSFERENCIA)
INSERT INTO pago (id_pedido, pagoType, alias_cbu, cuit, fecha_pago) VALUES
(1, 'MERCADO_PAGO', 'mercadopago.juan', NULL, '2024-11-10 14:00:00'),  -- MercadoPago (sin cuit)
(2, 'TRANSFERENCIA', 'cbu.maria.456', '20345678901', '2024-11-12 15:30:00'), -- Transferencia (con cuit)
(3, 'MERCADO_PAGO', 'mercadopago.carlos', NULL, '2024-11-14 16:45:00'),  -- MercadoPago (sin cuit)
(4, 'TRANSFERENCIA', 'cbu.laura.678', '20876543210', '2024-11-15 17:00:00'), -- Transferencia (con cuit)
(5, 'MERCADO_PAGO', 'mercadopago.lucia', NULL, '2024-11-16 18:00:00');  -- MercadoPago (sin cuit)

-- Inserciones para 'pedido_detalle'
INSERT INTO pedido_detalle (id_pedido, id_item_menu, cantidad) VALUES
(1, 1, 12),
(1, 2, 1),
(2, 3, 6),
(3, 4, 10),
(4, 5, 3),
(5, 2, 2);


