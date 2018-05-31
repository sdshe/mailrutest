package ru.reksoft.mailrutests;

import net.serenitybdd.core.pages.PageObject;
import net.thucydides.core.steps.ScenarioSteps;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Page extends PageObject {
    protected sWebElement locateOnThePage(String xpath) {
        return new sWebElement(xpath, this);
    }
    protected sWebElement locateOnThePage(By by) {
        return new sWebElement(by, this);
    }
    protected static Settings pageSettings;
    public static void setSettings(Settings settings) {
        pageSettings = settings;
    }


}
