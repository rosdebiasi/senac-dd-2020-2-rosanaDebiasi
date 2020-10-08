package br.sc.senac.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.sc.senac.model.vo.Pesquisador;
import br.sc.senac.model.vo.Pessoa;
import br.sc.senac.model.vo.Vacina;

public class PessoaVacinaDAO {
	
	public Pessoa cadastrar(Pessoa reacaoVacina) {
		Connection conexao = Banco.getConnection();
		
		String sql = "INSERT INTO PESSOA_VACINA (REACAO_VACINA)" +
					"VALUES(?)";
		
		PreparedStatement query = Banco.getPreparedStatementWithGeneratedKeys(conexao, sql);
		
		try {
			query.setString(1, String.valueOf(reacaoVacina.getReacaoVacina()));
		} catch(SQLException e) {
			System.out.println("Erro ao inserir a reação da vacina.\nCausa: " + e.getMessage());
		} finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}
		return reacaoVacina;
	}
	
	public boolean alterar(Pessoa reacaoVacina) {
		
		String sql = " UPDATE PESSOA_VACINA " 
				+ " SET IDPESSOA=?, IDVACINA=?, REACAO=? "
				+ " WHERE IDPESSOA=?, IDVACINA=? "; 
		
		boolean alterou = false;
		
		try(Connection conexao = Banco.getConnection();
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);){
				
			Pesquisador pessoa = new Pesquisador();
			Vacina vacina = new Vacina();
				
			query.setInt(1,pessoa.getId());
			query.setInt(2,vacina.getIdVacina());
			query.setInt(3,pessoa.getReacaoVacina());
	
			int codigoRetorno = query.executeUpdate();
			alterou = (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO);
			
		} catch (SQLException e) {
				System.out.println("Erro ao alterar a reação da vacina.\nCausa: " + e.getMessage());
		}
		return alterou;
	}
	
	public boolean excluir(int id) {
		Connection conexao = Banco.getConnection();
		
		String sql = " DELETE FROM PESSOA_VACINA WHERE IDPESSOA=? ";

		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		boolean excluiu = false;
		
		try {
			query.setInt(1, id);
			int codigoRetorno = query.executeUpdate();
			excluiu = (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO);
		} catch (SQLException e) {
			System.out.println("Erro ao excluir público geral (id: " + id + ") .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}		
		return excluiu;
	}
	
	public Pessoa pesquisarPorId(int id) {
		String sql = " SELECT * FROM PESSOA_VACINA ";
		Pessoa reacaoVacinaBuscada = null;
		
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setInt(1, id);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				reacaoVacinaBuscada= construirVacinaDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar vacina por Id (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		return reacaoVacinaBuscada;
	}

	private Pessoa construirVacinaDoResultSet(ResultSet conjuntoResultante) throws SQLException {
		Pesquisador reacaoVacinaBuscada = new Pesquisador();
		Vacina idVacinaBuscado = new Vacina();
		
		reacaoVacinaBuscada.setId(conjuntoResultante.getInt("idpessoa"));
		idVacinaBuscado.setIdVacina(conjuntoResultante.getInt("idvacina"));
		reacaoVacinaBuscada.setReacaoVacina(conjuntoResultante.getInt("reacao_vacina"));
		
		return conjuntoResultante;
	}



}
