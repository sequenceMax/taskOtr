INSERT INTO users (id, full_name) VALUES (1, 'Иванов');
INSERT INTO users (id, full_name) VALUES (2, 'Петров П.П.');
INSERT INTO users (id, full_name) VALUES (3, 'Сидоров С.С.');

INSERT INTO phones (id, num, user_id) VALUES (1, '+8 800 255 44 22', 1);
INSERT INTO phones  (id, num, user_id) VALUES (2, '+8 800 255 34 23', 1);
INSERT INTO phones  (id, num, user_id) VALUES (3, '+8 801 235 37 54', 1);

INSERT INTO phones  (id, num, user_id) VALUES (4, '+8 8013 3123 54', 2);
INSERT INTO phones  (id, num, user_id) VALUES (5, '+8 8013 3123 24', 2);

INSERT INTO phones  (id, num, user_id) VALUES (6, '+8 8013 3123 24', 3);
