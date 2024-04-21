/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RestApi.External.database.tables;

import com.google.gson.Gson;
import RestApi.External.database.DB_Connection;
import RestApi.External.mainClasses.BloodTest;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mike
 */
public class EditBloodTestTable {

	public boolean bloodTestExists(int testId) {
		try {
			Connection con = DB_Connection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE bloodtest_id = " + testId + "");
			return rs.next();
		} catch (Exception e) {
			return false;
		}
	}	 
	
	public boolean amkaExists(String amka) {
		try {
			Connection con = DB_Connection.getConnection();
			Statement stmt = con.createStatement();
			ResultSet rs;
			rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE amka = '" + amka + "'");
			return rs.next();
		} catch (Exception e) {
			return false;
		}
	}

	public void addBloodTestFromJSON(String json) throws ClassNotFoundException {
		BloodTest bt = jsonToBloodTest(json);
		bt.setValues();
		createNewBloodTest(bt);
	}

	public BloodTest jsonToBloodTest(String json) {
		Gson gson = new Gson();
		BloodTest btest = gson.fromJson(json, BloodTest.class);
		return btest;
	}

	public String bloodTestToJSON(BloodTest bt) {
		Gson gson = new Gson();

		String json = gson.toJson(bt, BloodTest.class);
		return json;
	}

	public ArrayList<BloodTest> databaseToBloodTestRange(String amka, String fromDate, String toDate) throws SQLException, ClassNotFoundException {
		ArrayList<BloodTest> ans = new ArrayList<BloodTest>();
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs;
		if (fromDate == null) fromDate = "0000-01-01";
		if (toDate == null) toDate = "9990-01-01";
		Gson gson = new Gson();
		try {
			rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE amka= '" + amka + "' AND test_date BETWEEN '" + fromDate + " 00:00:00' AND '" + toDate + " 23:59:59'");
			while(rs.next()) {
				String json = DB_Connection.getResultsToJSON(rs);
				ans.add(gson.fromJson(json, BloodTest.class));
			}
		} finally {
		    stmt.close();
		}
		return ans;
	}

	public BloodTest databaseToBloodTest(String amka, String date) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();

		ResultSet rs;
		try {
			rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE amka= '" + amka + "' AND test_date='" + date + "'");
			rs.next();
			String json = DB_Connection.getResultsToJSON(rs);
			Gson gson = new Gson();
			BloodTest bt = gson.fromJson(json, BloodTest.class);
			return bt;
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return null;
	}
	
	public void updateBloodTestBloodSugar(int id, double val) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		BloodTest bt = new BloodTest();
		bt.setBlood_sugar(val);
		bt.setValues();
		String update = "UPDATE bloodtest SET blood_sugar='" + val + "', blood_sugar_level='" + bt.getBlood_sugar_level() + "' WHERE bloodtest_id = '" + id + "'";
		stmt.executeUpdate(update);
	}
	
	public void updateBloodTestIron(int id, double val) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		BloodTest bt = new BloodTest();
		bt.setIron(val);
		bt.setValues();
		String update = "UPDATE bloodtest SET iron='" + val + "', iron_level='" + bt.getIron_level() + "' WHERE bloodtest_id = '" + id + "'";
		stmt.executeUpdate(update);
	}
	
	public void updateBloodTestB12(int id, double val) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		BloodTest bt = new BloodTest();
		bt.setVitamin_b12(val);
		bt.setValues();
		String update = "UPDATE bloodtest SET vitamin_b12='" + val + "', vitamin_b12_level='" + bt.getVitamin_b12() + "' WHERE bloodtest_id = '" + id + "'";
		stmt.executeUpdate(update);
	}
	
	public void updateBloodTestD3(int id, double val) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		BloodTest bt = new BloodTest();
		bt.setVitamin_d3(val);
		bt.setValues();
		String update = "UPDATE bloodtest SET vitamin_d3='" + val + "', vitamin_d3_level='" + bt.getVitamin_d3() + "' WHERE bloodtest_id = '" + id + "'";
		stmt.executeUpdate(update);
	}
	
	public void updateBloodTestCholesterol(int id, double val) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		BloodTest bt = new BloodTest();
		bt.setCholesterol(val);
		bt.setValues();
		String update = "UPDATE bloodtest SET cholesterol='" + val + "', cholesterol_level='" + bt.getCholesterol_level() + "' WHERE bloodtest_id = '" + id + "'";
		stmt.executeUpdate(update);
	}

	public void deleteBloodTest(int bloodtestid) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		String deleteQuery = "DELETE FROM bloodtest WHERE bloodtest_id='" + bloodtestid + "'";
		stmt.executeUpdate(deleteQuery);
		stmt.close();
		con.close();
	}

	public void createBloodTestTable() throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		String sql = "CREATE TABLE bloodtest " + "(bloodtest_id INTEGER not NULL AUTO_INCREMENT, "
				+ "amka VARCHAR (11) not null," + "test_date DATE not NULL, " + "medical_center VARCHAR(100) not NULL, "
				+ "blood_sugar DOUBLE, " + "blood_sugar_level VARCHAR(10)," + "cholesterol DOUBLE, "
				+ "cholesterol_level VARCHAR(10)," + "iron DOUBLE, " + "iron_level VARCHAR(10)," + "vitamin_d3 DOUBLE, "
				+ "vitamin_d3_level VARCHAR(10)," + "vitamin_b12 DOUBLE, " + "vitamin_b12_level VARCHAR(10),"
				+ "PRIMARY KEY ( bloodtest_id ))";
		stmt.execute(sql);
		stmt.close();
		con.close();

	}

	/**
	 * Establish a database connection and add in the database.
	 *
	 * @throws ClassNotFoundException
	 */
	public void createNewBloodTest(BloodTest bt) throws ClassNotFoundException {
		try {
			Connection con = DB_Connection.getConnection();

			Statement stmt = con.createStatement();

			String insertQuery = "INSERT INTO "
					+ " bloodtest (amka,test_date,medical_center,blood_sugar,blood_sugar_level,cholesterol,"
					+ "cholesterol_level,iron,iron_level,vitamin_d3,vitamin_d3_level,vitamin_b12,vitamin_b12_level) "
					+ " VALUES (" + "'" + bt.getAmka() + "'," + "'" + bt.getTest_date() + "'," + "'"
					+ bt.getMedical_center() + "'," + "'" + bt.getBlood_sugar() + "'," + "'" + bt.getBlood_sugar_level()
					+ "'," + "'" + bt.getCholesterol() + "'," + "'" + bt.getCholesterol_level() + "'," + "'"
					+ bt.getIron() + "'," + "'" + bt.getIron_level() + "'," + "'" + bt.getVitamin_d3() + "'," + "'"
					+ bt.getVitamin_d3_level() + "'," + "'" + bt.getVitamin_b12() + "'," + "'"
					+ bt.getVitamin_b12_level() + "'" + ")";
			// stmt.execute(table);
			System.out.println(insertQuery);
			stmt.executeUpdate(insertQuery);
			System.out.println("# The bloodtest was successfully added in the database.");

			/* Get the member id from the database and set it to the member */
			stmt.close();

		} catch (SQLException ex) {
			Logger.getLogger(EditBloodTestTable.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
