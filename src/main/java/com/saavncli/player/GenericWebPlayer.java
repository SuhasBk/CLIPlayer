package com.saavncli.player;

import com.saavncli.model.Song;

import java.util.List;

public interface GenericWebPlayer {

    // given a query, search for results
    List<Song> search(String query);

    // once a song is selected from results, initialize player settings
    void initializePlayer(Song song);

    // play/pause current song
    void togglePlayPause();

    // skip to next song
    void nextSong();

    // play previous song
    void previousSong();

    // toggle repeat ON/OFF
    Boolean toggleRepeat();

    // show current song stats
    void displaySongInfo();

    // update state of current song in player
    void updateCurrentSong();

    // seek song time
    void seek(String seconds);
}
