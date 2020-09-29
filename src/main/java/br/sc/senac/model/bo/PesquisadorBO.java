package br.sc.senac.model.bo;

import br.sc.senac.model.dao.PesquisadorDAO;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.vo.Pesquisador;

public class PesquisadorBO {
	
	private PesquisadorDAO pesquisadorDAO = new PesquisadorDAO();
	
	public Pesquisador salvar(Pesquisador novoPesquisador) 
			throws CpfJaCadastradoException{
		
		if(this.pesquisadorDAO.cpfJaCadastrado(novoPesquisador)) {
			throw new CpfJaCadastradoException("O CPF informado (" + novoPesquisador.getCpf() 
			+ ") já foi cadastrado para outro pesquisador");
		}
		this.pesquisadorDAO.cadastrar(novoPesquisador);
		
		return novoPesquisador;
	}
	
	public boolean atualizar(Pesquisador pesquisador) throws CpfJaCadastradoException{

		if(this.pesquisadorDAO.cpfJaCadastrado(pesquisador)) {
			throw new CpfJaCadastradoException("O CPF informado (" + pesquisador.getCpf() 
			+ ") já foi cadastrado para outro pesquisador");
		}
		return this.pesquisadorDAO.alterar(pesquisador);
	}
	
	public Pesquisador consultarPorCPF(String cpf) {
		return this.consultarPorCPF(cpf);
	}
	
	public boolean excluirPesquisador(Pesquisador pesquisadorQueSeraExcluido) {
		boolean excluiu = pesquisadorDAO.excluir(pesquisadorQueSeraExcluido.getId());
		
		return excluiu;
	}
	
}
