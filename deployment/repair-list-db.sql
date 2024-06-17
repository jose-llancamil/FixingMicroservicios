-- Conectar a la base de datos `postgres`
\c postgres;

-- Crear la nueva base de datos
CREATE DATABASE "repair-list-db";

-- Conectar a la nueva base de datos
\c "repair-list-db";
--
-- PostgreSQL database dump
--

-- Dumped from database version 12.19
-- Dumped by pg_dump version 12.19

-- Started on 2024-06-08 18:23:32

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
-- TOC entry 203 (class 1259 OID 105813)
-- Name: repair_list; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.repair_list (
    id bigint NOT NULL,
    tipo_reparacion character varying(255) NOT NULL,
    precio_gasolina numeric(10,2) NOT NULL,
    precio_diesel numeric(10,2) NOT NULL,
    precio_hibrido numeric(10,2) NOT NULL,
    precio_electrico numeric(10,2) NOT NULL
);


ALTER TABLE public.repair_list OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 105811)
-- Name: repair_list_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.repair_list_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.repair_list_id_seq OWNER TO postgres;

--
-- TOC entry 2823 (class 0 OID 0)
-- Dependencies: 202
-- Name: repair_list_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.repair_list_id_seq OWNED BY public.repair_list.id;


--
-- TOC entry 2687 (class 2604 OID 105816)
-- Name: repair_list id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_list ALTER COLUMN id SET DEFAULT nextval('public.repair_list_id_seq'::regclass);


--
-- TOC entry 2817 (class 0 OID 105813)
-- Dependencies: 203
-- Data for Name: repair_list; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.repair_list (id, tipo_reparacion, precio_gasolina, precio_diesel, precio_hibrido, precio_electrico) FROM stdin;
1	Reparaciones del Sistema de Frenos	120000.00	120000.00	180000.00	220000.00
2	Servicio del Sistema de Refrigeración	130000.00	130000.00	190000.00	230000.00
3	Reparaciones del Motor	350000.00	450000.00	700000.00	800000.00
4	Reparaciones de la Transmisión	210000.00	210000.00	300000.00	300000.00
5	Reparación del Sistema Eléctrico	150000.00	150000.00	200000.00	250000.00
6	Reparaciones del Sistema de Escape	100000.00	120000.00	450000.00	0.00
7	Reparación de Neumáticos y Ruedas	100000.00	100000.00	100000.00	100000.00
8	Reparaciones de la Suspensión y la Dirección	180000.00	180000.00	210000.00	250000.00
9	Reparación del Sistema de Aire Acondicionado y Calefacción	150000.00	150000.00	180000.00	180000.00
10	Reparaciones del Sistema de Combustible	130000.00	140000.00	220000.00	0.00
11	Reparación y Reemplazo del Parabrisas y Cristales	80000.00	80000.00	80000.00	80000.00
\.


--
-- TOC entry 2824 (class 0 OID 0)
-- Dependencies: 202
-- Name: repair_list_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.repair_list_id_seq', 13, true);


--
-- TOC entry 2689 (class 2606 OID 105818)
-- Name: repair_list repair_list_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.repair_list
    ADD CONSTRAINT repair_list_pkey PRIMARY KEY (id);


-- Completed on 2024-06-08 18:23:33

--
-- PostgreSQL database dump complete
--

