package com.myhexaville.UI.Adapter.AdapterMainChat;

public class $_Value_Item_Main_Chat {
    private String message;
    private String name;
    private String email;
    private int id_image;

    public $_Value_Item_Main_Chat(String message, String name, String email, int id_image) {
        this.message = message;
        this.name = name;
        this.email = email;
        this.id_image = id_image;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_image() {
        return id_image;
    }

    public void setId_image(int id_image) {
        this.id_image = id_image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
