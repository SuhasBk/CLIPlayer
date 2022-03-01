package com.saavncli.player;

import org.jsoup.Jsoup;

public class SongLyrics {
    public static void printLyrics(String query) {
        try {
            String first = Jsoup.connect("https://search.azlyrics.com/search.php?q=" + query)
                    .get()
                    .body()
                    .selectFirst("td.text-left.visitedlyr")
                    .getElementsByTag("a")
                    .first()
                    .attr("href");

            String lyrics = Jsoup.connect(first)
                    .get()
                    .select("div.col-xs-12.col-lg-8.text-center>div").get(4)
                    .text();

            System.out.println(lyrics);
        } catch (Exception e) {
            System.out.println("\nLyrics not found.. sorry 😓\n");
        }
    }
}
