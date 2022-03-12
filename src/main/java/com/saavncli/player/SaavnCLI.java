package com.saavncli.player;

import com.saavncli.constants.UIConstants;
import com.saavncli.model.Song;
import com.saavncli.utils.ApplicationUtils;
import org.openqa.selenium.*;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class SaavnCLI implements GenericWebPlayer {
    private static final String BASE_URL = "https://jiosaavn.com";
    private static final Integer MAX_RESULTS = 5;

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
        this.browser.get(BASE_URL);
    }

    public List<Song> search(String query) {
        String url = String.format(BASE_URL + "/search/%s", query);
        this.browser.get(url);

        return this.browser.findElements(By.cssSelector(UIConstants.SEARCH_PAGE.SEARCH_RESULTS))
                .stream()
                .limit(MAX_RESULTS)
                .map(ApplicationUtils::mapSearchResult)
                .collect(Collectors.toList());
    }

    public void initializePlayer(Song song) {
        this.clickButtons(song.getContextMenu());
        this.browser.findElement(By.cssSelector(UIConstants.SEARCH_PAGE.PLAY_NOW)).click();

        this.updateCurrentSong();
        this.PLAY_PAUSE_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.PLAYER.PLAY_PAUSE_BUTTON));
        this.REPEAT_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.PLAYER.REPEAT_BUTTON));
        this.NEXT_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.PLAYER.NEXT_BUTTON));
        this.PREVIOUS_BUTTON = this.browser.findElement(By.cssSelector(UIConstants.PLAYER.PREVIOUS_BUTTON));

        new Thread(() -> {
            while(true){
                try {
                    int currVol = Integer.parseInt(this.executor.executeScript(UIConstants.JS.GET_MAX_VOLUME).toString());
                    if(currVol != 100)
                        this.executor.executeScript(UIConstants.JS.SET_MAX_VOLUME);
                    Thread.sleep(10000);
                } catch (Exception e) {}
            }
        }).start();
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
        String songName = this.browser.findElement(By.cssSelector(UIConstants.PLAYER.SONG_NAME)).getAttribute("title");
        String artistName = this.browser.findElement(By.cssSelector(UIConstants.PLAYER.ARTIST_NAME)).getText();
        String songLink = this.browser.findElement(By.cssSelector(UIConstants.PLAYER.SONG_NAME)).getAttribute("href");
        String time = this.browser.findElement(By.cssSelector(UIConstants.PLAYER.TIME)).getText();

        this.currentSong = new Song(songName,artistName, time,songLink,null);
    }

    public void seek(String seconds) {
        String command = String.format(UIConstants.JS.SEEK_TIME, seconds);
        this.executor.executeScript(command);
    }

    public void clickButtons(@Nonnull WebElement element) {
        this.executor.executeScript(UIConstants.JS.CLICK_COMMAND, element);
    }
}
