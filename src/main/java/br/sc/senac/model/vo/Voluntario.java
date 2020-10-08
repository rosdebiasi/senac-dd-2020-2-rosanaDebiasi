package br.sc.senac.model.vo;

import java.time.LocalDate;
import java.util.List;

public class Voluntario extends Pessoa {

	public Voluntario(int id, String nome, String cpf, LocalDate dataNascimento, char sexo, String telefone,
			int reacaoVacina, List<Vacina> vacinas) {
		super(id, nome, cpf, dataNascimento, sexo, telefone, reacaoVacina, telefone, reacaoVacina, vacinas);

	}
	
	public Voluntario() {
		super();
	}




}
