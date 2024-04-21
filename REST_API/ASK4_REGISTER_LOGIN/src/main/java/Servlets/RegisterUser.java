package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import database.DB_Connection;
import database.tables.EditDoctorTable;
import database.tables.EditSimpleUserTable;
import mainClasses.Doctor;
import mainClasses.JSONConverter;
import mainClasses.User;
import mainClasses.SimpleUser;

/**
 * Servlet implementation class RegisterUser
 */
public class RegisterUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterUser() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

	public Boolean usernameExistsInUserTable(String username) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM users WHERE username= '" + username + "'");
			return rs.next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public Boolean usernameExistsInDoctorTable(String username) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM doctors WHERE username= '" + username + "'");
			return rs.next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
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

	public Boolean amkaExistsInUserTable(String amka) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM users WHERE amka= '" + amka + "'");
			return rs.next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public Boolean amkaExistsInDoctorTable(String amka) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM doctors WHERE amka= '" + amka + "'");
			return rs.next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("Got inside doPost() on register user servlet");
		//JSONConverter jc = new JSONConverter();
		EditSimpleUserTable userTable = new EditSimpleUserTable();
		EditDoctorTable doctorTable = new EditDoctorTable();
		String user = this.getJSONFromAjax(request.getReader());
		System.out.println("The user is " + user);
		String username, email, amka;

		if (user.contains("simple_user")) {
			SimpleUser simpleuser = userTable.jsonToSimpleUser(user);
			//System.out.println(simpleuser.getUserType());
			username = simpleuser.getUsername();
			email = simpleuser.getEmail();
			amka = simpleuser.getAmka();
		} else {
			Doctor doctor = doctorTable.jsonToDoctor(user);
			//System.out.println(doctor.getUserType());
			username = doctor.getUsername();
			email = doctor.getEmail();
			amka = doctor.getAmka();
		}
		
		String message = "";
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		//System.out.println(username + " " + email + " " + amka);

		try {
			if (usernameExistsInUserTable(username) || usernameExistsInDoctorTable(username))
				message += "Username already exists\n";
			if (emailExistsInUserTable(email) || emailExistsInDoctorTable(email))
				message += "Email already exists\n";
			if (amkaExistsInUserTable(amka) || amkaExistsInDoctorTable(amka))
				message += "Amka already exists\n";
			System.out.println(message);
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
			System.out.println("Exception while trying to find if username, or amka or email exists.");
		}

		if (!message.equals("")) { // username, amka or email already exists
			System.out.println(message);
			JsonObject jo = new JsonObject();
			jo.addProperty("error", message);
			response.setStatus(403);
			response.getWriter().write(jo.toString());
		} else {
			try {
				String response_message = "";
				if (user.contains("simple_user")) {
					userTable.addSimpleUserFromJSON(user);
					response_message = "H eggrafh sas oloklhrothike epitixos";
				} else { // Here I need to add doctor
					doctorTable.addDoctorFromJSON(user);
					response_message = "H eggrafh sas pragmatopoihthike alla tha prepei na sas pistopoihsei o diaxeirhsths";
				}
				JsonObject jo = new JsonObject();
				jo.addProperty(response_message, user);
				response.setStatus(200);
				response.getWriter().write(jo.toString());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				System.out.println("Error while trying to add user");
			}
		}
	}
}