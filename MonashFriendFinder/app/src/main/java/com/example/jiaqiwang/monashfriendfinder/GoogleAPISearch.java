package com.example.jiaqiwang.monashfriendfinder;

import com.example.jiaqiwang.monashfriendfinder.ServiceUse.RestConnection;
import com.example.jiaqiwang.monashfriendfinder.ServiceUse.RestResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by Jiaqi Wang on 5/5/2017.
 */

public class GoogleAPISearch {

    public GoogleAPISearch() {
    }

    public static String main() {
        return searchGoogleAPI("Monash University");
    }

    public static String searchGoogleAPI(String keyword) {
        String API_key = "AIzaSyAnWAbQMA2OT3arZozHq1TCCxONSJejGgs";
        String SEARCH_ID_cx = "005619748353146770221:9iodeyssfrw";
        keyword = keyword.replace(" ", "+");
        String[][] pathParams = new String[][]{};
        String[][] queryParams = new String[][]{};
        String strResponse = "";
        String snippet = "";
        RestConnection conn = new RestConnection("https://www.googleapis.com/customsearch/v1?key=" + API_key
                + "&cx=" + SEARCH_ID_cx + "&q=" + keyword + "&num=5", pathParams, queryParams);
        try {
            RestResponse response = conn.get();
            strResponse = response.getDataAsString();

            JSONObject json = new JSONObject(strResponse);
            JSONArray jsonarray = json.getJSONArray("items");
            if (jsonarray != null && jsonarray.length() > 0) {
                snippet = jsonarray.getJSONObject(0).getString("snippet");
            }
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return snippet;
    }

}
