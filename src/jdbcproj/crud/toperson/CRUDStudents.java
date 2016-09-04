package jdbcproj.crud.toperson;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.List;
import java.util.ArrayList;

import jdbcproj.person.Person;

import static jdbcproj.resources.Resources.DRIVER_CLASS;
import static jdbcproj.resources.Resources.URL;

/**
 * This class implements CRUD operation for students table.
 * 
 * @author Lebedev Alexander
 * @since 2016-09-03
 * */
public class CRUDStudents implements CRUDPerson{

	static{
		try{
			Class.forName(DRIVER_CLASS);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method insert data into students table.
	 * 
	 *  @see CRUDPerson#add(String, String)
	 *  
	 *  @param name Name of student.
	 *  @param familyName Family name of student.
	 *  @throw SQLException
	 *  @return Nothing.
	 * */
	@Override
	public void add(String name, String familyName) throws SQLException {
		
		Connection conn = DriverManager.getConnection(URL);
		
		String query = "INSERT INTO students (name, family_name) VALUES (?, ?)";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		statement.setString(2, familyName);
		statement.executeUpdate();
		
		statement.close();
		conn.close();
	}

	/**
	 * This method update data into students table.
	 * 
	 * @see CRUDPerson#update(String, String, String, String)
	 * 
	 * @param name Old name of student.
	 * @param familyName Old family name of student.
	 * @param newName New name of student.
	 * @param newFamilyName New family name of student.
	 * @throw SQLException
	 * @return Nothing.
	 * */
	@Override
	public void update(String name, String familyName, String newName, String newFamilyName) throws SQLException {

		Connection conn = DriverManager.getConnection(URL);
		
		String query = "UPDATE students SET name = ?, family_name = ? WHERE name = ? AND family_name = ?";
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
	 * This method delete data from students table.
	 * 
	 * @see CRUDPerson#delete(String, String)
	 * 
	 * @param name Name of student.
	 * @param familyName Family name of student.
	 * @throw SQLException
	 * @return Nothing
	 * */
	@Override
	public void delete(String name, String familyName) throws SQLException {
		
		Connection conn = DriverManager.getConnection(URL);
		
		String query = "DELETE FROM students WHERE name = ? AND family_name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		statement.setString(2, familyName);
		statement.executeUpdate();
		
		statement.close();
		conn.close();
	}

	/**
	 * This method return list of all students.
	 * 
	 * @see CRUDPerson#getAll()
	 * 
	 * @throw SQLException
	 * @return List of persons
	 * */
	@Override
	public List<Person> getAll() throws SQLException {
		
		List<Person> res = new ArrayList<Person>();
		Connection conn = DriverManager.getConnection(URL);
		
		String query = "SELECT name, family_name FROM students";
		PreparedStatement statement = conn.prepareStatement(query);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			String name = rs.getString("name");
			String familyName = rs.getString("family_name");
			
			res.add(new Person(name, familyName));
		}
		
		statement.close();
		conn.close();
		
		return res;
	}

	/**
	 * This method return list of all students who have a specific name.
	 * 
	 * @see CRUDPerson#getByName(String)
	 * 
	 * @param name Name of student for whom there is a search
	 * @throw SQLException
	 * @return List of person who have a specific name
	 * */
	@Override
	public List<Person> getByName(String name) throws SQLException {

		List<Person> res = new ArrayList<Person>();
		Connection conn = DriverManager.getConnection(URL);
		
		String query = "SELECT name, family_name FROM students WHERE name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			name = rs.getString("name");
			String familyName = rs.getString("family_name");
			
			res.add(new Person(name, familyName));
		}
		
		statement.close();
		conn.close();
		
		return res;
	}

	/**
	 * This method return list of all students who have a specific family name.
	 * 
	 * @see CRUDPerson#getByFamilyName(String)
	 * 
	 * @param familyName Family name of student for whom there is a search
	 * @throw SQLException
	 * @return List of person who have a specific family name
	 * */
	@Override
	public List<Person> getByFamilyName(String familyName) throws SQLException {

		List<Person> res = new ArrayList<Person>();
		Connection conn = DriverManager.getConnection(URL);
		
		String query = "SELECT name, family_name FROM students WHERE family_name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, familyName);
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()){
			String name = rs.getString("name");
			familyName = rs.getString("family_name");
			
			res.add(new Person(name, familyName));
		}
		
		statement.close();
		conn.close();
		
		return res;
	}
}