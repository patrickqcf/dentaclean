ALTER TABLE `schedulle-db`.agendamento DROP FOREIGN KEY FKrydafjnx8i7yk5ek28wu2h84o;
ALTER TABLE `schedulle-db`.agendamento DROP INDEX FKrydafjnx8i7yk5ek28wu2h84o;

ALTER TABLE `schedulle-db`.agendamento MODIFY COLUMN data_agendamento TIME NOT NULL;
ALTER TABLE `schedulle-db`.agendamento MODIFY COLUMN hora_inicio TIME NOT NULL;
ALTER TABLE `schedulle-db`.agendamento MODIFY COLUMN hora_fim TIME NOT NULL;
ALTER TABLE `schedulle-db`.agendamento MODIFY COLUMN status_id VARCHAR(25) NOT NULL;

ALTER TABLE `schedulle-db`.historico DROP FOREIGN KEY FKh1w6ucrdgck0hkjqgdabf3bru;
ALTER TABLE `schedulle-db`.historico DROP INDEX FKh1w6ucrdgck0hkjqgdabf3bru;

ALTER TABLE `schedulle-db`.historico MODIFY COLUMN status_id VARCHAR(25) NOT NULL;

ALTER TABLE `schedulle-db`.jornada_trabalho MODIFY COLUMN hora_inicio TIME NOT NULL;
ALTER TABLE `schedulle-db`.jornada_trabalho MODIFY COLUMN hora_fim TIME NOT NULL;

DROP TABLE `schedulle-db`.status;





