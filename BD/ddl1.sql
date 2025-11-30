DROP TABLE IF EXISTS "Admin";
DROP TABLE IF EXISTS "Ocorrencia";
DROP TABLE IF EXISTS "Gravacao";
DROP TABLE IF EXISTS "Produto";
DROP TABLE IF EXISTS "Trajeto";
DROP TABLE IF EXISTS "Endereco_Rota";
DROP TABLE IF EXISTS "Rota";
DROP TABLE IF EXISTS "Vigilante";
DROP TABLE IF EXISTS "Contrato_Endereco";
DROP TABLE IF EXISTS "Endereco";
DROP TABLE IF EXISTS "Contrato";
DROP TABLE IF EXISTS "Cliente";

CREATE TABLE "Admin" (
	id SERIAL,
	"Login" VARCHAR(256) NOT NULL UNIQUE,
	"Senha" VARCHAR(256) NOT NULL,
	CONSTRAINT admin_pk PRIMARY KEY (id)
);

CREATE TABLE "Cliente" (
	id SERIAL,
	"Login" VARCHAR(256) NOT NULL UNIQUE,
	"Senha" VARCHAR(256) NOT NULL,
	"CPF" CHAR(11) NOT NULL UNIQUE,
	"DataNasc" DATE NOT NULL,
	"Fone" CHAR(11),
	"Email" VARCHAR(256),
	"FoneContato" CHAR(11),
	CONSTRAINT cliente_pk PRIMARY KEY (id)
);

