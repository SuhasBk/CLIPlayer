package com.cliplayer.utils;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

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

            if(!debug) {
                browser = new ChromeDriver(new ChromeOptions()
                        .addArguments("--headless")
                        .addArguments("start-maximized")
                        .addArguments("--window-size=1400,600")
                        .addArguments(String.format("--user-agent=%s", AppConstants.USER_AGENT)));
            } else {
                browser = new ChromeDriver();
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

            if(!debug) {
                browser = new FirefoxDriver(new FirefoxOptions()
                        .setHeadless(true)
                        .addArguments("-height 1400")
                        .addArguments("-width 600")
                        .addPreference("general.useragent.override", AppConstants.USER_AGENT));
            } else {
                browser = new FirefoxDriver();
                browser.manage().window().maximize();
            }

            browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        } catch(Exception e) {
            System.out.println("\n Can't start the application. Browser driver issues. \n Ciao.\n\n" + e);
            System.exit(1);
        }

        return browser;
    }

    public static WebDriver getEdge(Boolean debug) {
        WebDriver browser = null;

        try {
            WebDriverManager.edgedriver().setup();

            if(!debug) {
                browser = new EdgeDriver(new EdgeOptions()
                        .setHeadless(true)
                        .addArguments("start-maximized")
                        .addArguments("--window-size=1400,600")
                        .addArguments(String.format("--user-agent=%s", AppConstants.USER_AGENT)));
            } else {
                browser = new EdgeDriver();
                browser.manage().window().maximize();
            }

            browser.manage().timeouts().implicitlyWait(Duration.ofSeconds(TIMEOUT));
        } catch(Exception e) {
            System.out.println("\n Can't start the application. Browser driver issues. \n Ciao.\n\n" + e);
            System.exit(1);
        }

        return browser;
    }
}
