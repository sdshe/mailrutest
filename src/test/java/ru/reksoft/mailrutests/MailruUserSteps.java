package ru.reksoft.mailrutests;

import net.thucydides.core.annotations.Step;
import org.apache.commons.io.FilenameUtils;
import org.junit.Assert;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

import static java.lang.Thread.sleep;

public class MailruUserSteps {
    MailruPage page;

    @Step
    public void theClientLogsIn(User user) {
        page.login(user);
    }

    @Step
    public void theClientComposes(Message message) {
        page.newMail();

        fillTextAttributes(message.to, message.subject, message.textMessage);

        if (message.attachments != null) {
            for(Attachment attachment: message.attachments) {
                if(attachment.attachmentType == AttachmentType.cloud) {
                    addCloudAttach(attachment.source);
                }
                if(attachment.attachmentType == AttachmentType.fromHardDrive) {
                    addFileAttach(attachment.source);
                }
            }
        }
    }



    @Step
    private void fillTextAttributes(List<String> to, String textSubject, String textMessage) {
        page.fillTextAttributes(to, textSubject, textMessage);
    }

    @Step
    public void theClientDeletesSentIfExist() {
        page.deleteSentIfExist();
    }

    @Step
    public void addCloudAttach(String fileName) {
        page.addCloudAttach(fileName);
    }

    @Step
    public void addFileAttach(String fileName) {
        page.addFileAttach(fileName);
    }

    @Step
    public void theClientClicksSend() {
        page.send();

    }

    @Step
    public void theMessageShoudBe(String Message) {

        String sentStatus = page.getSuccessMessageInnerText();
        Assert.assertEquals(Message, sentStatus);
    }


    @Step
    public void checkSentMailInSent(String subject) {
        Boolean isDisplayed = page.isSentDisplayed(subject);
        Assert.assertTrue(isDisplayed);
    }


}
