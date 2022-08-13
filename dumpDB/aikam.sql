--
-- PostgreSQL database dump
--

-- Dumped from database version 13.6
-- Dumped by pg_dump version 13.6

-- Started on 2022-08-13 13:16:47

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
-- TOC entry 201 (class 1259 OID 16524)
-- Name: buyers; Type: TABLE; Schema: public; Owner: Maksim
--

CREATE TABLE public.buyers (
    id integer NOT NULL,
    first_name character varying(50) NOT NULL,
    last_name character varying(50) NOT NULL
);


ALTER TABLE public.buyers OWNER TO "Maksim";

--
-- TOC entry 200 (class 1259 OID 16522)
-- Name: buyers_id_seq; Type: SEQUENCE; Schema: public; Owner: Maksim
--

CREATE SEQUENCE public.buyers_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.buyers_id_seq OWNER TO "Maksim";

--
-- TOC entry 3012 (class 0 OID 0)
-- Dependencies: 200
-- Name: buyers_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Maksim
--

ALTER SEQUENCE public.buyers_id_seq OWNED BY public.buyers.id;


--
-- TOC entry 203 (class 1259 OID 16532)
-- Name: products; Type: TABLE; Schema: public; Owner: Maksim
--

CREATE TABLE public.products (
    id integer NOT NULL,
    product_name character varying(100) NOT NULL,
    price integer NOT NULL
);


ALTER TABLE public.products OWNER TO "Maksim";

--
-- TOC entry 202 (class 1259 OID 16530)
-- Name: products_id_seq; Type: SEQUENCE; Schema: public; Owner: Maksim
--

CREATE SEQUENCE public.products_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.products_id_seq OWNER TO "Maksim";

--
-- TOC entry 3013 (class 0 OID 0)
-- Dependencies: 202
-- Name: products_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Maksim
--

ALTER SEQUENCE public.products_id_seq OWNED BY public.products.id;


--
-- TOC entry 205 (class 1259 OID 16540)
-- Name: purchases; Type: TABLE; Schema: public; Owner: Maksim
--

CREATE TABLE public.purchases (
    id integer NOT NULL,
    buyers_id integer,
    products_id integer,
    date_of_buy date NOT NULL
);


ALTER TABLE public.purchases OWNER TO "Maksim";

--
-- TOC entry 204 (class 1259 OID 16538)
-- Name: purchases_id_seq; Type: SEQUENCE; Schema: public; Owner: Maksim
--

CREATE SEQUENCE public.purchases_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.purchases_id_seq OWNER TO "Maksim";

--
-- TOC entry 3014 (class 0 OID 0)
-- Dependencies: 204
-- Name: purchases_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: Maksim
--

ALTER SEQUENCE public.purchases_id_seq OWNED BY public.purchases.id;


--
-- TOC entry 2862 (class 2604 OID 16527)
-- Name: buyers id; Type: DEFAULT; Schema: public; Owner: Maksim
--

ALTER TABLE ONLY public.buyers ALTER COLUMN id SET DEFAULT nextval('public.buyers_id_seq'::regclass);


--
-- TOC entry 2863 (class 2604 OID 16535)
-- Name: products id; Type: DEFAULT; Schema: public; Owner: Maksim
--

ALTER TABLE ONLY public.products ALTER COLUMN id SET DEFAULT nextval('public.products_id_seq'::regclass);


--
-- TOC entry 2864 (class 2604 OID 16543)
-- Name: purchases id; Type: DEFAULT; Schema: public; Owner: Maksim
--

ALTER TABLE ONLY public.purchases ALTER COLUMN id SET DEFAULT nextval('public.purchases_id_seq'::regclass);


--
-- TOC entry 3002 (class 0 OID 16524)
-- Dependencies: 201
-- Data for Name: buyers; Type: TABLE DATA; Schema: public; Owner: Maksim
--

INSERT INTO public.buyers (id, first_name, last_name) VALUES (1, 'Иван', 'Иванов');
INSERT INTO public.buyers (id, first_name, last_name) VALUES (2, 'Петр', 'Петров');
INSERT INTO public.buyers (id, first_name, last_name) VALUES (3, 'Леха', 'Шабанов');
INSERT INTO public.buyers (id, first_name, last_name) VALUES (4, 'Андрей', 'Аксенов');
INSERT INTO public.buyers (id, first_name, last_name) VALUES (5, 'Максим', 'Комозин');
INSERT INTO public.buyers (id, first_name, last_name) VALUES (6, 'Виталий', 'Упрямов');
INSERT INTO public.buyers (id, first_name, last_name) VALUES (7, 'Николай', 'Шишкин');
INSERT INTO public.buyers (id, first_name, last_name) VALUES (8, 'Андрей', 'Суворов');
INSERT INTO public.buyers (id, first_name, last_name) VALUES (9, 'Макcим', 'Захаров');
INSERT INTO public.buyers (id, first_name, last_name) VALUES (10, 'Дмитрий', 'Карпухин');


--
-- TOC entry 3004 (class 0 OID 16532)
-- Dependencies: 203
-- Data for Name: products; Type: TABLE DATA; Schema: public; Owner: Maksim
--

