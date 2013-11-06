package br.com.ftec.ads.poo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class BaseDAO {

	protected Connection getConnection() throws ClassNotFoundException, SQLException {
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

}
