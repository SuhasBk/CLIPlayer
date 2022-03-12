package com.saavncli.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class BackgroundBrowser {

    public static WebDriver getChrome(Boolean debug) {
        WebDriver browser;

        try {
            WebDriverManager.chromedriver().setup();
        } catch(Exception e) {
            System.out.println("\n Can't start the application. Browser driver issues. \n Ciao.");
            System.exit(1);
        }

        if(!debug) {
            ChromeOptions options = new ChromeOptions()
                    .addArguments("--headless")
                    .addArguments("start-maximized")
                    .addArguments("--window-size=1400,600");
            browser = new ChromeDriver(options);
        } else {
            browser = new ChromeDriver();
            browser.manage().window().maximize();
        }

        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        return browser;
    }

    public static WebDriver getFirefox(Boolean debug) {
        WebDriver browser;

        try {
            WebDriverManager.firefoxdriver().setup();
        } catch(Exception e) {
            System.out.println("\n Can't start the application. Browser driver issues. \n Ciao.");
            System.exit(1);
        }

        if(!debug) {
            browser = new FirefoxDriver(new FirefoxOptions()
                    .setHeadless(true)
                    .addArguments("-height 1400")
                    .addArguments("-width 600"));
        } else {
            browser = new FirefoxDriver();
            browser.manage().window().maximize();
        }

        browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        return browser;
    }
}
