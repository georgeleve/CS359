package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import database.DB_Connection;
import mainClasses.SimpleUser;

/**
 * Servlet implementation class Login
 */
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

	public SimpleUser findUserPassword(String username) throws ClassNotFoundException, SQLException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * from users WHERE username= '" + username + "'");
			rs.next();
			String json = DB_Connection.getResultsToJSON(rs);

			Gson gson = new Gson();
			SimpleUser user = gson.fromJson(json, SimpleUser.class);

			return user;
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public String databaseUserToJSON(String username) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();

		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT password FROM users WHERE username = '" + username + "'");
			rs.next();
			String json = DB_Connection.getResultsToJSON(rs);
			System.out.println(json);
			return json;
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return null;
	}

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		HttpSession session = request.getSession();
		if (session.getAttribute("loggedIn") != null) {
			response.setStatus(200);
			//Person p = Resources.registeredUsers.get(session.getAttribute("loggedIn").toString());
			//response.getWriter().write(p.getUsername());
		} else {
			response.setStatus(403);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("go inside doPost at login.java");

		String mystring = this.getJSONFromAjax(request.getReader());
		String[] array = mystring.split("&");
		String username = array[0];
		String password = array[1];
		String response_message = "";
		//System.out.println("username is" + username + "and password is" + password);
		HttpSession session = request.getSession();
		String correctPassword = "";
		String correctPassword1 = "";

		try {
			if (usernameExistsInUserTable(username)) {
				correctPassword = databaseUserToJSON(username);
				String[] mytemp = correctPassword.split(":");
				String temp1 = mytemp[1];
				String[] curPassword = temp1.split("}");
				correctPassword = curPassword[0];
				correctPassword1 = correctPassword.replace("\"", "");
				System.out.println("User password=" + correctPassword);
			} else {
				System.out.println("This username does not exist");
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}

		// System.out.println(password);
		// System.out.println(correctPassword1);
		if (password.equals(correctPassword1)) {
			session.setAttribute("loggedIn", username);
			System.out.println("correct Password");
			int activeUsers = 0;
			if (request.getServletContext().getAttribute("activeUsers") != null)
				activeUsers = (int) request.getServletContext().getAttribute("activeUsers");
			request.getServletContext().setAttribute("activeUsers", activeUsers + 1);
			response.setStatus(200);
		} else {
			System.out.println("Password is not correct");
			response.setStatus(403);
		}
	}
}
