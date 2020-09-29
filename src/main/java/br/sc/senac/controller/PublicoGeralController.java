package br.sc.senac.controller;


import br.sc.senac.model.bo.PublicoGeralBO;
import br.sc.senac.model.exception.CpfInvalidoException;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.exception.NomeInvalidoException;
import br.sc.senac.model.vo.PublicoGeral;

public class PublicoGeralController {
	
	private PublicoGeralBO publicoGeralBO = new PublicoGeralBO();
	
	public String salvar(PublicoGeral novoPublicoGeral) {
		String mensagem = "";
		
		try {
			this.validarCPF(novoPublicoGeral.getCpf());
			this.validarNome(novoPublicoGeral.getNome());
			novoPublicoGeral = publicoGeralBO.salvar(novoPublicoGeral);
		} catch (CpfInvalidoException
				| NomeInvalidoException
				| CpfJaCadastradoException excecao) {
			mensagem = excecao.getMessage();
		} 
		mensagem = "Público geral salvo com sucesso! Id gerado: " + novoPublicoGeral.getId();
		
		return mensagem;
	}
	
	public String atualizar(PublicoGeral publicoGeral) {
		String mensagem = "";
		boolean atualizou = false;
		
		try {
			this.validarCPF(publicoGeral.getCpf());
			this.validarNome(publicoGeral.getNome());
			atualizou = publicoGeralBO.atualizar(publicoGeral);
		} catch (CpfInvalidoException
				| NomeInvalidoException
				| CpfJaCadastradoException excecao) {
			mensagem = excecao.getMessage();
		} 
		
		if(atualizou) {
			mensagem = "Público geral atualizado com sucesso!";
		} else {
			mensagem = "Erro ao atualizar público geral: (";
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
