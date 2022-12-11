package com.cliplayer.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.Arrays;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;

import com.cliplayer.constants.AppConstants;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BackgroundBrowser {

    private static final Integer TIMEOUT = 10;

    public static WebDriver getDockerBrowser() {
        WebDriver browser = null;
        try {
            System.setProperty("webdriver.chrome.driver","/app/bin/chromedriver");
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            System.setProperty("webdriver.chrome.args", "--disable-logging");
            System.setProperty("webdriver.chrome.silentOutput", "true");
            options.addArguments("--disable-dev-shm-usage"); // overcome limited resource problems
            options.addArguments("disable-infobars"); // disabling infobars
            options.addArguments("--disable-extensions"); // disabling extensions
            options.addArguments("--disable-gpu"); // applicable to windows os only
            options.addArguments("window-size=1024,768"); // Bypass OS security model
            options.addArguments(String.format("--user-agent=%s", AppConstants.USER_AGENT));

            browser = new ChromeDriver(options);
            browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        } catch(Exception e) {
            System.out.println("\n Can't start the application. Browser driver issues. \n Ciao.\n\n" + e);
            System.exit(1);
        }

        return browser;
    }

    public static WebDriver getChrome(Boolean debug) {
        WebDriver browser = null;

        try {
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions()
                                        .addExtensions(Arrays.asList(fetchuBlockExtension("chrome")));

            if(!debug) {
                options
                    .addArguments("--headless=chrome")
                    .addArguments("start-maximized")
                    .addArguments("--window-size=1400,600")
                    .addArguments(String.format("--user-agent=%s", AppConstants.USER_AGENT));
                browser = new ChromeDriver(options);
            } else {
                browser = new ChromeDriver(options);
                browser.manage().window().maximize();
            }

            browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        } catch(Exception e) {
            System.out.println("\n Can't start the application. Browser driver issues. \n Ciao.\n\n" + e);
            System.exit(1);
        }

        return browser;
    }

    public static WebDriver getFirefox(Boolean debug) {
        WebDriver browser = null;

        try {
            WebDriverManager.firefoxdriver().setup();
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, ApplicationUtils.getOSNullPath());

            if(!debug) {
                browser = new FirefoxDriver(new FirefoxOptions()
                        .setProfile(getFirefoxCustomProfile())
                        .setHeadless(true)
                        .addArguments("-height 1400")
                        .addArguments("-width 600")
                        .addPreference("general.useragent.override", AppConstants.USER_AGENT));
            } else {
                browser = new FirefoxDriver(new FirefoxOptions()
                        .setProfile(getFirefoxCustomProfile()));
                browser.manage().window().maximize();
            }

            browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        } catch(Exception e) {
            System.out.println("\n Can't start the application. Browser driver issues. \n Ciao.\n\n" + e);
            System.exit(1);
        }

        return browser;
    }

    private static FirefoxProfile getFirefoxCustomProfile() throws Exception {
        File profileName = getFirefoxProfileDirectory();
        FirefoxProfile profile = new FirefoxProfile(profileName);
        // profile.addExtension(fetchuBlockExtension("firefox"));
        return profile;
    }

    private static File getFirefoxProfileDirectory() throws Exception {
        String os = AppConstants.USER_OS;
        String username = AppConstants.USERNAME;
        String baseDir = null;
        String unamePlaceholder = "${{USERNAME}}";

        switch (os) {
            case "MAC" -> baseDir = AppConstants.MAC_PROFILE.replace(unamePlaceholder, username);
            case "WIN" -> baseDir = AppConstants.WIN_PROFILE.replace(unamePlaceholder, username);
            default -> baseDir = AppConstants.LINUX_PROFILE.replace(unamePlaceholder, username);
        }

        Path profileName = Files.list(Path.of(Optional.ofNullable(baseDir).orElseThrow()))
                .filter(path -> path.toString().endsWith(AppConstants.DEFAULT_FIREFOX_PROFILE_NAME))
                .findFirst()
                .orElseThrow();

        return new File(profileName.toAbsolutePath().toString());
    }

    private static File fetchuBlockExtension(String browser) throws Exception {
        String downloadUrl = null;
        File uBlockFile = null;
                
        switch(browser) {
            case "firefox":
                downloadUrl = Jsoup.connect(AppConstants.FIREFOX_UBLOCK_RELEASE)
                    .get()
                    .body()
                    .getElementsByTag("a")
                    .stream()
                    .filter((a) -> a.attr("href").endsWith(AppConstants.FIREFOX_UBLOCK_FILE))
                    .findFirst().orElseThrow().attr("href");
                uBlockFile = new File("ublock.xpi");
                break;
            case "chrome":
                String version = fetchLatestchromeVersion();    //"108.0.5359.98";
                downloadUrl = AppConstants.CHROME_UBLOCK_RELEASE.replace("${{VERSION}}", version);
                uBlockFile = new File("ublock.crx");
                break;
            default:
                downloadUrl = null;
        }
        ApplicationUtils.downloadFileFromUrl(Optional.ofNullable(downloadUrl).orElseThrow(), uBlockFile);
        return uBlockFile;
    }

    private static String fetchLatestchromeVersion() throws Exception {
        return Jsoup.connect(AppConstants.CHROME_LATEST_VERSION_OMAHA).get().body().text();
    }
}
