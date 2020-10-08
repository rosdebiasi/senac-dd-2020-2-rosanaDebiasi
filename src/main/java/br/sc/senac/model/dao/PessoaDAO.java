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
import br.sc.senac.model.vo.Pessoa;

public class PessoaDAO {
	
	public Pessoa cadastrar(Pessoa novaPessoa) {
		Connection conexao = Banco.getConnection();
		
		String sql = "INSERT INTO PESSOA (TIPO, INSTITUICAO, NOME, CPF, DATA_NASCIMENTO, SEXO, TELEFONE)" + 
						"VALUES(?,?,?,?,?,?)";
		
		PreparedStatement query = Banco.getPreparedStatementWithGeneratedKeys(conexao, sql);
		
		try {
			query.setInt(1, novaPessoa.getTipo());
			query.setString(2, novaPessoa.getInstituicao());
			query.setString(3, novaPessoa.getNome());
			query.setString(4, novaPessoa.getCpf());
			query.setString(5, String.valueOf(novaPessoa.getDataNascimento())); 
			query.setString(6, String.valueOf(novaPessoa.getSexo())); 
			query.setString(7, novaPessoa.getTelefone());
			
		} catch(SQLException e) {
			System.out.println("Erro ao inserir o usuário.\nCausa: " + e.getMessage());
		} finally {
			Banco.closeStatement(query);
			Banco.closeConnection(conexao);
		}
		return novaPessoa;
	}
	
public boolean alterar(Pessoa pessoa) {
		
		String sql = " UPDATE PESSOA " 
					+ " SET TIPO=?, SET INSTITUICAO=?, SET NOME=?, CPF=?, "
					+ " DATA_NASCIMENTO=?, SEXO=?, TELEFONE=? "
					+ " WHERE IDPESSOA=? "; 

		boolean alterou = false;
		
		try(Connection conexao = Banco.getConnection();
			PreparedStatement query = Banco.getPreparedStatement(conexao, sql);){
			
			query.setInt(1, pessoa.getTipo());
			query.setString(2, pessoa.getInstituicao());
			query.setString(3, pessoa.getNome());
			query.setString(4, pessoa.getCpf());
			query.setString(5, String.valueOf(pessoa.getDataNascimento())); 
			query.setString(6, String.valueOf(pessoa.getSexo())); 
			query.setString(7, pessoa.getTelefone());
			query.setInt(1, pessoa.getId());
			
			int codigoRetorno = query.executeUpdate();
			alterou = (codigoRetorno == Banco.CODIGO_RETORNO_SUCESSO);
		} catch (SQLException e) {
			System.out.println("Erro ao alterar o público geral.\nCausa: " + e.getMessage());
		}
		return alterou;
	}
	
	public boolean excluir(int id) {
		Connection conexao = Banco.getConnection();
		
		String sql = " DELETE FROM PESSOA WHERE IDPESSOA=?";

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
		String sql = " SELECT * FROM PESSOA WHERE IDPESSOA=? ";
		Pessoa pessoaBuscada = null;
		
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setInt(1, id);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				pessoaBuscada= construirPessoaDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente por Id (id: " + id + ") .\nCausa: " + e.getMessage());
		}
		return pessoaBuscada;
	}
	
	public Pessoa pesquisarPorCpf(String cpf) {
		String sql = " SELECT * FROM PESSOA WHERE CPF=? ";
		Pessoa pessoaBuscada = null;
		
		try (Connection conexao = Banco.getConnection();
			PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);) {
			consulta.setString(1, cpf);
			ResultSet conjuntoResultante = consulta.executeQuery();
			
			if(conjuntoResultante.next()) {
				pessoaBuscada = construirPessoaDoResultSet(conjuntoResultante);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar cliente por CPF (" + cpf + ") .\nCausa: " + e.getMessage());
		}
		return pessoaBuscada;
	}
	
	public List<Pesquisador> pesquisarTodos() {
		Connection conexao = Banco.getConnection();
		String sql = " SELECT IDPESQUISADOR, NOME, CPF, DATA_NASCIMENTO, SEXO, REACAO_VACINA, TELEFONE, INSTITUICAO " +
					" FROM PESSOA, PESQUISADOR " +
					" WHERE IDPESSOA=? ";
		
		PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);
		List<Pesquisador> pessoasBuscadas = new ArrayList<Pesquisador>();
		
		try {
			ResultSet conjuntoResultante = consulta.executeQuery();
			while(conjuntoResultante.next()) {
				Pesquisador pessoaBuscada = construirPessoaDoResultSet(conjuntoResultante);
				pessoasBuscadas.add(pessoaBuscada);
			}
		} catch (SQLException e) {
			System.out.println("Erro ao consultar todos os públicos gerais buscados .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(consulta);
			Banco.closeConnection(conexao);
		}
		return pessoasBuscadas;
	}
	
	public boolean cpfJaCadastrado(Pessoa umaPessoa) {
		boolean jaCadastrado = false;

		Connection conexao = Banco.getConnection();
		String sql = "SELECT count(idpessoa) FROM PESSOA WHERE CPF = ?";
		
		if(umaPessoa.getId() > 0) {
			sql += " AND IDPESSOA <> ? ";
		}
		
		PreparedStatement consulta = Banco.getPreparedStatement(conexao, sql);
		
		try {
			consulta.setString(1, umaPessoa.getCpf());
			
			if(umaPessoa.getId() > 0) {
				consulta.setInt(2, umaPessoa.getId());
			}
	
			ResultSet conjuntoResultante = consulta.executeQuery();
			jaCadastrado = conjuntoResultante.next();
		} catch (SQLException e) {
			System.out.println("Erro ao verificar se CPF (" + umaPessoa.getCpf() + ") já foi usado .\nCausa: " + e.getMessage());
		}finally {
			Banco.closeStatement(consulta);
			Banco.closeConnection(conexao);
		}
		return jaCadastrado;
	}

	private Pesquisador construirPessoaDoResultSet(ResultSet conjuntoResultante) throws SQLException {
		Pesquisador pessoaBuscada = new Pesquisador();
		pessoaBuscada.setId(conjuntoResultante.getInt("id"));
		pessoaBuscada.setTipo(conjuntoResultante.getInt("tipo"));
		pessoaBuscada.setInstituicao(conjuntoResultante.getString("instituicao"));
		pessoaBuscada.setNome(conjuntoResultante.getString("nome"));
		pessoaBuscada.setCpf(conjuntoResultante.getString("cpf"));
		pessoaBuscada.setTelefone(conjuntoResultante.getString("telefone"));
		
		Date dataSQL = conjuntoResultante.getDate("data_nascimento");
		LocalDate dataNascimento = dataSQL.toLocalDate();
		pessoaBuscada.setDataNascimento(dataNascimento);
			
		pessoaBuscada.setSexo(conjuntoResultante.getCharacterStream("sexo")); // dúvida é para ser em char mas não tem getChar

		return  pessoaBuscada;
	}
	
}
