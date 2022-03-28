package com.saavncli.utils;

import com.saavncli.constants.UIConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SongLyrics {

    public static void printLyrics(WebDriver browser) {
        try {
//            String first = Jsoup.connect("https://search.azlyrics.com/search.php?q=" + query)
//                    .get()
//                    .body()
//                    .selectFirst(UIConstants.LYRICS.SEARCH_RESULTS_LINKS)
//                    .getElementsByTag("a")
//                    .first()
//                    .attr("href");
//
//            String lyrics = Jsoup.connect(first)
//                    .get()
//                    .select(UIConstants.LYRICS.LYRICS_DIV).get(4)
//                    .wholeText();

            JavascriptExecutor exe = (JavascriptExecutor) browser;

            ApplicationUtils.clickWebElement(exe, browser.findElement(By.cssSelector(UIConstants.PLAYER.CURRENT_SONG_MENU)));
            ApplicationUtils.clickWebElement(exe, browser.findElement(By.cssSelector(UIConstants.LYRICS.SONG_DETAILS)));
            ApplicationUtils.clickWebElement(exe, browser.findElement(By.cssSelector(UIConstants.LYRICS.LYRICS_LINK)));

            String lyrics = browser.findElement(By.cssSelector(UIConstants.LYRICS.LYRICS_TEXT)).getText();

            System.out.println("\n" + lyrics);
        } catch (Exception e) {
            System.out.println("\nLyrics not found... sorry 😓\n");
        }
    }
}
