package jdbcproj.crud.togroup;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import static jdbcproj.resources.Resources.DRIVER_CLASS;
import static jdbcproj.resources.Resources.URL;


/**
 * This class implements CRUD operation for groups table in database.
 * 
 * @author Lebedev Alexander
 * @since 2016-09-03
 * */
public class CRUDGroup {
	
	static{
		try{
			Class.forName(DRIVER_CLASS);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}

	/**
	 * This method insert data into groups table .
	 *
	 * @param name Name of new group.
	 * @throw SQLException
	 * @return Nothing
	 * */
	public void add(String name) throws SQLException{
		
		Connection conn = DriverManager.getConnection(URL);
		
		String query = "INSERT INTO groups (name) VALUES (?)";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		statement.executeUpdate();
		
		statement.close();
		conn.close();
	}
	
	/**
	 * This method update data in groups table.
	 * 
	 * @param name Old name of group
	 * @param newName New name of group
	 * @throw SQLException
	 * @return Nothing
	 * */
	public void update(String name, String newName) throws SQLException{
		
		Connection conn = DriverManager.getConnection(URL);
		
		String query = "UPDATE groups SET name = ? WHERE name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(2, name);
		statement.setString(1, newName);
		statement.executeUpdate();
		
		statement.close();
		conn.close();
	}
	
	/**
	 * This method delete data from groups table.
	 * 
	 * @param name Name of group which must be deleted.
	 * @throw SQLException
	 * @return Nothing
	 * */
	public void delete(String name) throws SQLException{

		Connection conn = DriverManager.getConnection(URL);
		
		String query = "DELETE FROM groups WHERE name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		statement.executeUpdate();
		
		statement.close();
		conn.close();
	}
	
	
	/**
	 * This method return list of all existing groups.
	 * 
	 * @throw SQLException
	 * @return List of name (String) of all groups
	 * */
	public List<String> getAll() throws SQLException{
		
		List<String> res = new ArrayList<String>();
		Connection conn = DriverManager.getConnection(URL);
		
		String query = "SELECT name FROM groups";
		PreparedStatement statement = conn.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			String name = rs.getString("name");
			
			res.add(name);
		}
		
		statement.close();
		conn.close();
		
		return res;
	}
}