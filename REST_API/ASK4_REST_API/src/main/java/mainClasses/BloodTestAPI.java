package mainClasses;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;

import database.tables.EditBloodTestTable;
import mainClasses.BloodTest;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

@Path("test")
public class BloodTestAPI {
	// http://localhost:8080/ASK4_REST_API/test/newBloodTest/
	@POST
	@Path("/newBloodTest/")
	@Consumes({MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_JSON})
	public Response addBloodTest(String bloodtest) {
		// Na ftiaxo na exei dothei esto mia metrhsh
		Response.Status OK = Response.Status.OK;
		Response.Status NOT_ACCEPTABLE = Response.Status.NOT_ACCEPTABLE;
		Response.Status BAD_REQUEST = Response.Status.BAD_REQUEST;
		
		System.out.println(bloodtest);

		EditBloodTestTable bloodtesttable = new EditBloodTestTable();
		BloodTest mybloodtest = bloodtesttable.jsonToBloodTest(bloodtest);

		// Na exei mesa to JSON toulaxiston AMKA, hmeromhnia kai onoma
		if (mybloodtest.getAmka().isEmpty()) {
			String json1 = new Gson().toJson("(POST) Error: must contain amka");
			return Response.status(NOT_ACCEPTABLE).type("application/json").entity(json1).build();
		}

		if (mybloodtest.getTest_date().isEmpty()) {
			String json1 = new Gson().toJson("(POST) Error: must contain test_date");
			return Response.status(NOT_ACCEPTABLE).type("application/json").entity(json1).build();
		}
		
		if (mybloodtest.getMedical_center().isEmpty()) {
			String json1 = new Gson().toJson("(POST) Error: must contain medical_center");
			return Response.status(NOT_ACCEPTABLE).type("application/json").entity(json1).build();
		}
		
		/*
		if (!bloodtest.contains("bloodsugar") && !bloodtest.contains("cholesterol") && !bloodtest.contains("iron")
				&& !bloodtest.contains("vitamin_d3") && !bloodtest.contains("vitamin_b12")) {
			String json3 = new Gson().toJson("(POST) Error: Prepei na dothei esto mia metrhsh");
			return Response.status(NOT_ACCEPTABLE).type("application/json").entity(json3).build();
		}*/
		
		// Oles oi metrhseis pou dothikan na exoun timh > 0
		if (mybloodtest.getBlood_sugar() <= 0 || mybloodtest.getCholesterol() <= 0 || mybloodtest.getIron() <= 0
				|| mybloodtest.getVitamin_d3() <= 0 || mybloodtest.getVitamin_b12() <= 0) {
			String json1 = new Gson().toJson("(POST) Error: Oi metrhseis prepei na einai megalyteres tou 0");
			return Response.status(NOT_ACCEPTABLE).type("application/json").entity(json1).build();
		}

		// H hmeromhnia na mhn einai mellontikh
		String dateFormat = "yyyy-MM-dd";
		String date = mybloodtest.getTest_date();
		LocalDate localDate = LocalDate.now(ZoneId.systemDefault());
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(dateFormat);
		LocalDate inputDate = LocalDate.parse(date, dtf);

		if (inputDate.isAfter(localDate)) {
			String json4 = new Gson().toJson("(POST) Error: H hmeromhnia den prepei na einai mellontikh");
			return Response.status(NOT_ACCEPTABLE).type("application/json").entity(json4).build();
		}

		try {
			bloodtesttable.addBloodTestFromJSON(bloodtest);
			System.out.println("bloodtest added");
			String json5 = new Gson().toJson("\"success\": \"Bloodtest added\"");
			return Response.status(OK).type("application/json").entity(json5).build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			String json6 = new Gson().toJson("Error: (POST)");
			System.out.println("Error: bloodtest not added");
			return Response.status(BAD_REQUEST).type("application/json").entity(json6).build();
		}
	}

