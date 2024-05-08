package pageObject;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;
import utility.Helper;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class DefaultDriver {
    private WebDriver webDriver;
    public WebDriver launchURL(Optional<String> browserMode) {
        if (browserMode.isPresent()) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--headless");
            chromeOptions.addArguments("--disable-gpu");
            webDriver = new ChromeDriver(chromeOptions);
        }else {
            System.setProperty("webdriver.safari.driver", "/usr/bin/safaridriver");
            webDriver = new SafariDriver();
        }
        webDriver.manage().window().maximize();
        webDriver.get(new Helper().readDataFromFile("url"));
        return webDriver;
    }

    public String getScreenShot(String screenshotName) throws IOException {
        String dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        TakesScreenshot screenshot = ((TakesScreenshot) webDriver);
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        String destination = "Screenshots/" + screenshotName + dateFormat + ".png";
        File finalDestination = new File(destination);
        FileUtils.copyFile(source, finalDestination, true);
        return destination;
    }

    public void tearDown() {
        webDriver.quit();
    }
}
