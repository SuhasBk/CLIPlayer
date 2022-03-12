package com.saavncli.model;

import org.openqa.selenium.WebElement;

public class Song {
    private String songName;
    private String artistName;
    private String time;
    private String songLink;
    private WebElement contextMenu;

    public Song(String songName, String artistName, String time, String songLink, WebElement contextMenu) {
        this.songName = songName;
        this.artistName = artistName;
        this.time = time;
        this.songLink = songLink;
        this.contextMenu = contextMenu;
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

    public WebElement getContextMenu() {
        return contextMenu;
    }

    public void setContextMenu(WebElement contextMenu) {
        this.contextMenu = contextMenu;
    }
}
