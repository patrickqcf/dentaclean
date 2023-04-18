ALTER TABLE `schedulle-db`.historico CHANGE status_id status varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL;

ALTER TABLE `schedulle-db`.agendamento CHANGE status_id status varchar(25) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL;
ALTER TABLE `schedulle-db`.agendamento CHANGE client_id cliente_id bigint NOT NULL;
ALTER TABLE `schedulle-db`.agendamento MODIFY COLUMN data_agendamento DATE NOT NULL;



