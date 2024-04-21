package RestApi.RequestHandlers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import RestApi.Main;
import RestApi.External.database.tables.EditBloodTestTable;
import RestApi.External.mainClasses.BloodTest;

@Path("newBloodTest")
public class BloodTestSubmit {
	@POST
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public Response addBloodTest(String input) {
		Gson gson = new Gson();
		BloodTest test = gson.fromJson(input, BloodTest.class);
		if(test.getAmka() == null) return Main.errorResponseBadRequest("Input doesn't contain AMKA.");
		if(test.getTest_date() == null) return Main.errorResponseBadRequest("Input doesn't contain Date.");
		if(test.getMedical_center() == null) return Main.errorResponseBadRequest("Input doesn't contain medical center name.");
		LocalDate ld;
		try {
			ld = LocalDate.parse(test.getTest_date() , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}catch (Exception e) {
			return Main.errorResponse406("Invalid date format!");
		}
		if(ld.isAfter(LocalDate.now())) return Main.errorResponse406("Invalid test date (future date).");
		boolean inputD3 = input.contains("vitamin_d3");
		boolean inputB12 = input.contains("vitamin_b12");
		boolean inputChol = input.contains("cholesterol");
		boolean inputBloodSugar = input.contains("blood_sugar");
		boolean inputIron = input.contains("iron");
		boolean atleast1 = inputD3 || inputB12 || inputChol || inputBloodSugar || inputIron;
		if(!atleast1) return Main.errorResponseBadRequest("Input must contain at least one test result.");
		
		if(inputD3) if(test.getVitamin_d3()<=0) return Main.errorResponse406("D3 vitamin must be positive!");
		if(inputB12) if(test.getVitamin_b12()<=0) return Main.errorResponse406("B12 vitamin must be positive!");
		if(inputChol) if(test.getCholesterol()<=0) return Main.errorResponse406("Cholesterol level must be positive!");
		if(inputBloodSugar) if(test.getBlood_sugar()<=0) return Main.errorResponse406("Blood sugar must be positive!");
		if(inputIron) if(test.getIron()<=0) return Main.errorResponse406("Iron level must be positive!");
		try {
			new EditBloodTestTable().createNewBloodTest(test);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return Main.successResponse("Blood test submited!");
	}
}
