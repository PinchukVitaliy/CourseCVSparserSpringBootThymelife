INSERT INTO Roles (id, name) VALUES (1, 'goust');
INSERT INTO Roles (id, name) VALUES (2, 'user');
INSERT INTO Roles (id, name) VALUES (3, 'moderator');
INSERT INTO Roles (id, name) VALUES (4, 'administrator');
INSERT INTO Roles (id, name) VALUES (5, 'owner');


INSERT INTO Users (id, login, email, password, icon) VALUES (1, 'goust', 'goust@i.ua', 'goust', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (2, 'user', 'user@i.ua', 'user', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (3, 'moderator', 'moderator@i.ua', 'moderator', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (4, 'administrator', 'administrator@i.ua', 'administrator', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (5, 'owner', 'owner@i.ua', 'owner', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (6, 'goblin', 'goblin@i.ua', 'goblin', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (7, 'pasha', 'gpashat@i.ua', 'pasha', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (8, 'lenya', 'lenya@i.ua', 'lenya', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (9, 'ben', 'moddern@i.ua', 'ben', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (10, 'zyuzya', 'zyuzya@i.ua', 'zyuzya', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (11, 'macar', 'macar@i.ua', 'macar', 'no_user.jpg');
INSERT INTO Users (id, login, email, password, icon) VALUES (12, 'focus', 'focus@i.ua', 'focus', 'no_user.jpg');


INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (1, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (2, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (3, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (4, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (5, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (6, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (7, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (8, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (9, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (10, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (11, 1);
INSERT INTO USER_ROLE  (USER_ID, ROLE_ID) VALUES (12, 1);

INSERT INTO Myfiles (id, origin_name, creator_of_file_id) VALUES (1, 'CSV-1', 1);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id) VALUES (2, 'CSV-2', 1);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id) VALUES (3, 'CSV-3', 1);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id) VALUES (4, 'CSV-4', 2);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id) VALUES (5, 'CS', 11);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id) VALUES (6, 'CSV-6', 11);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id) VALUES (7, 'Cosovo', 12);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id) VALUES (8, 'CSV-8', 12);
INSERT INTO Myfiles (id, origin_name, creator_of_file_id) VALUES (9, 'Bound', 12);

/*
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (1, 1);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (1, 1);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (1, 1);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (2, 2);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (2, 2);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (2, 2);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (3, 3);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (3, 3);
INSERT INTO USERS_LIST_CREATED_FILES (USER_ID, LIST_CREATED_FILES_ID) VALUES (3, 3);
*/