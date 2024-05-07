package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import utility.Helper;

public class LoginScreen {
    private final WebDriver webDriver;
    private final Helper helper;
    public LoginScreen(WebDriver webDriver) {
        this.webDriver = webDriver;
        helper = new Helper();
    }

    public void login() {
        webDriver.findElement(By.id("username")).sendKeys(helper.readDataFromFile("username"));
        webDriver.findElement(By.id("password")).sendKeys(helper.readDataFromFile("password"));
        webDriver.findElement(By.id("log-in")).click();
    }
}
