package com.example.celia.kenya.tools;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.example.celia.kenya.db.beans.*;



public class MyResponse {
    private JSONObject response;
    public MyResponse(String Response)
    {
        try {
            response = new JSONObject(Response);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public MyResponse()
    {
        response = new JSONObject();
    }

    public String toString()
    {
        return response.toString();
    }

    public String getLoginResult()
    {
        String result = "";
        try {
            result = response.getString("LoginResult");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public void setLoginResult(String LoginResult) throws JSONException
    {
        response.put("LoginResult", LoginResult);
    }

    public String getSessionID()
    {
        String result = "";
        try {
            result = response.getString("SessionID");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }

    public void setSessionID(String SessionID) throws JSONException
    {
        response.put("SessionID", SessionID);
    }

    public int getLoginStatus()
    {
        int status = -1; //-1 for error
        try {
            String result = "";
            result = response.getString("LoginStatus");
            status = Integer.parseInt(result);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return status;
    }
    public void setStatus(Boolean Status) throws JSONException
    {
        if (Status)
            response.put("Status", "1");
        else
            response.put("Status", "0");
    }

    public boolean getStatus()
    {
        boolean result = false;
        try {
            result = response.getString("Status").equals("1");
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return result;
    }


    public void setLoginStatus(boolean Status) throws JSONException
    {
        if (Status)
            response.put("LoginStatus", "1");
        else
            response.put("LoginStatus", "0");
    }

    public ArrayList<String> getTrack()
    {
        ArrayList<String> tracks = new ArrayList<String>();
        try {
            JSONArray array = response.getJSONArray("Tracks");
            int n = array.length();
            for (int i = 0; i < n; i++)
            {
                tracks.add(array.get(i).toString());
            }
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return tracks;
    }

    public void setTrack(ArrayList<Tracks> tracks) throws JSONException
    {
        int n = tracks.size();
        JSONArray array = new JSONArray();
        for (int i = 0; i < n; i++)
        {
            JSONObject obj = new JSONObject();
            obj.put("latitude",tracks.get(i).getLatitude());
            obj.put("longitude",tracks.get(i).getLongitude());
            obj.put("trackNo", tracks.get(i).getTrackID());
            obj.put("status", tracks.get(i).getStatus());
            obj.put("username", tracks.get(i).getUsername());
            obj.put("locationList",tracks.get(i).getLocation());
            obj.put("recordsList",tracks.get(i).getRecords());
            obj.put("timeList", tracks.get(i).getUpdatetime());

            array.put(obj);
        }
        response.put("Tracks", array);
    }


    public static void main(String[] args) throws JSONException {
        MyResponse myResponse = new MyResponse("{\"Posts\":[],\"LoginStatus\":\"1\"}");
        System.out.println(myResponse.getLoginStatus());
    }
}