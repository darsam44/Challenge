package com.example.challenge.Acvtivities.Videos;

public class ModelVideo {
    String timestamp, title, videoUrl;

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }
//constructor

    public ModelVideo() {
    }

    public ModelVideo(String timestamp, String title, String videoUrl) {
        this.timestamp = timestamp;
        this.title = title;
        this.videoUrl = videoUrl;
    }
}
