CREATE TABLE `status` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `descricao` varchar(30) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKY_descricao` (`descricao`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `agendamento` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `client_id` bigint NOT NULL,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `data_agendamento` date NOT NULL,
  `dentista_id` bigint NOT NULL,
  `hora_fim` datetime(6) NOT NULL,
  `hora_inicio` datetime(6) NOT NULL,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `agendamento_id` bigint DEFAULT NULL,
  `status_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKtcroh6u1qmvgimihsch6q8wfi` (`agendamento_id`),
  KEY `FKrydafjnx8i7yk5ek28wu2h84o` (`status_id`),
  CONSTRAINT `FKrydafjnx8i7yk5ek28wu2h84o` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `FKtcroh6u1qmvgimihsch6q8wfi` FOREIGN KEY (`agendamento_id`) REFERENCES `agendamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `historico` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `data` datetime(6) NOT NULL,
  `agendamento_id` bigint NOT NULL,
  `status_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqna0ft7r0e72bmbnj3q6g33rj` (`agendamento_id`),
  KEY `FKh1w6ucrdgck0hkjqgdabf3bru` (`status_id`),
  CONSTRAINT `FKh1w6ucrdgck0hkjqgdabf3bru` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`),
  CONSTRAINT `FKqna0ft7r0e72bmbnj3q6g33rj` FOREIGN KEY (`agendamento_id`) REFERENCES `agendamento` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `jornada_trabalho` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `clinica_id` bigint NOT NULL,
  `dentista_id` bigint NOT NULL,
  `dia_semana` int DEFAULT NULL,
  `hora_fim` datetime(6) NOT NULL,
  `hora_inicio` datetime(6) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;