package com.example.celia.kenya.client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.*;

import com.example.celia.kenya.tools.MyResponse;

/**
 * Created by celia on 4/4/15.
 */

public class TrackClient {
    private String url;

    public TrackClient(String URL) {
        url = URL;
    }

    public ArrayList<String> getByUsername(String userID, String sessionID) {
        //status: -2~error, -1~no userID, 0~wrong password, 1~successful
        ArrayList<String> result = new ArrayList<String>();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();

            formparams.add(new BasicNameValuePair("userID", userID));
            formparams.add(new BasicNameValuePair("sessionID", sessionID));
            formparams.add(new BasicNameValuePair("action", "username"));

            UrlEncodedFormEntity formEntity;
            formEntity = new UrlEncodedFormEntity(formparams);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader in = null;
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
//		            System.out.println(sb.toString());

                MyResponse myResponse = new MyResponse(sb.toString());
                int status = myResponse.getLoginStatus();
                if (status == 1) {
                    result = myResponse.getTrack();
                } else if (status == 0) {
                    System.out.println("Not Login");
                } else {
                    System.out.println("Error");
                }
            } else {
                System.out.println(response.getStatusLine());
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }

    public ArrayList<String> getByNo(String trackingNO) {
        //status: -2~error, -1~no userID, 0~wrong password, 1~successful
        ArrayList<String> result = new ArrayList<String>();
        try {
            HttpClient client = new DefaultHttpClient();
            HttpPost request = new HttpPost(url);
            List<NameValuePair> formparams = new ArrayList<NameValuePair>();

            formparams.add(new BasicNameValuePair("trackingNO", trackingNO));
            formparams.add(new BasicNameValuePair("action", "trackingNO"));


            UrlEncodedFormEntity formEntity;
            formEntity = new UrlEncodedFormEntity(formparams);
            request.setEntity(formEntity);
            HttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == 200) {
                BufferedReader in = null;
                in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                StringBuffer sb = new StringBuffer("");
                String line = "";
                String NL = System.getProperty("line.separator");
                while ((line = in.readLine()) != null) {
                    sb.append(line + NL);
                }
                in.close();
//		            System.out.println(sb.toString());

                MyResponse myResponse = new MyResponse(sb.toString());
                int status = myResponse.getLoginStatus();
                if (status == 1) {
                    result = myResponse.getTrack();
                } else if (status == 0) {
                    System.out.println("Not Login");
                } else {
                    System.out.println("Error");
                }
            } else {
                System.out.println(response.getStatusLine());
            }

        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return result;
    }


    public static void main(String[] args) throws JSONException {
        String url = "http://kenya-t6ap7bumpv.elasticbeanstalk.com/Track";
        TrackClient client = new TrackClient(url);
//		ArrayList<String> result = client.getByUsername("ww","d920682a-7418-43fd-9a5d-339ada650223");
        ArrayList<String> result = client.getByNo("1HV18380330121175");
        if (!result.isEmpty()) {
            System.out.println(result);
            JSONObject obj = new JSONObject(result.get(0));

            JSONArray locArray = obj.getJSONArray("locationList");
            System.out.println(locArray.get(1));
            System.out.println(locArray.length());
        } else {

        }

    }

}