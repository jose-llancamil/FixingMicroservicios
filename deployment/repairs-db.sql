-- Conectar a la base de datos `postgres`
\c postgres;

-- Crear la nueva base de datos
CREATE DATABASE "repairs-db";

-- Conectar a la nueva base de datos
\c "repairs-db";
--
-- PostgreSQL database dump
--

-- Dumped from database version 12.19
-- Dumped by pg_dump version 12.19

-- Started on 2024-06-08 18:24:05

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 207 (class 1259 OID 113993)
-- Name: discount_coupons; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.discount_coupons (
    id bigint NOT NULL,
    marca character varying(50),
    monto double precision,
    cantidad integer
);


ALTER TABLE public.discount_coupons OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 113991)
-- Name: discount_coupons_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.discount_coupons_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.discount_coupons_id_seq OWNER TO postgres;

--
-- TOC entry 2845 (class 0 OID 0)
-- Dependencies: 206
-- Name: discount_coupons_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.discount_coupons_id_seq OWNED BY public.discount_coupons.id;


--
-- TOC entry 205 (class 1259 OID 105830)
-- Name: repair_details; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repair_details (
    id bigint NOT NULL,
    repair_id bigint NOT NULL,
    tipo_reparacion character varying(255) NOT NULL,
    fecha_reparacion date NOT NULL,
    hora_reparacion time without time zone NOT NULL,
    monto_reparacion double precision
);


ALTER TABLE public.repair_details OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 105828)
-- Name: repair_details_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repair_details_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repair_details_id_seq OWNER TO postgres;

--
-- TOC entry 2846 (class 0 OID 0)
-- Dependencies: 204
-- Name: repair_details_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repair_details_id_seq OWNED BY public.repair_details.id;


--
-- TOC entry 203 (class 1259 OID 105822)
-- Name: repairs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repairs (
    id bigint NOT NULL,
    vehiculo_id bigint NOT NULL,
    fecha_ingreso date NOT NULL,
    hora_ingreso time without time zone NOT NULL,
    monto_total_reparaciones double precision,
    monto_recargos double precision,
    monto_descuentos double precision,
    monto_iva double precision,
    costo_total double precision,
    fecha_salida date,
    hora_salida time without time zone,
    fecha_retiro date,
    hora_retiro time without time zone
);


ALTER TABLE public.repairs OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 105820)
-- Name: repairs_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repairs_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repairs_id_seq OWNER TO postgres;

--
-- TOC entry 2847 (class 0 OID 0)
-- Dependencies: 202
-- Name: repairs_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repairs_id_seq OWNED BY public.repairs.id;


--
-- TOC entry 2701 (class 2604 OID 113996)
-- Name: discount_coupons id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discount_coupons ALTER COLUMN id SET DEFAULT nextval('public.discount_coupons_id_seq'::regclass);


--
-- TOC entry 2700 (class 2604 OID 105833)
-- Name: repair_details id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_details ALTER COLUMN id SET DEFAULT nextval('public.repair_details_id_seq'::regclass);


--
-- TOC entry 2699 (class 2604 OID 105825)
-- Name: repairs id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repairs ALTER COLUMN id SET DEFAULT nextval('public.repairs_id_seq'::regclass);


--
-- TOC entry 2839 (class 0 OID 113993)
-- Dependencies: 207
-- Data for Name: discount_coupons; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.discount_coupons (id, marca, monto, cantidad) FROM stdin;
2	Ford	50000	2
3	Hyundai	30000	1
4	Honda	40000	7
1	Toyota	70000	0
\.


