package com.cliplayer.utils;

import com.cliplayer.constants.AppConstants;
import com.cliplayer.player.GenericWebPlayer;
import com.saavn.player.SaavnCLI;
import com.ytmusic.player.YTMusicCLI;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Supplier;

public class ApplicationUtils {

    public static Map getRegisteredServices() {
        Map<Integer, String> services = new HashMap<>();
        services.put(1, "SAAVN");
        services.put(2, "YTMUSIC");
        return services;
    }
    public static String getSongName(Scanner sc) {
        System.out.print("Enter the song name:\n> ");
        String query = sc.nextLine();
        while(query.length() == 0) {
            System.out.print("\nSorry, come again? 😅\n> ");
            query = sc.nextLine();
        }
        return query;
    }

    public static Integer getIntegerInput(Scanner sc, String prompt) {
        int choice = 0;
        while (choice <= 0) {
            try {
                System.out.print(prompt);
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nNumber dude, number! 😑️");
            }
        }
        return choice;
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

    public static Process downloadYTMP3(String url) throws Exception {
        String[] downloadCommand = {"youtube-dl", "--extract-audio", "--audio-format", "mp3", url};
        return Runtime.getRuntime().exec(downloadCommand);
    }

    public static String getSearchQuery(String songName, String artistName) {
        return songName + " " + artistName;
    }

    public static void sleep(Double seconds) {
        try {
            Long ms = seconds.longValue() * 1000;
            Thread.sleep(ms);
        } catch (InterruptedException e) {}
    }

    public static void clickWebElement(JavascriptExecutor executor, WebElement element) {
        executor.executeScript(AppConstants.CLICK_COMMAND, element);
    }

    public static void clickWebElement(JavascriptExecutor executor, WebElement element, Double suspendTime) {
        executor.executeScript(AppConstants.CLICK_COMMAND, element);
        sleep(suspendTime);
    }
}
