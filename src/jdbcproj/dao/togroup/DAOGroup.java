package jdbcproj.dao.togroup;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;

import jdbcproj.data.group.Group;
import jdbcproj.data.person.Student;

import static jdbcproj.resources.Resources.getProperty;


/**
 * This class implements CRUD operation for groups table in database.
 * 
 * @author Lebedev Alexander
 * @since 2016-09-07
 * */
public class DAOGroup {
	
	static{
		try{
			Class.forName(getProperty("DRIVER_CLASS"));
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
	public void add(Group group) throws SQLException{

		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "INSERT INTO groups (name) VALUES ('" + group.getName() + "')";
		Statement statement = conn.createStatement();
		statement.executeUpdate(query);
		
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
		
		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
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

		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "DELETE FROM groups WHERE name = ?";
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		statement.executeUpdate();
		
		statement.close();
		conn.close();
	}
	

	/**
	 * Return group which have specific name
	 * 
	 * @param name Name of group
	 * @return Group
	 * */
	public Group getByName(String name) throws SQLException{
		
		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "SELECT students.name, students.family_name, tmp.name "
						+ "FROM students INNER JOIN (SELECT groups.name, student_in_group.id_student "
						+ "FROM groups INNER JOIN student_in_group "
						+ "WHERE groups.name = ? AND groups.id = student_in_group.id_group) AS tmp "
						+ "WHERE students.id = tmp.id_student";
		
		PreparedStatement statement = conn.prepareStatement(query);
		statement.setString(1, name);
		ResultSet rs = statement.executeQuery();
		
		String groupName = "";
		List<Student> students = new ArrayList<Student>();
		
		while(rs.next()){
			groupName = rs.getString("tmp.name");
			
			String studentName = rs.getString("students.name");
			String studentFamilyName = rs.getString("students.family_name");
			
			students.add(new Student(studentName, studentFamilyName, groupName));
		}
		
		Group res = new Group(groupName, students);
		
		rs.close();
		statement.close();
		conn.close();
		
		return res;
	}
	
	/**
	 * This method return list of all existing groups.
	 * 
	 * @throw SQLException
	 * @return List of name (String) of all groups
	 * */
	public List<Group> getAll() throws SQLException{
		
		Connection conn = DriverManager.getConnection(getProperty("URL"));
		
		String query = "SELECT name FROM groups";
		Statement statement = conn.createStatement();
		ResultSet rs = statement.executeQuery(query);
		
		List<Group> res = new ArrayList<Group>();
		
		while(rs.next()){
			Group newGroup = getByName(rs.getString("name"));
			res.add(newGroup);
		}
		
		rs.close();
		statement.close();
		conn.close();
		
		return res;
	}
}