--
-- TOC entry 2837 (class 0 OID 105830)
-- Dependencies: 205
-- Data for Name: repair_details; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repair_details (id, repair_id, tipo_reparacion, fecha_reparacion, hora_reparacion, monto_reparacion) FROM stdin;
1	1	Reparaciones del Sistema de Frenos	2024-05-01	10:00:00	120000
2	1	Servicio del Sistema de Refrigeración	2024-05-01	11:00:00	130000
3	2	Reparaciones del Motor	2024-05-03	12:00:00	350000
4	2	Reparaciones de la Transmisión	2024-05-03	13:00:00	210000
5	3	Reparación del Sistema Eléctrico	2024-05-05	09:00:00	150000
6	3	Reparaciones del Sistema de Escape	2024-05-05	10:00:00	100000
7	4	Reparación de Neumáticos y Ruedas	2024-05-07	12:00:00	100000
8	4	Reparaciones de la Suspensión y la Dirección	2024-05-07	13:00:00	180000
9	5	Reparación del Sistema de Aire Acondicionado y Calefacción	2024-05-09	10:00:00	150000
10	5	Reparaciones del Sistema de Combustible	2024-05-09	11:00:00	130000
16	16	Reparaciones del Sistema de Frenos	2024-06-01	10:00:00	120000
17	16	Servicio del Sistema de Refrigeración	2024-06-01	11:00:00	130000
18	15	Reparaciones del Sistema de Frenos	2024-06-01	10:00:00	120000
19	15	Servicio del Sistema de Refrigeración	2024-06-01	11:00:00	130000
20	19	Reparaciones del Sistema de Frenos	2024-06-01	10:00:00	120000
21	19	Servicio del Sistema de Refrigeración	2024-06-01	11:00:00	130000
22	20	Reparaciones del Sistema de Frenos	2024-06-04	12:00:00	120000
24	21	Reparación y Reemplazo del Parabrisas y Cristales	2024-06-05	12:00:00	80000
25	21	Reparaciones del Sistema de Escape	2024-06-07	12:00:00	120000
\.


--
-- TOC entry 2835 (class 0 OID 105822)
-- Dependencies: 203
-- Data for Name: repairs; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repairs (id, vehiculo_id, fecha_ingreso, hora_ingreso, monto_total_reparaciones, monto_recargos, monto_descuentos, monto_iva, costo_total, fecha_salida, hora_salida, fecha_retiro, hora_retiro) FROM stdin;
1	1	2024-05-01	09:00:00	200000	5000	10000	4000	194000	2024-05-02	17:00:00	2024-05-03	10:00:00
2	2	2024-05-03	10:30:00	300000	10000	15000	6000	301000	2024-05-04	16:30:00	2024-05-05	11:00:00
3	3	2024-05-05	11:00:00	500000	15000	20000	10000	505000	2024-05-06	18:00:00	2024-05-07	12:00:00
4	4	2024-05-07	12:30:00	250000	8000	12000	5000	251000	2024-05-08	17:30:00	2024-05-09	13:00:00
5	5	2024-05-09	14:00:00	150000	5000	8000	3000	150000	2024-05-10	15:30:00	2024-05-11	14:30:00
21	8	2024-06-05	12:00:00	200000	110000	70000	38000	278000	2024-06-12	12:00:00	2024-06-19	12:00:00
\.


--
-- TOC entry 2848 (class 0 OID 0)
-- Dependencies: 206
-- Name: discount_coupons_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.discount_coupons_id_seq', 4, true);


--
-- TOC entry 2849 (class 0 OID 0)
-- Dependencies: 204
-- Name: repair_details_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repair_details_id_seq', 25, true);


--
-- TOC entry 2850 (class 0 OID 0)
-- Dependencies: 202
-- Name: repairs_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repairs_id_seq', 21, true);


--
-- TOC entry 2707 (class 2606 OID 113998)
-- Name: discount_coupons discount_coupons_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.discount_coupons
    ADD CONSTRAINT discount_coupons_pkey PRIMARY KEY (id);


--
-- TOC entry 2705 (class 2606 OID 105835)
-- Name: repair_details repair_details_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_details
    ADD CONSTRAINT repair_details_pkey PRIMARY KEY (id);


--
-- TOC entry 2703 (class 2606 OID 105827)
-- Name: repairs repairs_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repairs
    ADD CONSTRAINT repairs_pkey PRIMARY KEY (id);


-- Completed on 2024-06-08 18:24:06

--
-- PostgreSQL database dump complete
--

