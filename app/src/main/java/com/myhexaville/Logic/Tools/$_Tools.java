package com.myhexaville.Logic.Tools;


import java.io.IOException;
import java.util.ArrayList;

public interface  $_Tools {

    boolean update(Object object) throws IOException;
    boolean add(Object object) throws IOException;
    boolean delete(String id) ;
    boolean isExist(String file_name) ;
    Object get(String id) throws IOException;
}
