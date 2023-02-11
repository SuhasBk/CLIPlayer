package com.saavn.player;

import java.util.List;
import java.util.stream.Collectors;

import org.openqa.selenium.*;

import com.cliplayer.constants.AppConstants;
import com.cliplayer.model.Song;
import com.cliplayer.player.GenericWebPlayer;
import com.cliplayer.utils.ApplicationUtils;
import com.saavn.player.constants.UIConstants;
import com.saavn.player.utils.SaavnUtils;

public class SaavnCLI implements GenericWebPlayer {
    private static final String BASE_URL = "https://jiosaavn.com";

    private final WebDriver browser;
    private final JavascriptExecutor executor;

    private WebElement NEXT_BUTTON;
    private WebElement PREVIOUS_BUTTON;
    private WebElement PLAY_PAUSE_BUTTON;
    private WebElement REPEAT_BUTTON;

    private boolean isRepeatOn = false;

    public Song currentSong;

    public SaavnCLI(WebDriver browser) {
        this.browser = browser;
        this.executor = (JavascriptExecutor) this.browser;
    }

    public List<Song> search(String query) {
        String url = String.format(BASE_URL);
        this.browser.get(url);

        WebElement searchBar = this.browser.findElement(By.cssSelector(UIConstants.SAAVN.SEARCH_PAGE.SEARCH_BAR));
        searchBar.sendKeys(query);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        searchBar.sendKeys(Keys.ENTER);

        return this.browser.findElements(By.cssSelector(UIConstants.SAAVN.SEARCH_PAGE.SEARCH_RESULTS))
                .stream()
                .limit(AppConstants.MAX_SEARCH_RESULTS)
                .map(SaavnUtils::mapSearchResult)
                .collect(Collectors.toList());
    }

    public void initializePlayer(Song song) {
        this.clickButtons(song.getContextMenu());
        this.browser.findElement(By.cssSelector(UIConstants.SAAVN.SEARCH_PAGE.PLAY_NOW)).click();

        this.updateCurrentSong();
        this.PLAY_PAUSE_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.SAAVN.PLAYER.PLAY_PAUSE_BUTTON));
        this.REPEAT_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.SAAVN.PLAYER.REPEAT_BUTTON));
        this.NEXT_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.SAAVN.PLAYER.NEXT_BUTTON));
        this.PREVIOUS_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.SAAVN.PLAYER.PREVIOUS_BUTTON));

        new Thread(() -> {
            while(true){
                try {
                    int currVol = Integer.parseInt(this.executor.executeScript(UIConstants.SAAVN.JS.GET_MAX_VOLUME).toString());
                    if(currVol != 100)
                        this.executor.executeScript(UIConstants.SAAVN.JS.SET_MAX_VOLUME);
                    Thread.sleep(10000);
                } catch (Exception e) {}
            }
        }).start();
    }

    public Song getCurrentSong() {
        return this.currentSong;
    }

    public void togglePlayPause() {
        this.clickButtons(this.PLAY_PAUSE_BUTTON);
    }

    public void nextSong() {
        try {
            this.clickButtons(this.NEXT_BUTTON);
            this.updateCurrentSong();
            this.displaySongInfo();
        } catch (ElementClickInterceptedException e) {
            System.out.println("\nCan't do that right now 😬. End of playlist maybe. Try playing a new song from the " +
                    "menu.\n");
        }
    }

    public void previousSong() {
        try {
            this.clickButtons(this.PREVIOUS_BUTTON);
            this.updateCurrentSong();
            this.displaySongInfo();
        } catch (ElementClickInterceptedException e) {
            System.out.println("\nCan't do that right now 😬. End of playlist maybe. Try playing a new song from the " +
                    "menu.\n");
        }
    }

    public Boolean toggleRepeat() {
        WebElement repeatButton = this.REPEAT_BUTTON;
        if(isRepeatOn) {
            this.clickButtons(repeatButton);
            isRepeatOn = false;
        } else {
            this.clickButtons(repeatButton);
            this.clickButtons(repeatButton);
            isRepeatOn = true;
        }
        return isRepeatOn;
    }

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

    public void updateCurrentSong() {
        String songName = this.browser.findElement(By.cssSelector(UIConstants.SAAVN.PLAYER.SONG_NAME)).getAttribute("title");
        String artistName = this.browser.findElement(By.cssSelector(UIConstants.SAAVN.PLAYER.ARTIST_NAME)).getText();
        String songLink = this.browser.findElement(By.cssSelector(UIConstants.SAAVN.PLAYER.SONG_NAME)).getAttribute("href");
        String time = this.browser.findElement(By.cssSelector(UIConstants.SAAVN.PLAYER.TIME)).getText();

        this.currentSong = new Song(songName,artistName, time,songLink,null);
    }

    public void seek(String seconds) {
        String command = String.format(UIConstants.SAAVN.JS.SEEK_TIME, seconds);
        this.executor.executeScript(command);
    }

    public void clickButtons(WebElement element) {
        ApplicationUtils.clickWebElement(this.executor, element);
    }

    public void getShareLink() {
        this.updateCurrentSong();
        String link = this.currentSong.getSongLink();
        System.out.println("\n👐 Share this link: " + link +" 👐\n");
    }
}
