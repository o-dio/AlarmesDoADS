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