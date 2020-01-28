--
-- PostgreSQL database dump
--

-- Dumped from database version 11.5 (Debian 11.5-1+deb10u1)
-- Dumped by pg_dump version 11.5 (Debian 11.5-1+deb10u1)

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

--
-- Name: delete_acknowledged_command(); Type: FUNCTION; Schema: public; Owner: humidity
--

CREATE FUNCTION public.delete_acknowledged_command() RETURNS trigger
    LANGUAGE plpgsql
    AS $$            
BEGIN                                   
        IF NEW.acknowledged = TRUE THEN                                                            
                DELETE FROM sensor_command                           
                WHERE sensor_command.command_id = OLD.command_id;    
        END  IF;                        
        RETURN NULL;                 
END;                                 
$$;


ALTER FUNCTION public.delete_acknowledged_command() OWNER TO humidity;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: sensor; Type: TABLE; Schema: public; Owner: humidity
--

CREATE TABLE public.sensor (
    sensor_id text NOT NULL,
    friendly_name text DEFAULT 'Please specify a friendly alias'::text NOT NULL,
    active boolean DEFAULT true NOT NULL,
    activated_on timestamp without time zone DEFAULT now() NOT NULL,
    deactivated_on timestamp without time zone
);


ALTER TABLE public.sensor OWNER TO humidity;

--
-- Name: sensor_command; Type: TABLE; Schema: public; Owner: humidity
--

CREATE TABLE public.sensor_command (
    command_id integer NOT NULL,
    sensor_id text NOT NULL,
    command integer NOT NULL,
    safety integer NOT NULL,
    acknowledged boolean DEFAULT false
);


ALTER TABLE public.sensor_command OWNER TO humidity;

--
-- Name: sensor_command_command_id_seq; Type: SEQUENCE; Schema: public; Owner: humidity
--

CREATE SEQUENCE public.sensor_command_command_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sensor_command_command_id_seq OWNER TO humidity;

--
-- Name: sensor_command_command_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: humidity
--

ALTER SEQUENCE public.sensor_command_command_id_seq OWNED BY public.sensor_command.command_id;


--
-- Name: sensor_data; Type: TABLE; Schema: public; Owner: humidity
--

CREATE TABLE public.sensor_data (
    entry_id integer NOT NULL,
    sensor_id text NOT NULL,
    humidity numeric,
    temperature numeric,
    entered_on timestamp without time zone NOT NULL,
    CONSTRAINT sensor_data_humidity_check CHECK ((humidity > (0)::numeric)),
    CONSTRAINT sensor_data_temperature_check CHECK ((temperature > (0)::numeric))
);


ALTER TABLE public.sensor_data OWNER TO humidity;

--
-- Name: sensor_data_entry_id_seq; Type: SEQUENCE; Schema: public; Owner: humidity
--

CREATE SEQUENCE public.sensor_data_entry_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.sensor_data_entry_id_seq OWNER TO humidity;

--
-- Name: sensor_data_entry_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: humidity
--

ALTER SEQUENCE public.sensor_data_entry_id_seq OWNED BY public.sensor_data.entry_id;


--
-- Name: sensor_command command_id; Type: DEFAULT; Schema: public; Owner: humidity
--

ALTER TABLE ONLY public.sensor_command ALTER COLUMN command_id SET DEFAULT nextval('public.sensor_command_command_id_seq'::regclass);


--
-- Name: sensor_data entry_id; Type: DEFAULT; Schema: public; Owner: humidity
--

ALTER TABLE ONLY public.sensor_data ALTER COLUMN entry_id SET DEFAULT nextval('public.sensor_data_entry_id_seq'::regclass);


--
-- Name: sensor_command sensor_command_pkey; Type: CONSTRAINT; Schema: public; Owner: humidity
--

ALTER TABLE ONLY public.sensor_command
    ADD CONSTRAINT sensor_command_pkey PRIMARY KEY (command_id);


--
-- Name: sensor_data sensor_data_pkey; Type: CONSTRAINT; Schema: public; Owner: humidity
--

ALTER TABLE ONLY public.sensor_data
    ADD CONSTRAINT sensor_data_pkey PRIMARY KEY (entry_id);


--
-- Name: sensor sensor_pkey; Type: CONSTRAINT; Schema: public; Owner: humidity
--

ALTER TABLE ONLY public.sensor
    ADD CONSTRAINT sensor_pkey PRIMARY KEY (sensor_id);


--
-- Name: sensor_command delete_acknowledged_commands; Type: TRIGGER; Schema: public; Owner: humidity
--

CREATE TRIGGER delete_acknowledged_commands AFTER UPDATE OF acknowledged ON public.sensor_command FOR EACH ROW WHEN ((new.acknowledged = true)) EXECUTE PROCEDURE public.delete_acknowledged_command();


--
-- Name: sensor_command sensor_command_sensor_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: humidity
--

ALTER TABLE ONLY public.sensor_command
    ADD CONSTRAINT sensor_command_sensor_id_fkey FOREIGN KEY (sensor_id) REFERENCES public.sensor(sensor_id);


--
-- Name: sensor_data sensor_id_reference; Type: FK CONSTRAINT; Schema: public; Owner: humidity
--

ALTER TABLE ONLY public.sensor_data
    ADD CONSTRAINT sensor_id_reference FOREIGN KEY (sensor_id) REFERENCES public.sensor(sensor_id);


--
-- PostgreSQL database dump complete
--

