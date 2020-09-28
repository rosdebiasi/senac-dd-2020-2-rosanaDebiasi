package br.sc.senac.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.sc.senac.model.vo.Voluntario;

public class VoluntarioDAO {
	
	public Voluntario cadastrar(Voluntario novoVoluntario) {
		Connection conexao = Banco.getConnection();
		
		String sql = "INSERT INTO PESSOA (NOME, CPF, DATA_NASCIMENTO, SEXO, REACAO_VACINA, TELEFONE)" + 
						"VALUES(?,?,?,?,?,?)"; 
	
		PreparedStatement query = Banco.getPreparedStatementWithGeneratedKeys(conexao, sql);
		
		try {
			query.setString(1, novoVoluntario.getNome());
			query.setString(2, novoVoluntario.getCpf());
			query.setString(3, String.valueOf(novoVoluntario.getDataNascimento())); 
			query.setString(4, String.valueOf(novoVoluntario.getSexo())); 
			query.setInt(5, novoVoluntario.getReacaoVacina());
			query.setString(6, novoVoluntario.getTelefone());
		} catch(SQLException e) {
			System.out.println("Erro ao inserir público geral.\nCausa: " + e.getMessage());
		} finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}
		return novoVoluntario;
	}
	
	public boolean alterar(Voluntario voluntario) {
		
		String sql = " UPDATE PESSOA, VOLUNTARIO " 
					+ " SET NOME=?, CPF=?, DATA_NASCIMENTO=?, SEXO=?, REACAO_VACINA=?, TELEFONE=? " 
					+ " WHERE IDPESSOA=IDVOLUNTARIO "; 
				
		boolean alterou = false;
		
		try(Connection conexao = Banco.getConnection();
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);){
			query.setString(1,voluntario.getNome());
			query.setString(2, voluntario.getCpf());
			query.setString(3, String.valueOf(voluntario.getDataNascimento())); 
			query.setString(4, String.valueOf(voluntario.getSexo())); 
			query.setInt(5, voluntario.getReacaoVacina());
			query.setString(6, voluntario.getTelefone());
			query.setInt(7, voluntario.getId());
			int codigoRetorno = query.executeUpdate();
			alterou = (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO);
		} catch (SQLException e) {
			System.out.println("Erro ao alterar o voluntário.\nCausa: " + e.getMessage());
		}
		return alterou;
	}
	
	public boolean excluir(int id) {
		Connection conexao = Banco.getConnection();
		
		String sql = " DELETE FROM PESSOA WHERE IDPESSOA=IDVOLUNTARIO ";

		PreparedStatement query = Banco.getPreparedStatement(conexao, sql);
		boolean excluiu = false;
		
		try {
			query.setInt(1, id);
			int codigoRetorno = query.executeUpdate();
			excluiu = (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO);
		} catch (SQLException e) {
			System.out.println("Erro ao excluir o voluntário (id: " + id + ") .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}		
		return excluiu;
	}
	
	public Voluntario pesquisarPorId(int id) {
		String sql = " SELECT * FROM PESSOA WHERE IDPESSOA=IDVOLUNTARIO ";
		Voluntario voluntarioBuscado = null;
		
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setInt(1, id);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				voluntarioBuscado= construirVoluntarioDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente por Id (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		return voluntarioBuscado;
	}
	
	public Voluntario pesquisarPorCpf(String cpf) {
		String sql = " SELECT * FROM PESSOA WHERE CPF=? ";
		Voluntario voluntarioBuscado = null;
		
		//Exemplo usando try-with-resources (similar ao bloco finally)
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setString(1, cpf);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				voluntarioBuscado = construirVoluntarioDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente por CPF (" + cpf + ") .\nCausa: " + e.getMessage());
		}
		return voluntarioBuscado;
	}
	
	public List<Voluntario> pesquisarTodos() {
		Connection conexao = Banco.getConnection();
		String sql = " SELECT IDVOLUNTARIO, NOME, CPF, DATA_NASCIMENTO, SEXO, REACAO_VACINA, TELEFONE " +
					" FROM PESSOA, VOLUNTARIO " +
					" WHERE IDPESSOA=IDVOLUNTARIO ";
				
		PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);
		List<Voluntario> voluntariosBuscados = new ArrayList<Voluntario>();
		
		try {
			ResultSet conjuntoResultante = consulta.executeQuery();
			while(conjuntoResultante.next()) {
				Voluntario publicoGeralBuscado = construirVoluntarioDoResultSet(conjuntoResultante);
				voluntariosBuscados.add(publicoGeralBuscado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os públicos gerais buscados .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(consulta);
			Banco.closeConnection(conexao);
		}
		return voluntariosBuscados;
	}
	
	public boolean cpfJaCadastrado(Voluntario umVoluntario) {
		boolean jaCadastrado = false;

		Connection conexao = Banco.getConnection();
		String sql = "SELECT count(idpessoa) FROM PESSOA WHERE CPF = ?";
		
		if(umVoluntario.getId() > 0) {
			sql += " AND IDPESSOA <> ? ";
		}
		PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);
		
		try {
			consulta.setString(1, umVoluntario.getCpf());
			
			if(umVoluntario.getId() > 0) {
				consulta.setInt(2, umVoluntario.getId());
			}
	
			ResultSet conjuntoResultante = consulta.executeQuery();
			jaCadastrado = conjuntoResultante.next();
		} catch (SQLException e) {
			System.out.println("Erro ao verificar se CPF (" + umVoluntario.getCpf() + ") já foi usado .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(consulta);
			Banco.closeConnection(conexao);
		}
		return jaCadastrado;
	}

	private Voluntario construirVoluntarioDoResultSet(ResultSet conjuntoResultante) throws SQLException {
		Voluntario voluntarioBuscado = new Voluntario();
		voluntarioBuscado.setId(conjuntoResultante.getInt("id"));
		voluntarioBuscado.setNome(conjuntoResultante.getString("nome"));
		voluntarioBuscado.setCpf(conjuntoResultante.getString("cpf"));
		//voluntarioBuscado.setDataNascimento((conjuntoResultante.getDate("data_nascimento)); // dúvida é para ser em dateLocal mas não tem getChar
		//voluntarioBuscado.setSexo(conjuntoResultante.getCharacterStream("sexo")); // dúvida é para ser em char mas não tem getChar
		voluntarioBuscado.setReacaoVacina(conjuntoResultante.getInt("reacao_vacina"));
		voluntarioBuscado.setTelefone(conjuntoResultante.getString("telefone"));
		
		return  voluntarioBuscado;
	}
	
}
