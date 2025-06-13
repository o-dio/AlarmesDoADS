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
	"Senha" VARCHAR(256) NOT NULL UNIQUE,
	"Role" CHAR NOT NULL,
	CONSTRAINT role_check_admin CHECK ("Role" = 'A'),
	CONSTRAINT admin_pk PRIMARY KEY (id)
);

CREATE TABLE "Cliente" (
	id SERIAL,
	"Login" VARCHAR(256) NOT NULL UNIQUE,
	"Senha" VARCHAR(256) NOT NULL UNIQUE,
	"CPF" CHAR(11) NOT NULL UNIQUE,
	"DataNasc" DATE NOT NULL,
	"Fone" CHAR(11),
	"Email" VARCHAR(256),
	"FoneContato" CHAR(11),
	"Role" CHAR NOT NULL,
	CONSTRAINT cliente_pk PRIMARY KEY (id),
	CONSTRAINT role_check_cli CHECK ("Role" = 'C')
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
	"Senha" VARCHAR(256) NOT NULL UNIQUE,
	"Turno" CHAR NOT NULL,
	"CargaHoraria" TIME NOT NULL,
	"Remuneracao" DECIMAL(5, 2) NOT NULL,
	"DataContratacao" DATE NOT NULL,
	"Fone" CHAR(11),
	"Email" VARCHAR(256),
	"FoneContato" CHAR(11),
	"Role" CHAR NOT NULL,
	"IdEndereco" INT,
	CONSTRAINT vigilante_pk PRIMARY KEY (id),
	CONSTRAINT turno_check CHECK ("Turno" = 'D' OR "Turno" = 'N'),
	CONSTRAINT role_check_vigi CHECK ("Role" = 'V'),
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