package br.sc.senac.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.sc.senac.model.vo.Pesquisador;
import br.sc.senac.model.vo.Pessoa;
import br.sc.senac.model.vo.ReacaoVacina;
import br.sc.senac.model.vo.Vacina;

public class ReacaoVacinaDAO {
	
	public ReacaoVacina cadastrar(ReacaoVacina novaReacaoVacina) {
		Connection conexao = Banco.getConnection();
		
		String sql = "INSERT INTO REACAO_VACINA (IDPESSOA, IDVACINA, REACAO_VACINA)" +
					"VALUES(?,?,?)";
		
		PreparedStatement query = Banco.getPreparedStatementWithGeneratedKeys(conexao, sql);
		
		try {
			query.setInt(1, novaReacaoVacina.getPessoa().getId());
			query.setInt(2, novaReacaoVacina.getVacina().getIdVacina());
			query.setInt(3, novaReacaoVacina.getReacaoVacina());

		} catch(SQLException e) {
			System.out.println("Erro ao inserir a reação da vacina.\nCausa: " + e.getMessage());
		} finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}
		return novaReacaoVacina;
	}
	
	public boolean alterar(ReacaoVacina reacaoVacina) {
		
		String sql = " UPDATE REACAO_VACINA " 
				+ " SET IDPESSOA=?, IDVACINA=?, REACAO=? "
				+ " WHERE IDREACAO_VACINA=?"; 
		
		boolean alterou = false;
		
		try(Connection conexao = Banco.getConnection();
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);){

			query.setInt(1, reacaoVacina.getPessoa().getId());
			query.setInt(2, reacaoVacina.getVacina().getIdVacina());
			query.setInt(3, reacaoVacina.getReacaoVacina());
			query.setInt(4, reacaoVacina.getId());
	
			int codigoRetorno = query.executeUpdate();
			alterou = (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO);
			
		} catch (SQLException e) {
				System.out.println("Erro ao alterar a reação da vacina.\nCausa: " + e.getMessage());
		}
		return alterou;
	}
	
	public boolean excluir(int id) {
		Connection conexao = Banco.getConnection();
		
		String sql = " DELETE FROM PESSOA_VACINA WHERE IDPESSOA_VACINA=? ";

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
	
	public ReacaoVacina pesquisarPorId(int id) {
		String sql = " SELECT * FROM PESSOA_VACINA ";
		ReacaoVacina reacaoVacinaBuscada = null;
		
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

	private ReacaoVacina construirVacinaDoResultSet(ResultSet conjuntoResultante) throws SQLException {
		ReacaoVacina reacaoVacinaBuscada = new ReacaoVacina();

		reacaoVacinaBuscada.setId(conjuntoResultante.getInt("idreacaovacina"));
		reacaoVacinaBuscada.setReacaoVacina(conjuntoResultante.getInt("reacao_vacina"));

		return reacaoVacinaBuscada;
	}



}
