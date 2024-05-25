package org.example;

import org.openqa.selenium.By;

public class POM {

    /*
    * POM locators and methods to be implemented here
    *   HERE IS AN EXAMPLE..
    */
    private String url = "https://www.eba.org.eg";
    private final By homePageKeyElement = By.xpath("//a[text()=\"Sitemap\"]");
    private UIActions uiActions;

    public POM(){
        uiActions = new UIActions();
    }

    public void navigateToHomePage(){
        BrowserActions.navigateTo(url,homePageKeyElement);
    }
    public void clickOnGetPaid(){
        uiActions.clickOn(homePageKeyElement,POM2.documentsPageKeyElement);
    }
}
