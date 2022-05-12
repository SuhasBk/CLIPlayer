package com.ytmusic.player.utils;

import com.cliplayer.model.Song;
import com.ytmusic.player.constants.UIConstants;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class YTUtils {
    public static Song mapSearchResult(WebElement result) {
        String[] metaData =
                result.findElement(By.cssSelector(UIConstants.YTMUSIC.SEARCH_PAGE.META_DATA)).getText().split("•");
        return new Song(
                result.findElement(By.cssSelector(UIConstants.YTMUSIC.SEARCH_PAGE.TITLE)).getText(),
                metaData[0],
                metaData[2],
                result.findElement(By.cssSelector(UIConstants.YTMUSIC.SEARCH_PAGE.SONG_LINK)).getAttribute("href"),
                result.findElement(By.cssSelector(UIConstants.YTMUSIC.SEARCH_PAGE.CONTEXT_MENU)));
    }
}
