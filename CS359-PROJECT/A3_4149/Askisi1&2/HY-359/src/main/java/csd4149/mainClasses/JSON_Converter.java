/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csd4149.mainClasses;

import java.io.BufferedReader;
import java.io.IOException;

import com.google.gson.Gson;

/**
 *
 * @author micha
 */

public class JSON_Converter {

	public static String getJSONFromAjax(BufferedReader reader) throws IOException {
		StringBuilder buffer = new StringBuilder();
		String line;
		while ((line = reader.readLine()) != null) buffer.append(line);
		return buffer.toString();
	}
	
    public static SimpleUser jsonToUser(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SimpleUser.class);
    }
    
    public static SimpleUser jsonToUser(BufferedReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, SimpleUser.class);
    }
    
    public static Doctor jsonToDoc(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Doctor.class);
    }
    
    public static Doctor jsonToDoc(BufferedReader json) {
        Gson gson = new Gson();
        return gson.fromJson(json, Doctor.class);
    }
}
