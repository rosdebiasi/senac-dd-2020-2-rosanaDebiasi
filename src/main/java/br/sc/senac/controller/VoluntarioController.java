package br.sc.senac.controller;


import br.sc.senac.model.bo.VoluntarioBO;
import br.sc.senac.model.exception.CpfInvalidoException;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.vo.Voluntario;

public class VoluntarioController {
	
private VoluntarioBO voluntarioBO = new VoluntarioBO();
	
	public String salvar(Voluntario novoVoluntario) {
		String mensagem = "";
		
		try {
			this.validarCPF(novoVoluntario.getCpf());
			novoVoluntario = voluntarioBO.salvar(novoVoluntario);
		} catch (CpfInvalidoException 
				| CpfJaCadastradoException excecao) {
			mensagem = excecao.getMessage();
		} 
		mensagem = "Volunt�rio salvo com sucesso! Id gerado: " + novoVoluntario.getId();
		
		return mensagem;
	}
	
	public String atualizar(Voluntario voluntario) {
		String mensagem = "";
		boolean atualizou = false;
		
		try {
			this.validarCPF(voluntario.getCpf());
			atualizou = voluntarioBO.atualizar(voluntario);
		} catch (CpfInvalidoException 
				| CpfJaCadastradoException excecao) {
			mensagem = excecao.getMessage();
		} 
		
		if(atualizou) {
			mensagem = "Volunt�rio atualizado com sucesso!";
		} else {
			mensagem = "Erro ao atualizar volunt�rio: (";
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
			throw new CpfInvalidoException("CPF deve possuir tamanho 11 e somente n�meros");
		}
	}

}