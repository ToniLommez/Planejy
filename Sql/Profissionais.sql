SELECT * FROM planejy.profissional;

SELECT B.registro, B.nome, B.preco, B.foto, B.facebook, B.twitter, B.instagram, B.linkedin, B.servico, string_agg(A.tipo_usuario, ', ') FROM planejy.tipo_de_usuario_do_profissional AS A
INNER JOIN planejy.profissional AS B
ON B.registro = A.registro_profissional
GROUP BY B.registro
ORDER BY B.registro
-- HAVING B.registro = 3

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ()

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Marvin Augusto', 'Professor de Quimica', '40.00', 'pro-1.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Arthur Campos', 'Personal Trainer', '70.00', 'pro-2.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Fernando Pessoa', 'Psicologo', '90.00', 'pro-3.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Lidia Martins', 'Professora de Ingles', '70.00', 'pro-4.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Julio Cesar', 'Professor de Musica', '60.00', 'pro-5.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Claudio Braganca', 'Quiroprata', '120.00', 'pro-6.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Rayner Silva', 'Professor de Calculo/Estatistica', '45.00', 'pro-7.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Caio Martins', 'Acompanhamento Escolar', '45.00', 'pro-8.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Isabelle Noronha', 'Baba', '60.00', 'pro-9.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Fernando Takeshi', 'Professor de Fisica', '60.00', 'pro-10.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Felipe Silveira', 'Nutricionista', '126.00', 'pro-11.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Joao Silva', 'Professor de Direito Penal', '90.00', 'pro-12.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Marcelo Campos', 'Profissional de Economia Domestica', '95.00', 'pro-13.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Andre Felipe', 'Personal Assistant', '80.00', 'pro-14.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Pedro Cunha', 'Psicologo', '100.00', 'pro-15.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')

INSERT INTO planejy.profissional (nome, servico, preco, foto, facebook, twitter, instagram, linkedin)
VALUES ('Igor Oliveira', 'Marketing Digital', '30.00', 'pro-16.jpg', 'www.facebook.com', 'www.twitter.com', 'www.instagram.com', 'www.linkedin.com')