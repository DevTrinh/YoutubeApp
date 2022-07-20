package com.example.youtubeapp.item;

import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.youtubeapp.fragment.FragmentHome;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class ItemVideoInChannel {
    private String titleVideo;
    private String timeUpVideo;
    private String viewCount;
    private String urlImage;
    private String idVideo;

    public String getTitleVideo() {
        return titleVideo;
    }

    public void setTitleVideo(String titleVideo) {
        this.titleVideo = titleVideo;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getTimeUpVideo() {
        return formatTimeUpVideo(timeUpVideo);
    }

    public void setTimeUpVideo(String timeUpVideo) {
        this.timeUpVideo = timeUpVideo;
    }

    public String getViewCount() {
        return formatData(Integer.parseInt(viewCount+"1"));
    }

    public void setViewCount(String viewCount) {
        this.viewCount = viewCount;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public ItemVideoInChannel(String titleVideo, String timeUpVideo, String urlImage, String idVideo) {
        this.titleVideo = titleVideo;
        this.timeUpVideo = timeUpVideo;
        this.urlImage = urlImage;
        this.idVideo = idVideo;
    }

    public String getIdVideo() {
        return idVideo;
    }

    public void setIdVideo(String idVideo) {
        this.idVideo = idVideo;
    }

    @NonNull
    public static String formatData(int value) {
        String arr[] = {"", "K", "M", "B", "T", "P", "E"};
        int index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s%s", decimalFormat.format(value), arr[index]);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatTimeUpVideo(String time) {
        String timeEnd = java.time.Clock.systemUTC().instant().toString();
        String timeStart = time;
        Instant start = Instant.parse(timeStart);
        Instant end = Instant.parse(timeEnd);

        long duration = Duration.between(start, end).toMillis();
        int hour = (int) TimeUnit.MILLISECONDS.toHours(duration);
        int min = (int) (TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.MILLISECONDS.toHours(duration) * 60);
//        int second = (int) (TimeUnit.MILLISECONDS.toSeconds(duration) - minutes);
        String timeUp = "";
        if (hour > 8760) {
            timeUp = (hour / 8760) + " year ago";
        }
        if (hour > 720 && hour < 8760) {
            timeUp = (hour / 720) + " month ago";
        }
        if (hour > 168 && hour < 720) {
            timeUp = (hour / 168) + " week ago";
        }
        if (hour < 168 && hour > 24) {
            timeUp = (hour / 24) + " day ago";
        }
        if (hour > 1 && hour < 24) {
            timeUp = (hour) + " hour ago";
        }
        if (hour < 1) {
            timeUp = min + "min ago";
        }
        return timeUp;
    }
}