CREATE TABLE "Contrato" (
	id SERIAL,
	"IdCliente" INT NOT NULL,
	"DataInicio" DATE NOT NULL,
	"DataFim" DATE NOT NULL,
	"FormaPagamento" VARCHAR(50) NOT NULL,
	"Valor" DOUBLE PRECISION NOT NULL,
	"Parcelas" INT NOT NULL,
	"Status" VARCHAR(20) NOT NULL,
	"Contrato" VARCHAR(256) NOT NULL,
	CONSTRAINT contrato_pk PRIMARY KEY (id),
	CONSTRAINT contrato_fk FOREIGN KEY ("IdCliente")
		REFERENCES "Cliente"(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE "Endereco" (
	id SERIAL,
	"Rua" VARCHAR(256) NOT NULL,
	"Numero" INT,
	"Complemento" VARCHAR(256),
	"Bairro" VARCHAR(256) NOT NULL,
	"Cidade" VARCHAR(256) NOT NULL,
	"Estado" CHAR(2) NOT NULL,
	CONSTRAINT endereco_pk PRIMARY KEY (id)
);

CREATE TABLE "Contrato_Endereco" (
	id serial,
	"IdContrato" INT NOT NULL,
	"IdEndereco" INT NOT NULL,
	CONSTRAINT contrato_endereco_pk PRIMARY KEY (id),
	CONSTRAINT contrato_endereco_fk_e FOREIGN KEY("IdEndereco")
		REFERENCES "Endereco"(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT contrato_endereco_fk_c FOREIGN KEY("IdContrato")
		REFERENCES "Contrato"(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE "Vigilante" (
	id SERIAL,
	"Login" VARCHAR(256) NOT NULL UNIQUE,
	"Senha" VARCHAR(256) NOT NULL,
	"Turno" CHAR NOT NULL,
	"CargaHoraria" TIME NOT NULL,
	"Remuneracao" NUMERIC(7, 2) NOT NULL,
	"DataContratacao" DATE NOT NULL,
	"Fone" CHAR(11),
	"Email" VARCHAR(256),
	"FoneContato" CHAR(11),
	"IdEndereco" INT,
	CONSTRAINT vigilante_pk PRIMARY KEY (id),
	CONSTRAINT turno_check CHECK ("Turno" = 'D' OR "Turno" = 'N'),
	CONSTRAINT vigilante_fk FOREIGN KEY ("IdEndereco")
		REFERENCES "Endereco"(id)
		ON UPDATE CASCADE
		ON DELETE SET NULL
);

CREATE TABLE "Rota" (
	id SERIAL,
	"Nome" VARCHAR(256) NOT NULL,
	"Bairro" VARCHAR(256) NOT NULL,
	"Descricao" TEXT,
	"Observacao" TEXT,
	CONSTRAINT rota_pk PRIMARY KEY (id)
);

CREATE TABLE "Endereco_Rota" (
	id SERIAL,
	"IdEndereco" INT NOT NULL,
	"IdRota" INT NOT NULL,
	CONSTRAINT endereco_rota_pk PRIMARY KEY(id),
	CONSTRAINT endereco_rota_fk_e FOREIGN KEY("IdEndereco")
		REFERENCES "Endereco"(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT endereco_rota_fk_r FOREIGN KEY("IdRota")
		REFERENCES "Rota"(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE "Trajeto" (
	id SERIAL,
	"DataIni" DATE NOT NULL DEFAULT NOW(),
	"DataFim" DATE,
	"IdVigilante" INT NOT NULL,
	"IdRota" INT NOT NULL,
	CONSTRAINT trajeto_pk PRIMARY KEY(id),
	CONSTRAINT trajeto_fk_v FOREIGN KEY("IdVigilante")
		REFERENCES "Vigilante"(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE,
	CONSTRAINT trajeto_fk_r FOREIGN KEY("IdRota")
		REFERENCES "Rota"(id)
		ON UPDATE CASCADE
		ON DELETE CASCADE
);

CREATE TABLE "Produto" (
	id SERIAL,
	"DataInst" DATE,
	"DataRet" DATE,
	"Defeito" BOOLEAN,
	"IdEndereco" INT,
	CONSTRAINT produto_pk PRIMARY KEY(id),
	CONSTRAINT produto_fk FOREIGN KEY("IdEndereco")
		REFERENCES "Endereco"(id)
		ON UPDATE CASCADE
		ON DELETE SET NULL
);

CREATE TABLE "Gravacao" (
	id SERIAL,
	"Data" DATE NOT NULL DEFAULT NOW(),
	"Duracao" TIME NOT NULL,
	"Arquivo" TEXT,
	"Descricao" TEXT,
	"IdProduto" INT,
	CONSTRAINT gravacao_pk PRIMARY KEY(id),
	CONSTRAINT gravacao_fk FOREIGN KEY("IdProduto")
		REFERENCES "Produto"(id)
		ON UPDATE CASCADE
		ON DELETE SET NULL
);

CREATE TABLE "Ocorrencia" (
	id SERIAL,
	"Data" DATE NOT NULL DEFAULT NOW(),
	"Duracao" TIME,
	"IdVigilante" INT,
	"IdProduto" INT,
	CONSTRAINT ocorrencia_pk PRIMARY KEY(id),
	CONSTRAINT ocorrencia_fk_v FOREIGN KEY("IdVigilante")
		REFERENCES "Vigilante"(id)
		ON UPDATE CASCADE
		ON DELETE SET NULL,
	CONSTRAINT ocorrencia_fk_p FOREIGN KEY("IdProduto")
		REFERENCES "Produto"(id)
		ON UPDATE CASCADE
		ON DELETE SET NULL
);

-- Inserções de Admin e Cliente
INSERT INTO "Admin" ("Login", "Senha") VALUES
	('teste', '698dc19d489c4e4db73e28a713eab07b');

INSERT INTO "Cliente" ("Login", "Senha", "CPF", "DataNasc", "Fone", "Email", "FoneContato") VALUES
	('cliente1', '92f20dafc5e5ac1c66820903c492cc04', '86705321984', '1988-10-14', '11945678234', 'cliente1@email.com', '11932147890'),
	('cliente2', '34ae07655b9a94e90556aff79bfd60b0', '25387143921', '1995-07-22', '11998765432', 'cliente2@email.com', '11987654321'),
	('cliente3', 'f940608acd624f8092bc86353052e734', '53417896421', '2001-03-12', '11976543210', 'cliente3@email.com', '11965432109'),
	('cliente4', 'f98494438eca6d421a8f9b5b3f02ed83', '64820913579', '1982-12-03', '11912345678', 'cliente4@email.com', '11943215678'),
	('cliente5', '4003ad34bf5fe5b88e5f393ff15ad623', '15932784610', '1999-05-19', '11956473829', 'cliente5@email.com', '11993827164');

INSERT INTO "Endereco" ("Rua", "Numero", "Complemento", "Bairro", "Cidade", "Estado") VALUES
    ('Avenida Paulista', 1000, 'Conjunto 101', 'Bela Vista', 'São Paulo', 'SP'),
    ('Rua Augusta', 200, NULL, 'Consolação', 'São Paulo', 'SP'),
    ('Avenida Brigadeiro Faria Lima', 1500, 'Sala 305', 'Itaim Bibi', 'São Paulo', 'SP'),
    ('Rua dos Três Irmãos', 500, NULL, 'Vila Progredior', 'São Paulo', 'SP'),
    ('Rua da Consolação', 800, 'Apto 12', 'Consolação', 'São Paulo', 'SP');

INSERT INTO "Contrato" (
	"IdCliente", "DataInicio", "DataFim", "FormaPagamento", "Valor", "Parcelas", "Status", "Contrato"
) VALUES
	(1, '2024-01-15', '2024-12-31', 'Cartão', 1500.00, 12, 'Ativo', 'cliente1-contrato-1.pdf'),
	(2, '2024-02-10', '2024-08-10', 'Boleto', 2999.90, 6, 'Finalizado', 'cliente2-contrato-1.pdf'),
	(3, '2024-03-05', '2024-09-05', 'Pix', 450.75, 3, 'Ativo', 'cliente3-contrato-1.pdf'),
	(1, '2024-04-20', '2024-10-20', 'Cartão', 780.00, 6, 'Cancelado', 'cliente1-contrato-2.pdf'),
	(4, '2024-05-12', '2025-05-12', 'Boleto', 12500.50, 12, 'Ativo', 'cliente4-contrato-1.pdf');

INSERT INTO "Contrato_Endereco" ("IdContrato", "IdEndereco") VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

INSERT INTO "Vigilante" ("Login", "Senha", "Turno", "CargaHoraria", "Remuneracao", "DataContratacao", "Fone", "Email", "FoneContato", "IdEndereco") VALUES
	('vigilante1', 'e6fe77c120104d0124d8b7f308e8bb0f', 'D', '08:00:00', 2750.50, '2018-04-15', '11956473829', 'vig1@empresa.com', '11993827164', 1),
	('vigilante2', '86ebbc51ed4d5b81e3f653fd9b364e55', 'N', '10:00:00', 3200.75, '2020-11-22', '11912348765', 'vig2@empresa.com', '11976543210', NULL),
	('vigilante3', 'c92dcb975ecb2395dbf69d6fa30a5504', 'D', '12:00:00', 4100.00, '2017-07-30', '11987654321', 'vig3@empresa.com', '11965432109', 3);

INSERT INTO "Rota" ("Nome", "Bairro", "Descricao", "Observacao") VALUES
	('Rota 1', 'Bairro 1', 'Descrição da Rota 1', 'Observação da Rota 1'),
	('Rota 2', 'Bairro 2', 'Descrição da Rota 2', 'Observação da Rota 2'),
	('Rota 3', 'Bairro 3', 'Descrição da Rota 3', 'Observação da Rota 3');

INSERT INTO "Endereco_Rota" ("IdEndereco", "IdRota") VALUES
	(1, 1),
	(2, 2),
	(3, 3),
	(4, 1),
	(5, 2);

INSERT INTO "Trajeto" ("DataIni", "DataFim", "IdVigilante", "IdRota") VALUES
	('2024-02-15', '2024-06-05', 1, 1),
	('2024-04-10', '2024-06-08', 2, 2),
	('2024-05-20', '2024-06-10', 3, 3);

INSERT INTO "Produto" ("DataInst", "DataRet", "Defeito", "IdEndereco") VALUES
	('2023-03-10', '2023-07-10', FALSE, 1),
	('2023-05-01', NULL, TRUE, 2),
	('2023-07-15', '2023-11-01', FALSE, 3),
	('2023-09-09', NULL, FALSE, 4),
	('2023-11-20', '2024-04-01', TRUE, 5);

INSERT INTO "Gravacao" ("Data", "Duracao", "Arquivo", "Descricao", "IdProduto") VALUES
	('2024-02-05', '01:15:00', 'arquivo_1.mp4', 'Descrição da gravação 1', 1),
	('2024-03-10', '00:45:00', 'arquivo_2.mp4', 'Descrição da gravação 2', 2),
	('2024-04-20', '02:00:00', 'arquivo_3.mp4', 'Descrição da gravação 3', 3),
	('2024-05-18', '00:30:00', 'arquivo_4.mp4', 'Descrição da gravação 4', 4),
	('2024-06-01', '01:10:00', 'arquivo_5.mp4', 'Descrição da gravação 5', 5);

INSERT INTO "Ocorrencia" ("Data", "Duracao", "IdVigilante", "IdProduto") VALUES
	('2024-02-10', '01:00:00', 1, 1),
	('2024-03-12', '00:30:00', 2, 2),
	('2024-04-15', '00:45:00', 3, 3),
	('2024-05-05', '01:20:00', 1, 4),
	('2024-06-01', '00:50:00', 2, 5);

