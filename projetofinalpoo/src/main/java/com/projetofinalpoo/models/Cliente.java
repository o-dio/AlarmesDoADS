package com.projetofinalpoo.models;

public class Cliente {
	//Atributos
	private String login;
	private String senha;
	private String cpf;
	private String dataNasc;
	private String fone;
	private String email;
	private String foneContato;
	
	//Construtor
	public Cliente(String login, String senha, String cpf, String dataNasc, String fone, String email,
			String foneContato) {
		super();
		this.login = login;
		this.senha = senha;
		this.cpf = cpf;
		this.dataNasc = dataNasc;
		this.fone = fone;
		this.email = email;
		this.foneContato = foneContato;
	}

	
	//Metodos
	public String getLogin() {
		return login;
	}
	
	public void setLogin(String login) {
		this.login = login;
	}
	
	public String getSenha() {
		return senha;
	}
	
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getDataNasc() {
		return dataNasc;
	}
	
	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}
	
	public String getFone() {
		return fone;
	}
	
	public void setFone(String fone) {
		this.fone = fone;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getFoneContato() {
		return foneContato;
	}
	
	public void setFoneContato(String foneContato) {
		this.foneContato = foneContato;
	}
	
}
