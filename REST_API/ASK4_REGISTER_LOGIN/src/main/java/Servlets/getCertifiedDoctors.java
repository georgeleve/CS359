package Servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;

import database.tables.EditDoctorTable;
import mainClasses.Doctor;

/**
 * Servlet implementation class getCertifiedDoctors
 */
public class getCertifiedDoctors extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getCertifiedDoctors() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		EditDoctorTable table = new EditDoctorTable();
		ArrayList<String> json;
		System.out.println("got into doGet of get Certified Doctors");
		try {
			json = table.cetrifiedDoctorsToJSON();
			//System.out.prinln(json);
		//	JsonObject jo = new JsonObject();
	//		jo.addProperty("success", json.toString());
			response.setStatus(200);
			response.getWriter().write(json.toString());
			//ArrayList <String> certifiedDoctors = printDoctorDetails();
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
