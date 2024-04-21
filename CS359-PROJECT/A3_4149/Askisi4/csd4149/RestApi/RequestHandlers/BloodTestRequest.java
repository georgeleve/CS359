package RestApi.RequestHandlers;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import com.google.gson.Gson;
import RestApi.Main;
import RestApi.External.database.tables.EditBloodTestTable;
import RestApi.External.mainClasses.BloodTest;

@Path("bloodTests")
public class BloodTestRequest {

	@GET
	@Path("/{AMKA}")
	@Produces({ MediaType.APPLICATION_JSON })
	public Response getBloodTest(@PathParam("AMKA") String amka, @QueryParam("fromDate") String fromDate, @QueryParam("toDate") String toDate) {
		LocalDate fd = null, td = null;
		try {
			if(fromDate!=null) fd = LocalDate.parse(fromDate , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if(toDate!=null) td = LocalDate.parse(toDate , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
			if(fd!=null && td!=null && fd.isAfter(td)) return Main.errorResponse406("Invalid date segment!");
		}catch (Exception e) {
			return Main.errorResponse406("Invalid date format!");
		}
		EditBloodTestTable bt = new EditBloodTestTable();
		if(!bt.amkaExists(amka)) return Main.errorResponse403("Could not find the given AMKA.");
		ArrayList<BloodTest> ans;
		try {
			ans = bt.databaseToBloodTestRange(amka, fromDate, toDate);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return Main.errorResponseConflict("Database error!");
		}
		String res = new Gson().toJson(ans);
		return Response.status(Response.Status.OK).type("application/json").entity(res).build();
	}
}
