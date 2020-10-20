package br.sc.senac.model.bo;

import br.sc.senac.model.dao.PessoaDAO;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.vo.Pesquisador;
import br.sc.senac.model.vo.Pessoa;

public class PessoaBO {
	
	private PessoaDAO pessoaDAO = new PessoaDAO();
	
	public Pesquisador salvar(Pesquisador novaPessoa) 
			throws CpfJaCadastradoException{
		
		if(this.pessoaDAO.cpfJaCadastrado(novaPessoa)) {
			throw new CpfJaCadastradoException("O CPF informado (" + novaPessoa.getCpf() 
			+ ") já foi cadastrado.");
		}
		this.pessoaDAO.cadastrar(novaPessoa);
		
		return novaPessoa;
	}
	
	public boolean atualizar(Pesquisador pessoa) throws CpfJaCadastradoException{

		if(this.pessoaDAO.cpfJaCadastrado(pessoa)) {
			throw new CpfJaCadastradoException("O CPF informado (" + pessoa.getCpf() 
			+ ") já foi cadastrado.");
		}
		return this.pessoaDAO.alterar(pessoa);
	}
	
	public Pessoa consultarPorCPF(String cpf) {
		return this.consultarPorCPF(cpf);
	}
	
	public boolean excluirPessoa(Pessoa pessoaQueSeraExcluida) {
		boolean excluiu = pessoaDAO.excluir(pessoaQueSeraExcluida.getId());
		
		return excluiu;
	}
	
}
