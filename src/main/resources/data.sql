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

INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 1);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (2, 2);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 3);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 4);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 5);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 6);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 7);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 8);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 9);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 10);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 11);
INSERT INTO ROLES_USER_LIST (ROLE_ID, USER_LIST_ID) VALUES (1, 12);

INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (1, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (2, 2);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (3, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (4, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (5, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (6, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (7, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (8, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (9, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (10, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (11, 1);
INSERT INTO USERS_ROLE_LIST (USER_ID, ROLE_LIST_ID) VALUES (12, 1);