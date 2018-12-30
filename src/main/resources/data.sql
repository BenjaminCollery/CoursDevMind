/*
Configuration initiale de la base de données afin de mener des tests
 */

/*
Insertion de quatre buildings
 */
INSERT INTO BUILDING(ID, NAME) VALUES(1,'Mines');
INSERT INTO BUILDING(ID, NAME) VALUES(2,'EF');
INSERT INTO BUILDING(ID, NAME) VALUES(3,'ME');
INSERT INTO BUILDING(ID, NAME) VALUES(4,'KEBAB');

/*
Insertion de 2 rooms dans le building 1, 1 room dans le building 2 et 1 room dans le building 3
On notera que chaque room bénéficie d'un ID unique du fait que cet attribue soit la clef primaire : il ne peut pas y avoir deux rooms d'ID 1 même dans deux buildings différents !
 */
INSERT INTO ROOM(ID, NAME, FLOOR, STATUS, BUILDING_ID) VALUES(1, 'Room1', 1, 'ON' , 1);
INSERT INTO ROOM(ID, NAME, FLOOR, STATUS, BUILDING_ID) VALUES(2, 'Room2', 1, 'ON' , 1);
INSERT INTO ROOM(ID, NAME, FLOOR, STATUS, BUILDING_ID) VALUES(3, 'Room3', 1, 'ON' , 2);
INSERT INTO ROOM(ID, NAME, FLOOR, STATUS, BUILDING_ID) VALUES(4, 'Room4', 1, 'ON' , 3);

/*
Insertion de 2 lights dans la room 1, 1 light dans la room 2 et 1 light dans la room 3
 */
INSERT INTO LIGHT(ID, LEVEL, STATUS, ROOM_ID , COLOR , BRIGHTNESS) VALUES (1, 8, 'ON', 1 , 'vert' , '1000');
INSERT INTO LIGHT(ID, LEVEL, STATUS, ROOM_ID , COLOR , BRIGHTNESS) VALUES (2, 0, 'OFF', 1 , 'vert', '1000');
INSERT INTO LIGHT(ID, LEVEL, STATUS, ROOM_ID ,  COLOR , BRIGHTNESS) VALUES (3, 8, 'ON', 2 , 'vert', '1000');
INSERT INTO LIGHT(ID, LEVEL, STATUS, ROOM_ID , COLOR , BRIGHTNESS) VALUES (4, 0, 'OFF', 3 , 'vert', '1000');