package com.mauriello.SearchProducts.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private Connection connection;
	private String url = "jdbc:mysql://localhost:3306/example?useSSL=false";
	private String name = "productuser";
	private String password = "ThePassword";
	
	public DBConnection() throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(this.url, this.name, this.password);
	}
	
	public DBConnection(String dbURL, String user, String pwd) throws ClassNotFoundException, SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		this.connection = DriverManager.getConnection(dbURL, user, pwd);
	}
	
	public Connection getConnection() {
		return this.connection;
	}
		
	public void closeConnection() throws SQLException {
		if (this.connection != null) {
			this.connection.close();
		}
	}
}