package serverbackend.datamanagement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import serverbackend.data.NewsCategory;

public class DataSource {
    private String host;
    private int port;
    private String beginningURL;

    public DataSource() {
        // use Node Express defaults
        host = "127.0.0.1";
        port = 3000;
        beginningURL = "https://" + host + ":" +  String.valueOf(port);
        beginningURL = "http://e952bbbc.ngrok.io";
    }

    public DataSource(String host, int port) {
        this.host = host;
        this.port = port;
        beginningURL = "http://" + host + ":" +  String.valueOf(port);
        beginningURL = "http://e952bbbc.ngrok.io";
    }

    public List<NewsCategory> getAllCategories() throws Exception {
        List<NewsCategory> catList = new ArrayList<NewsCategory>();

        URL url = null;
        HttpURLConnection conn = null;
//        try {
            url = new URL(this.beginningURL + "/getAllCategories");
            conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("GET");

            // open connection and send HTTP request
            conn.connect();

            // now the response comes back
            int responsecode = conn.getResponseCode();
            if (responsecode != 200) {
                throw new IllegalStateException();
            }
//        } catch (Exception e) {
//            if (conn != null) {
//                conn.disconnect();
//            }
//            throw e;
//        }

        try {
            JSONParser parser = new JSONParser();
            Scanner in = new Scanner(url.openStream());
            while (in.hasNext()) {
                String inNext = in.nextLine();
                Iterator catIt = ((JSONArray)parser.parse((inNext))).iterator();
                while (catIt.hasNext()) {
                    JSONObject next = (JSONObject) catIt.next();
                    String name = (String) next.get("name");
                    String imgURL = (String) next.get("img");

                    catList.add(new NewsCategory(name, imgURL));
                }
            }
        } catch (Exception e) {
            if (conn != null) {
                conn.disconnect();
            }
            return new ArrayList<NewsCategory>();
        }

        if (conn != null) {
            conn.disconnect();
        }

        return catList;
    }
}
