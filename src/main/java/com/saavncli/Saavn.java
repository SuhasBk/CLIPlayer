package com.saavncli;

import com.saavncli.model.Song;
import com.saavncli.player.SaavnCLI;
import com.saavncli.utils.ApplicationUtils;
import com.saavncli.utils.BackgroundBrowser;
import com.saavncli.utils.SongLyrics;
import com.saavncli.utils.YoutubeBrowser;
import org.openqa.selenium.WebDriver;

import java.util.List;
import java.util.Scanner;

public class Saavn {

    public static void main(String[] args) {
        WebDriver browser = null;

        try {
            if(args.length == 0){
                browser = BackgroundBrowser.getChrome(false);
//                browser = BackgroundBrowser.getFirefox(false);
            } else {
                browser = BackgroundBrowser.getChrome(true);
//                browser = BackgroundBrowser.getFirefox(true);
            }

            System.out.println("\nWelcome to Saavn CLI! 🙌\n\nWhat would you like to listen today? 😁\n");

            Scanner sc = new Scanner(System.in);
            SaavnCLI cli = new SaavnCLI(browser);
            String query = ApplicationUtils.getSongName(sc);
            List<Song> results = cli.search(query);

            if(results.size() == 0) {
                System.out.println("No results found! Bye! 😕");
                throw new Exception("QUIT");
            }

            int choice = chooseAndPlay(sc, results);
            cli.initializePlayer(results.get(choice - 1));

            String prompt = "\n'1' : New Song\n'2' : Next Song\n'3' : " +
                    "Play/Pause\n'4' : Previous Song\n'5' : Seek Song\n'6' : Player Info\n" +
                    "'7' : Toggle Repeat\n'8' : Lyrics for Current Song...\n'9' : Download current song...\n'10' " +
                    ": Close Saavn\n\nEnter your choice...\n> ";

            while (true) {
                int user_choice = ApplicationUtils.getIntegerInput(sc, prompt, 11);

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
                        String secs = ApplicationUtils.getUserTimeInSeconds(sc, cli.currentSong);
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
                        String lyricsQuery = ApplicationUtils.getSearchQuery(cli.currentSong.getSongName(), cli.currentSong.getArtistName());
                        SongLyrics.printLyrics(lyricsQuery);
                        break;
                    case 9:
                        cli.updateCurrentSong();
                        String ytQuery = ApplicationUtils.getSearchQuery(cli.currentSong.getSongName(), cli.currentSong.getArtistName());
                        String url = YoutubeBrowser.searchYoutubeResults(ytQuery);
                        YoutubeBrowser.downloadVideo(url);
                        break;
                    case 10:
                        throw new Exception("QUIT");
                    case 50:
                        ApplicationUtils.takeErrorScreenshot(browser);
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
