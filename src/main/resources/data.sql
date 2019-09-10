INSERT INTO Roles (id, name) VALUES (1, 'goust');
INSERT INTO Roles (id, name) VALUES (2, 'user');
INSERT INTO Roles (id, name) VALUES (3, 'moderator');
INSERT INTO Roles (id, name) VALUES (4, 'administrator');
INSERT INTO Roles (id, name) VALUES (5, 'owner');


INSERT INTO Users (id, login, email, password, icon, active) VALUES (1, 'goust', 'goust@i.ua', 'goust', 'no_user.jpg',true);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (2, 'user', 'user@i.ua', 'user', 'no_user.jpg',true);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (3, 'moderator', 'moderator@i.ua', 'moderator', 'no_user.jpg',true);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (4, 'administrator', 'administrator@i.ua', 'administrator', 'no_user.jpg',true);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (5, 'owner', 'owner@i.ua', 'owner', 'no_user.jpg',true);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (6, 'goblin', 'goblin@i.ua', 'goblin', 'no_user.jpg',false);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (7, 'pasha', 'gpashat@i.ua', 'pasha', 'no_user.jpg',false);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (8, 'lenya', 'lenya@i.ua', 'lenya', 'no_user.jpg',false);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (9, 'ben', 'moddern@i.ua', 'ben', 'no_user.jpg',false);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (10, 'zyuzya', 'zyuzya@i.ua', 'zyuzya', 'no_user.jpg',false);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (11, 'macar', 'macar@i.ua', 'macar', 'no_user.jpg',false);
INSERT INTO Users (id, login, email, password, icon, active) VALUES (12, 'focus', 'focus@i.ua', 'focus', 'no_user.jpg',false);


INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (1, 1); INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (1, 4);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (2, 1); INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (2, 2);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (3, 1); INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (3, 2);  INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (3, 3);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (4, 1); INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (4, 2);  INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (4, 3); INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (4, 4);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (5, 1); INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (5, 5);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (6, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (7, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (8, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (9, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (10, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (11, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (12, 1);

INSERT INTO Accesslinks (id, reading, dowload, edit, link) VALUES (1, false, true, true, 'test link 1');
INSERT INTO Accesslinks (id, reading, dowload, edit, link) VALUES (2, false, false, true, 'No link');
INSERT INTO Accesslinks (id, reading, dowload, edit, link) VALUES (3, false, true, true, 'test link 2');
INSERT INTO Accesslinks (id, reading, dowload, edit, link) VALUES (4, true, true, true, 'test link 3');
INSERT INTO Accesslinks (id, reading, dowload, edit, link) VALUES (5, false, false, true, 'No link');
INSERT INTO Accesslinks (id, reading, dowload, edit, link) VALUES (6, false, false, true, 'No link');
INSERT INTO Accesslinks (id, reading, dowload, edit, link) VALUES (7, true, true, true, 'test lik 4');
INSERT INTO Accesslinks (id, reading, dowload, edit, link) VALUES (8, false, false, true, 'No link');
INSERT INTO Accesslinks (id, reading, dowload, edit, link) VALUES (9, true, false, true, '8462beb7-9c86-48be-9f3d-dddc7a426acf');

INSERT INTO Myfiles (id, name, origin_name, creator_of_file_id, access_link_id) VALUES (1, 'SunAug11133950EEST2019_1565519990799.csv', 'CSV-1.csv', 1, 1);
INSERT INTO Myfiles (id, name, origin_name, creator_of_file_id, access_link_id) VALUES (2, 'SatAug31185155EEST2019_1567266715012.csv', 'CSV-2.csv', 1, 2);
INSERT INTO Myfiles (id, name, origin_name, creator_of_file_id, access_link_id) VALUES (3, 'TueAug20162623EEST2019_1566307583438.csv', 'CSV-3.csv', 1, 3);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id, access_link_id) VALUES (4, 'CSV-4', 2, 4);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id, access_link_id) VALUES (5, 'CS', 11, 5);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id, access_link_id) VALUES (6, 'CSV-6', 11, 6);
INSERT INTO Myfiles (id, name, origin_name, creator_of_file_id, access_link_id) VALUES (7, 'TueAug20140354EEST2019_1566299034641.csv','Cosovo.csv', 12, 7);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id, access_link_id) VALUES (8, 'CSV-8', 12, 8);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id, access_link_id) VALUES (9, 'Bound', 12, 9);

INSERT INTO USERS_LIST_OPEN_FILES (USER_ID, LIST_OPEN_FILES_ID) VALUES (1, 4);
INSERT INTO USERS_LIST_OPEN_FILES (USER_ID, LIST_OPEN_FILES_ID) VALUES (1, 7);

INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (1, 1);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (1, 2);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (1, 3);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (2, 4);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (11, 5);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (11, 6);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (12, 7);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (12, 8);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (12, 9);

INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (1, '2019-09-01 12:05:49.628', 'Opened access to read','SunAug11133950EEST2019_1565519990799.csv','CSV-1.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (2, '2019-09-01 12:05:49.662', 'Opened access to read','SunAug11133950EEST2019_1565519990799.csv','CSV-1.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (3, '2019-09-01 12:05:49.667', 'Opened access to read','SunAug11133950EEST2019_1565519990799.csv','CSV-1.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (4, '2019-09-01 12:05:53.031', 'Opened access to read','SatAug31185155EEST2019_1567266715012.csv','CSV-2.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (5, '2019-09-01 12:05:53.054', 'Opened access to read','SatAug31185155EEST2019_1567266715012.csv','CSV-2.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (6, '2019-09-01 12:05:53.057', 'Opened access to read','SatAug31185155EEST2019_1567266715012.csv','CSV-2.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (7, '2019-09-01 12:05:57.131', 'Opened access to read','TueAug20162623EEST2019_1566307583438.csv','CSV-3.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (8, '2019-09-01 12:05:57.133', 'Opened access to read','TueAug20162623EEST2019_1566307583438.csv','CSV-3.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (9, '2019-09-01 12:05:57.136', 'Opened access to read','TueAug20162623EEST2019_1566307583438.csv','CSV-3.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (10, '2019-09-01 12:06:57.136', 'Opened access to read','TueAug20162623EEST2019_1566307583438.csv','CSV-3.csv', FALSE, 'autorise user');
INSERT INTO STATISTICS  (ID, DATE, FILE_ACTION, FILE_NAME, FILE_NAME_ORIGINAL, USER_CREATE, USER_NAME)
VALUES (11, '2019-09-01 14:05:57.136', 'Opened access to read','TueAug20162623EEST2019_1566307583438.csv','CSV-3.csv', TRUE, 'autorise user');



