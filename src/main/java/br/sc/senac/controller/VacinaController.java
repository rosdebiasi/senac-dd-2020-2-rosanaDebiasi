package br.sc.senac.controller;

import br.sc.senac.model.bo.VacinaBO;
import br.sc.senac.model.exception.CpfInvalidoException;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.vo.Vacina;

public class VacinaController {
	
private VacinaBO vacinaBO = new VacinaBO();
	
	public String salvar(Vacina novaVacina) {
		String mensagem = "";
		
		mensagem = "Vacina salva com sucesso! Id gerado: " + novaVacina.getIdVacina();
		
		return mensagem;
	}
	
	public String atualizar(Vacina vacina) {
		String mensagem = "";

		mensagem = "Vacina atualizada com sucesso! Id gerado: " + vacina.getIdVacina();
		
		return mensagem;
	}
	
}
