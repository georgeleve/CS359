/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database.tables;

import mainClasses.BloodTest;
import com.google.gson.Gson;
import database.DB_Connection;
//import mainClasses.SimpleUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EditBloodTestTable {
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

	public Boolean amkaExists(String amka) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		try {
			rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE amka= '" + amka + "'");
			return rs.next();
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}
	}

	public ArrayList<String> databaseToBloodTest1(String amka) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		ArrayList<String> json = new ArrayList<String>();

		try {
			rs = stmt.executeQuery("SELECT * FROM bloodtest WHERE amka= '" + amka + "'");
			while(rs.next()){
				json.add(DB_Connection.getResultsToJSON(rs));  // how can I fix that?  Prepei na pernei oles tis grammes, oxi mono thn proth
			}
			return json;
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return null;
	}

	public ArrayList<String> databaseToBloodTest(String amka, String measure) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		ResultSet rs = null;
		ArrayList<String> json = new ArrayList<String>();
		try {
			if (measure.equals("blood_sugar")) {
				rs = stmt.executeQuery(
						"SELECT test_date, medical_center, blood_sugar, blood_sugar_level FROM bloodtest WHERE amka= '"
								+ amka + "'");
			}
			if (measure.equals("cholesterol")) {
				rs = stmt.executeQuery(
						"SELECT test_date, medical_center, cholesterol, cholesterol_level FROM bloodtest WHERE amka= '"
								+ amka + "'");
			}
			if (measure.equals("iron")) {
				rs = stmt.executeQuery(
						"SELECT test_date, medical_center, iron, iron_level FROM bloodtest WHERE amka= '" + amka + "'");
			}
			if (measure.equals("vitamin_d3")) {
				rs = stmt.executeQuery(
						"SELECT test_date, medical_center, vitamin_d3, vitamin_d3_level FROM bloodtest WHERE amka= '"
								+ amka + "'");
			}
			if (measure.equals("vitamin_b12")) {
				rs = stmt.executeQuery(
						"SELECT test_date, medical_center, vitamin_b12, vitamin_b12_level FROM bloodtest WHERE amka= '"
								+ amka + "'");
			}

			while(rs.next()){
				json.add(DB_Connection.getResultsToJSON(rs));  // how can I fix that?  Prepei na pernei oles tis grammes, oxi mono thn proth
			}
			return json;
		} catch (Exception e) {
			System.err.println("Got an exception! ");
			System.err.println(e.getMessage());
		}
		return null;
	}

	public int updateBloodTest(int id, String measure, double val) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		BloodTest bt = new BloodTest();
		String update = null;

		if (measure.equals("blood_sugar")) {
			bt.setBlood_sugar(val);
			bt.setValues();
			update = "UPDATE bloodtest SET blood_sugar='" + val + "', blood_sugar_level='" + bt.getBlood_sugar_level()
					+ "' WHERE bloodtest_id = '" + id + "'";
		}
		if (measure.equals("cholesterol")) {
			bt.setCholesterol(val);
			bt.setValues();
			update = "UPDATE bloodtest SET cholesterol='" + val + "', cholesterol_level='" + bt.getCholesterol_level()
					+ "' WHERE bloodtest_id = '" + id + "'";
		}
		if (measure.equals("iron")) {
			bt.setIron(val);
			bt.setValues();
			update = "UPDATE bloodtest SET iron='" + val + "', iron_level='" + bt.getIron_level()
					+ "' WHERE bloodtest_id = '" + id + "'";
		}
		if (measure.equals("vitamin_d3")) {
			bt.setVitamin_d3(val);
			bt.setValues();
			update = "UPDATE bloodtest SET vitamin_d3='" + val + "', vitamin_d3_level='" + bt.getVitamin_d3_level()
					+ "' WHERE bloodtest_id = '" + id + "'";
		}
		if (measure.equals("vitamin_b12")) { // vitamin_b12
			bt.setVitamin_b12(val);
			bt.setValues();
			update = "UPDATE bloodtest SET vitamin_b12='" + val + "', vitamin_b12_level='" + bt.getVitamin_b12_level()
					+ "' WHERE bloodtest_id = '" + id + "'";
		}
		int returnValue = stmt.executeUpdate(update); //returns 0 on failure
		System.out.println("Return Value of execute Update is " + returnValue);
		return returnValue;
		
	}

	public int deleteBloodTest(int bloodtestid) throws SQLException, ClassNotFoundException {
		Connection con = DB_Connection.getConnection();
		Statement stmt = con.createStatement();
		String deleteQuery = "DELETE FROM bloodtest WHERE bloodtest_id='" + bloodtestid + "'";
		int returnValue = stmt.executeUpdate(deleteQuery); //returns 0 on failure
		System.out.println("Return value of deleteBloodTest() is " + returnValue);
		stmt.close();
		con.close();
		return returnValue;
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