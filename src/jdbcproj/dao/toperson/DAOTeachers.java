package jdbcproj.dao.toperson;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;

import jdbcproj.data.person.Person;
import jdbcproj.data.person.Teacher;

import java.util.ArrayList;

import static jdbcproj.resources.Resources.getProperty;

/**
 * This class implements CRUD operation for teachers table.
 * 
 * @author Lebedev Alexander
 * @since 2016-09-07
 * */
public class DAOTeachers implements DAOPerson {

	static{
		try{
			Class.forName(getProperty("DRIVER_CLASS"));
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method insert data into teachers table.
	 * 
	 *  @see DAOPerson#add(String, String)
	 *  
	 *  @param name Name of teacher.
	 *  @param familyName Family name of teacher.
	 *  @throw SQLException
	 *  @return Nothing.
	 * */
	@Override
	public void add(String name, String familyName) throws SQLException {
		
		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "INSERT INTO teachers (name, family_name) VALUES (?, ?)";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		statement.setString(2, familyName);
		statement.executeUpdate();
		
		statement.close();
		conn.close();
	}

	/**
	 * This method update data into teachers table.
	 * 
	 * @see DAOPerson#update(String, String, String, String)
	 * 
	 * @param name Old name of teacher.
	 * @param familyName Old family name of teacher.
	 * @param newName New name of teacher.
	 * @param newFamilyName New family name of teacher.
	 * @throw SQLException
	 * @return Nothing.
	 * */
	@Override
	public void update(String name, String familyName, String newName, String newFamilyName) throws SQLException {

		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "UPDATE teachers SET name = ?, family_name = ? WHERE name = ? AND family_name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(3, name);
		statement.setString(4, familyName);
		statement.setString(1, newName);
		statement.setString(2, newFamilyName);
		statement.executeUpdate();
		
		statement.close();
		conn.close();
	}

	/**
	 * This method delete data from teachers table.
	 * 
	 * @see DAOPerson#delete(String, String)
	 * 
	 * @param name Name of teacher.
	 * @param familyName Family name of teacher.
	 * @throw SQLException
	 * @return Nothing
	 * */
	@Override
	public void delete(String name, String familyName) throws SQLException {
		
		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "DELETE FROM teachers WHERE name = ? AND family_name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		statement.setString(2, familyName);
		statement.executeUpdate();
		
		statement.close();
		conn.close();
	}

	/**
	 * This method return list of all teachers.
	 * 
	 * @see DAOPerson#getAll()
	 * 
	 * @throw SQLException
	 * @return List of persons
	 * */
	@Override
	public List<Person> getAll() throws SQLException {
		
		List<Person> res = new ArrayList<Person>();
		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "SELECT name, family_name FROM teachers";
		PreparedStatement statement = conn.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			String name = rs.getString("name");
			String familyName = rs.getString("family_name");
			
			res.add(new Teacher(name, familyName));
		}
		
		statement.close();
		conn.close();
		
		return res;
	}

	/**
	 * This method return list of all teachers who have a specific name.
	 * 
	 * @see DAOPerson#getByName(String)
	 * 
	 * @param name Name of teacher for whom there is a search
	 * @throw SQLException
	 * @return List of person who have a specific name
	 * */
	@Override
	public List<Person> getByName(String name) throws SQLException {

		List<Person> res = new ArrayList<Person>();
		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "SELECT name, family_name FROM teachers WHERE name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			name = rs.getString("name");
			String familyName = rs.getString("family_name");
			
			res.add(new Teacher(name, familyName));
		}
		
		statement.close();
		conn.close();
		
		return res;
	}

	/**
	 * This method return list of all teachers who have a specific family name.
	 * 
	 * @see DAOPerson#getByFamilyName(String)
	 * 
	 * @param familyName Family name of teacher for whom there is a search
	 * @throw SQLException
	 * @return List of person who have a specific family name
	 * */
	@Override
	public List<Person> getByFamilyName(String familyName) throws SQLException {

		List<Person> res = new ArrayList<Person>();
		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "SELECT name, family_name FROM teachers WHERE family_name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, familyName);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			String name = rs.getString("name");
			familyName = rs.getString("family_name");
			
			res.add(new Teacher(name, familyName));
		}
		
		statement.close();
		conn.close();
		
		return res;
	}
}