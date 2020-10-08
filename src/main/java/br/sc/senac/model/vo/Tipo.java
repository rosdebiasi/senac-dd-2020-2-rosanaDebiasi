package br.sc.senac.model.vo;

public class Tipo {
	
	private int id;
	private String descricao;
	
	
	public Tipo(int id, String descricao) {
		super();
		this.id = id;
		this.descricao = descricao;
	}
	
	public Tipo() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
}
