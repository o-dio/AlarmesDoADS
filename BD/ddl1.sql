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

-- Inserções de Admin
INSERT INTO "Admin" ("Login", "Senha") VALUES
    ('admin', '698dc19d489c4e4db73e28a713eab07b'),
    ('supervisor', '5f4dcc3b5aa765d61d8327deb882cf99');

-- Inserções de Clientes
INSERT INTO "Cliente" ("Login", "Senha", "CPF", "DataNasc", "Fone", "Email", "FoneContato") VALUES
    ('joao.silva', '92f20dafc5e5ac1c66820903c492cc04', '12345678901', '1985-02-20', '11987654321', 'joao.silva@gmail.com', '11999998888'),
    ('maria.oliveira', '34ae07655b9a94e90556aff79bfd60b0', '23456789012', '1990-07-15', '11998765432', 'maria.oliveira@gmail.com', '11988887777'),
    ('carlos.souza', 'f940608acd624f8092bc86353052e734', '34567890123', '1978-11-10', '11976543210', 'carlos.souza@gmail.com', '11977776666'),
    ('ana.pereira', 'f98494438eca6d421a8f9b5b3f02ed83', '45678901234', '1995-05-25', '11912345678', 'ana.pereira@gmail.com', '11966665555'),
    ('lucas.ferreira', '4003ad34bf5fe5b88e5f393ff15ad623', '56789012345', '2000-09-12', '11956473829', 'lucas.ferreira@gmail.com', '11955554444');

-- Inserções de Endereços
INSERT INTO "Endereco" ("Rua", "Numero", "Complemento", "Bairro", "Cidade", "Estado") VALUES
    ('Rua das Acácias', 150, 'Apto 12', 'Vila Nova', 'São Paulo', 'SP'),
    ('Avenida Paulista', 1000, 'Conjunto 101', 'Bela Vista', 'São Paulo', 'SP'),
    ('Rua dos Jasmins', 45, NULL, 'Jardim América', 'São Paulo', 'SP'),
    ('Rua Haddock Lobo', 500, 'Sala 205', 'Consolação', 'São Paulo', 'SP'),
    ('Avenida Faria Lima', 1200, 'Cobertura', 'Itaim Bibi', 'São Paulo', 'SP');

-- Inserções de Contratos
INSERT INTO "Contrato" ("IdCliente", "DataInicio", "DataFim", "FormaPagamento", "Valor", "Parcelas", "Status", "Contrato") VALUES
    (1, '2024-01-10', '2024-12-31', 'Cartão', 1800.00, 12, 'Ativo', 'joao_silva_contrato.pdf'),
    (2, '2024-02-05', '2024-08-05', 'Boleto', 2500.50, 6, 'Finalizado', 'maria_oliveira_contrato.pdf'),
    (3, '2024-03-12', '2024-09-12', 'Pix', 1200.75, 3, 'Ativo', 'carlos_souza_contrato.pdf'),
    (4, '2024-04-01', '2024-10-01', 'Cartão', 3000.00, 6, 'Cancelado', 'ana_pereira_contrato.pdf'),
    (5, '2024-05-20', '2025-05-20', 'Boleto', 5000.00, 12, 'Ativo', 'lucas_ferreira_contrato.pdf');

