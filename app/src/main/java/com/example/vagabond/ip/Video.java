package com.example.vagabond.ip;

import org.parceler.Parcel;

/**
 * Created by vagabond on 3/30/17.
 */

@Parcel
public class Video {
    private String mTitle;
    private String mImageUrl;
    private String mchannelTitle;
    private String mVideoId;
    private String mDescription;

    public Video(){}

    public Video(String title, String channelTitle, String imageUrl, String videoId,String description){
        this.mTitle = title;
        this.mchannelTitle = channelTitle;
        this.mImageUrl = imageUrl;
        this.mVideoId = videoId;
        this.mDescription = description;
    }

    public String getTitle(){
        return mTitle;
    }
    public String getChannelTitle(){
        return mchannelTitle;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getVideoId() {
        return mVideoId;
    }

    public String getDescription(){return mDescription;}
}
