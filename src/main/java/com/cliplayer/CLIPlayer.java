package com.cliplayer;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.openqa.selenium.WebDriver;

import com.cliplayer.constants.AppConstants;
import com.cliplayer.model.Song;
import com.cliplayer.player.GenericWebPlayer;
import com.cliplayer.utils.ApplicationUtils;
import com.cliplayer.utils.BackgroundBrowser;
import com.cliplayer.utils.SongLyrics;
import com.cliplayer.utils.YoutubeBrowser;
import com.saavn.player.SaavnCLI;
import com.saavn.player.utils.SaavnUtils;
import com.ytmusic.player.YTMusicCLI;

public class CLIPlayer {

    public static void main(String[] args) {
        GenericWebPlayer cli = null;
        Scanner sc = new Scanner(System.in);
        WebDriver browser = null;
        Map<Integer, String> registeredServices = ApplicationUtils.getRegisteredServices();

        try {
            if(AppConstants.DOCKERIZED) {
                browser = BackgroundBrowser.getDockerBrowser();
            } else {
                browser = BackgroundBrowser.getEdge(args.length != 0);
            }

            System.out.println(AppConstants.CLEAR_SCREEN);
            System.out.println("\n🙌 Welcome to CLI Music Player! 🙌");

            int serviceId = ApplicationUtils.getIntegerInput(sc,
                    "\n\nService, service which service do you choose?\n\n" +
                    "'1' : Saavn (ad-free 🥳) \n" +
                    "'2' : YouTube Music (w/ ads 😅)\n\n> ", 2);

            if(registeredServices.get(serviceId).equals("SAAVN")) {
                cli = new SaavnCLI(browser);
            } else if (registeredServices.get(serviceId).equals("YTMUSIC")) {
                cli = new YTMusicCLI(browser);
            }

            String query = ApplicationUtils.getSongName(sc);
            System.out.println("\nSearching for " + query + "\n");
            List<Song> results = cli.search(query);

            if(results.size() == 0) {
                System.out.println("No results found! Bye! 😕");
                throw new Exception("QUIT");
            }

            int choice = chooseAndPlay(sc, results);
            cli.initializePlayer(results.get(choice - 1));

            String prompt = "\n" +
                    "'1' : New Song\n" +
                    "'2' : Next Song\n" +
                    "'3' : Play/Pause\n" +
                    "'4' : Previous Song\n" +
                    "'5' : Seek Song\n" +
                    "'6' : Player Info\n" +
                    "'7' : Toggle Repeat\n" +
                    "'8' : Lyrics for Current Song...\n" +
                    "'9' : Download current song...\n" +
                    "'10' : Share this song...\n" +
                    "'11' : Close "+ cli.getClass().getSimpleName() +"\n" +
                    "\nEnter your choice...\n> ";

            while (true) {
                int user_choice = ApplicationUtils.getIntegerInput(sc, prompt);

                switch (user_choice) {
                    case 1:
                        query = ApplicationUtils.getSongName(sc);
                        System.out.println("\nSearching for " + query + "\n");
                        results = cli.search(query);

                        if(results.size() == 0) {
                            System.out.println("No results found! Try again! 😕");
                            break;
                        }

                        choice = chooseAndPlay(sc, results);
                        cli.initializePlayer(results.get(choice - 1));
                        break;
                    case 2:
                        cli.nextSong();
                        break;
                    case 3:
                        cli.togglePlayPause();
                        break;
                    case 4:
                        cli.previousSong();
                        break;
                    case 5:
                        cli.updateCurrentSong();
                        String secs = SaavnUtils.getUserTimeInSeconds(sc, cli.getCurrentSong());
                        cli.seek(secs);
                        cli.displaySongInfo();
                        break;
                    case 6:
                        cli.displaySongInfo();
                        break;
                    case 7:
                        if(cli.toggleRepeat()) {
                            System.out.println("\nCurrent song will be repeated 🔂✅\n");
                        } else{
                            System.out.println("\nCurrent song will not be repeated 🔂❌\n");
                        }
                        break;
                    case 8:
                        cli.updateCurrentSong();
                        String lyricsQuery = ApplicationUtils.getSearchQuery(cli.getCurrentSong().getSongName(),
                                cli.getCurrentSong().getArtistName());
                        SongLyrics.printLyrics(browser, lyricsQuery, cli.getClass().getSimpleName());
                        break;
                    case 9:
                        cli.updateCurrentSong();
                        String ytQuery = ApplicationUtils.getSearchQuery(cli.getCurrentSong().getSongName(),
                                cli.getCurrentSong().getArtistName());
                        String url = YoutubeBrowser.searchYoutubeResults(ytQuery);
                        YoutubeBrowser.downloadVideo(url);
                        break;
                    case 10:
                        cli.getShareLink();
                        break;
                    case 11:
                        throw new Exception("QUIT");
                    case 50:
                        ApplicationUtils.takeErrorScreenshot(browser);
                        break;
                    default:
                        System.out.println("Nice!");
                }
            }
        } catch (Exception e) {
            if(!e.getMessage().contains("QUIT")) {
                System.out.println("Something went horribly wrong 😰 : " + e);
                ApplicationUtils.takeErrorScreenshot(browser);
            }
            else
                System.out.println("Thank you for using this software. Keep rockin' 🤘 🎸");
        } finally {
            if(browser != null) browser.quit();
            System.exit(0);
        }
    }

    private static int chooseAndPlay(Scanner sc, List<Song> results) {
        int choice;

        for(Song song: results) {
            String detail = String.format("\n%d: %-15s\t\t%-15s\t\t%-15s\n", results.indexOf(song) + 1,
                    song.getSongName(),
                    song.getArtistName(), song.getTime());
            System.out.println(detail);
        }

        choice = ApplicationUtils.getIntegerInput(sc, "Enter the choice from above\n> ",
                results.size());

        return choice;
    }
}
