-- Senha para ambos os utilizadores é "123456". O hash BCrypt foi gerado para esta senha.
-- Utilizador Comum (ROLE_USER)
INSERT INTO users (name, email, password, role) VALUES ('Ana User', 'ana.user@forum.hub', '$2a$12$qbxynfVQWbNjHmjMkNrZV.XBEHsiEy7yPmeTQYexl4SeTqNSdsIce', 'ROLE_USER');
-- Utilizador Admin (ROLE_ADMIN)
INSERT INTO users (name, email, password, role) VALUES ('Bia Admin', 'bia.admin@forum.hub', '$2a$12$qbxynfVQWbNjHmjMkNrZV.XBEHsiEy7yPmeTQYexl4SeTqNSdsIce', 'ROLE_ADMIN');

-- Cursos
INSERT INTO cursos (nome, categoria) VALUES ('Spring Boot Avançado', 'Programação');
INSERT INTO cursos (nome, categoria) VALUES ('SQL e Bancos de Dados', 'Data Science');

-- Tópicos
-- Tópico criado pela Ana (user_id=1), no curso de Spring Boot (curso_id=1)
INSERT INTO topicos (titulo, mensagem, data_criacao, status, user_id, curso_id) VALUES ('Dúvida sobre Beans', 'Qual a diferença entre @Component e @Service?', '2025-08-17 10:00:00', true, 1, 1);
-- Tópico criado pela Bia (user_id=2), no curso de SQL (curso_id=2)
INSERT INTO topicos (titulo, mensagem, data_criacao, status, user_id, curso_id) VALUES ('Melhor forma de fazer JOIN', 'INNER JOIN vs LEFT JOIN, quando usar?', '2025-08-17 11:00:00', true, 2, 2);