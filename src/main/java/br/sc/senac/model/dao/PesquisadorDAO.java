package br.sc.senac.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.sc.senac.model.vo.Pesquisador;

public class PesquisadorDAO {
	
	public Pesquisador cadastrar(Pesquisador novoPesquisador) {
		Connection conexao = Banco.getConnection();
		
		String sql = "INSERT INTO PESSOA (NOME, CPF, DATA_NASCIMENTO, SEXO, REACAO_VACINA, TELEFONE)" + 
						"VALUES(?,?,?,?,?,?)" + 
						"INSERT INTO PESQUISADOR (INSTITUICAO)" + "VALUES(?)";
		
		PreparedStatement query = Banco.getPreparedStatementWithGeneratedKeys(conexao, sql);
		
		try {
			query.setString(1, novoPesquisador.getNome());
			query.setString(2, novoPesquisador.getCpf());
			query.setString(3, String.valueOf(novoPesquisador.getDataNascimento())); 
			query.setString(4, String.valueOf(novoPesquisador.getSexo())); 
			query.setInt(5, novoPesquisador.getReacaoVacina());
			query.setString(6, novoPesquisador.getTelefone());
			query.setString(1, novoPesquisador.getInstituicao());
		} catch(SQLException e) {
			System.out.println("Erro ao inserir o pesquisador.\nCausa: " + e.getMessage());
		} finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}
		return novoPesquisador;
	}
	
public boolean alterar(Pesquisador pesquisador) {
		
		String sql = " UPDATE PESSOA, PESQUISADOR " 
					+ " SET NOME=?, CPF=?, DATA_NASCIMENTO=?, SEXO=?, REACAO_VACINA=?, TELEFONE=?, INSTITUICAO=? " 
					+ " WHERE IDPESSOA=IDPESQUISADOR ";

		boolean alterou = false;
		
		try(Connection conexao = Banco.getConnection();
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);){
			
			query.setString(1,pesquisador.getNome());
			query.setString(2, pesquisador.getCpf());
			query.setString(3, String.valueOf(pesquisador.getDataNascimento())); 
			query.setString(4, String.valueOf(pesquisador.getSexo())); 
			query.setInt(5, pesquisador.getReacaoVacina());
			query.setString(6, pesquisador.getTelefone());
			query.setString(7, pesquisador.getInstituicao());
			query.setInt(8, pesquisador.getId());
			int codigoRetorno = query.executeUpdate();
			alterou = (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO);
		} catch (SQLException e) {
			System.out.println("Erro ao alterar o público geral.\nCausa: " + e.getMessage());
		}
		return alterou;
	}
	
	public boolean excluir(int id) {
		Connection conexao = Banco.getConnection();
		
		String sql = " DELETE FROM PESSOA, PESQUISADOR WHERE IDPESSOA=IDPESQUISADOR ";

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
	
	public Pesquisador pesquisarPorId(int id) {
		String sql = " SELECT * FROM PESSOA WHERE IDPESSOA=IDPESQUISADOR ";
		Pesquisador pesquisadorBuscado = null;
		
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setInt(1, id);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				pesquisadorBuscado= construirPesquisadorDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente por Id (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		return pesquisadorBuscado;
	}
	
	public Pesquisador pesquisarPorCpf(String cpf) {
		String sql = " SELECT * FROM PESSOA WHERE CPF=? ";
		Pesquisador pesquisadorBuscado = null;
		
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setString(1, cpf);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				pesquisadorBuscado = construirPesquisadorDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente por CPF (" + cpf + ") .\nCausa: " + e.getMessage());
		}
		return pesquisadorBuscado;
	}
	
	public List<Pesquisador> pesquisarTodos() {
		Connection conexao = Banco.getConnection();
		String sql = " SELECT IDPESQUISADOR, NOME, CPF, DATA_NASCIMENTO, SEXO, REACAO_VACINA, TELEFONE, INSTITUICAO " +
					" FROM PESSOA, PESQUISADOR " +
					" WHERE IDPESSOA=IDPESQUISADOR ";
		
		PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);
		List<Pesquisador> pesquisadoresBuscados = new ArrayList<Pesquisador>();
		
		try {
			ResultSet conjuntoResultante = consulta.executeQuery();
			while(conjuntoResultante.next()) {
				Pesquisador pesquisadorBuscado = construirPesquisadorDoResultSet(conjuntoResultante);
				pesquisadoresBuscados.add(pesquisadorBuscado);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os públicos gerais buscados .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(consulta);
			Banco.closeConnection(conexao);
		}
		return pesquisadoresBuscados;
	}
	
	public boolean cpfJaCadastrado(Pesquisador umPesquisador) {
		boolean jaCadastrado = false;

		Connection conexao = Banco.getConnection();
		String sql = "SELECT count(idpessoa) FROM PESSOA WHERE CPF = ?";
		
		if(umPesquisador.getId() > 0) {
			sql += " AND ID <> ? ";
		}
		PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);
		
		try {
			consulta.setString(1, umPesquisador.getCpf());
			
			if(umPesquisador.getId() > 0) {
				consulta.setInt(2, umPesquisador.getId());
			}
	
			ResultSet conjuntoResultante = consulta.executeQuery();
			jaCadastrado = conjuntoResultante.next();
		} catch (SQLException e) {
			System.out.println("Erro ao verificar se CPF (" + umPesquisador.getCpf() + ") já foi usado .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(consulta);
			Banco.closeConnection(conexao);
		}
		return jaCadastrado;
	}

	private Pesquisador construirPesquisadorDoResultSet(ResultSet conjuntoResultante) throws SQLException {
		Pesquisador pesquisadorBuscado = new Pesquisador();
		pesquisadorBuscado.setId(conjuntoResultante.getInt("id"));
		pesquisadorBuscado.setNome(conjuntoResultante.getString("nome"));
		pesquisadorBuscado.setCpf(conjuntoResultante.getString("cpf"));
		//pesquisadorBuscado.setDataNascimento((conjuntoResultante.getDate("data_nascimento)); // dúvida é para ser em dateLocal mas não tem getChar
		//pesquisadorBuscado.setSexo(conjuntoResultante.getCharacterStream("sexo")); // dúvida é para ser em char mas não tem getChar
		pesquisadorBuscado.setReacaoVacina(conjuntoResultante.getInt("reacao_vacina"));
		pesquisadorBuscado.setTelefone(conjuntoResultante.getString("telefone"));
		pesquisadorBuscado.setInstituicao(conjuntoResultante.getString("instituicao"));
		
		return  pesquisadorBuscado;
	}
	

}
