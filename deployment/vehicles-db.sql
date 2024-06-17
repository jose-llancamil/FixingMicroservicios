-- Conectar a la base de datos `postgres`
\c postgres;

-- Crear la nueva base de datos
CREATE DATABASE "vehicles-db";

-- Conectar a la nueva base de datos
\c "vehicles-db";
--
-- PostgreSQL database dump
--

-- Dumped from database version 12.19
-- Dumped by pg_dump version 12.19

-- Started on 2024-06-08 18:24:54

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
-- TOC entry 203 (class 1259 OID 114002)
-- Name: vehicles; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.vehicles (
    id bigint NOT NULL,
    patente character varying(10) NOT NULL,
    marca character varying(50) NOT NULL,
    modelo character varying(50) NOT NULL,
    tipo character varying(20) NOT NULL,
    anio_fabricacion integer NOT NULL,
    tipo_motor character varying(20) NOT NULL,
    numero_asientos integer NOT NULL,
    kilometraje integer NOT NULL
);


ALTER TABLE public.vehicles OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 114000)
-- Name: vehicles_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.vehicles_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.vehicles_id_seq OWNER TO postgres;

--
-- TOC entry 2825 (class 0 OID 0)
-- Dependencies: 202
-- Name: vehicles_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.vehicles_id_seq OWNED BY public.vehicles.id;


--
-- TOC entry 2687 (class 2604 OID 114005)
-- Name: vehicles id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles ALTER COLUMN id SET DEFAULT nextval('public.vehicles_id_seq'::regclass);


--
-- TOC entry 2819 (class 0 OID 114002)
-- Dependencies: 203
-- Data for Name: vehicles; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.vehicles (id, patente, marca, modelo, tipo, anio_fabricacion, tipo_motor, numero_asientos, kilometraje) FROM stdin;
1	ABCD01	Toyota	Corolla	Sedan	2020	Gasolina	5	15000
2	EFGH02	Ford	Fiesta	Hatchback	2018	Diésel	5	22000
3	IJKL03	Hyundai	Tucson	SUV	2021	Híbrido	5	8000
4	MNOP04	Honda	Ridgeline	Pickup	2019	Eléctrico	5	30000
5	QRST05	Chevrolet	Express	Furgoneta	2017	Gasolina	8	35000
6	UVWXYZ	Mazda	CX-5	SUV	2020	Gasolina	5	45000
8	VEHI01	Toyota	Hilux	Pickup	2020	Diésel	4	55500
9	POIU12	Toyota	Tercel	SUV	1998	Gasolina	5	30000
\.


--
-- TOC entry 2826 (class 0 OID 0)
-- Dependencies: 202
-- Name: vehicles_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.vehicles_id_seq', 9, true);


--
-- TOC entry 2689 (class 2606 OID 114009)
-- Name: vehicles vehicles_patente_key; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles
    ADD CONSTRAINT vehicles_patente_key UNIQUE (patente);


--
-- TOC entry 2691 (class 2606 OID 114007)
-- Name: vehicles vehicles_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.vehicles
    ADD CONSTRAINT vehicles_pkey PRIMARY KEY (id);


-- Completed on 2024-06-08 18:24:55

--
-- PostgreSQL database dump complete
--

