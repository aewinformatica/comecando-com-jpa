INSERT INTO grupo (codigo, nome) VALUES (1, 'Administrador');
INSERT INTO grupo (codigo, nome) VALUES (2, 'Vendedor');

INSERT INTO usuario (nome, email, senha, ativo) VALUES ('Admin', 'admin@brewer.com', '$2a$10$g.wT4R0Wnfel1jc/k84OXuwZE02BlACSLfWy6TycGPvvEKvIm86SG', 1);

INSERT INTO permissao VALUES (1, 'ROLE_CADASTRAR_CIDADE');
INSERT INTO permissao VALUES (2, 'ROLE_CADASTRAR_USUARIO');

INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 1);
INSERT INTO grupo_permissao (codigo_grupo, codigo_permissao) VALUES (1, 2);

INSERT INTO usuario_grupo (codigo_usuario, codigo_grupo) VALUES (
	(SELECT codigo FROM usuario WHERE email = 'admin@brewer.com'), 1);
