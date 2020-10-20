package br.sc.senac.controller;

import br.sc.senac.model.bo.PessoaBO;
import br.sc.senac.model.exception.CpfInvalidoException;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.exception.NomeInvalidoException;
import br.sc.senac.model.vo.Pessoa;

public class PessoaController {
	
	private PessoaBO pessoaBO = new PessoaBO();
	
	public String salvar(Pessoa novaPessoa) {
		String mensagem = "";
		
		try {
			this.validarCPF(novaPessoa.getCpf());
			this.validarNome(novaPessoa.getNome());
			novaPessoa = pessoaBO.salvar(novaPessoa);
			
		} catch (CpfInvalidoException
				| NomeInvalidoException
				| CpfJaCadastradoException excecao) {
			mensagem = excecao.getMessage();
		} 
		
		mensagem = "Usuário salvo com sucesso! Id gerado: " + novaPessoa.getId();
		
		return mensagem;
	}
	
	public String atualizar(Pessoa pessoa) {
		String mensagem = "";
		boolean atualizou = false;
		
		try {
			this.validarCPF(pessoa.getCpf());
			this.validarNome(pessoa.getNome());
			atualizou = pessoaBO.atualizar(pessoa);
		} catch (CpfInvalidoException 
				| NomeInvalidoException
				| CpfJaCadastradoException excecao) {
			mensagem = excecao.getMessage();
		} 
		
		if(atualizou) {
			mensagem = "Pesquisador atualizado com sucesso!";
		} else {
			mensagem = "Erro ao atualizar pesquisador: (";
		}
		return mensagem;
	}
	
	private void validarCPF(String cpf) throws CpfInvalidoException{
		if(cpf == null || cpf.isEmpty()
				|| cpf.length() != 11) {
			throw new CpfInvalidoException("CPF deve possuir tamanho 11");
		}
		
		try {
			Integer.parseInt(cpf);
		} catch (NumberFormatException ex) {
			throw new CpfInvalidoException("CPF deve possuir tamanho 11 e somente números");
		}
	}
	
	private void validarNome(String nome) throws NomeInvalidoException {
		
		if(nome == null || nome.isEmpty()
				|| nome.length() >= 3) {
			throw new NomeInvalidoException("Nome deve ter no mínimo 3 caracteres.");
		}
	}

}
