DROP TABLE IF EXISTS employee_driving_license;
DROP TABLE IF EXISTS customer_theory_lesson;
DROP TABLE IF EXISTS customer_driving_license;
DROP TABLE IF EXISTS lesson;
DROP TABLE IF EXISTS subject;
DROP TABLE IF EXISTS vehicle;
DROP TABLE IF EXISTS driving_license;
DROP TABLE IF EXISTS classroom;
DROP TABLE IF EXISTS location;
DROP TABLE IF EXISTS customer;
DROP TABLE IF EXISTS employee;


CREATE  TABLE customer ( 
	id                   bigserial  NOT NULL  ,
	name                 varchar  NOT NULL  ,
	first_name           varchar  NOT NULL  ,
	passed_theory_exam   boolean DEFAULT false NOT NULL  ,
	CONSTRAINT pk_klant PRIMARY KEY ( id )
 );

CREATE  TABLE driving_license ( 
	id                   bigserial  NOT NULL  ,
	license              varchar  NOT NULL  ,
	transmission         varchar  NOT NULL  ,
	CONSTRAINT pk_rijbewijs PRIMARY KEY ( id )
 );

CREATE  TABLE employee ( 
	id                   bigserial  NOT NULL  ,
	name                 varchar  NOT NULL  ,
	first_name           varchar  NOT NULL  ,
	address_with_house_number varchar  NOT NULL  ,
	city                 varchar  NOT NULL  ,
	zip_code             integer  NOT NULL  ,
	CONSTRAINT pk_medewerker PRIMARY KEY ( id )
 );

CREATE  TABLE employee_driving_license ( 
	employee_id          bigint  NOT NULL  ,
	driving_license_id   bigint  NOT NULL  ,
	CONSTRAINT pk_medewerker_heeft_rijbewijs PRIMARY KEY ( employee_id, driving_license_id ),
	CONSTRAINT fk_medewerker_heeft_rijbewijs_medewerker FOREIGN KEY ( employee_id ) REFERENCES employee( id )   ,
	CONSTRAINT fk_medewerker_heeft_rijbewijs_rijbewijs FOREIGN KEY ( driving_license_id ) REFERENCES driving_license( id )   
 );


CREATE  TABLE "location" ( 
	id                   bigserial  NOT NULL  ,
	name                 varchar  NOT NULL  ,
	address_with_house_number varchar  NOT NULL  ,
	city                 varchar  NOT NULL  ,
	zip_code             integer    ,
	CONSTRAINT pk_vestiging PRIMARY KEY ( id )
 );

CREATE  TABLE subject ( 
	title                varchar  NOT NULL  ,
	max_nr_of_participants integer    ,
	id                   bigserial  NOT NULL  ,
	CONSTRAINT pk_onderwerp PRIMARY KEY ( id )
 );

CREATE  TABLE vehicle ( 
	id                   bigserial  NOT NULL  ,
	license_plate        varchar  NOT NULL  ,
	license_id           bigint  NOT NULL  ,
	brand                varchar    ,
	model                varchar    ,
	CONSTRAINT pk_voertuig PRIMARY KEY ( id ),
	CONSTRAINT fk_voertuig_rijbewijs FOREIGN KEY ( license_id ) REFERENCES driving_license( id )   
 );

CREATE  TABLE classroom ( 
	id                   bigserial  NOT NULL  ,
	location_id          bigint  NOT NULL  ,
	capacity             integer    ,
	CONSTRAINT pk_lokaal PRIMARY KEY ( id ),
	CONSTRAINT fk_leslokaal_vestiging FOREIGN KEY ( location_id ) REFERENCES "location"( id )   
 );

CREATE  TABLE customer_driving_license ( 
	customer_id          bigint  NOT NULL  ,
	driving_license_id   bigint  NOT NULL  ,
	CONSTRAINT pk_klant_heeft_rijbewijs PRIMARY KEY ( customer_id, driving_license_id ),
	CONSTRAINT fk_klant_heeft_rijbewijs_rijbewijs FOREIGN KEY ( driving_license_id ) REFERENCES driving_license( id )   ,
	CONSTRAINT fk_klant_heeft_rijbewijs_klant FOREIGN KEY ( customer_id ) REFERENCES customer( id )   
 );

