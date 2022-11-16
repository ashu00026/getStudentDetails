package com.web.jdbc;

//import java.io.PrintWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//define dataSource/connectionPool for resource injection

	@Resource(name="jdbc/web_student_tracker")
	private DataSource datasource;
//	DataSources.destroy(datasource);
	

       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 1. setting up the printWriter
		PrintWriter out= response.getWriter();
		response.setContentType("text/plain");
//		out.println(datasource);
		
		// 2. get a connection to the database
		Connection myConn=null;
		Statement myStmt=null;
		ResultSet myRs=null;
		
		try {
			myConn=datasource.getConnection();
		// 3. create a sql statement
			String sql="SELECT * FROM student";
			myStmt=myConn.createStatement();
		
		// 4.execute a sql statement
		myRs=myStmt.executeQuery(sql);
		
		// 5.process the result set
		while(myRs.next()) {
			String email=myRs.getString("email");
			out.println(email);
		}
		}catch(Exception e) {
			e.getStackTrace();
		}
		
		
		
		
		
	}

	

}
