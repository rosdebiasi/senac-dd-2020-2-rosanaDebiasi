package br.sc.senac.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import br.sc.senac.model.vo.Pesquisador;
import br.sc.senac.model.vo.Vacina;


public class VacinaDAO {
	
	public Vacina cadastrar(Vacina novaVacina) {
		Connection conexao = Banco.getConnection();
		
		String sql = "INSERT INTO VACINA (IDPESSOA, PAIS_ORIGEM, ESTAGIO_PESQUISA, DATA_INICIO_PESQUISA, PESQUISADOR)" + 
						"VALUES(?,?,?,?,?)";
		
		PreparedStatement query = Banco.getPreparedStatementWithGeneratedKeys(conexao, sql);
		Pesquisador pessoa = new Pesquisador();
		
		try {
			query.setInt(1, pessoa.getId());
			query.setString(2, novaVacina.getPaisOrigem());
			query.setString(3, String.valueOf(novaVacina.getEstagioPesquisa()));
			query.setString(4, String.valueOf(novaVacina.getDataInicioPesquisa())); 
			query.setString(5, pessoa.getNome());
		} catch(SQLException e) {
			System.out.println("Erro ao inserir o pesquisador.\nCausa: " + e.getMessage());
		} finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}
		return novaVacina;
	}
	
	public boolean alterar(Vacina vacina) {
		
		String sql = " UPDATE VACINA " 
					+ " SET IDPESSOA=?, PAIS_ORIGEM=?, ESTAGIO_PESQUISA=?, DATA_INICIO_PESQUISA=?, IDPESQUISADOR=? " 
					+ " WHERE IDVACINA=?";

		boolean alterou = false;
		
		try(Connection conexao = Banco.getConnection();
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);){
			
			Pesquisador pessoa = new Pesquisador();
			
			query.setInt(1,pessoa.getId());
			query.setString(2,vacina.getPaisOrigem());
			query.setString(3, String.valueOf(vacina.getEstagioPesquisa()));
			query.setString(4, String.valueOf(vacina.getDataInicioPesquisa()));
			query.setInt(5, vacina.getIdVacina());
	
			int codigoRetorno = query.executeUpdate();
			alterou = (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO);
		} catch (SQLException e) {
			System.out.println("Erro ao alterar a vacina.\nCausa: " + e.getMessage());
		}
		return alterou;
	}
	
	public boolean excluir(int id) {
		Connection conexao = Banco.getConnection();
		
		String sql = " DELETE FROM VACINA WHERE IDVACINA=? ";

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
	
	public Vacina pesquisarPorId(int id) {
		String sql = " SELECT * FROM VACINA ";
		Vacina vacinaBuscada = null;
		
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setInt(1, id);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				vacinaBuscada= construirVacinaDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar vacina por Id (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		return vacinaBuscada;
	}
	
	public List<Vacina> pesquisarTodos() {
		Connection conexao = Banco.getConnection();
		String sql = " SELECT * FROM VACINA ";

		PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);
		List<Vacina> vacinasBuscadas = new ArrayList<Vacina>();
		
		try {
			ResultSet conjuntoResultante = consulta.executeQuery();
			while(conjuntoResultante.next()) {
				Vacina vacinaBuscada = construirVacinaDoResultSet(conjuntoResultante);
				vacinasBuscadas.add(vacinaBuscada);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os públicos gerais buscados .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(consulta);
			Banco.closeConnection(conexao);
		}
		return vacinasBuscadas;
	}
		
	private Vacina construirVacinaDoResultSet(ResultSet conjuntoResultante) throws SQLException {
		Vacina vacinaBuscada = new Vacina();
		vacinaBuscada.setIdVacina(conjuntoResultante.getInt("idvacina"));
		vacinaBuscada.setPaisOrigem(conjuntoResultante.getString("origem"));
		
		Date dataSQL = conjuntoResultante.getDate("estagio_pesquisa");
		LocalDate dataInicioPesquisa = dataSQL.toLocalDate();
		vacinaBuscada.setDataInicioPesquisa(dataInicioPesquisa);
		
		vacinaBuscada.setEstagioPesquisa.valueOf(conjuntoResultante.getString("estagio_pesquisa")));
		
		Pesquisador pesquisadorBuscado = new Pesquisador();
		pesquisadorBuscado.setNome("pesquisador");
		
		
		return  vacinaBuscada;
	}
	
}