INSERT INTO public.products (id, product_name, price) VALUES (1, 'Минеральная вода', 50);
INSERT INTO public.products (id, product_name, price) VALUES (2, 'Кока-кола', 100);
INSERT INTO public.products (id, product_name, price) VALUES (3, 'Хлеб', 35);
INSERT INTO public.products (id, product_name, price) VALUES (4, 'Батон', 40);
INSERT INTO public.products (id, product_name, price) VALUES (5, 'Краковская колбаса', 250);
INSERT INTO public.products (id, product_name, price) VALUES (6, 'Сок', 150);
INSERT INTO public.products (id, product_name, price) VALUES (7, 'Молоко', 75);
INSERT INTO public.products (id, product_name, price) VALUES (8, 'Чай', 120);
INSERT INTO public.products (id, product_name, price) VALUES (9, 'Кофе', 400);
INSERT INTO public.products (id, product_name, price) VALUES (10, 'Кефир', 80);


--
-- TOC entry 3006 (class 0 OID 16540)
-- Dependencies: 205
-- Data for Name: purchases; Type: TABLE DATA; Schema: public; Owner: Maksim
--

INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (1, 1, 1, '2022-08-08');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (2, 2, 2, '2022-06-06');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (3, 1, 2, '2022-05-23');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (4, 3, 2, '2022-06-25');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (5, 1, 3, '2022-08-07');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (6, 4, 2, '2022-08-08');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (7, 2, 2, '2022-06-06');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (8, 6, 2, '2022-05-23');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (9, 5, 2, '2022-06-25');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (10, 4, 2, '2022-08-21');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (11, 4, 2, '2022-06-22');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (12, 4, 2, '2022-05-17');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (13, 4, 2, '2022-06-18');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (14, 6, 2, '2022-07-11');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (15, 6, 2, '2022-06-18');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (16, 6, 2, '2022-07-21');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (17, 6, 2, '2022-05-25');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (18, 8, 3, '2022-08-11');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (19, 8, 2, '2022-07-11');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (20, 8, 5, '2022-07-11');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (21, 8, 6, '2022-07-11');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (22, 8, 4, '2022-06-21');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (23, 8, 5, '2022-06-22');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (24, 8, 4, '2022-06-17');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (25, 8, 7, '2022-06-18');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (26, 8, 8, '2022-06-19');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (27, 8, 5, '2022-06-19');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (28, 7, 7, '2022-07-21');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (29, 7, 2, '2022-05-25');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (30, 9, 9, '2022-08-11');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (31, 9, 9, '2022-07-11');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (32, 9, 9, '2022-07-11');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (33, 9, 8, '2022-07-11');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (34, 7, 8, '2022-06-21');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (35, 7, 5, '2022-06-22');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (36, 7, 4, '2022-06-17');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (37, 7, 7, '2022-06-18');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (38, 7, 8, '2022-06-19');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (39, 6, 5, '2022-06-19');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (40, 6, 7, '2022-07-21');
INSERT INTO public.purchases (id, buyers_id, products_id, date_of_buy) VALUES (41, 6, 2, '2022-05-25');


--
-- TOC entry 3015 (class 0 OID 0)
-- Dependencies: 200
-- Name: buyers_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Maksim
--

SELECT pg_catalog.setval('public.buyers_id_seq', 10, true);


--
-- TOC entry 3016 (class 0 OID 0)
-- Dependencies: 202
-- Name: products_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Maksim
--

SELECT pg_catalog.setval('public.products_id_seq', 10, true);


--
-- TOC entry 3017 (class 0 OID 0)
-- Dependencies: 204
-- Name: purchases_id_seq; Type: SEQUENCE SET; Schema: public; Owner: Maksim
--

SELECT pg_catalog.setval('public.purchases_id_seq', 41, true);


--
-- TOC entry 2866 (class 2606 OID 16529)
-- Name: buyers buyers_pkey; Type: CONSTRAINT; Schema: public; Owner: Maksim
--

ALTER TABLE ONLY public.buyers
    ADD CONSTRAINT buyers_pkey PRIMARY KEY (id);


--
-- TOC entry 2868 (class 2606 OID 16537)
-- Name: products products_pkey; Type: CONSTRAINT; Schema: public; Owner: Maksim
--

ALTER TABLE ONLY public.products
    ADD CONSTRAINT products_pkey PRIMARY KEY (id);


--
-- TOC entry 2869 (class 2606 OID 16544)
-- Name: purchases purchases_buyers_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Maksim
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_buyers_id_fkey FOREIGN KEY (buyers_id) REFERENCES public.buyers(id);


--
-- TOC entry 2870 (class 2606 OID 16549)
-- Name: purchases purchases_products_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: Maksim
--

ALTER TABLE ONLY public.purchases
    ADD CONSTRAINT purchases_products_id_fkey FOREIGN KEY (products_id) REFERENCES public.products(id);


-- Completed on 2022-08-13 13:16:49

--
-- PostgreSQL database dump complete
--

