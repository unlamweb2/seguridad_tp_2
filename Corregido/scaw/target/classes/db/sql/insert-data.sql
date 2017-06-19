INSERT INTO person (FIRSTNAME, LASTNAME, EMAIL) VALUES ('Cintia', 'Gioia', 'cgioia@unlam.edu.ar'); 
INSERT INTO person (FIRSTNAME, LASTNAME, EMAIL) VALUES ('Walter', 'Ureta', 'wureta@unlam.edu.ar');
INSERT INTO person (FIRSTNAME, LASTNAME, EMAIL) VALUES ('Juan', 'Monteagudo', 'jmonteagudo@unlam.edu.ar');

INSERT INTO usuario(NOMBRE,APELLIDO,CONTRASENA,TIPO,ESTAAPROBADO,NICKNAME) VALUES  ('user1','molaro','123','usuarioNormal',1,'user1');
INSERT INTO usuario(NOMBRE,APELLIDO,CONTRASENA,TIPO,ESTAAPROBADO,NICKNAME) VALUES  ('user2','molaro','123','usuarioNormal',1,'user2');
INSERT INTO usuario(NOMBRE,APELLIDO,CONTRASENA,TIPO,ESTAAPROBADO,NICKNAME) VALUES  ('denegado','molaro','123','usuarioNormal',0,'denegado');
INSERT INTO usuario(NOMBRE,APELLIDO,CONTRASENA,TIPO,ESTAAPROBADO,NICKNAME) VALUES  ('axel','molaro','123','usuarioAdministrador',1,'admin');
INSERT INTO usuario(NOMBRE,APELLIDO,CONTRASENA,TIPO,ESTAAPROBADO,NICKNAME) VALUES  ('jony','molaro2','axel1232','usuarioAdministrador',1,'nickName5');

INSERT INTO tarea(NOMBRE,DESCRIPCION,ACCESO,ESTADO) VALUES ('Limpieza','barrer la cocina','privada','pendiente');
INSERT INTO tarea(NOMBRE,DESCRIPCION,ACCESO,ESTADO) VALUES ('tareaNoPendiente','barrer la cocina','privada','completa');
INSERT INTO tarea(NOMBRE,DESCRIPCION,ACCESO,ESTADO) VALUES ('Tarea 2 publica','limpiar el baño','publica','pendiente');

INSERT INTO accedetarea(MODO,IDTAREA,IDUSUARIO) VALUES ('escritura','1','1');
INSERT INTO accedetarea(MODO,IDTAREA,IDUSUARIO) VALUES ('lectura','2','1');
INSERT INTO accedetarea(MODO,IDTAREA,IDUSUARIO) VALUES ('lectura','3','2');



--COMMIT;