-- Associação de Contratos a Endereços
INSERT INTO "Contrato_Endereco" ("IdContrato", "IdEndereco") VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Inserções de Vigilantes
INSERT INTO "Vigilante" ("Login", "Senha", "Turno", "CargaHoraria", "Remuneracao", "DataContratacao", "Fone", "Email", "FoneContato", "IdEndereco") VALUES
    ('vigilante_joao', 'e6fe77c120104d0124d8b7f308e8bb0f', 'D', '08:00:00', 2800.00, '2019-05-10', '11911223344', 'joao.vig@gmail.com', '11922334455', 1),
    ('vigilante_maria', '86ebbc51ed4d5b81e3f653fd9b364e55', 'N', '10:00:00', 3200.00, '2020-08-20', '11999887766', 'maria.vig@gmail.com', '11966778899', 2),
    ('vigilante_carlos', 'c92dcb975ecb2395dbf69d6fa30a5504', 'D', '12:00:00', 4000.00, '2018-01-15', '11988776655', 'carlos.vig@gmail.com', '11955667788', 3),
    ('vigilante_ana', '5f4dcc3b5aa765d61d8327deb882cf99', 'N', '08:00:00', 3500.00, '2021-03-12', '11944556677', 'ana.vig@gmail.com', '11977889900', 4),
    ('vigilante_lucas', '5ebe2294ecd0e0f08eab7690d2a6ee69', 'D', '10:00:00', 3700.00, '2017-11-05', '11933445566', 'lucas.vig@gmail.com', '11966778811', 5);

-- Inserções de Rotas
INSERT INTO "Rota" ("Nome", "Bairro", "Descricao", "Observacao") VALUES
    ('Rota Centro', 'Centro', 'Rota de vigilância pela região central', 'Prioridade nos prédios comerciais'),
    ('Rota Norte', 'Zona Norte', 'Rota de vigilância residencial', 'Atenção a áreas de construção'),
    ('Rota Sul', 'Zona Sul', 'Rota de vigilância mista', 'Inclui zonas comerciais e residenciais'),
    ('Rota Leste', 'Zona Leste', 'Rota de vigilância industrial', 'Atenção aos depósitos e fábricas'),
    ('Rota Oeste', 'Zona Oeste', 'Rota de vigilância mista', 'Checagem de escolas e hospitais');

-- Associação de Endereços às Rotas
INSERT INTO "Endereco_Rota" ("IdEndereco", "IdRota") VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 4),
    (5, 5);

-- Inserções de Trajetos
INSERT INTO "Trajeto" ("DataIni", "DataFim", "IdVigilante", "IdRota") VALUES
    ('2024-01-15', '2024-02-15', 1, 1),
    ('2024-02-20', '2024-03-20', 2, 2),
    ('2024-03-25', '2024-04-25', 3, 3),
    ('2024-04-10', '2024-05-10', 4, 4),
    ('2024-05-15', '2024-06-15', 5, 5);

-- Inserções de Produtos
INSERT INTO "Produto" ("DataInst", "DataRet", "Defeito", "IdEndereco") VALUES
    ('2023-12-01', NULL, FALSE, 1),
    ('2024-01-10', NULL, TRUE, 2),
    ('2024-02-05', '2024-06-05', FALSE, 3),
    ('2024-03-15', NULL, FALSE, 4),
    ('2024-04-20', '2024-08-20', TRUE, 5);

-- Inserções de Gravações
INSERT INTO "Gravacao" ("Data", "Duracao", "Arquivo", "Descricao", "IdProduto") VALUES
    ('2024-01-20', '01:00:00', 'gravação_joao.mp4', 'Gravação inicial de vigilância', 1),
    ('2024-02-15', '00:45:00', 'gravação_maria.mp4', 'Gravação residencial', 2),
    ('2024-03-10', '01:30:00', 'gravação_carlos.mp4', 'Gravação comercial', 3),
    ('2024-04-05', '00:50:00', 'gravação_ana.mp4', 'Gravação de monitoramento', 4),
    ('2024-05-01', '01:10:00', 'gravação_lucas.mp4', 'Gravação mista', 5);

-- Inserções de Ocorrências
INSERT INTO "Ocorrencia" ("Data", "Duracao", "IdVigilante", "IdProduto") VALUES
    ('2024-01-22', '00:30:00', 1, 1),
    ('2024-02-17', '01:00:00', 2, 2),
    ('2024-03-12', '00:45:00', 3, 3),
    ('2024-04-07', '01:20:00', 4, 4),
    ('2024-05-03', '00:50:00', 5, 5);


