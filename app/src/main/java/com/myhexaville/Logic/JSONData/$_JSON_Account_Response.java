package com.myhexaville.Logic.JSONData;


public abstract class $_JSON_Account_Response extends $_JSON_Response {


    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }

    protected boolean Done ;

    public $_JSON_Account_Response(String type, String idReceived, boolean Done) {
        super(type, idReceived);
        this.Done=Done;
    }
}
