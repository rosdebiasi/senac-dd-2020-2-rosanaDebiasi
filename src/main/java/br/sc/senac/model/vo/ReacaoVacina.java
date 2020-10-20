package br.sc.senac.model.vo;

public class ReacaoVacina {
	
	private int id;
	private int reacaoVacina;
	
	private Pessoa pessoa;
	private Vacina vacina;
	
	public ReacaoVacina(int id, int reacaoVacina, Pessoa pessoa, Vacina vacina) {
		super();
		this.id = id;
		this.reacaoVacina = reacaoVacina;
		this.pessoa = pessoa;
		this.vacina = vacina;
	}
	
	public ReacaoVacina() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getReacaoVacina() {
		return reacaoVacina;
	}

	public void setReacaoVacina(int reacaoVacina) {
		this.reacaoVacina = reacaoVacina;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Vacina getVacina() {
		return vacina;
	}

	public void setVacina(Vacina vacina) {
		this.vacina = vacina;
	}
	
	

	

}
