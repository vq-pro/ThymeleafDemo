CREATE TABLE ITEM
(
	id SERIAL UNIQUE,
	name VARCHAR(255) UNIQUE NOT NULL,
	PRIMARY KEY (id)
);
