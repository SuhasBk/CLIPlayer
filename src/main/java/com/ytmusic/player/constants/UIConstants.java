package com.ytmusic.player.constants;

public interface UIConstants {

    interface YTMUSIC {

        interface SEARCH_PAGE {
            String SONGS_FILTER = "a[title='Show song results']";
            String CONTENTS = "#contents";
            String RESULTS = "ytmusic-responsive-list-item-renderer";
            String TITLE = ".title";
            String META_DATA = ".flex-column";
            String SONG_LINK = ".title a";
            String CONTEXT_MENU = "#play-button";
            String SEARCH_INPUT = "input";
        }

        interface PLAYER {
            String SONG_NAME = "ytmusic-player-bar .title";
            String ARTIST_NAME = "ytmusic-player-bar .byline a";
            String TIME = "ytmusic-player-bar .time-info";
            String PLAY_PAUSE_BUTTON = "ytmusic-player-bar #play-pause-button";
            String NEXT_BUTTON = "ytmusic-player-bar .next-button";
            String PREVIOUS_BUTTON = "ytmusic-player-bar .previous-button";
            String REPEAT_BUTTON = "ytmusic-player-bar .repeat";
        }

    }
}
