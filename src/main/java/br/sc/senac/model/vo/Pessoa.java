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
	private int reacaoVacina;
	private String instituicao;
	private int tipo;
	
	private List <Vacina> vacinas;

	public Pessoa(int id, String nome, String cpf, LocalDate dataNascimento, char sexo, String telefone,
			int reacaoVacina, String instituicao, int tipo, List<Vacina> vacinas) {
		super();
		this.id = id;
		this.nome = nome;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.sexo = sexo;
		this.telefone = telefone;
		this.reacaoVacina = reacaoVacina;
		this.instituicao = instituicao;
		this.tipo = tipo;
		this.vacinas = vacinas;
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

	public List<Vacina> getVacinas() {
		return vacinas;
	}

	public void setVacinas(List<Vacina> vacinas) {
		this.vacinas = vacinas;
	}
	
	
	public int getReacaoVacina() {
		return reacaoVacina;
	}

	public void setReacaoVacina(int reacaoVacina) {
		this.reacaoVacina = reacaoVacina;
	}
	
	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
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
				"\nCPF: " + this.getCpf() +
				"\nData de Nascimento: " + this.getDataNascimento() +
				"\nSexo: " + this.getSexo() +
				"\nTelefone: " + this.getTelefone() +
				"\nVacinas tomadas: " + "\n" + this.getVacinas() +
				"\nReação Vacina: " + this.getReacaoVacina();
	}
	
}
