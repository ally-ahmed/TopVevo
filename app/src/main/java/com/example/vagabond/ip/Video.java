package com.example.vagabond.ip;

import org.parceler.Parcel;

/**
 * Created by vagabond on 3/30/17.
 */

@Parcel
public class Video {
    String title;
    String imageUrl;
    String channelTitle;
    String videoId;
    String description;
    String pushId;

    public Video(){}

    public Video(String title, String channelTitle, String imageUrl, String videoId,String description){
        this.title = title;
        this.channelTitle = channelTitle;
        this.imageUrl = imageUrl;
        this.videoId = videoId;
        this.description = description;
    }

    public String getTitle(){
        return title;
    }
    public String getChannelTitle(){
        return channelTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getDescription(){return description;}


    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }
}
