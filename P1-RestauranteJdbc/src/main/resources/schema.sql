DROP TABLE IF EXISTS plato;
DROP TABLE IF EXISTS categoria;

CREATE TABLE categoria (
    codigo INT          AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL
);

CREATE TABLE plato (
    codigo           INT          AUTO_INCREMENT PRIMARY KEY,
    nombre           VARCHAR(100) NOT NULL,
    precio           DOUBLE       NOT NULL,
    calorias         INT          NOT NULL,
    codigo_categoria INT          NOT NULL,
    CONSTRAINT fk_plato_categoria
        FOREIGN KEY (codigo_categoria) REFERENCES categoria (codigo)
);
