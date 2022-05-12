package com.saavn.player.utils;

import com.saavn.player.constants.UIConstants;
import com.cliplayer.model.Song;
import org.openqa.selenium.*;

import java.util.Scanner;

public class SaavnUtils {

    public static Song mapSearchResult(WebElement result) {
        return new Song(
                result.findElement(By.cssSelector(UIConstants.SAAVN.SEARCH_PAGE.SONG_NAME)).getText(),
                result.findElement(By.cssSelector(UIConstants.SAAVN.SEARCH_PAGE.ARTIST_NAME)).getText(),
                result.findElements(By.cssSelector(UIConstants.SAAVN.SEARCH_PAGE.SONG_TIME)).get(1).getText(),
                result.findElement(By.cssSelector(UIConstants.SAAVN.SEARCH_PAGE.SONG_NAME)).getAttribute("href"),
                result.findElement(By.cssSelector(UIConstants.SAAVN.SEARCH_PAGE.CONTEXT_MENU))
        );
    }

    public static String getUserTimeInSeconds(Scanner sc, Song song) {
        String seconds = "";
        String[] time = song.getTime().replaceAll(" ", "").split("/");

        String curr = time[0];
        String max = time[1];

        try {
            int maxMinutes = Integer.parseInt(max.split(":")[0]);
            int maxSeconds = Integer.parseInt(max.split(":")[1]);

            System.out.printf("\nThe total duration of the track is : %s.\nThe current duration of the " +
                    "track is : %s.\nEnter the new time in '[mm:ss]' format :\n> %n", max, curr);

            String user_time = sc.nextLine();

            int minutes = Integer.parseInt(user_time.split(":")[0]);
            int secs = Integer.parseInt(user_time.split(":")[1]);

            if (minutes > maxMinutes || (minutes == maxMinutes && secs > maxSeconds)) {
                throw new Exception("");
            }

            seconds = Integer.toString(60 * minutes + secs);
        } catch(Exception e) {
            System.out.println("\nWrong time or time format\n");
        }

        return seconds;
    }
}
