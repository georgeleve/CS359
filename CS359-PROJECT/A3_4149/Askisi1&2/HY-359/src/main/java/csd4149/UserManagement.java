package csd4149;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import csd4149.database.EditDoctorTable;
import csd4149.database.EditSimpleUserTable;
import csd4149.mainClasses.Doctor;
import csd4149.mainClasses.SimpleUser;

public class UserManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public UserManagement() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		String v = request.getReader().readLine();
		JsonObject ja = new Gson().fromJson(v, JsonObject.class);

		JsonObject jo = new JsonObject();
		if (ja == null) {
			response.setStatus(403);
			jo.addProperty("error", "Could not validate the user data!");
			response.getWriter().write(jo.toString());
			return;
		}
		
		
		if(ja.has("GET_USER_INFORMATION")) {
			ja = ja.getAsJsonObject("GET_USER_INFORMATION");
			if (!(ja.has("username") && ja.has("password"))) {
				response.setStatus(403);
				jo.addProperty("error", "Invalid request!");
				response.getWriter().write(jo.toString());
				return;
			}
			String username = ja.get("username").getAsString();
			String password = ja.get("password").getAsString();
			EditSimpleUserTable eut = new EditSimpleUserTable();
			if (!eut.userExists(username, password)) {
				response.setStatus(403);
				jo.addProperty("error", "Could not validate the user credentials!");
				response.getWriter().write(jo.toString());
				return;
			}
			try {
				SimpleUser user = eut.databaseToSimpleUser(username);
				response.setStatus(200);
				jo.addProperty("success", new Gson().toJson(user, SimpleUser.class));
				response.getWriter().write(jo.toString());
			} catch (ClassNotFoundException | SQLException e) {
				response.setStatus(403);
				jo.addProperty("error", "Database error!");
				response.getWriter().write(jo.toString());
			}
			return;
		}
		
		if(ja.has("GET_DOCTORS")) {
			ja = ja.getAsJsonObject("GET_DOCTORS");
			try {
				EditDoctorTable edt = new EditDoctorTable();
				ArrayList<Doctor> docs = edt.databaseToCertifiedDoctors();
				response.setStatus(200);
				jo.addProperty("success", new Gson().toJson(docs));
				response.getWriter().write(jo.toString());
				
			}catch (Exception e) {
				response.setStatus(403);
				jo.addProperty("error", "Database error!");
				response.getWriter().write(jo.toString());
			}
			return;
		}
		
		response.setStatus(403);
		jo.addProperty("error", "Invalid request!");
		response.getWriter().write(jo.toString());
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JsonObject ja = new Gson().fromJson(request.getReader().readLine(), JsonObject.class);
		String[] field = { "email", "password", "firstname", "lastname", "gender", "country", "city", "address",
				"telephone", "height", "weight", "blooddonor", "bloodtype" };
		boolean valid = false;
		for (String x : field)
			valid |= ja.has(x);
		valid &= ja.has("username");
		JsonObject jo = new JsonObject();
		if (!valid) {
			response.setStatus(403);
			jo.addProperty("error", "Invalid field!");
			response.getWriter().write(jo.toString());
			return;
		}
		String username = ja.get("username").getAsString();
		EditSimpleUserTable eut = new EditSimpleUserTable();
		EditDoctorTable edt = new EditDoctorTable();
		if (ja.has("email")) {
			String mail = ja.get("email").getAsString();
			Pattern pattern = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
			if (!pattern.matcher(mail).matches()) {
				response.setStatus(403);
				jo.addProperty("error", "Invalid email!");
				response.getWriter().write(jo.toString());
				return;
			}
			if (eut.emailExists(mail) || edt.emailExists(mail)) {
				response.setStatus(403);
				jo.addProperty("error", "Email is already registered!");
				response.getWriter().write(jo.toString());
				return;
			}
		}
		if (ja.has("height")) {
			String height = ja.get("height").getAsString();
			try {
				int v = Integer.parseInt(height);
				if (!(100 <= v && v <= 250)) {
					response.setStatus(403);
					jo.addProperty("error", "Height must be between 100-250!");
					response.getWriter().write(jo.toString());
					return;
				}
			} catch (Exception e) {
				response.setStatus(403);
				jo.addProperty("error", "Height must be an integer!");
				response.getWriter().write(jo.toString());
				return;
			}
		}

		if (ja.has("weight")) {
			String height = ja.get("weight").getAsString();
			try {
				int v = Integer.parseInt(height);
				if (!(20 <= v && v <= 300)) {
					response.setStatus(403);
					jo.addProperty("error", "Weight must be between 20-300!");
					response.getWriter().write(jo.toString());
					return;
				}
			} catch (Exception e) {
				response.setStatus(403);
				jo.addProperty("error", "Weight must be an integer!");
				response.getWriter().write(jo.toString());
				return;
			}
		}
		if (ja.has("telephone")) {
			String phone = ja.get("telephone").getAsString();
			Pattern pattern = Pattern.compile("^[0-9]{0,14}$");
			if (!pattern.matcher(phone).matches()) {
				response.setStatus(403);
				jo.addProperty("error", "Invalid phone!");
				response.getWriter().write(jo.toString());
				return;
			}
		}
		if (ja.has("blooddonor")) {
			String v = ja.get("blooddonor").getAsString();
			if (!(v.equals("1") || v.equals("0"))) {
				response.setStatus(403);
				jo.addProperty("error", "Blood donor value must be either 0 or 1!");
				response.getWriter().write(jo.toString());
				return;
			}
		}
		String f = "";
		for (String x : field)
			if (ja.has(x))
				f = x;
		try {
			if (f.equals("height") || f.equals("weight")) {
				int v = Integer.parseInt(ja.get(f).getAsString());
				eut.updateSimpleUserValue(username, f, v);
			} else {
				String v = ja.get(f).getAsString();
				eut.updateSimpleUserValue(username, f, v);
			}
		} catch (Exception ex) {
			response.setStatus(403);
			jo.addProperty("error", "Database error!");
			response.getWriter().write(jo.toString());
			return;
		}
		response.setStatus(200);
		jo.addProperty("success", "Updated!");
		response.getWriter().write(jo.toString());
	}
}
