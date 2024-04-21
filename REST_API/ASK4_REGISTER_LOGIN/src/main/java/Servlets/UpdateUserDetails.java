package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.text.html.HTMLDocument.Iterator;

import com.google.gson.JsonObject;

import database.DB_Connection;
import database.tables.EditSimpleUserTable;
import mainClasses.SimpleUser;


/**
 * Servlet implementation class UpdateUserDetails
 */
public class UpdateUserDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateUserDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}
	
	public String getJSONFromAjax(BufferedReader reader) throws IOException {
		StringBuilder buffer = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) {
			buffer.append(line);
		}
		String data = buffer.toString();
		return data;
	}
	
	public Boolean emailExistsInUserTable(String email) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM users WHERE email= '" + email + "'");
			return rs.next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public Boolean emailExistsInDoctorTable(String email) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM doctors WHERE email= '" + email + "'");
			return rs.next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println(mystring);
		System.out.println("hello world");
		String mystring = this.getJSONFromAjax(request.getReader());
		String[] array = mystring.split("&");
		
		String username = array[0];
		String key = array[1]; 
		String value = array[2];
		System.out.println(username + key + value);
		String response_message = "";
		EditSimpleUserTable usertable = new EditSimpleUserTable();
		try {
			if(key.equals("email")) {
				if(emailExistsInUserTable(value) || emailExistsInDoctorTable(value)) {
					System.out.println("Email Exists");
					response_message = "Error: email already exists"; 
					JsonObject jo = new JsonObject();
					jo.addProperty("Error", response_message);
					response.setStatus(403);
					response.getWriter().write(jo.toString());
				}else {
					response_message = "Email Update was successful"; 
					JsonObject jo = new JsonObject();
					jo.addProperty("Success", response_message);
					response.setStatus(200);
					response.getWriter().write(jo.toString());
					usertable.updateUser(username, key, value);
				}
			}else {
				System.out.println("this is NOT an email");
				response_message = "Update was successful"; 
				JsonObject jo = new JsonObject();
				jo.addProperty("Success", response_message);
				response.setStatus(200);
				response.getWriter().write(jo.toString());
				usertable.updateUser(username, key, value);
			}
			System.out.println(response_message);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}	
	}

}
