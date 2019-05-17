package com.myhexaville.Logic.Tools;

import android.content.SharedPreferences;
import com.myhexaville.login.MainActivity;
import org.json.JSONException;
import org.json.JSONObject;
import static android.content.Context.MODE_PRIVATE;

public class $_SharedPreferences {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String name;



    public $_SharedPreferences(String name){
        sharedPreferences = MainActivity.context.getSharedPreferences(name,MODE_PRIVATE);
        editor = sharedPreferences.edit();
        this.name = name;
    }

    public void storeObject(String key, Object object) {
        editor.putString(key,object.toString());
        editor.apply();
    }


    public void removeObject(String key){
        editor.remove(key);
        editor.apply();
    }

    public JSONObject getObject(String key){
        String res = sharedPreferences.getString(key,"");
        if(!res.equals("")){
            try {
                JSONObject jsonObject = new JSONObject(res);
                return jsonObject;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }else return null;
    }

    public String isExist(String key){
        return sharedPreferences.getString("key","");
    }


}
