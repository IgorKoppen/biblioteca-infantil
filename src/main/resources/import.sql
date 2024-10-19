INSERT INTO tb_autor (nome, biografia) VALUES ('J.R.R. Tolkien', 'Autor de O Senhor dos Anéis'), ('George Orwell', 'Autor de 1984'), ('Rick Riordan','Autor de Percy Jackson');

INSERT INTO tb_categoria (nome) VALUES ('Ficção Científica'), ('Fantasia');

INSERT INTO tb_livro (titulo, disponivel, autorId, categoriaId) VALUES ('O Senhor dos Anéis', true, 1, 2), ('1984', true, 2, 1), ('Percy Jackson e os Olimpianos', true, 3, 2);

INSERT INTO tb_usuario (nome, email, senha, tipo) VALUES ('Alice Silva', 'alice@email.com', 'senha123', 'PROFESSOR'), ('João Sousa', 'joao@email.com', 'senha456', 'CRIANCA');

INSERT INTO tb_emprestimo (livroId, usuarioId, dataEmprestimo, dataDevolucao, dataDevolvido) VALUES (1, 1, '2024-10-01', '2024-10-10', NULL), (2, 2, '2024-10-05', '2024-10-15', NULL);