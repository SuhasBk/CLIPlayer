package com.cliplayer.utils;

import com.saavn.player.utils.SaavnUtils;
import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeBrowser {

    private static final String BASE_URL = "https://youtube.com";

    public static String searchYoutubeResults(String query) {
        String url = "";
        String doc = null;
        try {
            query = query.replaceAll("\"", "'");
            doc = Jsoup.connect(BASE_URL + "/results?search_query=" + query)
                    .get()
                    .outerHtml();
            Pattern pattern = Pattern.compile("\"videoId\":\"([A-Za-z0-9]+)\"");
            Matcher m = pattern.matcher(doc);
            if(m.find()) {
                url = BASE_URL + "/watch?v=" + m.group().split(":")[1].replaceAll("\"","");
            }
        } catch (IOException e) {
            System.out.println("\nCould not download current song! 😓\n");
        }
        return url;
    }

    public static void downloadVideo(String url) throws Exception {
        if(url!=null && url.length()>0) {
            String s = null;
            Process proc = ApplicationUtils.downloadYTMP3(url);
            BufferedReader op = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            while ((s = op.readLine()) != null) {
                System.out.print(s + "\r");
            }
            System.out.println("\rMP3 file successfully downloaded! 🥳\n");
        }
    }
}
