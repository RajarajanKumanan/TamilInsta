package com.socfire.socialfeed.tamilinsta;

/**
 * Created by vaishu on 01/08/2019.
 */

public class JsonData {

    private String FileName;

    public  JsonData(){

    }

    public JsonData(String fileName) {
        FileName = fileName;
    }

    public String getFileName() {
        return FileName;
    }

    public void setFileName(String fileName) {
        FileName = fileName;
    }
}
