package RestApi.RequestHandlers;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import RestApi.Main;
import RestApi.External.database.tables.EditBloodTestTable;

@Path("bloodTestDeletion")
public class BloodTestDelete {
    @DELETE
    @Path("/{bloodTestID}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBloodTest(@PathParam("bloodTestID") int bloodTestID) {
		EditBloodTestTable bt = new EditBloodTestTable();
		if(!bt.bloodTestExists(bloodTestID)) return Main.errorResponse403("Could not find the given blood test id!");
    	try {
			bt.deleteBloodTest(bloodTestID);
		} catch (Exception e) {
			e.printStackTrace();
			return Main.errorResponseConflict("Database error!");
		}
		return Main.successResponse("Blood test deleted!");
    }
}
