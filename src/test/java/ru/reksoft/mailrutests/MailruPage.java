package ru.reksoft.mailrutests;

import net.thucydides.core.annotations.DefaultUrl;
import org.apache.commons.io.FilenameUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.util.List;

import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;

public class MailruPage extends Page {
    private sWebElement login = locateOnThePage("//input[@id='mailbox:login']");
    private sWebElement password = locateOnThePage("//input[@id='mailbox:password']");
    private sWebElement submit = locateOnThePage("//label[@id='mailbox:submit']");
    private sWebElement newMail = locateOnThePage("//a[contains(@title, 'аписать письмо')]");
    private sWebElement contact = locateOnThePage("//textarea[contains(@data-original-name, 'To')]");
    private sWebElement subject = locateOnThePage("//input[@name='Subject']");
    private sWebElement messageFrame = locateOnThePage("//iframe[contains(@id, 'ditor')]");
    private sWebElement body = locateOnThePage("//body");
    private sWebElement noSent = locateOnThePage("//*[text()='У вас нет отправленных писем.']");
    private sWebElement selectAll = locateOnThePage(".//div[@*='Выделить']//div[@class='b-checkbox__box']");
    private sWebElement deleteSent = locateOnThePage("//*[text()='Удалить']");
    private sWebElement sent = locateOnThePage("//*[text()='Отправленные']");
    private sWebElement fromCloud = locateOnThePage("//*[text()='из Облака']");
    private sWebElement fileFromCloud;
    private sWebElement attach = locateOnThePage("//div[@class='b-layer']//*[text()='Прикрепить']");
    private sWebElement attachFile = locateOnThePage("//*[text()='Прикрепить файл']");
    private sWebElement fileIcon;
    private sWebElement send = locateOnThePage("//div[contains(@title, 'Отправить')]");
    private sWebElement messageIsSent = locateOnThePage("//div[@class='message-sent__title']");
    private sWebElement sentLetter;

    public void login(User user) {
        getDriver().navigate().to("https://mail.ru/");
        login.keys(user.name);
        password.keys(user.password);
        submit.click();
        newMail.waitFor(pageSettings.jumpTimeout);
    }

    public void newMail() {
        newMail.click();
        contact.waitFor(pageSettings.jumpTimeout);
    }

    public void fillTextAttributes(List<String> to, String textSubject, String textMessage) {
        for (String address: to) {
            contact.keys(address)
                    .keys(Keys.TAB);
        }
        subject.keys(textSubject);
        fillTestMessage(textMessage);
    }

    public void fillTestMessage(String message) {
        getDriver().switchTo().frame(messageFrame.find());
        body.keys(message);
        getDriver().switchTo().defaultContent();
    }

    public void addCloudAttach(String fileName) {
        fromCloud.click();
        attach.waitFor(pageSettings.jumpTimeout);

        //page.fileFromCloud = page.locateOnThePage("//*[text()='" + fileName + "']");
        fileFromCloud = locateOnThePage("//div[contains(@data-id, '" + fileName + "')]");
        fileFromCloud.waitFor(pageSettings.jumpTimeout)
                .click();
        attach.click();
        checkFileUpload(fileName);
    }

    public void checkFileUpload(String fileName) {
        fileIcon = locateOnThePage("//div[@title='" + fileName + "']");
        fileIcon.waitFor(pageSettings.uploadTimeout);
    }

    public void addFileAttach(String fileName) {
        attachFile.click();

        StringSelection ss = new StringSelection(fileName);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
        try {
            Robot robot = new Robot();
            sleep(pageSettings.uploadWinTimeout);
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_CONTROL);
            sleep(pageSettings.uploadWinTimeout);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
        String shortName = FilenameUtils.getName(fileName);
        checkFileUpload(shortName);
    }

    public void checkSentLetter(String subject){

        sentLetter = locateOnThePage("//*[contains(text(), '" + subject + "')]");

    }

    public void deleteSentIfExist() {
        sent.click();
        if (!noSent.displayed()) {
            selectAll.click();
            deleteSent.click();
            noSent.waitFor(pageSettings.jumpTimeout);
        }
    }

    public String getSuccessMessageInnerText() {
        messageIsSent.waitFor(pageSettings.jumpTimeout);
        return messageIsSent.find().getAttribute("innerText");
    }

    public Boolean isSentDisplayed(String subject) {
        sent.click();
        checkSentLetter(subject);
        WebElement sentLetterw = this.sentLetter.find();
        return sentLetterw.isDisplayed();
    }

    public void send() {
        send.click();

    }

}
