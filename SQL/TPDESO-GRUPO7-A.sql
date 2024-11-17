--
-- PostgreSQL database dump
--

-- Dumped from database version 17.0
-- Dumped by pg_dump version 17.0

-- Started on 2024-11-17 06:18:46

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4933 (class 1262 OID 32771)
-- Name: tp_deso; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE tp_deso WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Spanish_Argentina.1252';


ALTER DATABASE tp_deso OWNER TO postgres;

\connect tp_deso

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 4 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: pg_database_owner
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO pg_database_owner;

--
-- TOC entry 4934 (class 0 OID 0)
-- Dependencies: 4
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: pg_database_owner
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 220 (class 1259 OID 33192)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    id integer NOT NULL,
    descripcion character varying(100),
    tipo character varying(20)
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 219 (class 1259 OID 33191)
-- Name: categoria_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categoria_id_seq OWNER TO postgres;

--
-- TOC entry 4935 (class 0 OID 0)
-- Dependencies: 219
-- Name: categoria_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_id_seq OWNED BY public.categoria.id;


--
-- TOC entry 224 (class 1259 OID 33218)
-- Name: cliente; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cliente (
    id integer NOT NULL,
    cuit character varying(11),
    mail character varying(50),
    direccion character varying(50),
    latitud numeric(8,2),
    longitud numeric(8,2)
);


ALTER TABLE public.cliente OWNER TO postgres;

--
-- TOC entry 223 (class 1259 OID 33217)
-- Name: cliente_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cliente_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.cliente_id_seq OWNER TO postgres;

--
-- TOC entry 4936 (class 0 OID 0)
-- Dependencies: 223
-- Name: cliente_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cliente_id_seq OWNED BY public.cliente.id;


--
-- TOC entry 225 (class 1259 OID 33226)
-- Name: estado_pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.estado_pedido (
    tipo character varying(20) NOT NULL
);


ALTER TABLE public.estado_pedido OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 33201)
-- Name: item_menu; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item_menu (
    id integer NOT NULL,
    es_comida boolean,
    nombre character varying(20),
    descripcion character varying(100),
    id_vendedor integer,
    id_categoria integer,
    precio real,
    graduacion_alcoholica real,
    tam real,
    peso real,
    calorias real,
    apto_celiaco boolean,
    apto_vegano boolean
);


ALTER TABLE public.item_menu OWNER TO postgres;

--
-- TOC entry 221 (class 1259 OID 33200)
-- Name: item_menu_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.item_menu_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.item_menu_id_seq OWNER TO postgres;

--
-- TOC entry 4937 (class 0 OID 0)
-- Dependencies: 221
-- Name: item_menu_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.item_menu_id_seq OWNED BY public.item_menu.id;


--
-- TOC entry 230 (class 1259 OID 33254)
-- Name: pago; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pago (
    id integer NOT NULL,
    id_pedido integer,
    id_pagotype character varying(20),
    alias_cbu character varying(40),
    fecha_pago date
);


ALTER TABLE public.pago OWNER TO postgres;

--
-- TOC entry 229 (class 1259 OID 33253)
-- Name: pago_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pago_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pago_id_seq OWNER TO postgres;

--
-- TOC entry 4938 (class 0 OID 0)
-- Dependencies: 229
-- Name: pago_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pago_id_seq OWNED BY public.pago.id;


--
-- TOC entry 226 (class 1259 OID 33231)
-- Name: pago_type; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pago_type (
    tipo character varying(20) NOT NULL
);


ALTER TABLE public.pago_type OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 33237)
-- Name: pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedido (
    id integer NOT NULL,
    id_estado_pedido character varying(20),
    id_cliente integer
);


ALTER TABLE public.pedido OWNER TO postgres;

--
-- TOC entry 231 (class 1259 OID 33270)
-- Name: pedido_detalle; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedido_detalle (
    id_pedido integer NOT NULL,
    id_item_menu integer NOT NULL,
    cantidad integer NOT NULL
);


ALTER TABLE public.pedido_detalle OWNER TO postgres;

--
-- TOC entry 227 (class 1259 OID 33236)
-- Name: pedido_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedido_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pedido_id_seq OWNER TO postgres;

