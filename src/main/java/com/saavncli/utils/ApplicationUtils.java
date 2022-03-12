package com.saavncli.utils;

import com.saavncli.constants.UIConstants;
import com.saavncli.model.Song;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.nio.file.Files;
import java.util.Optional;
import java.util.Scanner;

public class ApplicationUtils {

    public static String getSongName(Scanner sc) {
        System.out.print("Enter the song name:\n> ");
        return sc.nextLine();
    }

    public static Song mapSearchResult(WebElement result) {
        return new Song(
                result.findElement(By.cssSelector(UIConstants.SEARCH_PAGE.SONG_NAME)).getText(),
                result.findElement(By.cssSelector(UIConstants.SEARCH_PAGE.ARTIST_NAME)).getText(),
                result.findElements(By.cssSelector(UIConstants.SEARCH_PAGE.SONG_TIME)).get(1).getText(),
                result.findElement(By.cssSelector(UIConstants.SEARCH_PAGE.SONG_NAME)).getAttribute("href"),
                result.findElement(By.cssSelector(UIConstants.SEARCH_PAGE.CONTEXT_MENU))
        );
    }

    public static Integer getIntegerInput(Scanner sc, String prompt, int limit) {
        int choice = 0;
        while (choice <= 0 || choice > limit) {
            try {
                System.out.print(prompt);
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nNumber dude, number! 😑️");
            }
        }
        return choice;
    }

    public static void takeErrorScreenshot(WebDriver browser) {
        TakesScreenshot ss = (TakesScreenshot) browser;
        File src = ss.getScreenshotAs(OutputType.FILE);
        try {
            File dest = new File(Files.createTempFile("err", ".png").toAbsolutePath().toString());
            FileUtils.copyFile(src, dest);
            Runtime.getRuntime().exec("open " + dest.getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
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

    public static Process downloadYTMP3(String url) throws Exception {
        String[] downloadCommand = {"youtube-dl", "--extract-audio", "--audio-format", "mp3", url};
        return Runtime.getRuntime().exec(downloadCommand);
    }

    public static String getSearchQuery(String songName, String artistName) {
        return songName + " " + artistName;
    }
 }
