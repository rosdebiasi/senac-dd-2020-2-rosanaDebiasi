package br.sc.senac.controller;


import br.sc.senac.model.bo.PublicoGeralBO;
import br.sc.senac.model.exception.CpfInvalidoException;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.vo.PublicoGeral;

public class PublicoGeralController {
	
	private PublicoGeralBO publicoGeralBO = new PublicoGeralBO();
	
	public String salvar(PublicoGeral novoPublicoGeral) {
		String mensagem = "";
		
		try {
			this.validarCPF(novoPublicoGeral.getCpf());
			novoPublicoGeral = publicoGeralBO.salvar(novoPublicoGeral);
		} catch (CpfInvalidoException 
				| CpfJaCadastradoException excecao) {
			mensagem = excecao.getMessage();
		} 
		mensagem = "Públuco geral salvo com sucesso! Id gerado: " + novoPublicoGeral.getId();
		
		return mensagem;
	}
	
	public String atualizar(PublicoGeral publicoGeral) {
		String mensagem = "";
		boolean atualizou = false;
		
		try {
			this.validarCPF(publicoGeral.getCpf());
			atualizou = publicoGeralBO.atualizar(publicoGeral);
		} catch (CpfInvalidoException 
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

}
