package com.example.jiaqiwang.monashfriendfinder.ServiceUse;

import android.util.Log;

import com.example.jiaqiwang.monashfriendfinder.DataStore.Friendship;
import com.example.jiaqiwang.monashfriendfinder.DataStore.StudentProfile;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

/**
 * Created by Jiaqi Wang on 5/1/2017.
 */

public class RestClient {

    private static final String BASE_URI = "http://192.168.0.25:8080/FIT5046AssignmentDB/webresources";

    public static String findStudentByEmail(String email) {
        final String methodPath = "/assignmentrestclient.studentprofile/findByMonashemail/" + email;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findByAnyKeywords(String kwList, String kwResult) {
        final String methodPath = "/assignmentrestclient.studentprofile/findByAnyKeywords/" + kwList + "/" + kwResult;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static String findStudentById(int id) {
        final String methodPath = "/assignmentrestclient.studentprofile/" + id;
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static   String findAllFriendship(){
        final String methodPath = "/assignmentrestclient.friendship/";
        URL url = null;
        HttpURLConnection conn = null;
        String textResult = "";
        try {
            url = new URL(BASE_URI + methodPath);
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept", "application/json");
            Scanner inStream = new Scanner(conn.getInputStream());
            while (inStream.hasNextLine()) {
                textResult += inStream.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
        return textResult;
    }

    public static void createStudentProfile(StudentProfile studentProfile) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "/assignmentrestclient.studentprofile/";
        try {
            Gson gson = new Gson();
            String stringCourseJson = gson.toJson(studentProfile);
            url = new URL(BASE_URI + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true); //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json"); //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void createFriendship(Friendship fs) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "/assignmentrestclient.friendship/";
        try {
            Gson gson = new Gson();
            String stringCourseJson = gson.toJson(fs);
            url = new URL(BASE_URI + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("POST"); //set the output to true
            conn.setDoOutput(true); //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json"); //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void deleteFriendship(Friendship fs, int id) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "/assignmentrestclient.friendship/" + id;
        try {
            Gson gson = new Gson();
            String stringCourseJson = gson.toJson(fs);
            url = new URL(BASE_URI + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("PUT"); //set the output to true
            conn.setDoOutput(true); //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json"); //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }

    public static void updateStudentProfile(StudentProfile studentProfile, int id) {
        //initialise
        URL url = null;
        HttpURLConnection conn = null;
        final String methodPath = "/assignmentrestclient.studentprofile/" + id;
        try {
            Gson gson = new Gson();
            String stringCourseJson = gson.toJson(studentProfile);
            url = new URL(BASE_URI + methodPath); //open the connection
            conn = (HttpURLConnection) url.openConnection(); //set the timeout
            conn.setReadTimeout(10000);
            conn.setConnectTimeout(15000); //set the connection method to POST
            conn.setRequestMethod("PUT"); //set the output to true
            conn.setDoOutput(true); //set length of the data you want to send
            conn.setFixedLengthStreamingMode(stringCourseJson.getBytes().length); //add HTTP headers
            conn.setRequestProperty("Content-Type", "application/json"); //Send the POST out
            PrintWriter out = new PrintWriter(conn.getOutputStream());
            out.print(stringCourseJson);
            out.close();
            Log.i("error", new Integer(conn.getResponseCode()).toString());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            conn.disconnect();
        }
    }


}
