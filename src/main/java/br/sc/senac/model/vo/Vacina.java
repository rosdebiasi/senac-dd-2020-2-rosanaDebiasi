package br.sc.senac.model.vo;

import java.time.LocalDate;
import java.util.List;

public class Vacina {
	
	private int idVacina;
	private String paisOrigem;
	private char estagioPesquisa;
	private LocalDate dataInicioPesquisa;
	
	private Pesquisador pesquisador;
	
	public Vacina(int idVacina, String paisOrigem, char estagioPesquisa, LocalDate dataInicioPesquisa, Pesquisador pesquisador) {
		super();
		this.idVacina = idVacina;
		this.paisOrigem = paisOrigem;
		this.estagioPesquisa = estagioPesquisa;
		this.dataInicioPesquisa = dataInicioPesquisa;
		this.pesquisador = pesquisador;
	}

	public Vacina() {
		super();
	}

	public int getIdVacina() {
		return idVacina;
	}

	public void setIdVacina(int idVacina) {
		this.idVacina = idVacina;
	}

	public String getPaisOrigem() {
		return paisOrigem;
	}

	public void setPaisOrigem(String paisOrigem) {
		this.paisOrigem = paisOrigem;
	}

	public char getEstagioPesquisa() {
		return estagioPesquisa;
	}

	public void setEstagioPesquisa(char estagioPesquisa) {
		this.estagioPesquisa = estagioPesquisa;
	}

	public LocalDate getDataInicioPesquisa() {
		return dataInicioPesquisa;
	}

	public void setDataInicioPesquisa(LocalDate dataInicioPesquisa) {
		this.dataInicioPesquisa = dataInicioPesquisa;
	}

	public Pesquisador getPesquisador() {
		return pesquisador;
	}

	public void setPesquisador(Pesquisador pesquisador) {
		this.pesquisador = pesquisador;
	}

	@Override
	public String toString() {
		return "\nid vacina: " + this.getIdVacina() +
				"\nPaís Origem: " + this.getPaisOrigem() +
				"\nEstágio pesquisa: " + this.getEstagioPesquisa() +
				"\nData início da pesquisa: " + this.getEstagioPesquisa() +
				"\nDados do(a) pesquisador(a):" + "\n" + this.getPesquisador();
	}
	
}
