package br.com.ftec.ads.poo.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.com.ftec.ads.poo.entidade.Usuario;

public class UsuarioDAO extends BaseDAO{

	private static UsuarioDAO usuarioDAO;

	/*
	 * factory method
	 */
	public static UsuarioDAO getInstance(){
		if (usuarioDAO == null){
			usuarioDAO = new UsuarioDAO();
		}
		return usuarioDAO;
	}
	
	private UsuarioDAO() {}	
	
	public Integer inserir(Usuario usuario) throws SQLException, ClassNotFoundException{
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = super.getConnection().prepareStatement("insert into usuario (email, senha) values (?, ?);");
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
			prepareStatement = super.getConnection().prepareStatement("delete from usuario;");
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
			return lu;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public List<Usuario> selecionarPorEmail(String email) throws SQLException, ClassNotFoundException {
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = this.getConnection().prepareStatement("select * from usuario where email like '%" + email + "%';");
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
				String emailUsuario = executeQuery.getString("email");
				String senha = executeQuery.getString("senha");
				Usuario u = new Usuario(id, emailUsuario, senha);
				lu.add(u);
			}
			return lu;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public Integer deletar(List<Usuario> us) throws ClassNotFoundException, SQLException {
		String ids = "";
		for (Usuario usuario : us) {
			ids += usuario.getId();
			ids += ",";
		}
		ids = ids.substring(0, ids.length() - 1);
		
		PreparedStatement prepareStatement = null;
		try {
			prepareStatement = super.getConnection().prepareStatement("delete from usuario where id in ("+ids+");");
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
	
}
