package com.myhexaville.Logic.Friend;


import com.myhexaville.Logic.Tools.$_Tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class $_FriendStorgeMangement implements $_Tools {

    private FileInputStream fileInputFriend;
    private FileOutputStream fileOutputFriend;
    private String FriendPath;
    public FileInputStream getFileInputFriend() {
        return fileInputFriend;
    }

    public void setFileInputFriend(FileInputStream fileInputFriend) {
        this.fileInputFriend = fileInputFriend;
    }

    public FileOutputStream getFileOutputFriend() {
        return fileOutputFriend;
    }

    public void setFileOutputFriend(FileOutputStream fileOutputFriend) {
        this.fileOutputFriend = fileOutputFriend;
    }

    public String getFriendPath() {
        return FriendPath;
    }

    public void setFriendPath(String friendPath) {
        FriendPath = friendPath;
    }

    @Override
    public boolean update(Object object) throws IOException {
        $_FriendInfo my_Friend = ($_FriendInfo) object;
        String id = my_Friend.getId();
        if (!isExist(id)) return false;
        delete(id);
        add(my_Friend);
        return true;    }

    @Override
    public boolean add(Object object) throws IOException {
        $_FriendInfo my_friend = ($_FriendInfo) object;
        String file_name = my_friend.getId();
       //if (isExist(file_name)) return false;
        try {
            ObjectOutputStream out = new ObjectOutputStream(fileOutputFriend);
            out.writeObject(my_friend);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            return  false;
        }

        return true;
    }

    @Override
    public boolean delete(String id) {
        if (!isExist(id)) return false;
        File Friend_file = new File(FriendPath + File.separator + id + ".Fr");
        Friend_file.delete();
        return true;
    }

    @Override
    public boolean isExist(String file_name) {
        File Friend_folder = new File(FriendPath);
        for (File Friend : Friend_folder.listFiles()) {
            if (Friend.getName().equals(file_name + ".Fr")) return true;
        }
        return false;
    }

    @Override
    public Object get(String id) throws IOException {
        $_FriendInfo friend = null;

        try {
            ObjectInputStream in = new ObjectInputStream(fileInputFriend);
            friend = ($_FriendInfo) in.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return friend;    }
}
