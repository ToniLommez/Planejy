CREATE SCHEMA Planejy;

CREATE TABLE Planejy.Usuario (
    id          SERIAL,
    nome        VARCHAR(120) NOT NULL,
    nascimento  DATE         NOT NULL         DEFAULT NOW()::DATE,
    nick        VARCHAR(40)  NOT NULL UNIQUE,
    senha       TEXT         NOT NULL,
    email       VARCHAR(120) NOT NULL UNIQUE,
    genero      CHAR(1)                       DEFAULT 'n',
    token       CHAR(80),
    
    PRIMARY KEY (id)
);

CREATE TABLE Planejy.Classificacao_Usuario (
    id_usuario           INTEGER,
    trabalho             INTEGER DEFAULT 0, 
    estudo               INTEGER DEFAULT 0, 
    faculdade            INTEGER DEFAULT 0, 
    curso                INTEGER DEFAULT 0, 
    vida_noturna         INTEGER DEFAULT 0, 
    descanso             INTEGER DEFAULT 0, 
    dia_a_dia            INTEGER DEFAULT 0, 
    social               INTEGER DEFAULT 0, 
    social_profissional  INTEGER DEFAULT 0, 
    saude                INTEGER DEFAULT 0, 

    FOREIGN KEY (id_usuario)
    REFERENCES Planejy.Usuario (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Planejy.Nota (
    chave       BIGSERIAL,
    id_usuario  INT,
    titulo      VARCHAR(40)  NOT NULL,
    dia         DATE         NOT NULL DEFAULT NOW()::DATE,
    descricao   VARCHAR(255),
    horario     TIME,
    categoria   VARCHAR(40)  NOT NULL,
    cor         CHAR(7)      NOT NULL,
    
    PRIMARY KEY (chave),

    FOREIGN KEY (id_usuario)
    REFERENCES Planejy.Usuario (id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Planejy.Artigo (
    chave       SERIAL,
    imagem      VARCHAR(255)  NOT NULL,
    imagem_alt  VARCHAR(80)   NOT NULL,
    titulo      VARCHAR(80)   NOT NULL UNIQUE,
    conteudo    TEXT          NOT NULL,
    resumo      VARCHAR(512)  NOT NULL,
    autor       VARCHAR(80),
    dia         DATE                   DEFAULT NOW()::DATE,
    
    PRIMARY KEY (chave)
);

CREATE TABLE Planejy.Entrega_Artigo (
    id_usuario    INT,
    chave_artigo  INT,
    cliques       INT        DEFAULT 0,
    tempo         INT        DEFAULT 0,
    avaliacao     SMALLINT   DEFAULT 0,
    
    PRIMARY KEY (id_Usuario, chave_Artigo),

    FOREIGN KEY (id_usuario)
    REFERENCES Planejy.Usuario (id)
    ON UPDATE CASCADE
    ON DELETE SET NULL,

    FOREIGN KEY (chave_artigo)
    REFERENCES Planejy.Artigo (chave)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Planejy.Tipo_de_Usuario_do_Artigo (
    chave_artigo  INT,
    tipo          VARCHAR(40),
    
    PRIMARY KEY (chave_artigo, tipo),

    FOREIGN KEY (chave_artigo)
    REFERENCES Planejy.Artigo (chave)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Planejy.Profissional (
    registro   SERIAL,
    nome       VARCHAR(120)  NOT NULL,
    servico    VARCHAR(60),
    preco      MONEY         NOT NULL   DEFAULT 0.00,
    foto       VARCHAR(255)             UNIQUE,
    facebook   VARCHAR(255),
    twitter    VARCHAR(255),
    instagram  VARCHAR(255),
    linkedIn   VARCHAR(255),
    
    PRIMARY KEY (registro)
);

CREATE TABLE Planejy.Recomendacao_Profissional (
    id_usuario             INT,
    registro_profissional  INT,
    cliques                INT       DEFAULT 0,
    tempo                  INTEGER   DEFAULT 0,
    avaliacao              SMALLINT  DEFAULT NULL,
    
    PRIMARY KEY (id_usuario, registro_profissional),

    FOREIGN KEY (id_usuario)
    REFERENCES Planejy.Usuario (id)
    ON UPDATE CASCADE
    ON DELETE SET NULL,

    FOREIGN KEY (registro_profissional)
    REFERENCES Planejy.Profissional (registro)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);

CREATE TABLE Planejy.Tipo_de_Usuario_do_Profissional (
    registro_profissional  INT,
    tipo_usuario           VARCHAR(40),
    
    PRIMARY KEY (registro_profissional, tipo_usuario),

    FOREIGN KEY (registro_profissional)
    REFERENCES Planejy.Profissional (registro)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);


CREATE TABLE Planejy.Area_Profissional (
    registro_profissional INT,
    area                  VARCHAR(40),
    
    PRIMARY KEY (registro_profissional, area),

    FOREIGN KEY (registro_profissional)
    REFERENCES Planejy.Profissional (registro)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
