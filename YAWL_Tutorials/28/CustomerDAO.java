package de.hbrs.yawl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDAO {
	
	private static String connectionUrl = "jdbc:postgresql://localhost:5432/customers";
	
	public static void main(String[] args) {
		try {
                   	System.out.println(new CustomerDAO().find(1));
		} catch (SQLException e) {			
			e.printStackTrace();
		}
	}

	private Connection con;

	public CustomerDAO() throws SQLException {
		super();
       		con = DriverManager.getConnection(connectionUrl,"wfms","wfms");		
	}
	
	public String find(long id) throws SQLException {
		PreparedStatement findStatement = con.prepareStatement("SELECT * FROM cust WHERE cust.id = ? ");
		findStatement.setLong(1, id);
		ResultSet resultSet = findStatement.executeQuery();
		if (resultSet.next()) {
			return resultSet.getString(resultSet.findColumn("name"));
		} else {
			return "Not Found";
		}
	}
	
	public void update(long id, String customerName) throws SQLException {
		PreparedStatement updateStatement = con.prepareStatement("UPDATE cust SET name = ? " +
				"WHERE id = ? ");
		updateStatement.setLong(2, id);
		updateStatement.setString(1, customerName);
		updateStatement.execute();
	}	
	

}
