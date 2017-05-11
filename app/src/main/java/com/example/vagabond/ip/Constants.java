package com.example.vagabond.ip;

/**
 * Created by vagabond on 3/28/17.
 */

public class Constants {
    public static final String YOUTUBE_KEY = BuildConfig.YOUTUBE_KEY;
    public static final String KEY = "key";
    public static final String YOUTUBE_BASE_URL = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId=PL9tY0BWXOZFvC7SwuIOJ7y7xx4hhpkcTT";
    public static final String FIREBASE_CHILD_VIDEOS = "videos";
    public static final String KEY_TYPE     = "anim_type";
    public static final String KEY_TITLE    = "anim_title";
    public static final String KEY_NAME     = "anim_name";

    public enum AnimType {
        ExplodeJava, ExplodeXML, SlideJava, SlideXML, FadeJava, FadeXML
    }
}