CREATE  TABLE lesson ( 
	id                   bigserial  NOT NULL  ,
	"date"               date  NOT NULL  ,
	time_start           time  NOT NULL  ,
	subject_id           bigint    ,
	classroom_id         bigint    ,
	employee_id  		 bigint    ,
	vehicle_id           bigint    ,
	duration_minutes     integer DEFAULT 120 NOT NULL  ,
	CONSTRAINT pk_les PRIMARY KEY ( id ),
	CONSTRAINT fk_les_onderwerp FOREIGN KEY ( subject_id ) REFERENCES subject( id )   ,
	CONSTRAINT fk_les_medewerker FOREIGN KEY ( employee_id ) REFERENCES employee( id )   ,
	CONSTRAINT fk_les_leslokaal FOREIGN KEY ( classroom_id ) REFERENCES classroom( id )   ,
	CONSTRAINT fk_les_voertuig FOREIGN KEY ( vehicle_id ) REFERENCES vehicle( id )   
 );

CREATE  TABLE customer_theory_lesson (
	customer_id          bigint  NOT NULL  ,
	lesson_id            bigint  NOT NULL  ,
	CONSTRAINT pk_klant_volgt_theorieles PRIMARY KEY ( customer_id, lesson_id ),
	CONSTRAINT fk_klant_volgt_theorieles_les FOREIGN KEY ( lesson_id ) REFERENCES lesson( id )   ,
	CONSTRAINT fk_klant_volgt_theorieles_klant FOREIGN KEY ( customer_id ) REFERENCES customer( id )   
 );

INSERT INTO customer( name, first_name, passed_theory_exam ) VALUES ( 'Gilroy', 'Nadège', true);
INSERT INTO customer( name, first_name, passed_theory_exam ) VALUES ( 'Gibben', 'Clémentine', false);
INSERT INTO customer( name, first_name, passed_theory_exam ) VALUES ( 'Stanmore', 'Danièle', true);
INSERT INTO customer( name, first_name, passed_theory_exam ) VALUES ( 'Barbera', 'Adélie', false);
INSERT INTO customer( name, first_name, passed_theory_exam ) VALUES ( 'MacGillivrie', 'Yè', true);
INSERT INTO customer( name, first_name, passed_theory_exam ) VALUES ( 'Steptoe', 'Maëlys', false);

INSERT INTO driving_license(  license, transmission ) VALUES (  'B', 'Manual');
INSERT INTO driving_license(  license, transmission ) VALUES (  'A1', 'Manual');
INSERT INTO driving_license(  license, transmission ) VALUES (  'A2', 'Manual');

INSERT INTO employee( name, first_name, address_with_house_number, city, zip_code ) VALUES (  'Dubois', 'Olivia', '5 Mei plein  204', 'Bilzen', 3740);
INSERT INTO employee( name, first_name, address_with_house_number, city, zip_code ) VALUES (  'Willems', 'Arthur', 'Aan de Rijn  343', 'Zwalm', 9630);
INSERT INTO employee( name, first_name, address_with_house_number, city, zip_code ) VALUES (  'Janssens', 'Noah', 'Abersonlaan  28', 'Sint-Pieters-Leeuw', 1600);
INSERT INTO employee( name, first_name, address_with_house_number, city, zip_code ) VALUES (  'Mertens', 'Alice', 'Abraham Kuyperstraat  253', 'Merchtem', 1785);

INSERT INTO employee_driving_license( employee_id, driving_license_id ) VALUES ( 1, 1);
INSERT INTO employee_driving_license( employee_id, driving_license_id ) VALUES ( 1, 2);
INSERT INTO employee_driving_license( employee_id, driving_license_id ) VALUES ( 1, 3);
INSERT INTO employee_driving_license( employee_id, driving_license_id ) VALUES ( 2, 1);
INSERT INTO employee_driving_license( employee_id, driving_license_id ) VALUES ( 3, 2);
INSERT INTO employee_driving_license( employee_id, driving_license_id ) VALUES ( 4, 2);
INSERT INTO employee_driving_license( employee_id, driving_license_id ) VALUES ( 4, 3);

INSERT INTO "location"( name, address_with_house_number, city, zip_code ) VALUES ( 'Rijschool Haasrode', 'Winkelstraat 12', 'Haasrode', 3053);
INSERT INTO "location"( name, address_with_house_number, city, zip_code ) VALUES ( 'Rijschool Alken', 'Geldenaaksebaan 302', 'Alken', 3570);

