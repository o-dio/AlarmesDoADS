package com.projetofinalpoo.models;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.projetofinalpoo.services.HashMD5Service;

public class Cliente implements Usuario{
	//Atributos
	private String login;
	private String senha;
	private String cpf;
	private LocalDate dataNasc;
	private String fone;
	private String email;
	private String foneContato;
	
	//Construtor
	public Cliente() {};
	
	public Cliente(String login, String senha, String cpf, String dataNasc, String fone, String email,
			String foneContato) {
		super();
		this.login = login;
		this.senha = senha;
		this.cpf = cpf;
		this.dataNasc = LocalDate.parse(dataNasc, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
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
	
	public void setSenha(String senha) throws NoSuchAlgorithmException {
		this.senha = HashMD5Service.gerarMD5(senha);
    }
	
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public LocalDate getDataNasc() {
		return dataNasc;
	}
	
	public void setDataNasc(LocalDate dataNasc) {
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((senha == null) ? 0 : senha.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		result = prime * result + ((dataNasc == null) ? 0 : dataNasc.hashCode());
		result = prime * result + ((fone == null) ? 0 : fone.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((foneContato == null) ? 0 : foneContato.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (senha == null) {
			if (other.senha != null)
				return false;
		} else if (!senha.equals(other.senha))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		if (dataNasc == null) {
			if (other.dataNasc != null)
				return false;
		} else if (!dataNasc.equals(other.dataNasc))
			return false;
		if (fone == null) {
			if (other.fone != null)
				return false;
		} else if (!fone.equals(other.fone))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (foneContato == null) {
			if (other.foneContato != null)
				return false;
		} else if (!foneContato.equals(other.foneContato))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [login=" + login + ", senha=" + senha + ", cpf=" + cpf + ", dataNasc=" + dataNasc + ", fone="
				+ fone + ", email=" + email + ", foneContato=" + foneContato + "]";
	}
	
}
