package ru.reksoft.mailrutests;

import com.google.gson.Gson;
import net.serenitybdd.junit.runners.SerenityParameterizedRunner;
import net.thucydides.core.annotations.Managed;
import net.thucydides.core.annotations.Steps;

import net.thucydides.junit.annotations.TestData;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;


//@RunWith(SerenityRunner.class)
@RunWith(SerenityParameterizedRunner.class)
public class MailruTest {

    private final UserTestData userTestData;
    private Settings settings;

    @Managed(driver="firefox")
    WebDriver driver;

    @Steps
    private MailruUserSteps mailruUserSteps;

    public MailruTest(UserTestData userTestData) {
        this.userTestData = userTestData;

        Gson gson = new Gson();
        String settingsJson = getFile("settings.json");
        settings = gson.fromJson(settingsJson, Settings.class);
        MailruPage.setSettings(settings);
    }

    @Before
    public void init() {
        System.setProperty("webdriver.gecko.driver", settings.pathToGeckoWebdriver);

        /*driver = new FirefoxDriver();
        driver.navigate().to("https://mail.ru/");*/


    }

    @Test
    public void sendMail() {

        mailruUserSteps.theClientLogsIn(userTestData.user);
        mailruUserSteps.theClientDeletesSentIfExist();
        mailruUserSteps.theClientComposes(userTestData.message);
        mailruUserSteps.theClientClicksSend();
        mailruUserSteps.theMessageShoudBe("Ваше письмо отправлено. Перейти во Входящие");
        mailruUserSteps.checkSentMailInSent(userTestData.message.subject);
    }


    private static String getFile(String fileName){

        String result = "";
        ClassLoader classLoader = MailruTest.class.getClassLoader();
        try {
            result = IOUtils.toString(classLoader.getResourceAsStream(fileName), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;

    }

    @TestData
    public static Collection<Object[]> testData(){
        String dataJson = getFile("data.json");
        Gson gson = new Gson();
        Collection<Object[]> t = new ArrayList<Object[]>();
        UserTestData[] userTestDataSet = gson.fromJson(dataJson, UserTestData[].class);

        for(UserTestData data:userTestDataSet) {
            t.add(new Object[]{ data });
        }
        return t;
    }
}
