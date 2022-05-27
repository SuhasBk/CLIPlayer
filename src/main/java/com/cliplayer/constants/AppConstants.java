package com.cliplayer.constants;

import java.util.Optional;

public interface AppConstants {
    Integer MAX_SEARCH_RESULTS = 5;
    String CLICK_COMMAND = "arguments[0].click();";
    String CLEAR_SCREEN = "\033[H\033[2J";

    String USER_AGENT = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 " +
            "(KHTML, like Gecko) Chrome/101.0.4951.41 Safari/537.36 Edg/101.0.1210.32";

    Boolean DOCKERIZED = Optional.ofNullable(System.getenv("DOCKERIZED")).orElse("FALSE").equals("TRUE");
}
