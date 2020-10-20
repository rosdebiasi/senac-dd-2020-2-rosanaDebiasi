package br.sc.senac.model.dao;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.SQLException;

import br.sc.senac.model.vo.Tipo;

public class TipoDAO {
	
	public ArrayList<Tipo> listarTipos() {
		String query = "SELECT ID, DESCRICAO FROM tipo_usuario";
		Connection conn = Banco.getConnection();
		Statement stmt = Banco.getStatement(conn);
		ResultSet resultado = null;
		ArrayList<Tipo> listaTipos = new ArrayList();
		
		try {
			resultado = stmt.executeQuery(query);
			while(resultado.next()) {
				Tipo tipo = new Tipo();
				tipo.setId(Integer.parseInt(resultado.getString("ID")));
				tipo.setDescricao(resultado.getString("DESCRICAO"));
				listaTipos.add(tipo);
			}
		} catch(SQLException e) {
			System.out.println("Erro ao obter lista de tipos de usuários!");
			System.out.println("Erro: " + e.getMessage());
		} finally {
			Banco.closeResultSet(resultado);
			Banco.closeStatement(stmt);
			Banco.closeConnection(conn);
		}
		return listaTipos;
	}

}
