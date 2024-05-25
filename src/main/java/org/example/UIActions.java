package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class UIActions {
    private WebDriver driver;

    public UIActions() {
        this.driver = BrowserActions.getDriver();
    }

    public void clickOn(By by, By expectedElementLocator) {
        try {

            new WebDriverWait(driver, Duration.ofSeconds(30)).
                    until(ExpectedConditions.elementToBeClickable(by));
            driver.findElement(by).click();

        } catch (Exception ignored) {
            System.out.println("Clicking FAILED as the element not found of this locator: " + by);
        }

        try {

            for (int i = 0; i < 5; i++) {
                if (!waitUntilVisibilityOf(expectedElementLocator, 30)) {
                    System.out.println("iteration no." + i + " element of " + expectedElementLocator + " NOT FOUND!!");
                    driver.findElement(by).click();
                } else {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Expected element not found of this locator: " + expectedElementLocator);
        }
    }

    public void clickOn(By by, By expectedElementLocator, int time) {
        try {

            new WebDriverWait(driver, Duration.ofSeconds(time)).
                    until(ExpectedConditions.elementToBeClickable(by));
            driver.findElement(by).click();

        } catch (Exception ignored) {
            System.out.println("Clicking FAILED as the element not found of this locator: " + by);
        }

        try {

            for (int i = 0; i < 5; i++) {
                if (!waitUntilVisibilityOf(expectedElementLocator, time)) {
                    System.out.println("iteration no." + i + " element of " + expectedElementLocator + " NOT FOUND!!");
                    driver.findElement(by).click();
                } else {
                    break;
                }
            }

        } catch (Exception e) {
            System.out.println("Expected element not found of this locator: " + expectedElementLocator);
        }
    }

    public void doubleClickOn(By by, By expectedElementLocator) {
        Actions act = new Actions(driver);
        try {

            new WebDriverWait(driver, Duration.ofSeconds(30)).
                    until(ExpectedConditions.elementToBeClickable(by));
            act.doubleClick(driver.findElement(by)).perform();

        } catch (Exception ignored) {
            System.out.println("DoubleClicking on element by By is Failed! " + by);
        }
        try {

            for (int i = 0; i < 5; i++) {

                if (!waitUntilVisibilityOf(expectedElementLocator, 30)) {
                    System.out.println("iteration no." + i + " element of " + expectedElementLocator + " NOT FOUND!!");
                    act.doubleClick(driver.findElement(by)).perform();

                } else {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("Expected element not found of this locator: " + expectedElementLocator);
        }

    }

    public void sendKeysToElement(By element, String text) {
        WebElement textBox = driver.findElement(element);
        if (textBox.isEnabled()) {
            textBox.sendKeys(text);
        } else {
            System.out.println("TestBox of this locator: " + element.toString() + " is not Found!");
        }

    }

    public boolean waitUntilInvisibilityOf(By by, int time) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(time)).
                    until(ExpectedConditions.invisibilityOfElementLocated(by));
            if (driver.findElement(by).isDisplayed()) {
                new WebDriverWait(driver, Duration.ofSeconds(time)).
                        until(ExpectedConditions.invisibilityOfElementLocated(by));
                return true;
            }
        } catch (Exception e) {
            System.out.println("Invisibility of Element Failed as Element not found: " + by);
        }
        return false;
    }

    public boolean waitUntilVisibilityOf(By by, int time) {
        try {
            new WebDriverWait(driver, Duration.ofSeconds(time)).
                    until(ExpectedConditions.visibilityOfElementLocated(by));
            if (driver.findElement(by).isDisplayed()) {
                new WebDriverWait(driver, Duration.ofSeconds(time)).
                        until(ExpectedConditions.visibilityOfElementLocated(by));
                return true;
            }
        } catch (Exception e) {
            System.out.println("couldn't find visible element of " + by);
        }
        return false;
    }

    public List<WebElement> getAllElementsOf(By element) {
        return driver.findElements(element);
    }

    public void scrollTo(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void hoverOver(By elementToHover) {
        try {
            Actions action = new Actions(driver);
            action.moveToElement(driver.findElement(elementToHover)).perform();
        } catch (Exception e) {
            System.out.println("Failed to Hover over element: " + elementToHover);
        }
    }

    public String getElementAttribute(By elementLocator, String attributeName) {
        return driver.findElement(elementLocator).getAttribute(attributeName);
    }
}

