DROP TABLE IF EXISTS generos;

CREATE TABLE generos (
    id          BIGINT          NOT NULL AUTO_INCREMENT,
    nombre      VARCHAR(100)    NOT NULL,
    descripcion VARCHAR(500),

    CONSTRAINT pk_generos     PRIMARY KEY (id),
    CONSTRAINT uq_genero_nombre UNIQUE (nombre)
);
