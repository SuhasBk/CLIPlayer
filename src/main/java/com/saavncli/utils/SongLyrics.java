package com.saavncli.utils;

import com.saavncli.constants.UIConstants;
import org.jsoup.Jsoup;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class SongLyrics {

    public static void printLyrics(WebDriver browser, String query) {
        String lyrics = null;
        JavascriptExecutor exe = (JavascriptExecutor) browser;

        try {
            ApplicationUtils.clickWebElement(exe,
                    browser.findElement(By.cssSelector(UIConstants.PLAYER.CURRENT_SONG_MENU)), 1D);
            ApplicationUtils.clickWebElement(exe,
                    browser.findElement(By.cssSelector(UIConstants.LYRICS.SONG_DETAILS)), 1D);

            if (browser.findElements(By.cssSelector(UIConstants.LYRICS.LANGUAGE)).get(1).getText().contains("English")) {
                String first = Jsoup.connect("https://search.azlyrics.com/search.php?q=" + query)
                        .get()
                        .body()
                        .selectFirst(UIConstants.LYRICS.SEARCH_RESULTS_LINKS)
                        .getElementsByTag("a")
                        .first()
                        .attr("href");

                lyrics = Jsoup.connect(first)
                        .get()
                        .select(UIConstants.LYRICS.LYRICS_DIV).get(4)
                        .wholeText();
            } else {
                ApplicationUtils.clickWebElement(exe,
                        browser.findElement(By.cssSelector(UIConstants.LYRICS.LYRICS_LINK)), 1D);

                lyrics = browser.findElement(By.cssSelector(UIConstants.LYRICS.LYRICS_TEXT)).getText();
            }

            System.out.println("\n" + lyrics);
        } catch (Exception e) {
            System.out.println("\nLyrics not found... sorry 😓\n");
        }
    }
}
