DROP TABLE PERSON IF EXISTS;
DROP TABLE USUARIO IF EXISTS CASCADE;
DROP TABLE TAREA IF EXISTS CASCADE;
DROP TABLE ACCEDETAREA IF EXISTS CASCADE ;

CREATE TABLE PERSON (
  ID 			INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
  FIRSTNAME 	VARCHAR(30),
  LASTNAME 		VARCHAR(30),
  EMAIL  		VARCHAR(50)
);

CREATE TABLE USUARIO(

	  ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,
	  NOMBRE VARCHAR (30),
	  APELLIDO VARCHAR(30),
	  CONTRASENA VARCHAR(30),
	  TIPO VARCHAR (30),
	  ESTAAPROBADO INTEGER,
	  NICKNAME VARCHAR (30)

);


CREATE TABLE TAREA(
	 ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,	
		NOMBRE VARCHAR (30),
		DESCRIPCION VARCHAR(200),	
		ACCESO VARCHAR(30),
		ESTADO VARCHAR(30)
);

CREATE TABLE ACCEDETAREA(
	
 	ID INTEGER GENERATED BY DEFAULT AS IDENTITY(START WITH 1, INCREMENT BY 1) PRIMARY KEY,	
 	MODO VARCHAR(30),
 	IDTAREA INTEGER,
 	IDUSUARIO INTEGER,
 	CONSTRAINT FK_IDTAREA_ACCEDETAREA  FOREIGN KEY (IDTAREA) REFERENCES TAREA(ID) ON DELETE SET NULL ,
 	CONSTRAINT FK_IDUSUARIO_ACCEDETAREA FOREIGN KEY (IDUSUARIO) REFERENCES USUARIO(ID) ON DELETE CASCADE
	
);