INSERT INTO subject( title, max_nr_of_participants, id ) VALUES ( 'What is the meaning of the traffic sign yield?', 20, 1);
INSERT INTO subject( title, max_nr_of_participants, id ) VALUES ( 'How long must you keep your brake lights on when you stop at a stop sign?', 8, 2);
INSERT INTO subject( title, max_nr_of_participants, id ) VALUES ( 'What is the maximum blood alcohol concentration (BAC) for drivers in Belgium?', 14, 3);
INSERT INTO subject( title, max_nr_of_participants, id ) VALUES ( 'What is the function of the engine in a car?', 6, 4);
INSERT INTO subject( title, max_nr_of_participants, id ) VALUES ( 'How does a braking system work?', 20, 5);

INSERT INTO vehicle( license_plate, license_id, brand, model ) VALUES ( '2-BRL-179', 1, 'Volkswagen', 'Golf');
INSERT INTO vehicle( license_plate, license_id, brand, model ) VALUES ( '2-YFZ-154', 2, 'Ford', 'Focus');
INSERT INTO vehicle( license_plate, license_id, brand, model ) VALUES ( '2-KTK-628', 3, 'BMW', '3 Serie');
INSERT INTO vehicle( license_plate, license_id, brand, model ) VALUES ( '2-HFW-367', 1, 'Peugeot', '308');

INSERT INTO classroom( location_id, capacity ) VALUES ( 1, 20);
INSERT INTO classroom( location_id, capacity ) VALUES ( 1, 20);
INSERT INTO classroom( location_id, capacity ) VALUES ( 2, 20);
INSERT INTO classroom( location_id, capacity ) VALUES ( 2, 20);

INSERT INTO customer_driving_license( customer_id, driving_license_id ) VALUES ( 1, 1);
INSERT INTO customer_driving_license( customer_id, driving_license_id ) VALUES ( 2, 1);
INSERT INTO customer_driving_license( customer_id, driving_license_id ) VALUES ( 3, 1);
INSERT INTO customer_driving_license( customer_id, driving_license_id ) VALUES ( 4, 2);
INSERT INTO customer_driving_license( customer_id, driving_license_id ) VALUES ( 6, 3);

INSERT INTO lesson( "date", time_start, subject_id, classroom_id, employee_id, vehicle_id, duration_minutes ) VALUES (  '2006-01-02', '08:00:00', 1, 4, 4, null, 120);
INSERT INTO lesson( "date", time_start, subject_id, classroom_id, employee_id, vehicle_id, duration_minutes ) VALUES (  '2006-01-02', '08:00:00', 2, 3, 2, 1, 120);
INSERT INTO lesson( "date", time_start, subject_id, classroom_id, employee_id, vehicle_id, duration_minutes ) VALUES (  '2006-01-04', '17:00:00', 3, 1, 1, null, 120);
INSERT INTO lesson( "date", time_start, subject_id, classroom_id, employee_id, vehicle_id, duration_minutes ) VALUES (  '2006-01-05', '08:00:00', 3, 2, 3, null, 120);
INSERT INTO lesson( "date", time_start, subject_id, classroom_id, employee_id, vehicle_id, duration_minutes ) VALUES (  '2006-01-05', '13:00:00', 3, 3, 4, null, 120);
INSERT INTO lesson( "date", time_start, subject_id, classroom_id, employee_id, vehicle_id, duration_minutes ) VALUES (  '2006-01-05', '08:00:00', 4, 1, 1, 2, 120);

INSERT INTO customer_theory_lesson( customer_id, lesson_id ) VALUES ( 1, 1);
INSERT INTO customer_theory_lesson( customer_id, lesson_id ) VALUES ( 2, 2);
INSERT INTO customer_theory_lesson( customer_id, lesson_id ) VALUES ( 3, 3);
INSERT INTO customer_theory_lesson( customer_id, lesson_id ) VALUES ( 4, 4);
INSERT INTO customer_theory_lesson( customer_id, lesson_id ) VALUES ( 5, 5);
INSERT INTO customer_theory_lesson( customer_id, lesson_id ) VALUES ( 6, 5);
INSERT INTO customer_theory_lesson( customer_id, lesson_id ) VALUES ( 1, 5);
