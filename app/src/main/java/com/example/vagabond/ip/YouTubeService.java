package com.example.vagabond.ip;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by vagabond on 3/28/17.
 */

public class YouTubeService {
    public static void findVideos(Callback callback){

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YOUTUBE_BASE_URL).newBuilder();
            urlBuilder.addQueryParameter(Constants.KEY,Constants.YOUTUBE_KEY);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public ArrayList<Video> processResults(Response response){
        ArrayList<Video> videos = new ArrayList<>();
        try{
            String jsonData = response.body().string();
            if(response.isSuccessful()){
                JSONObject youtubeJSON = new JSONObject(jsonData);
                JSONArray itemsJSON = youtubeJSON.getJSONArray("items");
                for (int i = 0; i<itemsJSON.length();i++){
                    JSONObject videosJSON = itemsJSON.getJSONObject(i);
                    String title = videosJSON.getJSONObject("snippet").getString("title");
                    String channelId = videosJSON.getJSONObject("snippet").getString("channelTitle");
                    String videoId = videosJSON.getJSONObject("snippet").getJSONObject("resourceId").getString("videoId");
                    String imageUrl =videosJSON.getJSONObject("snippet").getJSONObject("thumbnails").getJSONObject("high").getString("url");
                    String description = videosJSON.getJSONObject("snippet").getString("description");

                    Video video = new Video(title,channelId,imageUrl,videoId,description);
                    videos.add(video);


                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return videos;
    }
}
