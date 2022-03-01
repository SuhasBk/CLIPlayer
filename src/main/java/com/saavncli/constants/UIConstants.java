package com.saavncli.constants;

public interface UIConstants {
    interface SEARCH_PAGE
    {
        String SEARCH_RESULTS = "article.o-snippet--draggable";
        String SONG_NAME = "h4>a";
        String ARTIST_NAME = "p>a";
        String SONG_TIME = ".o-snippet__action-init.u-centi";
        String PLAY_ICON = ".o-flag__img";
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
    }

    interface JS
    {
        String SET_MAX_VOLUME = "MUSIC_PLAYER.setVolume(100);";
        String GET_MAX_VOLUME = "return MUSIC_PLAYER.getVolume()";
        String SEEK_TIME = "MUSIC_PLAYER.seek(%s)";
        String CLICK_COMMAND = "arguments[0].click();";
    }
}