--
-- TOC entry 4939 (class 0 OID 0)
-- Dependencies: 227
-- Name: pedido_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedido_id_seq OWNED BY public.pedido.id;


--
-- TOC entry 218 (class 1259 OID 33185)
-- Name: vendedor; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vendedor (
    id integer NOT NULL,
    nombre character varying(30),
    direccion character varying(50),
    latitud numeric(8,2),
    longitud numeric(8,2)
);


ALTER TABLE public.vendedor OWNER TO postgres;

--
-- TOC entry 217 (class 1259 OID 33184)
-- Name: vendedor_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vendedor_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.vendedor_id_seq OWNER TO postgres;

--
-- TOC entry 4940 (class 0 OID 0)
-- Dependencies: 217
-- Name: vendedor_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vendedor_id_seq OWNED BY public.vendedor.id;


--
-- TOC entry 4733 (class 2604 OID 33195)
-- Name: categoria id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN id SET DEFAULT nextval('public.categoria_id_seq'::regclass);


--
-- TOC entry 4735 (class 2604 OID 33221)
-- Name: cliente id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente ALTER COLUMN id SET DEFAULT nextval('public.cliente_id_seq'::regclass);


--
-- TOC entry 4734 (class 2604 OID 33204)
-- Name: item_menu id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_menu ALTER COLUMN id SET DEFAULT nextval('public.item_menu_id_seq'::regclass);


--
-- TOC entry 4737 (class 2604 OID 33257)
-- Name: pago id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago ALTER COLUMN id SET DEFAULT nextval('public.pago_id_seq'::regclass);


--
-- TOC entry 4736 (class 2604 OID 33240)
-- Name: pedido id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido ALTER COLUMN id SET DEFAULT nextval('public.pedido_id_seq'::regclass);


--
-- TOC entry 4732 (class 2604 OID 33188)
-- Name: vendedor id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendedor ALTER COLUMN id SET DEFAULT nextval('public.vendedor_id_seq'::regclass);


--
-- TOC entry 4916 (class 0 OID 33192)
-- Dependencies: 220
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoria VALUES (1, 'Comida rápida', 'Fast food');
INSERT INTO public.categoria VALUES (2, 'Bebida con gas', 'Gaseosa');
INSERT INTO public.categoria VALUES (3, 'Bebidas con graduación alcoholica', 'Alcohol');
INSERT INTO public.categoria VALUES (4, 'Comida china', 'Comida china');


--
-- TOC entry 4920 (class 0 OID 33218)
-- Dependencies: 224
-- Data for Name: cliente; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.cliente VALUES (1, '20123456789', 'juan.perez@gmail.com', 'Av. Corrientes 1123, Buenos Aires', -34.60, -58.38);
INSERT INTO public.cliente VALUES (2, '20345678901', 'maria.lopez@yahoo.com', 'Calle Mitre 456, Rosario', -32.95, -60.64);
INSERT INTO public.cliente VALUES (3, '20987654321', 'carlos.garcia@outlook.com', 'Av. San Martín 789, Córdoba', -31.41, -64.18);
INSERT INTO public.cliente VALUES (4, '20876543210', 'laura.fernandez@gmail.com', 'San Juan 345, Mendoza', -32.89, -68.83);
INSERT INTO public.cliente VALUES (5, '20111222333', 'lucia.martinez@hotmail.com', 'Belgrano 890, Salta', -24.79, -65.41);


--
-- TOC entry 4921 (class 0 OID 33226)
-- Dependencies: 225
-- Data for Name: estado_pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.estado_pedido VALUES ('PENDIENTE');
INSERT INTO public.estado_pedido VALUES ('RECIBIDO');
INSERT INTO public.estado_pedido VALUES ('EN_ENVIO');
INSERT INTO public.estado_pedido VALUES ('RECIBIDO_CLIENTE');


