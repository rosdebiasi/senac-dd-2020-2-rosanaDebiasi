package br.sc.senac.controller;

import br.sc.senac.model.bo.VacinaBO;
import br.sc.senac.model.exception.PaisOrigemInvalidoException;
import br.sc.senac.model.vo.Vacina;

public class VacinaController {
	
private VacinaBO vacinaBO = new VacinaBO();
	
	public String salvar(Vacina novaVacina) {
		String mensagem = "";
		
		try {
			this.validarPaisOrigem(novaVacina.getPaisOrigem());
			novaVacina = vacinaBO.salvar(novaVacina);
		} catch (PaisOrigemInvalidoException excecao) {
			mensagem = excecao.getMessage();
		} 
		
		mensagem = "Vacina salva com sucesso! Id gerado: " + novaVacina.getIdVacina();
		
		return mensagem;
	}
	
	public String atualizar(Vacina vacina) {
		String mensagem = "";
		boolean atualizou = false;
		
		try {
			this.validarPaisOrigem(vacina.getPaisOrigem());
			atualizou = vacinaBO.atualizar(vacina);
		} catch (PaisOrigemInvalidoException excecao) {
			mensagem = excecao.getMessage();
		} 
		
		if(atualizou) {
			mensagem = "Vacina atualizada com sucesso!";
		} else {
			mensagem = "Erro ao atualizar vacina: (";
		}
			
		return mensagem;
	}
	
	private void validarPaisOrigem(String paisOrigem) throws PaisOrigemInvalidoException{
		
		if(paisOrigem == null || paisOrigem.isEmpty()
				|| paisOrigem.length() >= 3) {
			throw new PaisOrigemInvalidoException("País de origem deve ter no mínimo 3 caracteres.");
		}	
	}
	
}
