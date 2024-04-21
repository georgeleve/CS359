package csd4149;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import csd4149.database.EditDoctorTable;
import csd4149.database.EditSimpleUserTable;
import csd4149.mainClasses.Doctor;
import csd4149.mainClasses.JSON_Converter;
import csd4149.mainClasses.SimpleUser;
import csd4149.mainClasses.User;

/**
 * Servlet implementation class Register
 */
public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
    public Register() {
        super();
    }
    public static String getVal(EditSimpleUserTable eut, EditDoctorTable edt, User user) {
		String val = null;
		if(eut.amkaExists(user.getAmka())) val = "AMKA";
		if(eut.emailExists(user.getEmail())) val = "Email";
		if(eut.usernameExists(user.getUsername())) val = "Username";
		if(edt.amkaExists(user.getAmka())) val = "AMKA";
		if(edt.emailExists(user.getEmail())) val = "Email";
		if(edt.usernameExists(user.getUsername())) val = "Username";
		return val;
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String json = JSON_Converter.getJSONFromAjax(request.getReader());
		boolean isDoc = json.contains("\"userType\":\"Doctor\"");
		EditSimpleUserTable eut = new EditSimpleUserTable();
		EditDoctorTable edt = new EditDoctorTable();
		try {
			if(isDoc) {
				Doctor doc = JSON_Converter.jsonToDoc(json); 
				String val = getVal(eut, edt, doc);
				if(val == null) {
					edt.addNewDoctor(doc);
					response.setStatus(200);
					JsonObject jo = new JsonObject();
		            jo.addProperty("success", "Your registration was successfull, it will be verified by an administrator.");
					response.getWriter().write(jo.toString());
				}
				else {
					response.setStatus(403);           
					JsonObject jo = new JsonObject();
		            jo.addProperty("error", val+" already taken!");
					response.getWriter().write(jo.toString());
				}
			}else {
				SimpleUser su = JSON_Converter.jsonToUser(json); 
				String val = getVal(eut, edt, su);
				if(val == null) {
					eut.addNewSimpleUser(su);
					response.setStatus(200);
					JsonObject jo = new JsonObject();
					jo.addProperty("success", "Your registration was successfull.");
					response.getWriter().write(jo.toString());
				}
				else {
					response.setStatus(403);           
					JsonObject jo = new JsonObject();
		            jo.addProperty("error", val+" already taken!");
					response.getWriter().write(jo.toString());
				}
			}
		} catch (Exception e) {
			response.setStatus(403);           
			JsonObject jo = new JsonObject();
            jo.addProperty("error", "Database error!");
			response.getWriter().write(jo.toString());
		}
	}
}
