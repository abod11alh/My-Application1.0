package com.myhexaville.Logic.Friend;

import com.myhexaville.Logic.Client.$_Client;
import com.myhexaville.Logic.JSONData.$_JSONAttributes;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Friendship_sender {

    public void send_To(final JSONObject jsonObject, final String id, final String Type, final String Type_ID)
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    JSONObject jsonObject1=new JSONObject();
                    jsonObject1.put($_JSONAttributes.Type.toString(),Type);
                    jsonObject1.put($_JSONAttributes.Id.toString(), jsonObject.getString($_JSONAttributes.Id.toString()));
                    jsonObject1.put(Type_ID,id);
                    jsonObject1.put($_JSONAttributes.User_Name.toString(),"abod");
                    $_Client.getDataOutputStreamMessage().writeUTF(jsonObject1.toString());


                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();




    }
}
