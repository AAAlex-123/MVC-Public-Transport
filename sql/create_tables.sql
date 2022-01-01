DROP DATABASE IF EXISTS oasaripoff;
CREATE DATABASE oasaripoff;

USE oasaripoff;

CREATE TABLE IF NOT EXISTS City (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(31)
);

CREATE TABLE IF NOT EXISTS Station (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(63),
	x_coord DOUBLE,
	y_coord DOUBLE,
	city_id INT,
	FOREIGN KEY (city_id) REFERENCES City (id)
);

CREATE TABLE IF NOT EXISTS Line (
	id INT AUTO_INCREMENT PRIMARY KEY,
	lineNo VARCHAR(7), /* A5 / 308 / B9 */
	description VARCHAR(255), /* start-end */
	type VARCHAR(15),
);

CREATE TABLE IF NOT EXISTS LineStation (
	line_id INT,
	station_id INT,
	station_index INT, /* ! */
	PRIMARY KEY (line_id, station_id),
	FOREIGN KEY (line_id) REFERENCES Line(id),
	FOREIGN KEY (station_id) REFERENCES Station(id)
);

CREATE TABLE IF NOT EXISTS LineTimetable (
	line_id INT,
	departure_time TIME, /* ! */
	
	PRIMARY KEY (line_id, departure_time),
	FOREIGN KEY (line_id) REFERENCES Line(id),
);

/*
pp with:
er
diagramma klasewn
activity diagram for 2-3 activities (PCE for VCM)
very sinoptika o kwdikas

leitoyrgikothta -> arxitektonikh
er -> model ->

*/
