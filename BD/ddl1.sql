	DROP TABLE IF EXISTS "Admin";
	DROP TABLE IF EXISTS "Ocorrencia";
	DROP TABLE IF EXISTS "Gravacao";
	DROP TABLE IF EXISTS "Produto";
	DROP TABLE IF EXISTS "Trajeto";
	DROP TABLE IF EXISTS "Endereco_Rota";
	DROP TABLE IF EXISTS "Rota";
	DROP TABLE IF EXISTS "Vigilante";
	DROP TABLE IF EXISTS "Endereco";
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

	CREATE TABLE "Endereco" (
		id SERIAL,
		"Rua" VARCHAR(256) NOT NULL,
		"Numero" INT,
		"Complemento" VARCHAR(256),
		"Bairro" VARCHAR(256) NOT NULL,
		"Cidade" VARCHAR(256) NOT NULL,
		"Estado" CHAR(2) NOT NULL,
		"IdCliente" INT NOT NULL,
		CONSTRAINT endereco_pk PRIMARY KEY (id),
		CONSTRAINT endereco_fk FOREIGN KEY ("IdCliente")
			REFERENCES "Cliente"(id)
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
		"IdEndereco" IN	T,
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

	INSERT INTO "Admin" ("Login", "Senha") VALUES
		('teste', 'teste');

	INSERT INTO "Cliente" ("Login", "Senha", "CPF", "DataNasc", "Fone", "Email", "FoneContato") VALUES
		('cliente1', 'senha1', '86705321984', '1988-10-14', '11945678234', 'cliente1@email.com', '11932147890'),
		('cliente2', 'senha2', '25387143921', '1995-07-22', '11998765432', 'cliente2@email.com', '11987654321'),
		('cliente3', 'senha3', '53417896421', '2001-03-12', '11976543210', 'cliente3@email.com', '11965432109'),
		('cliente4', 'senha4', '64820913579', '1982-12-03', '11912345678', 'cliente4@email.com', '11943215678'),
		('cliente5', 'senha5', '15932784610', '1999-05-19', '11956473829', 'cliente5@email.com', '11993827164');

	INSERT INTO "Endereco" ("Rua", "Numero", "Complemento", "Bairro", "Cidade", "Estado", "IdCliente") VALUES
		('Rua 1', 101, 'Apto 10', 'Bairro 1', 'Cidade 1', 'SP', 1),
		('Rua 2', 202, 'Apto 20', 'Bairro 2', 'Cidade 2', 'SP', 2),
		('Rua 3', 303, 'Apto 30', 'Bairro 3', 'Cidade 3', 'SP', 3),
		('Rua 4', 404, 'Apto 40', 'Bairro 4', 'Cidade 4', 'SP', 4),
		('Rua 5', 505, 'Apto 50', 'Bairro 5', 'Cidade 5', 'SP', 5);

	INSERT INTO "Vigilante" ("Login", "Senha", "Turno", "CargaHoraria", "Remuneracao", "DataContratacao", "Fone", "Email", "FoneContato", "IdEndereco") VALUES
		('vigilante1', 'senhavig1', 'D', '08:00:00', 2750.50, '2018-04-15', '11956473829', 'vig1@empresa.com', '11993827164', 1),
		('vigilante2', 'senhavig2', 'N', '10:00:00', 3200.75, '2020-11-22', '11912348765', 'vig2@empresa.com', '11976543210', NULL),
		('vigilante3', 'senhavig3', 'D', '12:00:00', 4100.00, '2017-07-30', '11987654321', 'vig3@empresa.com', '11965432109', 3);

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
