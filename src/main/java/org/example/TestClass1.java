package org.example;

import org.testng.annotations.*;

public class TestClass1 {
    @BeforeMethod
    public void setup() {
        BrowserActions.setWebDriver(BrowserActions.BROWSERS.FIREFOX);
    }

    @Test
    public void test1() {
        POM page1 = new POM();
        page1.navigateToHomePage();
        page1.clickOnGetPaid();
    }

    @Test
    public void test2() {
        POM page2 = new POM();
        page2.navigateToHomePage();
        page2.clickOnGetPaid();
    }

    @AfterMethod
    public void endTests() {
        BrowserActions.closeBrowserSession();
    }


}
