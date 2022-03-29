package com.saavncli.constants;

public interface UIConstants {
    interface SEARCH_PAGE
    {
        String SEARCH_RESULTS = "article.o-snippet--draggable";
        String SONG_NAME = "h4>a";
        String ARTIST_NAME = "p>a";
        String SONG_TIME = ".o-snippet__action-init.u-centi";

        String CONTEXT_MENU = ".o-icon-ellipsis";
        String PLAY_NOW = "a[data-menu-icon='q']";
    }

    interface PLAYER
    {
        String REPEAT_BUTTON = "#player_repeat";
        String PLAY_PAUSE_BUTTON = "#player_play_pause";
        String NEXT_BUTTON = "#player_next";
        String PREVIOUS_BUTTON = "#player_prev";
        String TIME = ".u-centi.u-valign-text-bottom";

        String SONG_NAME = "#player figcaption h4 a";
        String ARTIST_NAME = "#player figcaption p a";
        String CURRENT_SONG_MENU = "#player_ellipsis";
    }

    interface JS
    {
        String SET_MAX_VOLUME = "MUSIC_PLAYER.setVolume(100);";
        String GET_MAX_VOLUME = "return MUSIC_PLAYER.getVolume()";
        String SEEK_TIME = "MUSIC_PLAYER.seek(%s)";
        String CLICK_COMMAND = "arguments[0].click();";
    }

    interface LYRICS
    {
        String SEARCH_RESULTS_LINKS = "td.text-left.visitedlyr";
        String LYRICS_DIV = "div.col-xs-12.col-lg-8.text-center>div";
        String SONG_DETAILS = "a[title='Song Details & Lyrics']";
        String LYRICS_LINK = "a[title='Song Lyrics']";
        String LYRICS_TEXT = ".u-disable-select p";
        String LANGUAGE = "span.u-visible-visually\\@lg";
    }
}
