CREATE DATABASE IF NOT EXISTS oasaripoff;

USE oasaripoff;

CREATE TABLE IF NOT EXISTS City (
   id INT AUTO_INCREMENT PRIMARY KEY,
   name VARCHAR(31)
);

CREATE TABLE IF NOT EXISTS Station (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(63),
	x_coord INT,
	y_coord INT,
	city_id INT,
	FOREIGN KEY (city_id) REFERENCES City (id)
);

CREATE TABLE IF NOT EXISTS Line (
	id INT AUTO_INCREMENT PRIMARY KEY,
	name VARCHAR(255),
	type VARCHAR(15),
	is_return BOOLEAN
);

CREATE TABLE IF NOT EXISTS LineStation (
	line_id INT,
	station_id INT,
	PRIMARY KEY (line_id, station_id),
	FOREIGN KEY (line_id) REFERENCES Line(id),
	FOREIGN KEY (station_id) REFERENCES Station(id)
);

CREATE TABLE IF NOT EXISTS Timetable (
	id INT AUTO_INCREMENT PRIMARY KEY,
	hour INT,
	minute INT
);

CREATE TABLE IF NOT EXISTS LineTimetable (
	line_id INT,
	timetable_id INT,
	PRIMARY KEY (line_id, timetable_id),
	FOREIGN KEY (line_id) REFERENCES Line(id),
	FOREIGN KEY (timetable_id) REFERENCES Timetable(id)
);
