package csd4149;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import csd4149.database.EditSimpleUserTable;
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public Login() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if (session.getAttribute("loggedIn") != null) {
			response.setStatus(200);
			JsonObject jo = new JsonObject();
			jo.addProperty("username", (String) session.getAttribute("username"));
			jo.addProperty("password", (String) session.getAttribute("password"));
			response.getWriter().write(jo.toString());
		} else {
			response.setStatus(403);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject ja = new Gson().fromJson(request.getReader().readLine(), JsonObject.class);
		HttpSession session = request.getSession();
		String us = ja.get("username").getAsString();
		String pw = ja.get("password").getAsString();
		EditSimpleUserTable edt = new EditSimpleUserTable();
		JsonObject jo = new JsonObject();
		if (edt.userExists(us, pw)) {
			jo.addProperty("success", "Login successfull.");
			session.setAttribute("loggedIn", "true");
			session.setAttribute("username", us);
			session.setAttribute("password", pw);
			Cookie c = new Cookie("JSESSIONID", session.getId());
			c.setMaxAge(60 * 60 * 24 * 365 * 10);
			response.addCookie(c);
			response.setStatus(200);
		} else {
			jo.addProperty("error", "Invalid credentials!");
			response.setStatus(403);
		}
		response.getWriter().write(jo.toString());
	}

}