package br.sc.senac.model.vo;

import java.time.LocalDate;
import java.util.List;

public class Voluntario extends Pessoa {

	public Voluntario(int id, String nome, String cpf, LocalDate dataNascimento, char sexo, String telefone,
			 int tipo) {
		super(id, nome, cpf, dataNascimento, sexo, telefone, tipo);
		
	}

	public Voluntario() {
		super();
	}




}
