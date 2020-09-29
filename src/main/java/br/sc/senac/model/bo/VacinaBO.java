package br.sc.senac.model.bo;

import br.sc.senac.model.dao.VacinaDAO;
import br.sc.senac.model.vo.Vacina;


public class VacinaBO {
	
	private VacinaDAO vacinaDAO = new VacinaDAO();
	
	public Vacina salvar(Vacina novaVacina) { 
		this.vacinaDAO.cadastrar(novaVacina);
		
		return novaVacina;
	}
	
	public boolean atualizar(Vacina vacina){
		return this.vacinaDAO.alterar(vacina);
	}
	
	public boolean excluirVacina(Vacina vacinaQueSeraExcluida) {
		boolean excluiu = vacinaDAO.excluir(vacinaQueSeraExcluida.getIdVacina());
		
		return excluiu;
	}

}