	// http://localhost:8080/ASK4_REST_API/test/bloodTests/03069200000?fromDate=2021-01-01&toDate=2021-11-11
	@GET
	@Path("/bloodTests/{AMKA}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBloodTest(@PathParam("AMKA") String AMKA, @QueryParam("date") String date) {
		Response.Status OK = Response.Status.OK;
		Response.Status BAD_REQUEST = Response.Status.BAD_REQUEST;
		EditBloodTestTable bloodtesttable = new EditBloodTestTable();

		try {
			if(bloodtesttable.amkaExists(AMKA)==false) {
				String json = new Gson().toJson("Amka do not exist in the database");
				return Response.status(Response.Status.NOT_ACCEPTABLE).type("application/json").entity(json).build();
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		
		// H hmeromhnia na mhn einai mellontikh
		// get them from String date ......
//		String fromDate1 = "";
//		String toDate1 = "";
//		if (fromDate1 != "" && toDate1 != "") {
//			SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd");
//			Date fromDate = null, toDate = null;
//			try {
//				fromDate = (Date) sdformat.parse(fromDate1);
//				toDate = (Date) sdformat.parse(toDate1);
//			} catch (ParseException e) {
//				e.printStackTrace();
//			}
//			// System.out.println("The date 1 is: " + sdformat.format(d1));
//			// System.out.println("The date 2 is: " + sdformat.format(d2));
//
//			if (fromDate.compareTo(toDate) > 0) {
//				System.out.println("fromDate occurs after toDate\n");
//				String json1 = new Gson().toJson("(GET) Error: fromDate occurs after toDate");
//				return Response.status(BadStatus).type("application/json").entity(json1).build();
//			}
//		}
		ArrayList<String> json;
		try {
			json = bloodtesttable.databaseToBloodTest1(AMKA);
			return Response.status(OK).type("application/json").entity(new Gson().toJson(json)).build();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// http://localhost:8080/ASK4_REST_API/test/bloodTestMeasure/03069200000/cholesterol
	@GET
	@Path("/bloodTestMeasure/{AMKA}/{Measure}")
	@Produces({MediaType.APPLICATION_JSON})
	public Response getBloodTestMeasure(@PathParam("AMKA") String AMKA, @PathParam("Measure") String Measure) {
		Response.Status OK = Response.Status.OK;
		Response.Status NOT_ACCEPTABLE = Response.Status.NOT_ACCEPTABLE;
		EditBloodTestTable bloodtesttable = new EditBloodTestTable();
		
		try {
			if(bloodtesttable.amkaExists(AMKA)==false) {
				String json = new Gson().toJson("Amka doesn't exists in the database");
				return Response.status(NOT_ACCEPTABLE).type("application/json").entity(json).build();
			}
		} catch (ClassNotFoundException | SQLException e1) {
			e1.printStackTrace();
		}
		ArrayList<String> json;
		try {
			json = bloodtesttable.databaseToBloodTest(AMKA, Measure);
			return Response.status(OK).type("application/json").entity(new Gson().toJson(json)).build();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	// http://localhost:8080/ASK4_REST_API/test/bloodTest/5/blood_sugar/69
	@PUT
	@Path("/bloodTest/{bloodTestID}/{measure}/{value}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response updateBloodTest(@PathParam("bloodTestID") int bloodTestID, @PathParam("measure") String measure,
			@PathParam("value") double value) {
		Response.Status OK = Response.Status.OK;
		Response.Status BAD_REQUEST = Response.Status.BAD_REQUEST;        
		Response.Status NOT_ACCEPTABLE = Response.Status.NOT_ACCEPTABLE; //arnhtikes times
		Response.Status FORBIDDEN = Response.Status.FORBIDDEN; 			// otan einai lathos kapoio ID
		

		if (!measure.equals("blood_sugar") && !measure.equals("cholesterol") && !measure.equals("iron")
				&& !measure.equals("vitamin_d3") && !measure.equals("vitamin_b12")) {
			String json = new Gson().toJson("(PUT) measure must be blood_sugar, cholesterol, iron, vitamin_d3 or vitamin_b12");
			return Response.status(NOT_ACCEPTABLE).type("application/json").entity(json).build();
		}

		if (value <= 0) {
			String json = new Gson().toJson("(PUT) Value must be greater than zero");
			return Response.status(NOT_ACCEPTABLE).type("application/json").entity(json).build();
		}

		EditBloodTestTable bloodtesttable = new EditBloodTestTable();
		try {
			int returnValue = bloodtesttable.updateBloodTest(bloodTestID, measure, value);
			if(returnValue==0) {
				String jsonn = new Gson().toJson("(PUT) BloodtestID does not exist");
				return Response.status(FORBIDDEN).type("application/json").entity(jsonn).build();
			}
			String jsonn = new Gson().toJson("(PUT) 4. Ananeosh Plhroforion Exetashs");
			return Response.status(OK).type("application/json").entity(jsonn).build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			String json = new Gson().toJson("(PUT) Error on try catch block");
			return Response.status(BAD_REQUEST).type("application/json").entity(json).build();
		} catch (SQLException e) {
			e.printStackTrace();
			String json = new Gson().toJson("(PUT) Error on try catch block");
			return Response.status(BAD_REQUEST).type("application/json").entity(json).build();
		}
	}

	// http://localhost:8080/ASK4_REST_API/test/bloodTestDeletion/1
	@DELETE
	@Path("/bloodTestDeletion/{bloodTestID}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteBloodTest(@PathParam("bloodTestID") int bloodTestID) {
		Response.Status BadStatus = Response.Status.BAD_REQUEST;
		Response.Status FORBIDDEN = Response.Status.FORBIDDEN; 			// otan einai lathos kapoio ID
		Response.Status OK = Response.Status.OK;

		String json = new Gson().toJson("success : BloodTest Deleted");
		EditBloodTestTable bloodtesttable = new EditBloodTestTable();
		try {
			int returnValue = bloodtesttable.deleteBloodTest(bloodTestID);
			if(returnValue==0) {
				String error_message = new Gson().toJson("(PUT) Error, can not find this BloodTestID");
				return Response.status(FORBIDDEN).type("application/json").entity(error_message).build();
			}
			return Response.status(OK).type("application/json").entity(json).build();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_ACCEPTABLE).type("application/json").entity(json).build();
		} catch (SQLException e) {
			e.printStackTrace();
			return Response.status(Response.Status.NOT_ACCEPTABLE).type("application/json").entity(json).build();
		}
	}
}