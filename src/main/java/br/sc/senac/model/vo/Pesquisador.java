package br.sc.senac.model.vo;

import java.time.LocalDate;
import java.util.List;

public class Pesquisador extends Pessoa{
	
	private Instituicao instituicao;
	
	public Pesquisador(int id, String nome, String cpf, LocalDate dataNascimento, char sexo, String telefone, int tipo,
			Instituicao instituicao) {
		super(id, nome, cpf, dataNascimento, sexo, telefone, tipo);
		this.instituicao = instituicao;
	}

	public Pesquisador() {
		super();
	}
	
	public Instituicao getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(Instituicao instituicao) {
		this.instituicao = instituicao;
	}

	@Override
	public String toString() {
		return  "\nNome do pesquisador: " + this.getNome()+
				"\nInstituição: " + instituicao.getNome();
	}
}
