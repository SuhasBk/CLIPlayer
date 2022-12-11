package com.cliplayer.constants;

import java.util.Optional;

import com.cliplayer.utils.ApplicationUtils;

public interface AppConstants {
    final String USERNAME = System.getProperty("user.name");

    final Integer MAX_SEARCH_RESULTS = 5;
    final String CLICK_COMMAND = "arguments[0].click();";
    final String CLEAR_SCREEN = "\033[H\033[2J";
    final String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/101.0.4951.41 Safari/537.36 Edg/101.0.1210.32";
    final Boolean DOCKERIZED = Optional.ofNullable(System.getenv("DOCKERIZED")).orElse("FALSE").equals("TRUE");

    final String USER_OS = ApplicationUtils.getUserOS();
    final String MAC_PROFILE = "/Users/${{USERNAME}}/Library/Application Support/Firefox/Profiles/";
    final String WIN_PROFILE = "C:\\Users\\${{USERNAME}}\\AppData\\Roaming\\Mozilla\\Firefox\\Profiles\\";
    final String LINUX_PROFILE = "/home/${{USERNAME}}/.mozilla/firefox/";

    final String DEFAULT_FIREFOX_PROFILE_NAME = "default-release";
    final String FIREFOX_UBLOCK_RELEASE = "https://github.com/gorhill/uBlock/releases";
    final String FIREFOX_UBLOCK_FILE = "firefox.signed.xpi";
    final String CHROME_UBLOCK_RELEASE = "https://clients2.google.com/service/update2/crx?response=redirect&prodversion=${{VERSION}}&acceptformat=crx2,crx3&x=id%3Dcjpalhdlnbpafiamejdnhcphjbkeiagm%26uc";
    final String CHROME_LATEST_VERSION_OMAHA = "https://omahaproxy.appspot.com/" + USER_OS.toLowerCase();
}
