package com.traylz.traylzmix;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A class to send POST requests to the Traylz server.
 * @author Chris Wang
 */
public class PostUploader {

    private static String url = "http://traylz.x10host.com/NewTrail.php";
    private static String agent = "Mozilla/5.0"; // Close enough.

    public static boolean sendPost(String trailtype, String name, String difficulty, double[][] coords) {
        StringBuilder sb = new StringBuilder("trailtype=" + trailtype + "&name=" + name.replaceAll("&","") + "&difficulty=" + difficulty);
        sb.append("&inputlats=[");
        for (int i = 0; i < coords.length; i++) {
            sb.append(coords[i][0]);
            if (i < coords.length - 1)
                sb.append(",");
        }
        sb.append("]&inputlngs=[");
        for (int i = 0; i < coords.length; i++) {
            sb.append(coords[i][1]);
            if (i < coords.length - 1)
                sb.append(",");
        }
        sb.append("]");
        String params = sb.toString();
        try {
            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("User-Agent", agent);
            connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            connection.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(params);
            wr.flush();
            wr.close();

            int responseCode = connection.getResponseCode();

            System.out.println(responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader((connection.getInputStream())));
            String msg = null;
            StringBuffer response = new StringBuffer();

            while ((msg = in.readLine()) != null) {
                response.append(msg);
            }
            in.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
