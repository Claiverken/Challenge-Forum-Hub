-- Cria a tabela de usuários
CREATE TABLE users (
    id BIGSERIAL NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(100) NOT NULL,
    PRIMARY KEY (id)
);

-- Cria a tabela de cursos
CREATE TABLE cursos (
    id BIGSERIAL NOT NULL,
    nome VARCHAR(255) NOT NULL UNIQUE,
    categoria VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Cria a tabela de tópicos com as chaves estrangeiras
CREATE TABLE topicos (
    id BIGSERIAL NOT NULL,
    titulo VARCHAR(255) NOT NULL,
    mensagem TEXT NOT NULL,
    data_criacao TIMESTAMP NOT NULL,
    status BOOLEAN NOT NULL,
    user_id BIGINT NOT NULL,
    curso_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (curso_id) REFERENCES cursos(id)
);

-- ADICIONADO: Cria a tabela de respostas (answers)
CREATE TABLE answers (
    id BIGSERIAL NOT NULL,
    message TEXT NOT NULL,
    creation_date TIMESTAMP NOT NULL,
    user_id BIGINT NOT NULL,
    topico_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (topico_id) REFERENCES topicos(id)
);