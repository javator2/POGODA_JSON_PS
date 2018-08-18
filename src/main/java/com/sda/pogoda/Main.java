package com.sda.pogoda;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.HashMap;

import java.util.Iterator;


public class Main {

    static String url = "http://api.apixu.com/v1/current.json?key=6b74cc3f2a0b4eda98d72628181808&q=";
    static HashMap<String,HashMap<String,Object>> weather = new HashMap<String, HashMap<String,Object>>();

    public static void main(String[] args) {
        String town = "Torun";
        checkWethear(town);
        }

   static void checkWethear(String town) {
        try {
           System.out.println(IOUtils.toString(new URL(url + town), Charset.forName("UTF-8"))+"\n\n");
           JSONObject json = new JSONObject(IOUtils.toString(new URL(url + town), Charset.forName("UTF-8")));

            Iterator<String> keys = json.keys();
            for (Iterator <String> it= keys; it.hasNext();){
             String k = it.next();
             HashMap<String,Object> currentInfo = new HashMap<String, Object>();
             Iterator<String> keys2 = json.getJSONObject(k).keys();
             for (Iterator <String> it2= keys2; it2.hasNext();) {
                 String k2 = it2.next();
                 if (json.getJSONObject(k).get(k2).getClass().getName().contains("JSONObject")) {
                     Iterator<String> keys3 = json.getJSONObject(k).getJSONObject(k2).keys();
                     for (Iterator <String> it3= keys3; it3.hasNext();) {
                         String k3 = it3.next();
                         System.out.println(json.getJSONObject(k).getJSONObject(k2).get(k3));
                         currentInfo.put(k3,json.getJSONObject(k).getJSONObject(k2).get(k3));
                     }
                 } else {
                     currentInfo.put(k2, json.getJSONObject(k).get(k2));

                 }
             }
             weather.put(k,currentInfo);
            }


            System.out.println(weather.toString());
            System.out.println("\nThe weather in "+weather.get("location").get("name")+" is "
                    +weather.get("current").get("text"));


        } catch (IOException e) {
            e.printStackTrace();
            checkWethear(town);
        }

    }
}
