SELECT * FROM planejy.profissional;

SELECT B.registro, B.nome, B.preco, B.foto, B.facebook, B.twitter, B.instagram, B.linkedin, B.servico, string_agg(A.tipo_usuario, ', ') FROM planejy.tipo_de_usuario_do_profissional AS A
INNER JOIN planejy.profissional AS B
ON B.registro = A.registro_profissional
GROUP BY B.registro
ORDER BY B.registro
-- HAVING B.registro = 3

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ()

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Marvin Augusto',  '40.00', 'pro-1.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Arthur Campos', '70.00', 'pro-2.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Fernando Pessoa', '90.00', 'pro-3.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Lidia Martins', '70.00', 'pro-4.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Julio Cesar', '60.00', 'pro-5.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Claudio Braganca', '120.00', 'pro-6.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Rayner Silva', '45.00', 'pro-7.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Caio Martins', '45.00', 'pro-8.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Isabelle Noronha', '60.00', 'pro-9.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Fernando Takeshi', '60.00', 'pro-10.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Felipe Silveira', '126.00', 'pro-11.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Joao Silva', '90.00', 'pro-12.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Marcelo Campos', '95.00', 'pro-13.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Andre Felipe', '80.00', 'pro-14.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Pedro Cunha', '100.00', 'pro-15.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Igor Oliveira', '30.00', 'pro-16.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')


INSERT INTO planejy.area_profissional
(registro_profissional, area)
VALUES
(1, 'Professor de Quimica'),
(1, 'Professor de Fisica'),
(2, 'Personal Trainer'),
(3, 'Psicologo'),
(3, 'Terapeuta Familiar'),
(4, 'Professora de Ingles'),
(5, 'Professor de Musica'),
(6, 'Quiroprata'),
(7, 'Professor de Calculo'),
(7, 'Professor de Estatistica'),
(8, 'Acompanhamento Escolar'),
(9, 'Baba'),
(10, 'Professor de Fisica'),
(10, 'Professor de Calculo'),
(11, 'Nutricionista'),
(12, 'Professor de Direito Penal'),
(12, 'Professor de Direito Civil'),
(13, 'Economista Domestico'),
(14, 'Personal Assistant'),
(15, 'Psicologo'),
(16, 'Marketing Digital')