--
-- TOC entry 4918 (class 0 OID 33201)
-- Dependencies: 222
-- Data for Name: item_menu; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.item_menu VALUES (1, true, 'Empanada', 'Empanada de carne', 1, 1, 150, 0, NULL, 0.15, 200, true, false);
INSERT INTO public.item_menu VALUES (2, true, 'Pizza', 'Pizza napolitana', 1, 1, 1200, 0, 12, NULL, 1000, true, true);
INSERT INTO public.item_menu VALUES (3, false, 'Coca Cola', 'Lata de Coca Cola', 2, 2, 250, 0, 0.33, NULL, 150, true, true);
INSERT INTO public.item_menu VALUES (4, false, 'Quilmes', 'Cerveza Quilmes', 3, 3, 300, 4.5, 0.5, NULL, 180, true, false);
INSERT INTO public.item_menu VALUES (5, true, 'Chow Mein', 'Fideos chinos con verduras', 4, 4, 850, 0, 0.75, NULL, 450, true, true);


--
-- TOC entry 4926 (class 0 OID 33254)
-- Dependencies: 230
-- Data for Name: pago; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pago VALUES (1, 1, 'MERCADO_PAGO', 'mercadopago.juan', '2024-11-10');
INSERT INTO public.pago VALUES (2, 2, 'TRANSFERENCIA', 'cbu.maria.456', '2024-11-12');
INSERT INTO public.pago VALUES (3, 3, 'MERCADO_PAGO', 'mercadopago.carlos', '2024-11-14');
INSERT INTO public.pago VALUES (4, 4, 'TRANSFERENCIA', 'cbu.laura.678', '2024-11-15');
INSERT INTO public.pago VALUES (5, 5, 'MERCADO_PAGO', 'mercadopago.lucia', '2024-11-16');


--
-- TOC entry 4922 (class 0 OID 33231)
-- Dependencies: 226
-- Data for Name: pago_type; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pago_type VALUES ('MERCADO_PAGO');
INSERT INTO public.pago_type VALUES ('TRANSFERENCIA');


--
-- TOC entry 4924 (class 0 OID 33237)
-- Dependencies: 228
-- Data for Name: pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pedido VALUES (1, 'PENDIENTE', 1);
INSERT INTO public.pedido VALUES (2, 'RECIBIDO', 2);
INSERT INTO public.pedido VALUES (3, 'EN_ENVIO', 3);
INSERT INTO public.pedido VALUES (4, 'RECIBIDO_CLIENTE', 4);
INSERT INTO public.pedido VALUES (5, 'PENDIENTE', 5);


--
-- TOC entry 4927 (class 0 OID 33270)
-- Dependencies: 231
-- Data for Name: pedido_detalle; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.pedido_detalle VALUES (1, 1, 12);
INSERT INTO public.pedido_detalle VALUES (1, 2, 1);
INSERT INTO public.pedido_detalle VALUES (2, 3, 6);
INSERT INTO public.pedido_detalle VALUES (3, 4, 10);
INSERT INTO public.pedido_detalle VALUES (4, 5, 3);
INSERT INTO public.pedido_detalle VALUES (5, 2, 2);


--
-- TOC entry 4914 (class 0 OID 33185)
-- Dependencies: 218
-- Data for Name: vendedor; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.vendedor VALUES (1, 'Carlos López', 'Av. Rivadavia 1234, Buenos Aires', -34.61, -58.37);
INSERT INTO public.vendedor VALUES (2, 'Ana Fernández', 'Calle Córdoba 567, Rosario', -32.95, -60.64);
INSERT INTO public.vendedor VALUES (3, 'Martín Gómez', 'San Martín 890, Mendoza', -32.89, -68.83);
INSERT INTO public.vendedor VALUES (4, 'Susana Martínez', 'Mitre 234, Córdoba', -31.42, -64.18);
INSERT INTO public.vendedor VALUES (5, 'Juan Pérez', 'Av. Belgrano 345, Salta', -24.78, -65.42);


--
-- TOC entry 4941 (class 0 OID 0)
-- Dependencies: 219
-- Name: categoria_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_id_seq', 4, true);


--
-- TOC entry 4942 (class 0 OID 0)
-- Dependencies: 223
-- Name: cliente_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cliente_id_seq', 5, true);


--
-- TOC entry 4943 (class 0 OID 0)
-- Dependencies: 221
-- Name: item_menu_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.item_menu_id_seq', 5, true);


--
-- TOC entry 4944 (class 0 OID 0)
-- Dependencies: 229
-- Name: pago_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pago_id_seq', 5, true);


--
-- TOC entry 4945 (class 0 OID 0)
-- Dependencies: 227
-- Name: pedido_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedido_id_seq', 5, true);


