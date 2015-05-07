package com.example.celia.kenya.db.beans;

/**
 * Created by celia on 4/4/15.
 */

import java.util.*;

public class Tracks {
    private String trackID;
    private ArrayList<String> location;
    private ArrayList<String> records;
    private ArrayList<String> updatetime;
    private String latitude;
    private String longitude;
    private String status;
    private String username;


    public Tracks(){
        location = new ArrayList<String>();
        records = new ArrayList<String>();
        updatetime = new ArrayList<String>();
    }

    public void setTrackID(String trackID){
        this.trackID=trackID;
    }

    public String getTrackID(){
        return trackID;
    }

    public void setLocation(String l){
        location.add(l);
    }

    public ArrayList<String> getLocation(){
        return location;
    }

    public ArrayList<String> getRecords(){
        return records;
    }
    public void setRecords(String record){
        records.add(record);
    }

    public ArrayList<String> getUpdatetime(){
        return updatetime;
    }
    public void setUpdatetime(String update){
        updatetime.add(update);
    }

    public void setLatitude(String latitude){
        this.latitude=latitude;
    }

    public String getLatitude(){
        return latitude;
    }

    public void setLongitude(String longitude){
        this.longitude=longitude;
    }

    public String getLongitude(){
        return longitude;
    }

    public void setStatus(String status){
        this.status=status;
    }

    public String getStatus(){
        return status;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getUsername(){
        return username;
    }

}
