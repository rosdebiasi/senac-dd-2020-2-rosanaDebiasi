package br.sc.senac.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.sc.senac.model.vo.Pesquisador;
import br.sc.senac.model.vo.Pessoa;
import br.sc.senac.model.vo.PublicoGeral;
import br.sc.senac.model.vo.Vacina;
import br.sc.senac.model.vo.Voluntario;

public class VacinaDAO {
	
	public Vacina cadastrar(Vacina novaVacina) {
		Connection conexao = Banco.getConnection();
		
		String sql = "INSERT INTO VACINA (IDPESQUISADOR, IDPESSOA, PAIS_ORIGEM, ESTAGIO_PESQUISA, DATA_INICIO_PESQUISA)" + 
						"VALUES(?,?,?,?,?,?)";
		
		PreparedStatement query = Banco.getPreparedStatementWithGeneratedKeys(conexao, sql);
		
		try {
			
			Pesquisador pesquisador = verificarIdPesquisador(pesquisador);
			
			PublicoGeral publicoGeral = verificarIdPublicoGeral(publicoGeral); // como setar para ser um dos dois? 
			Voluntario voluntario = verificarIdVoluntario(voluntario); // como setar para ser um dos dois? 
			
			query.setInt(1, pesquisador.getId());
			query.setInt(2, publicoGeral.getId() || voluntario.getId() ); // como setar para ser um dos dois? 
			query.setString(3, novaVacina.getPaisOrigem());
			query.setString(4, String.valueOf(novaVacina.getEstagioPesquisa()));
			query.setString(5, String.valueOf(novaVacina.getDataInicioPesquisa())); 
		} catch(SQLException e) {
			System.out.println("Erro ao inserir o pesquisador.\nCausa: " + e.getMessage());
		} finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}
		return novoPesquisador;
	}
	
public boolean alterar(Vacina vacina) {
		
		String sql = " UPDATE VACINA " 
					+ " SET PAIS_ORIGEM=?, ESTAGIO_PESQUISA=?, DATA_INICIO_PESQUISA=? " 
					+ " WHERE IDVACINA=?";

		boolean alterou = false;
		
		try(Connection conexao = Banco.getConnection();
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);){
			
			query.setString(1,vacina.getPaisOrigem());
			query.setString(2, String.valueOf(vacina.getEstagioPesquisa()));
			query.setString(3, String.valueOf(vacina.getDataInicioPesquisa()));
			query.setInt(4, vacina.getIdVacina());
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
		
	private Voluntario verificarIdVoluntario(Voluntario voluntario) {
		// TODO Auto-generated method stub
		return null;
	}

	private PublicoGeral verificarIdPublicoGeral(PublicoGeral publicoGeral) {
		// TODO Auto-generated method stub
		return null;
	}

	private Pesquisador verificarIdPesquisador(Pesquisador pesquisador) {
		int idPesquisador = pesquisador.getId();
		if(idPesquisador !=null) {
				if(idPesquisador == 0) {
					PesquisadorDAO pesqDAO= new PesquisadorDAO();
					idPesquisador = pesqDAO.cadastrar(pesquisador.getId());
				}	
		}
		return idPesquisador;
	}

	private Vacina construirVacinaDoResultSet(ResultSet conjuntoResultante) throws SQLException {
		Vacina vacinaBuscada = new Vacina();
		vacinaBuscada.setIdVacina(conjuntoResultante.getInt("idvacina"));
		vacinaBuscada.setPaisOrigem(conjuntoResultante.getString("origem"));
		//vacinaBuscada.setEstagioPesquisa(conjuntoResultante.getString("estagio_pesquisa"));  
		// vacinaBuscada.setDataInicioPesquisa(conjuntoResultante.getString("data_inicio_pesquisa)); // dúvida é para ser em char mas não tem getChar
		
		// como preenche o id do publico geral e/ou id do voluntário??? é como está abaixo? e como associar isso com vacina?
		VoluntarioDAO voluntarioDAO = new VoluntarioDAO();
		int idVoluntario = conjuntoResultante.getInt("idvoluntario");
		
		return  vacinaBuscada;
	}
	
	
	

}
