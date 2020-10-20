package br.sc.senac.model.vo;

import java.time.LocalDate;
import java.util.List;

public abstract class Pessoa {
	
	private int id;
	private String nome;
	private String cpf;
	private LocalDate dataNascimento;
	private char sexo;
	private String telefone;
	private int tipo;
	

	public Pessoa(int id, String nome, String cpf, LocalDate dataNascimento, char sexo, String telefone,
			int tipo) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.telefone = telefone;
		this.tipo = tipo;
	}

	public Pessoa() {
		super();
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public char getSexo() {
		return sexo;
	}

	public void setSexo(char sexo) {
		this.sexo = sexo;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "\nNome: " + this.getNome() +
				"\nid : " + this.getId() +
				"\nTipo: " + this.getTipo() +
				"\nCPF: " + this.getCpf() +
				"\nData de Nascimento: " + this.getDataNascimento() +
				"\nSexo: " + this.getSexo() +
				"\nTelefone: " + this.getTelefone();
	}
	
}
