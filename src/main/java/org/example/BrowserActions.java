package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.apache.commons.io.FileUtils;
import java.io.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class BrowserActions {
    public static ThreadLocal<WebDriver> drivers = new ThreadLocal<>();
    private static List<String> windowsHandlers;
    private static int currentTabIndex;

    public BrowserActions() {
        windowsHandlers = new ArrayList<String>();
    }

    public static String createNewTab(String link, By expectedElementLocator) {
        String currentHandler = drivers.get().getWindowHandle();
        if (!windowsHandlers.contains(currentHandler)) {
            windowsHandlers.add(currentHandler);
        }
        drivers.get().switchTo().newWindow(WindowType.TAB);
        navigateTo(link, expectedElementLocator);
        currentHandler = drivers.get().getWindowHandle();
        windowsHandlers.add(currentHandler);
        return currentHandler;
    }

    public static void switchToTab(int tabIndex) {
        try {
            drivers.get().switchTo().window(windowsHandlers.get(tabIndex));
        } catch (Exception e) {
            System.out.println("Failed to Switch to tab number: " + tabIndex + " Switching back to First Tab!...");
            drivers.get().switchTo().window(windowsHandlers.getFirst());
        }
    }

    public static void switchToNextTab() {
        try {
            if (BrowserActions.windowsHandlers.size() > 1) {
                currentTabIndex = windowsHandlers.indexOf(drivers.get().getWindowHandle());
                if (currentTabIndex == windowsHandlers.indexOf(windowsHandlers.getLast())) {
                    drivers.get().switchTo().window(windowsHandlers.get(1));
                } else {
                    drivers.get().switchTo().window(windowsHandlers.get(currentTabIndex + 1));
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to Switch to next tab Switching back to First Tab!...");
            drivers.get().switchTo().window(windowsHandlers.getFirst());
        }
    }

    public static Integer getCurrentTabIndex() {
        return windowsHandlers.indexOf(drivers.get().getWindowHandle());
    }

    public static void closeCurrentTab() {
        try {
            String currentWindowHandler = drivers.get().getWindowHandle();
            drivers.get().close();
            windowsHandlers.remove(currentWindowHandler);
            drivers.get().switchTo().window(windowsHandlers.getFirst());
        } catch (Exception e) {
            System.out.println("ERROR in Closing Tab No. " + currentTabIndex);
        }

    }

    public static void setWebDriver(BROWSERS browser) {
        switch (browser) {
            case CHROME:
                drivers.set(new ChromeDriver());
                break;

            case FIREFOX:
                WebDriverManager.firefoxdriver().setup();
                drivers.set(new FirefoxDriver());
                break;
        }
    }

    public static WebDriver getDriver() {
        return drivers.get();
    }

    public static void closeBrowserSession() {
        WebDriver driver = drivers.get();
        if (driver != null) {
            driver.quit();
            drivers.remove();
        }
    }

    public static void refreshTab() {
        drivers.get().navigate().refresh();
    }

    public static void maximizeWindow() {
        WebDriver driver = drivers.get();
        driver.manage().window().maximize();
    }

    public static void minimizeWindow() {
        WebDriver driver = drivers.get();
        driver.manage().window().minimize();
    }

    public static void navigateTo(String url, By expectedElementLocator) {
        WebDriver driver = drivers.get();
        driver.get(url);

        try {
            new WebDriverWait(driver, Duration.ofSeconds(30)).
                    until(ExpectedConditions.presenceOfElementLocated
                            (expectedElementLocator));
        } catch (Exception e) {
            driver.get(url);

            if (new WebDriverWait(driver, Duration.ofSeconds(30)).
                    until(ExpectedConditions.presenceOfElementLocated
                            (expectedElementLocator)) == null) {

                Assert.fail("Page not Found!");
            }
        }
    }

    public static void navigateBack() {
        WebDriver driver = drivers.get();
        driver.navigate().back();
    }

    public static void navigateForward() {
        WebDriver driver = drivers.get();
        driver.navigate().forward();
    }

    public static int getTabsTotalCount() {
        return windowsHandlers.size();
    }

    public static String getCurrentURL() {
        return drivers.get().getCurrentUrl();
    }

    public static String getPageTitle() {
        WebDriver driver = drivers.get();
        return driver.getTitle();
    }

    public static void takeScreenShot() throws IOException {
        File scrFile = ((TakesScreenshot) drivers.get()).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./image_" + ".png"));
    }

    public static void takeScreenShotOfElement(By by) throws IOException {
        File scrFile = drivers.get().findElement(by).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(scrFile, new File("./image_" + ".png"));
    }

    public enum BROWSERS {
        CHROME,
        FIREFOX
    }
}