package com.saavncli.model;

import org.openqa.selenium.WebElement;

public class Song {
    private String songName;
    private String artistName;
    private String time;
    private String songLink;
    private WebElement playLink;

    public Song(String songName, String artistName, String time, String songLink, WebElement playLink) {
        this.songName = songName;
        this.artistName = artistName;
        this.time = time;
        this.songLink = songLink;
        this.playLink = playLink;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    public WebElement getPlayLink() {
        return playLink;
    }

    public void setPlayLink(WebElement playLink) {
        this.playLink = playLink;
    }
}