--
-- TOC entry 4946 (class 0 OID 0)
-- Dependencies: 217
-- Name: vendedor_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vendedor_id_seq', 5, true);


--
-- TOC entry 4741 (class 2606 OID 33197)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (id);


--
-- TOC entry 4743 (class 2606 OID 33199)
-- Name: categoria categoria_tipo_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_tipo_key UNIQUE (tipo);


--
-- TOC entry 4747 (class 2606 OID 33225)
-- Name: cliente cliente_cuit_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_cuit_key UNIQUE (cuit);


--
-- TOC entry 4749 (class 2606 OID 33223)
-- Name: cliente cliente_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cliente
    ADD CONSTRAINT cliente_pkey PRIMARY KEY (id);


--
-- TOC entry 4751 (class 2606 OID 33230)
-- Name: estado_pedido estado_pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.estado_pedido
    ADD CONSTRAINT estado_pedido_pkey PRIMARY KEY (tipo);


--
-- TOC entry 4745 (class 2606 OID 33206)
-- Name: item_menu item_menu_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_menu
    ADD CONSTRAINT item_menu_pkey PRIMARY KEY (id);


--
-- TOC entry 4757 (class 2606 OID 33259)
-- Name: pago pago_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago
    ADD CONSTRAINT pago_pkey PRIMARY KEY (id);


--
-- TOC entry 4753 (class 2606 OID 33235)
-- Name: pago_type pago_type_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago_type
    ADD CONSTRAINT pago_type_pkey PRIMARY KEY (tipo);


--
-- TOC entry 4759 (class 2606 OID 33274)
-- Name: pedido_detalle pedido_detalle_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_detalle
    ADD CONSTRAINT pedido_detalle_pkey PRIMARY KEY (id_pedido, id_item_menu);


--
-- TOC entry 4755 (class 2606 OID 33242)
-- Name: pedido pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (id);


--
-- TOC entry 4739 (class 2606 OID 33190)
-- Name: vendedor vendedor_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vendedor
    ADD CONSTRAINT vendedor_pkey PRIMARY KEY (id);


--
-- TOC entry 4760 (class 2606 OID 33212)
-- Name: item_menu item_menu_id_categoria_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_menu
    ADD CONSTRAINT item_menu_id_categoria_fkey FOREIGN KEY (id_categoria) REFERENCES public.categoria(id);


--
-- TOC entry 4761 (class 2606 OID 33207)
-- Name: item_menu item_menu_id_vendedor_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item_menu
    ADD CONSTRAINT item_menu_id_vendedor_fkey FOREIGN KEY (id_vendedor) REFERENCES public.vendedor(id);


--
-- TOC entry 4764 (class 2606 OID 33265)
-- Name: pago pago_id_pagotype_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago
    ADD CONSTRAINT pago_id_pagotype_fkey FOREIGN KEY (id_pagotype) REFERENCES public.pago_type(tipo);


--
-- TOC entry 4765 (class 2606 OID 33260)
-- Name: pago pago_id_pedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pago
    ADD CONSTRAINT pago_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id);


--
-- TOC entry 4766 (class 2606 OID 33280)
-- Name: pedido_detalle pedido_detalle_id_item_menu_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_detalle
    ADD CONSTRAINT pedido_detalle_id_item_menu_fkey FOREIGN KEY (id_item_menu) REFERENCES public.item_menu(id);


--
-- TOC entry 4767 (class 2606 OID 33275)
-- Name: pedido_detalle pedido_detalle_id_pedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido_detalle
    ADD CONSTRAINT pedido_detalle_id_pedido_fkey FOREIGN KEY (id_pedido) REFERENCES public.pedido(id);


--
-- TOC entry 4762 (class 2606 OID 33248)
-- Name: pedido pedido_id_cliente_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_id_cliente_fkey FOREIGN KEY (id_cliente) REFERENCES public.cliente(id);


--
-- TOC entry 4763 (class 2606 OID 33243)
-- Name: pedido pedido_id_estado_pedido_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_id_estado_pedido_fkey FOREIGN KEY (id_estado_pedido) REFERENCES public.estado_pedido(tipo);


-- Completed on 2024-11-17 06:18:46

--
-- PostgreSQL database dump complete
--

