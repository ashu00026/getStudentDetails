package com.web.jdbc;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
//import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

/**
 * Servlet implementation class studenControllerServlet
 */
@WebServlet("/studenControllerServlet")
public class studenControllerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    private StudentDbUtil studentDbUtil;
    @Resource(name="jdbc/web_student_tracker")//this is the resource we are linking to our servlet for db connection.!!
	private DataSource datasource;//generates the connection-pool as per the resource given in the address

    public void init() throws ServletException{
        super.init();
        try {
        	 studentDbUtil= new StudentDbUtil(datasource);
        	
        }catch(Exception e) {
        	throw new ServletException(e);
        }
    }
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//read the command from the parameters 
			String theCommand=request.getParameter("command");
			//if the command is missing then default to list parameters
			if(theCommand==null) {
				theCommand="LIST";
			}
			//route to appropriate method

			switch (theCommand) {
			case "LIST":
				listStudents(request,response);				
				break;
				
			case "ADD":
				addStudent(request,response);
				break;
			case "LOAD":
				loadStudent(request,response);
				break;
			case "UPDATE":
				updateStudent(request,response);
				break;
			case "DELETE":
				deleteStudent(request,response);
				break;
			default:
				listStudents(request,response);
				break;
			}
			
			
		}catch(Exception e) {
			throw new ServletException(e);
		}
	}
	private void deleteStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read the student id from the form data
		int id=Integer.parseInt(request.getParameter("studentId"));
		
		//delete Student from the database
		studentDbUtil.deleteStudent(id);
		
		//send them back to he "list Students" page
		listStudents(request,response);
		
	}
	private void updateStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//read the student info from the form data
		int id=Integer.parseInt(request.getParameter("studentId"));
		String firstName= request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
		
		//create a new Student OBject
		Student theStudent= new Student(id,firstName,lastName,email);
		
		//perform update on database
		studentDbUtil.updateStudent(theStudent);
		
		//send them back to the "list Students" page
		listStudents(request,response);
		
	}
	private void loadStudent(HttpServletRequest request, HttpServletResponse response) throws Exception  {
		//read student id form data
		String theStudentId= request.getParameter("studentId");
		//get student from database (db util)
		Student theStudent=studentDbUtil.getStudent(theStudentId);
		//place Student in the request attribute
		request.setAttribute("the_student", theStudent);
	
		//send to jsp page: update-student-form.jsp
		RequestDispatcher dispatcher=request.getRequestDispatcher("/update-student-form.jsp");
		dispatcher.forward(request,response);
		
	}
	private void addStudent(HttpServletRequest request, HttpServletResponse response) throws Exception {
	//read the student info form data
		String firstName=request.getParameter("firstName");
		String lastName=request.getParameter("lastName");
		String email=request.getParameter("email");
	//create a new student object
		Student newStudent=new Student(firstName,lastName,email);
	//add student to the database
		studentDbUtil.addStudent(newStudent);
		
	//send back to main page(the student list)	
	listStudents(request,response);
	}
	
	
	
	
	
	
	
	
	
	private void listStudents(HttpServletRequest request,HttpServletResponse response)throws Exception {
		//get student from the dbutil
		List<Student>students=studentDbUtil.getStudents();
		//add students to the request object
		request.setAttribute("students_list", students);
		//send the req and response results to the jsp page via requestDispatcher
		RequestDispatcher dispatcher= request.getRequestDispatcher("/StudentsCrud.jsp");
		dispatcher.forward(request, response);
	}

}
