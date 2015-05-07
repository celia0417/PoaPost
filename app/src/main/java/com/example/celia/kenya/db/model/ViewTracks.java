package com.example.celia.kenya.db.model;


import com.example.celia.kenya.db.Database;

import java.sql.ResultSet;
import java.util.*;

import com.example.celia.kenya.db.beans.Tracks;

/**
 * Created by celia on 4/4/15.
 */


public class ViewTracks {
    public ArrayList<Tracks> selectTrackList(String userOrTrack,Database db,String param) {

        ArrayList<Tracks> list = new ArrayList<Tracks>();
        ResultSet rs=null;
        String sql = "";
        if(param.equals("uesrname")){
            sql="select * from package where username='"+userOrTrack+"'";
        }else if (param.equals("trackingNO")){
            sql="select * from package where trackingid='"+userOrTrack+"'";
        }

        rs = db.doSelect(sql);
        try{

            while (rs.next()) {

                Tracks t = new Tracks();

                t.setTrackID(rs.getString("trackingid"));

                String loString = (rs.getString("location"));

                String lo [] = loString.split("\\|");

                for (String l : lo){
                    t.setLocation(l);
                }

                String recString = rs.getString("records");

                String rec []= recString.split("\\|");
                for (String r : rec){
                    t.setRecords(r);
                }

                String timeString = rs.getString("updatetime");


                String time []= timeString.split("\\|");
                for (String ts : time){
                    t.setUpdatetime(ts);
                }

                t.setLatitude(rs.getString("latitude"));
                t.setLongitude(rs.getString("longitude"));
                t.setStatus(rs.getString("status"));
                t.setLatitude(rs.getString("latitude"));
                t.setUsername(rs.getString("username"));

                list.add(t);

            }
            db.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return list;

    }
    public static void main(String args[]){
        ViewTracks vt=new ViewTracks();
        Database db=new Database();
        ArrayList<Tracks> tracks = vt.selectTrackList("1HV18380330121175",db,"trackingNO");
        for(int i = 0 ; i< tracks.size();i++) {
            Tracks t = tracks.get(i);

            System.out.println(t.getTrackID());
            System.out.println(t.getLocation().get(1));
            System.out.println(t.getLatitude());
            System.out.println(t.getLongitude());
            System.out.println(t.getUpdatetime());

            System.out.println(t.getRecords());
            System.out.println(t.getStatus());


        }
    }
}


