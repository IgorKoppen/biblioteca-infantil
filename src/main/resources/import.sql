INSERT INTO tb_autor (id, nome, biografia) VALUES
                                               (1, 'J.R.R. Tolkien', 'Autor de O Senhor dos Anéis'),
                                               (2, 'George Orwell', 'Autor de 1984'),
                                               (3,'Rick Riordan','Autor de Percy Jackson');

INSERT INTO tb_categoria (id, nome) VALUES
                                        (1, 'Ficção Científica'),
                                        (2, 'Fantasia');

INSERT INTO tb_livro (id, titulo, disponivel, autorId, categoriaId) VALUES
                                                                        (1, 'O Senhor dos Anéis', true, 1, 2),
                                                                        (2, '1984', true, 2, 1),
                                                                        (3, 'Percy Jackson e os Olimpianos', true, 3, 2);

INSERT INTO tb_usuario (id, nome, email, senha, tipo) VALUES
                                                          (1, 'Alice Silva', 'alice@email.com', 'senha123', 'PROFESSOR'),
                                                          (2, 'João Sousa', 'joao@email.com', 'senha456', 'CRIANCA');

INSERT INTO tb_emprestimo (id, livroId, usuarioId, dataEmprestimo, dataDevolucao, dataDevolvido) VALUES
                                                                                                     (1, 1, 1, '2024-10-01', '2024-10-10', NULL),
                                                                                                     (2, 2, 2, '2024-10-05', '2024-10-15', NULL);