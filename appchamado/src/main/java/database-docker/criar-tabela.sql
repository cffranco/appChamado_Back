CREATE TABLE usuario(
    id bigserial not null,
    email VARCHAR(100) not null,
    senha VARCHAR(200) not null
);
ALTER TABLE tarefa ADD CONSTRAINT tarefa_pk PRIMARY KEY (id);