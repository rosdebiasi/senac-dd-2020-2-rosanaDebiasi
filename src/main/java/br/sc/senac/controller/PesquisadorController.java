package br.sc.senac.controller;

import br.sc.senac.model.bo.PesquisadorBO;
import br.sc.senac.model.exception.CpfInvalidoException;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.exception.NomeInvalidoException;
import br.sc.senac.model.vo.Pesquisador;

public class PesquisadorController {
	
	private PesquisadorBO pesquisadorBO = new PesquisadorBO();
	
	public String salvar(Pesquisador novoPesquisador) {
		String mensagem = "";
		
		try {
			this.validarCPF(novoPesquisador.getCpf());
			this.validarNome(novoPesquisador.getNome());
			novoPesquisador = pesquisadorBO.salvar(novoPesquisador);
			
		} catch (CpfInvalidoException
				| NomeInvalidoException
				| CpfJaCadastradoException excecao) {
			mensagem = excecao.getMessage();
		} 
		
		mensagem = "Pesquisador salvo com sucesso! Id gerado: " + novoPesquisador.getId();
		
		return mensagem;
	}
	
	public String atualizar(Pesquisador pesquisador) {
		String mensagem = "";
		boolean atualizou = false;
		
		try {
			this.validarCPF(pesquisador.getCpf());
			this.validarNome(pesquisador.getNome());
			atualizou = pesquisadorBO.atualizar(pesquisador);
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
