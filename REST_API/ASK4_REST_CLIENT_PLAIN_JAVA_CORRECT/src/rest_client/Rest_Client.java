/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest_client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import static org.apache.http.HttpHeaders.ACCEPT;
import static org.apache.http.HttpHeaders.CONTENT_TYPE;
import org.apache.http.HttpResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class Rest_Client {
	private HttpClient client;
	private HttpGet allLaptopsBrand;
	private HttpGet allLaptopsMemory_Core;
	private HttpPut bloodTestUpdate;
	private HttpDelete bloodTestDelete;
	private HttpPost addlaptopsService;
	private static final String URL = "http://localhost:8080/ASK4_REST_API/test";
	private String serviceName;

	public Rest_Client() {
		client = HttpClientBuilder.create().build();
	}

	// POST Request: http://localhost:8080/ASK4_REST_API/test/newBloodTest/
	public void addBloodTest(String json) throws IOException {
		try {
			serviceName = "newBloodTest";
			addlaptopsService = new HttpPost(URL + "/" + serviceName + "/");
			addlaptopsService.addHeader(ACCEPT, "application/json");
			addlaptopsService.addHeader(CONTENT_TYPE, "application/json");
			StringEntity toSend = new StringEntity(json);
			addlaptopsService.setEntity(toSend);
			HttpResponse response = client.execute(addlaptopsService);
			int responseCode = response.getStatusLine().getStatusCode();
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception ex) {
			Logger.getLogger(Rest_Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// GET Request 1: http://localhost:8080/ASK4_REST_API/test/bloodTests/03069200000?fromDate=2021-01-01&toDate=2021-11-11
	public void getBloodTest(String amka, String fromDate, String toDate) throws IOException {
		try {
			serviceName = "bloodTests/" + amka; // add day as optional
			//if (!"".equals(core))
			//	serviceName += "?core=" + core;
			allLaptopsBrand = new HttpGet(URL + "/" + serviceName);
			allLaptopsBrand.addHeader(ACCEPT, "application/json");
			HttpResponse response = client.execute(allLaptopsBrand);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception ex) {
			Logger.getLogger(Rest_Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// GET Request 2: http://localhost:8080/ASK4_REST_API/test/bloodTestMeasure/03069200000/cholesterol
	public void getBloodTestMeasure(String amka, String measure) throws IOException {
		try {
			serviceName = "bloodTestMeasure/" + amka + "/" + measure;
			allLaptopsMemory_Core = new HttpGet(URL + "/" + serviceName);
			allLaptopsMemory_Core.addHeader(ACCEPT, "application/json");
			HttpResponse response = client.execute(allLaptopsMemory_Core);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception ex) {
			Logger.getLogger(Rest_Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	// PUT Request: http://localhost:8080/ASK4_REST_API/test/bloodTest/5/blood_sugar/69
	public void updateBloodTest(int bloodTestID, String measure, double value) throws IOException {
		try {
			serviceName = "bloodTest";
			bloodTestUpdate = new HttpPut(URL + "/" + serviceName + "/" + bloodTestID + "/" + measure + "/" + value);
			bloodTestUpdate.addHeader(ACCEPT, "application/json");
			HttpResponse response = client.execute(bloodTestUpdate);
			int responseCode = response.getStatusLine().getStatusCode();
			System.out.println("Response Code " + responseCode);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception ex) {
			Logger.getLogger(Rest_Client.class.getName()).log(Level.SEVERE, null, ex);

		}
	}

	// DELETE Request: http://localhost:8080/ASK4_REST_API/test/bloodTestDeletion/1
	public void deleteBloodTest(int bloodTestID) throws IOException {
		try {
			serviceName = "bloodTestDeletion";
			bloodTestDelete = new HttpDelete(URL + "/" + serviceName + "/" + bloodTestID);
			bloodTestDelete.addHeader(ACCEPT, "application/json");
			HttpResponse response = client.execute(bloodTestDelete);
			int responseCode = response.getStatusLine().getStatusCode();
			System.out.println("Response Code " + responseCode);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) {
				System.out.println(line);
			}
		} catch (Exception ex) {
			Logger.getLogger(Rest_Client.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public static void main(String[] args) throws IOException {
		Rest_Client rs = new Rest_Client();
		//------------ POST Request ------------//
        String bloodTest = "{"
                + "\"amka\":\"111111\","
                + "\"test_date\": \"2021-10-22\","
                + "\"medical_center\":\"Venizeleio\","
                + "\"blood_sugar\":\"400\","
                + "\"cholesterol\":\"400\","
                + "\"iron\":\"400\","
                + "\"vitamin_d3\":\"400\","
                + "\"vitamin_b12\":\"400\""
                + "}";
        
		System.out.println("Add BloodTest");
		//rs.addBloodTest(bloodTest);
		
		//------------GET Request 1------------//
		System.out.println("\n\nGet BloodTest");
		String fromDate = "2021-10-11";
		String toDate = "2021-16-16";
		rs.getBloodTest("99999999999", fromDate, toDate);

		//------------ GET Request 2 ------------//
		System.out.println("\n\nGet BloodTest Measure");
		rs.getBloodTestMeasure("876543", "blood_sugar");
		rs.getBloodTestMeasure("99999999999", "iron");

		//------------ PUT Request ------------//
		System.out.println("\n\nUpdate BloodTest");
//		rs.updateBloodTest(30, "iron", 33333);
//		rs.updateBloodTest(31, "cholesterol", 22222);
//		rs.updateBloodTest(32, "blood_sugar", 11111);

		//------------ Delete Request ------------//
		System.out.println("\n\nDelete BloodTest");
//		rs.deleteBloodTest(45);
//		rs.deleteBloodTest(46);
//		rs.deleteBloodTest(47);
	}
}
