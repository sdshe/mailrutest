package ru.reksoft.mailrutests;


import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Collections;
import java.util.List;

public class sWebElement {
    private By by;
    private Page page;

    public sWebElement(String xpath, Page page) {
        by = By.xpath(xpath);
        this.page = page;
    }

    public sWebElement(By by, Page page) {
        this.by = by;
        this.page = page;
    }

    public WebElement find() {

        page.getDriver().findElement(by); //wait
        List<WebElement> elements = findAll();
        for (WebElement element: elements
             ) {
            if(element.isDisplayed())
                return element;
        }
        return null;
    }

    public List<WebElement> findAll() {

        return page.getDriver().findElements(by);
    }

    public boolean displayed() {
        return !findAll().equals(Collections.<WebElement>emptyList());
    }

    public sWebElement click() {
        try {
            find().click();
        } catch (Exception e) {
            (new Actions(page.getDriver())).click(find()).perform();
        }
        return this;
    }

    public sWebElement keys(CharSequence keys) {
        find().sendKeys(keys);
        return this;
    }

    public sWebElement waitFor(long seconds) {
        Wait<WebDriver> wait = new WebDriverWait(this.page.getDriver(), seconds).withMessage("Element " + by.toString()
                + " is not found. Timeout: " + seconds);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
        return this;
    }

    public sWebElement clear() { //doesnt work with serenity @managed
        //WebDriver a = page.getDriver();
        (new Actions(page.getDriver())).keyDown(Keys.CONTROL)
                .sendKeys(Keys.chord("A"))
                .keyUp(Keys.CONTROL)
                .sendKeys(Keys.DELETE).perform();
        return this;
    }
}
