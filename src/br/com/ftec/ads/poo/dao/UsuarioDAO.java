package br.com.ftec.ads.poo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ftec.ads.poo.entidade.Usuario;

public class UsuarioDAO {

	private Connection getConnection() throws ClassNotFoundException, SQLException{
		/*
		 * CARREGAR O DRIVER DO BANCO DE DADOS UTILIZADO
		 */
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		
		/*
		 * ESTABELECER A CONEXAO COM O BANCO DE DADOS
		 */
		Connection connection = null;
		try {
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/poodb", "poouser", "poopassword");
			return connection;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Integer inserir(Usuario usuario) throws SQLException, ClassNotFoundException{
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = this.getConnection().prepareStatement("insert into usuario (email, senha) values (?, ?);");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		try {
			prepareStatement.setString(1, usuario.getEmail());
			prepareStatement.setString(2, usuario.getSenha());
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		try {
			int executeUpdate = prepareStatement.executeUpdate();
			return executeUpdate;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public Integer deletarTodos() throws SQLException, ClassNotFoundException{
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = this.getConnection().prepareStatement("delete from usuario;");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		try {
			int executeUpdate = prepareStatement.executeUpdate();
			return executeUpdate;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}
	
	public List<Usuario> selecionarTodos() throws SQLException, ClassNotFoundException{
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = this.getConnection().prepareStatement("select * from usuario;");
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw e;
		}
		
		ResultSet executeQuery = null;
		try {
			executeQuery = prepareStatement.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		List<Usuario> lu = new ArrayList<Usuario>();
		try {
			while (executeQuery.next()){
				long id = executeQuery.getLong("id");
				String email = executeQuery.getString("email");
				String senha = executeQuery.getString("senha");
				Usuario u = new Usuario(id, email, senha);
				lu.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		return lu;
	}
	
}