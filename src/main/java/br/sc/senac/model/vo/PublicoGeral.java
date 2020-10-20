package br.sc.senac.model.vo;

import java.time.LocalDate;
import java.util.List;

public class PublicoGeral extends Pessoa{

	public PublicoGeral(int id, String nome, String cpf, LocalDate dataNascimento, char sexo, String telefone,
			String instituicao, int tipo) {
		super(id, nome, cpf, dataNascimento, sexo, telefone, tipo);
		// TODO Auto-generated constructor stub
	}

	public PublicoGeral() {
		super();
	}

}
