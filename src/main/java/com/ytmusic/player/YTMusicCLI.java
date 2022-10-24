package com.ytmusic.player;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.cliplayer.constants.AppConstants;
import com.cliplayer.model.Song;
import com.cliplayer.player.GenericWebPlayer;
import com.cliplayer.utils.ApplicationUtils;
import com.ytmusic.player.constants.UIConstants;
import com.ytmusic.player.utils.YTUtils;

public class YTMusicCLI implements GenericWebPlayer {

    private static final String BASE_URL = "https://music.youtube.com";
    private final WebDriver browser;
    private final JavascriptExecutor executor;
    private WebElement NEXT_BUTTON;
    private WebElement PREVIOUS_BUTTON;
    private WebElement PLAY_PAUSE_BUTTON;
    private WebElement REPEAT_BUTTON;
    private boolean isRepeatOn = false;
    public Song currentSong;
    public YTMusicCLI(WebDriver browser) {
        this.browser = browser;
        this.executor = (JavascriptExecutor) this.browser;
    }

    @Override
    public List<Song> search(String query) {
        if(this.browser.getCurrentUrl().contains("music.youtube.com")) {
            WebElement input = this.browser.findElement(By.cssSelector(UIConstants.YTMUSIC.SEARCH_PAGE.SEARCH_INPUT));
            input.clear();
            input.sendKeys(query + Keys.ENTER);
        } else {
            String url = String.format(BASE_URL + "/search?q=%s", query.replaceAll(" ", "+"));
            this.browser.get(url);
        }

        ApplicationUtils.sleep(2D);
        this.browser.findElement(By.cssSelector(UIConstants.YTMUSIC.SEARCH_PAGE.SONGS_FILTER)).click();
        ApplicationUtils.sleep(2D);

        return this.browser.findElements(By.cssSelector(UIConstants.YTMUSIC.SEARCH_PAGE.CONTENTS)).get(2).findElements(By.cssSelector(UIConstants.YTMUSIC.SEARCH_PAGE.RESULTS))
                .stream()
                .limit(AppConstants.MAX_SEARCH_RESULTS)
                .map(YTUtils::mapSearchResult)
                .collect(Collectors.toList());
    }

    @Override
    public void initializePlayer(Song song) {
        this.clickButtons(song.getContextMenu());
        this.updateCurrentSong();
        this.PLAY_PAUSE_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.YTMUSIC.PLAYER.PLAY_PAUSE_BUTTON));
        this.REPEAT_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.YTMUSIC.PLAYER.REPEAT_BUTTON));
        this.NEXT_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.YTMUSIC.PLAYER.NEXT_BUTTON));
        this.PREVIOUS_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.YTMUSIC.PLAYER.PREVIOUS_BUTTON));
    }

    @Override
    public Song getCurrentSong() {
        return this.currentSong;
    }

    @Override
    public void togglePlayPause() {
        try {
            this.clickButtons(PLAY_PAUSE_BUTTON);
        } catch (Exception e) {
            System.out.println("\nCan't do that right now 😬. Try Again\n");
        }
    }

    @Override
    public void nextSong() {
        try {
            this.clickButtons(this.NEXT_BUTTON);
            this.updateCurrentSong();
            this.displaySongInfo();
        } catch (ElementClickInterceptedException e) {
            System.out.println("\nCan't do that right now 😬. Try Again\n");
        }
    }

    @Override
    public void previousSong() {
        try {
            this.clickButtons(this.PREVIOUS_BUTTON);
            this.updateCurrentSong();
            this.displaySongInfo();
        } catch (ElementClickInterceptedException e) {
            System.out.println("\nCan't do that right now 😬. Try Again\n");
        }
    }

    @Override
    public Boolean toggleRepeat() {
        try {
            WebElement repeatButton = this.REPEAT_BUTTON;
            if (isRepeatOn) {
                this.clickButtons(repeatButton);
                isRepeatOn = false;
            } else {
                this.clickButtons(repeatButton);
                this.clickButtons(repeatButton);
                isRepeatOn = true;
            }
        } catch (Exception e) {
            System.out.println("\nCan't do that right now 😬. Try Again\n");
        }
        return isRepeatOn;
    }

    @Override
    public void displaySongInfo() {
        this.updateCurrentSong();
        String details = "SONG NAME: " +
                this.currentSong.getSongName() + "\n" +
                "ARTIST NAME: " +
                this.currentSong.getArtistName() + "\n" +
                "SONG LINK: " +
                this.currentSong.getSongLink() + "\n" +
                "PLAYBACK TIME: " +
                this.currentSong.getTime();
        System.out.println("\n"+ details);
    }

    @Override
    public void updateCurrentSong() {
        boolean updated = false;
        while (!updated) {
            try {
                String songName = this.browser.findElement(By.cssSelector(UIConstants.YTMUSIC.PLAYER.SONG_NAME)).getText();
                String artistName = this.browser.findElement(By.cssSelector(UIConstants.YTMUSIC.PLAYER.ARTIST_NAME)).getText();
                String songLink = this.browser.getCurrentUrl().split("&")[0];
                String time = this.browser.findElement(By.cssSelector(UIConstants.YTMUSIC.PLAYER.TIME)).getText().trim();

                this.currentSong = new Song(songName, artistName, time, songLink, null);
                updated = true;
            } catch (Exception e) {
                System.out.println("Waiting for ads to finish 🫠");
                ApplicationUtils.sleep(10D);
                this.updateCurrentSong();
            }
        }
    }

    @Override
    public void seek(String seconds) {
        System.out.println("\nSorry. This feature is not supported in YouTube Music mode. 😢 Works in Saavn though 😬\n");
    }

    @Override
    public void getShareLink() {
        this.updateCurrentSong();
        String link = this.currentSong.getSongLink();
        System.out.println("\n👐 Share this link: " + link +" 👐\n");
    }

    public void clickButtons(WebElement element) {
        ApplicationUtils.clickWebElement(this.executor, element, 1D);
    }
}
