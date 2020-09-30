package br.sc.senac.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.sc.senac.model.vo.PublicoGeral;

public class PublicoGeralDAO {
	
	public PublicoGeral cadastrar(PublicoGeral novoPublicoGeral) {
		Connection conexao = Banco.getConnection();
		
		String sql = "INSERT INTO PESSOA (NOME, CPF, DATA_NASCIMENTO, SEXO, REACAO_VACINA, TELEFONE)" + 
						"VALUES(?,?,?,?,?,?)" + 
						"INSERT INTO PUBLICO_GERAL(IDPESSOA)" + "VALUES(?)"; 
	
		PreparedStatement query = Banco.getPreparedStatementWithGeneratedKeys(conexao, sql);
		
		try {
			query.setString(1, novoPublicoGeral.getNome());
			query.setString(2, novoPublicoGeral.getCpf());
			query.setString(3, String.valueOf(novoPublicoGeral.getDataNascimento())); 
			query.setString(4, String.valueOf(novoPublicoGeral.getSexo())); 
			query.setInt(5, novoPublicoGeral.getReacaoVacina());
			query.setString(6, novoPublicoGeral.getTelefone());
			query.setInt(1, novoPublicoGeral.getId());
		} catch(SQLException e) {
			System.out.println("Erro ao inserir público geral.\nCausa: " + e.getMessage());
		} finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}
		return novoPublicoGeral;
	}
	
	public boolean alterar(PublicoGeral publicoGeral) {
		
		String sql = " UPDATE PESSOA, PUBLICO_GERAL " 
					+ " SET NOME=?, CPF=?, DATA_NASCIMENTO=?, SEXO=?, REACAO_VACINA=?, TELEFONE=? " 
					+ " WHERE IDPESSOA=IDPUBLICO_GERAL "; 
				
		boolean alterou = false;
		
		try(Connection conexao = Banco.getConnection();
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);){
			query.setString(1,publicoGeral.getNome());
			query.setString(2, publicoGeral.getCpf());
			query.setString(3, String.valueOf(publicoGeral.getDataNascimento())); 
			query.setString(4, String.valueOf(publicoGeral.getSexo())); 
			query.setInt(5, publicoGeral.getReacaoVacina());
			query.setString(6, publicoGeral.getTelefone());
			query.setInt(7, publicoGeral.getId());
			int codigoRetorno = query.executeUpdate();
			alterou = (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO);
		} catch (SQLException e) {
			System.out.println("Erro ao alterar o público geral.\nCausa: " + e.getMessage());
		}
		return alterou;
	}
	
	public boolean excluir(int id) {
		Connection conexao = Banco.getConnection();
		
		String sql = " DELETE FROM PESSOA WHERE IDPESSOA=IDPUBLICO_GERAL ";

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
	
	public PublicoGeral pesquisarPorId(int id) {
		String sql = " SELECT * FROM PESSOA WHERE IDPESSOA=IDPUBLICO_GERAL ";
		PublicoGeral publicoGeralBuscado = null;
		
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setInt(1, id);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				publicoGeralBuscado= construirPublicoGeralDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente por Id (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		return publicoGeralBuscado;
	}
	
	public PublicoGeral pesquisarPorCpf(String cpf) {
		String sql = " SELECT * FROM PESSOA WHERE CPF=? ";
		PublicoGeral publicoGeralBuscado = null;
		
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setString(1, cpf);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				publicoGeralBuscado = construirPublicoGeralDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente por CPF (" + cpf + ") .\nCausa: " + e.getMessage());
		}
		return publicoGeralBuscado;
	}
	
	public List<PublicoGeral> pesquisarTodos() {
		Connection conexao = Banco.getConnection();
		String sql = " SELECT IDPUBLICO_GERAL, NOME, CPF, SEXO, DATA_NASCIMENTO, REACAO_VACINA, TELEFONE, INSTITUICAO " +
				" FROM PESSOA, PUBLICO_GERAL " +
				" WHERE IDPESSOA=IDPUBLICO_GERAL ";
		
		PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);
		List<PublicoGeral> publicosGeraisBuscados = new ArrayList<PublicoGeral>();
		
		try {
			ResultSet conjuntoResultante = consulta.executeQuery();
			while(conjuntoResultante.next()) {
				PublicoGeral publicoGeralBuscado = construirPublicoGeralDoResultSet(conjuntoResultante);
				publicosGeraisBuscados.add(publicoGeralBuscado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os públicos gerais buscados .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(consulta);
			Banco.closeConnection(conexao);
		}
		return publicosGeraisBuscados;
	}
	
	public boolean cpfJaCadastrado(PublicoGeral umPublicoGeral) {
		boolean jaCadastrado = false;

		Connection conexao = Banco.getConnection();
		String sql = "SELECT count(idpessoa) FROM PESSOA WHERE CPF = ?";
		
		if(umPublicoGeral.getId() > 0) {
			sql += " AND IDPESSOA <> ? "; // DUVIDA
		}
		PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);
		
		try {
			consulta.setString(1, umPublicoGeral.getCpf());
			
			if(umPublicoGeral.getId() > 0) {
				consulta.setInt(2, umPublicoGeral.getId());
			}
	
			ResultSet conjuntoResultante = consulta.executeQuery();
			jaCadastrado = conjuntoResultante.next();
		} catch (SQLException e) {
			System.out.println("Erro ao verificar se CPF (" + umPublicoGeral.getCpf() + ") já foi usado .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(consulta);
			Banco.closeConnection(conexao);
		}
		return jaCadastrado;
	}

	private PublicoGeral construirPublicoGeralDoResultSet(ResultSet conjuntoResultante) throws SQLException {
		PublicoGeral publicoGeralBuscado = new PublicoGeral();
		publicoGeralBuscado.setId(conjuntoResultante.getInt("id"));
		publicoGeralBuscado.setNome(conjuntoResultante.getString("nome"));
		publicoGeralBuscado.setCpf(conjuntoResultante.getString("cpf"));
		//publicoGeralBuscado.setDataNascimento((conjuntoResultante.getDate("data_nascimento)); // dúvida é para ser em dateLocal mas não tem getChar
		//publicoGeralBuscado.setSexo(conjuntoResultante.getCharacterStream("sexo")); // dúvida é para ser em char mas não tem getChar
		publicoGeralBuscado.setReacaoVacina(conjuntoResultante.getInt("reacao_vacina"));
		publicoGeralBuscado.setTelefone(conjuntoResultante.getString("telefone"));
		
		return  publicoGeralBuscado;
	}
	
}
