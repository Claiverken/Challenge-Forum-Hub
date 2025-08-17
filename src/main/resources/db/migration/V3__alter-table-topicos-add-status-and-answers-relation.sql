-- Apaga a coluna 'status' antiga que era do tipo BOOLEAN
ALTER TABLE topicos DROP COLUMN status;

-- Adiciona a nova coluna 'status' do tipo VARCHAR e define um valor padrão
ALTER TABLE topicos ADD COLUMN status VARCHAR(100) NOT NULL DEFAULT 'NAO_RESPONDIDO';

-- Remove a coluna 'topico_id' da tabela 'answers' que estava na migration V1 (estava incorreta)
-- E adiciona a restrição de chave estrangeira
ALTER TABLE answers
ADD CONSTRAINT fk_answers_topico_id
FOREIGN KEY (topico_id)
REFERENCES topicos(id);