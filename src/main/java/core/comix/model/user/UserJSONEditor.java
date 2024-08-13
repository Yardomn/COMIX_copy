package core.comix.model.user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class UserJSONEditor {
    private static final String FILENAME = "src/main/resources/users.json";
    
    public UserJSONEditor() {

    }

    public void saveSingleUser(String username, String password, String fileHash) {
        JSONObject object = getJSONObject();
        JSONArray users = object.getJSONArray("users");
        JSONObject newUser = new JSONObject();
        newUser.put("username", username);
        newUser.put("password", password);
        newUser.put("fileHash", fileHash);
        users.put(newUser);

        FileWriter out;
        try {
            out = new FileWriter(FILENAME);
            out.write(object.toString());
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void readAllUsers(LoginManager loginManager) {
        JSONObject object = getJSONObject();
        JSONArray users = object.getJSONArray("users");
        for (Object jo : users.toList()) {
            System.out.println(jo.getClass());
            
            HashMap<String, String> obj = (HashMap<String, String>)jo;
            loginManager.addStoredUser(obj.get("username"), obj.get("password"), obj.get("fileHash"));
        }
    }

    private JSONObject getJSONObject() {
        InputStream is;
        try {
            is = new FileInputStream(FILENAME);
            JSONTokener tokener = new JSONTokener(is);
            JSONObject object = new JSONObject(tokener);
            return object;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }
}
