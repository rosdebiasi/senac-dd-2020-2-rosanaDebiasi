package br.sc.senac.model.bo;

import br.sc.senac.model.dao.PublicoGeralDAO;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.vo.PublicoGeral;

public class PublicoGeralBO {
	
	private PublicoGeralDAO publicoGeralDAO = new PublicoGeralDAO();
	
	public PublicoGeral salvar(PublicoGeral novoPublicoGeral) 
			throws CpfJaCadastradoException{
		
		if(this.publicoGeralDAO.cpfJaCadastrado(novoPublicoGeral)) {
			throw new CpfJaCadastradoException("O CPF informado (" + novoPublicoGeral.getCpf() 
			+ ") já foi cadastrado para outro público geral");
		}
		this.publicoGeralDAO.cadastrar(novoPublicoGeral);
		
		return novoPublicoGeral;
	}
	
	public boolean atualizar(PublicoGeral publicoGeral) throws CpfJaCadastradoException{

		if(this.publicoGeralDAO.cpfJaCadastrado(publicoGeral)) {
			throw new CpfJaCadastradoException("O CPF informado (" + publicoGeral.getCpf() 
			+ ") já foi cadastrado para outro público geral");
		}
		return this.publicoGeralDAO.alterar(publicoGeral);
	}
	
	public PublicoGeral consultarPorCPF(String cpf) {
		return this.consultarPorCPF(cpf);
	}
	
	public boolean excluirPublicoGeral(PublicoGeral publicoGeralQueSeraExcluido) {
		boolean excluiu = publicoGeralDAO.excluir(publicoGeralQueSeraExcluido.getId());
		
		return excluiu;
	}

}
