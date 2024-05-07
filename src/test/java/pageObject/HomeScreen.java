package pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

public class HomeScreen {
    private final WebDriver webDriver;
    private WebElement homeScreenElement;

    public HomeScreen(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    public void verifyHomeScreen() {
        try{
            Thread.sleep(3000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }

        homeScreenElement = webDriver.findElement(By.partialLinkText("Add Account"));
        Assert.assertTrue(homeScreenElement.isDisplayed());
    }

    public void searchBranch() {
        homeScreenElement = webDriver.findElement(By.xpath("//input[@placeholder='Start typing to search...']"));
        homeScreenElement.click();
        homeScreenElement.sendKeys("Hello");
    }
}
