package br.sc.senac.model.bo;


import br.sc.senac.model.dao.VoluntarioDAO;
import br.sc.senac.model.exception.CpfJaCadastradoException;
import br.sc.senac.model.vo.Voluntario;

public class VoluntarioBO {
	
private VoluntarioDAO voluntarioDAO = new VoluntarioDAO();
	
	public Voluntario salvar(Voluntario novoVoluntario) 
			throws CpfJaCadastradoException{
		
		if(this.voluntarioDAO.cpfJaCadastrado(novoVoluntario)) {
			throw new CpfJaCadastradoException("O CPF informado (" + novoVoluntario.getCpf() 
			+ ") já foi cadastrado para outro voluntário");
		}
		this.voluntarioDAO.cadastrar(novoVoluntario);
		
		return novoVoluntario;
	}
	
	public boolean atualizar(Voluntario voluntario) throws CpfJaCadastradoException{

		if(this.voluntarioDAO.cpfJaCadastrado(voluntario)) {
			throw new CpfJaCadastradoException("O CPF informado (" + voluntario.getCpf() 
			+ ") já foi cadastrado para outro voluntário");
		}
		return this.voluntarioDAO.alterar(voluntario);
	}
	
	public Voluntario consultarPorCPF(String cpf) {
		return this.consultarPorCPF(cpf);
	}
	
	public boolean excluirVoluntario(Voluntario voluntarioQueSeraExcluido) {
		boolean excluiu = voluntarioDAO.excluir(voluntarioQueSeraExcluido.getId());
		
		return excluiu;
	}

}
