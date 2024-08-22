import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class ClickButtonTest {
        private WebDriver driver;
    private WebDriverWait wait;

    @BeforeClass
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "src/main/drivers/chromedriver");

        ChromeOptions options = new ChromeOptions();
        //options.addArguments("--headless");
        options.addArguments("--window-size=1920,1080");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-gpu");
        options.addArguments("--ignore-certificate-errors");
        // options.addArguments("--incognito");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--start-maximized");

        driver = new ChromeDriver(options);

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));

        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.get("https://demoqa.com/elements");
    }
     @Test(priority = 1)
    public void testPageTitleShown(){
        String pageTitle = driver.getTitle();

        Assert.assertEquals(pageTitle, ("DEMOQA"));

    }
    @Test(priority = 0)
    public void testButtonClick(){

        WebElement buttonsClick = driver.findElement(By.cssSelector("div.show li#item-4"));
        buttonsClick.click();

        WebElement ClickMe = driver.findElement(By.xpath("//div[contains(@class, 'mt-4')]/button[contains(@class, 'btn-primary') and not(@id='rightClickBtn')]"));
        ClickMe.click();
        WebElement resultOfClick = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//p[@id='dynamicClickMessage']")));

        Assert.assertEquals(resultOfClick.getText(), "You have done a dynamic click");
    }
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

}
