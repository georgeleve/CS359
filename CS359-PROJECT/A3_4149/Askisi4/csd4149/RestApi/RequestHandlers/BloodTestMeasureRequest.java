package RestApi.RequestHandlers;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import RestApi.Main;
import RestApi.External.database.tables.EditBloodTestTable;
import RestApi.External.mainClasses.BloodTest;

@Path("bloodTestMeasure")
public class BloodTestMeasureRequest {

	@GET
	@Path("/{AMKA}/{Measure}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBloodTestMeasure(@PathParam("AMKA") String amka, @PathParam("Measure") String measure) {
		EditBloodTestTable bt = new EditBloodTestTable();
		if(!bt.amkaExists(amka)) return Main.errorResponse403("Could not find the given AMKA.");
		if(!(measure.equalsIgnoreCase("vitamin_d3") || measure.equalsIgnoreCase("vitamin_b12") || measure.equalsIgnoreCase("cholesterol") ||measure.equalsIgnoreCase("blood_sugar") || measure.equalsIgnoreCase("iron"))) return Main.errorResponse406("Invalid measure.");
		
		ArrayList<BloodTest> bts = null;
		try {
			bts = bt.databaseToBloodTestRange(amka, null, null);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return Main.errorResponseConflict("Database error!");
		}
		ArrayList<Double> ans = new ArrayList<Double>();
		for(BloodTest x : bts) {
			if(measure.equalsIgnoreCase("vitamin_d3")) ans.add(x.getVitamin_d3());
			if(measure.equalsIgnoreCase("vitamin_b12")) ans.add(x.getVitamin_b12());
			if(measure.equalsIgnoreCase("cholesterol")) ans.add(x.getCholesterol());
			if(measure.equalsIgnoreCase("blood_sugar")) ans.add(x.getBlood_sugar());
			if(measure.equalsIgnoreCase("iron")) ans.add(x.getIron());
		}
		String res = new Gson().toJson(ans);
		return Response.status(Response.Status.OK).type("application/json").entity(res).build();
	}
}