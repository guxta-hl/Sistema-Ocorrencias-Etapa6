CREATE DATABASE IF NOT EXISTS sistema_ocorrenciaspi
    CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE sistema_ocorrenciaspi;

CREATE TABLE IF NOT EXISTS ocorrencia (
    id BIGINT NOT NULL AUTO_INCREMENT,
    numeroOc INT NOT NULL,
    ano INT NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS apreensao (
    id BIGINT NOT NULL AUTO_INCREMENT,
    descricao VARCHAR(500) NOT NULL,
    tipo VARCHAR(100) NOT NULL,
    ocorrencia_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_apreensao_ocorrencia UNIQUE (ocorrencia_id),
    CONSTRAINT fk_apreensao_ocorrencia
        FOREIGN KEY (ocorrencia_id) REFERENCES ocorrencia(id)
        ON DELETE CASCADE
);
