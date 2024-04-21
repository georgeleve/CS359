package RestApi.RequestHandlers;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import RestApi.Main;
import RestApi.External.database.tables.EditBloodTestTable;

@Path("bloodTest")
public class BloodTestMeasureUpdate {
	
    @PUT
    @Path("/{bloodTestID}/{measure}/{value}")
    @Produces({MediaType.APPLICATION_JSON})
    public Response updateBloodTestMeasure(@PathParam("bloodTestID") int bloodTestID, @PathParam("measure") String measure, @PathParam("value") int value) {
		EditBloodTestTable bt = new EditBloodTestTable();
		if(!bt.bloodTestExists(bloodTestID)) return Main.errorResponse403("Could not find the given blood test id!");
		if(!(measure.equalsIgnoreCase("vitamin_d3") || measure.equalsIgnoreCase("vitamin_b12") || measure.equalsIgnoreCase("cholesterol") ||measure.equalsIgnoreCase("blood_sugar") || measure.equalsIgnoreCase("iron"))) return Main.errorResponse406("Invalid measure.");
		if(value<=0) return Main.errorResponse406("Value must be positive!");
		
		try {
			if(measure.equalsIgnoreCase("vitamin_d3")) bt.updateBloodTestD3(bloodTestID, value);
			if(measure.equalsIgnoreCase("vitamin_b12")) bt.updateBloodTestB12(bloodTestID, value);
			if(measure.equalsIgnoreCase("cholesterol")) bt.updateBloodTestCholesterol(bloodTestID, value);
			if(measure.equalsIgnoreCase("blood_sugar")) bt.updateBloodTestBloodSugar(bloodTestID, value);
			if(measure.equalsIgnoreCase("iron")) bt.updateBloodTestIron(bloodTestID, value);
		}catch (Exception e) {
			e.printStackTrace();
			return Main.errorResponseConflict("Database error!");
		}
		return Main.successResponse("Value succesfully updated!");
    }
}
