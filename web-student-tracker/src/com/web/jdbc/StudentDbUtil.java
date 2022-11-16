package com.web.jdbc;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

public class StudentDbUtil {
	private DataSource datasource;
	public StudentDbUtil(DataSource datasource) {
		this.datasource=datasource;
	}
	public void addStudent(Student theStudent)throws Exception {
		
		Connection myConn=null;
		PreparedStatement myStmt=null;
		try {
			//get dbConnection
			myConn=datasource.getConnection();
			//create sql for insert
			String sql="insert into student "+"(first_name, last_name, email) "+"values (?,?,?)";
			
			myStmt=myConn.prepareStatement(sql);
			//set param values for the student
			myStmt.setString(1, theStudent.getFirstName());
			myStmt.setString(2, theStudent.getLastName());
			myStmt.setString(3, theStudent.getEmail());
			//execute the sql querry
			myStmt.execute();
			
		}finally {
			//clean up JDBC objects
			close(myConn,myStmt,null);
		}
		
		
	}
	
	public List<Student> getStudents() throws Exception{
		List<Student>students=new ArrayList<>();
		
		Connection myConn=null;
		Statement myStmt=null;
		ResultSet myRs=null;
		try {
		     myConn=datasource.getConnection();
		     String sql="SELECT * FROM student order by last_name";
		     myStmt=myConn.createStatement();
		     myRs=myStmt.executeQuery(sql);
		     while(myRs.next()) {
		    	 //retrieve data from resultset 
		    	int id = myRs.getInt("id");
		    	String firstName=myRs.getString("first_name");
		    	String lastName=myRs.getString("last_name");
		    	String email=myRs.getString("email");
		    	//create new student object and add it to arrayList.
		    	students.add(new Student(id,firstName,lastName,email));
		     }	
		     return students;
		}
		catch(Exception e) {
			e.getStackTrace();
		}finally {
			//close the JDBC objects..!!
			close(myConn,myStmt,myRs);
		}
		return students;	
	}
	
	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {
		try {
			if(myRs!=null) {
				myRs.close();
			}
			if(myConn!=null) {
				myConn.close();
			}
			if(myRs!=null) {
				myRs.close();
			}
		}catch(Exception ex) {
			ex.getStackTrace();
		}
	}
	public Student getStudent(String theStudentId) throws Exception {
		Student theStudent=null;
		Connection myConn=null;
		PreparedStatement myStmt=null;
		ResultSet myRs=null;
		int studentId;
		try {
			//convert student id to int
			studentId=Integer.parseInt(theStudentId);
			//get connection to database
			myConn=datasource.getConnection();
			//create sql to get selected student
			String sql="Select * from student where id= ?";
			//create prepared statement
			myStmt=myConn.prepareStatement(sql);
			
			//set params
			myStmt.setInt(1,studentId);
			
			//execute the statement
			myRs=myStmt.executeQuery();
			
			//retrieve data from result set row
			if(myRs.next()) {
				String firstName=myRs.getString("first_name");
				String lastName=myRs.getString("last_name");
				String email=myRs.getString("email");
				
				//use the studentId during construction
				theStudent=new Student(studentId,firstName,lastName,email);
			}
			else {
				throw new Exception("could not find the student id:"+studentId);
			}
			
			return theStudent;
		}finally {
             //clean jdbc connections
			close(myConn,myStmt,myRs);
		}
	}
	public void updateStudent(Student theStudent) throws Exception {
		Connection myConn=null;
		PreparedStatement myStmt=null;
//		ResultSet myRs=null;
		try {
		myConn=datasource.getConnection();
		String sql="Update student set first_name=?,last_name=?,email=? where id= ?";
		myStmt=myConn.prepareStatement(sql);
		myStmt.setString(1,theStudent.getFirstName());
		myStmt.setString(2,theStudent.getLastName());
		myStmt.setString(3,theStudent.getEmail());
		myStmt.setInt(4,theStudent.getId());
		myStmt.execute();
	}
		finally {
			close(myConn,myStmt,null);
		}
	}
	public void deleteStudent(int id) throws Exception {
		Connection myConn=null;
		PreparedStatement myStmt=null;
		try {
		myConn=datasource.getConnection();
		String sql="delete from student where id=?";
		myStmt=myConn.prepareStatement(sql);
		myStmt.setInt(1, id);
		myStmt.execute();
		}finally {
			close(myConn,myStmt,null);
		}
	}
}
