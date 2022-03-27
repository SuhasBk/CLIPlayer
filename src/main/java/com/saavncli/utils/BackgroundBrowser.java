package com.saavncli.utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

public class BackgroundBrowser {

    public static WebDriver getChrome(Boolean debug) {
        WebDriver browser = null;

        try {
            WebDriverManager.chromedriver().setup();

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
                        .addArguments("-width 600"));
            } else {
                browser = new FirefoxDriver();
                browser.manage().window().maximize();
            }

            browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
                        .addArguments("--window-size=1400,600"));
            } else {
                browser = new EdgeDriver();
                browser.manage().window().maximize();
            }

            browser.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        } catch(Exception e) {
            System.out.println("\n Can't start the application. Browser driver issues. \n Ciao.\n\n" + e);
            System.exit(1);
        }

        return browser;
    }
}
