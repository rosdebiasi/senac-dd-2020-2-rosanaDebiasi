package br.sc.senac.model.vo;

import java.time.LocalDate;
import java.util.List;

public class Pesquisador extends Pessoa{
	
	private String instituicao;

	public Pesquisador(int id, String nome, String cpf, LocalDate dataNascimento, char sexo, String telefone,
			int reacaoVacina, List<Vacina> vacinas, String instituicao) {
		super(id, nome, cpf, dataNascimento, sexo, telefone, reacaoVacina, vacinas);
		this.instituicao = instituicao;
	}

	public Pesquisador() {
		super();
	}

	public String getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(String instituicao) {
		this.instituicao = instituicao;
	}

	@Override
	public String toString() {
		return  "\nNome do pesquisador: " + this.getNome() +
				"\nInstituição: " + this.getInstituicao();
	}
}
