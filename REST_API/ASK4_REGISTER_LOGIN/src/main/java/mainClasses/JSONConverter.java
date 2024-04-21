/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainClasses;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;

public class JSONConverter {
    public User jsonToUser(BufferedReader json) {
        Gson gson = new Gson();
        User msg = gson.fromJson(json, User.class);
        return msg;
    }
    public String JavaObjectToJSONRemoveElements(User p,String removeProp) {
        // Creating a Gson Object
        Gson gson = new Gson();
        String json = gson.toJson(p, User.class);
        JsonObject object = (JsonObject) gson.toJsonTree(p);
        object.remove(removeProp);
        return object.toString();
    }
    public String userToJSON(User per) {
        Gson gson = new Gson();
        String json = gson.toJson(per, User.class);
        return json;
    }
    public void setValues() {
        return;
    }
}