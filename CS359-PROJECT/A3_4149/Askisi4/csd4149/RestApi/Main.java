package RestApi;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import RestApi.RequestHandlers.BloodTestDelete;
import RestApi.RequestHandlers.BloodTestMeasureRequest;
import RestApi.RequestHandlers.BloodTestMeasureUpdate;
import RestApi.RequestHandlers.BloodTestRequest;
import RestApi.RequestHandlers.BloodTestSubmit;

@javax.ws.rs.ApplicationPath("")
public class Main extends Application {
	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> resources = new java.util.HashSet<>();
		addRestResourceClasses(resources);
		return resources;
	}

	@Override
	public Set<Object> getSingletons() {
		Set<Object> set = new HashSet<>();
		set.add(new BloodTestSubmit()); 			//newBloodTest
		set.add(new BloodTestRequest());			//bloodTests
		set.add(new BloodTestMeasureRequest());		//bloodTestMeasure
		set.add(new BloodTestMeasureUpdate());		//bloodTest
		set.add(new BloodTestDelete());				//bloodTestDeletion
		return set;
	}

	private void addRestResourceClasses(Set<Class<?>> resources) {
		resources.add(BloodTestSubmit.class);
		resources.add(BloodTestRequest.class);
		resources.add(BloodTestMeasureRequest.class);
		resources.add(BloodTestMeasureUpdate.class);
		resources.add(BloodTestDelete.class);
	}
	
	public static Response errorResponse403(String msg) { //ex. wrong id
		return Response.status(Response.Status.FORBIDDEN).type("application/json").entity("{\"error\":\""+msg+"\"}").build();
	}
	
	public static Response errorResponse406(String msg) { //ex. negative value
		return Response.status(Response.Status.NOT_ACCEPTABLE).type("application/json").entity("{\"error\":\""+msg+"\"}").build();
	}
	
	public static Response errorResponseConflict(String msg) { //ex. database error.
		return Response.status(Response.Status.CONFLICT).type("application/json").entity("{\"error\":\""+msg+"\"}").build();
	}
	
	public static Response errorResponseBadRequest(String msg) { //ex. wrong id
		return Response.status(Response.Status.BAD_REQUEST).type("application/json").entity("{\"error\":\""+msg+"\"}").build();
	}
	
	public static Response successResponse(String msg) {
		return Response.status(Response.Status.OK).type("application/json").entity("{\"success\":\""+msg+"\"}").build();   
	}
}
