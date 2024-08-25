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
import java.util.List;

import static org.testng.AssertJUnit.assertEquals;

public class AddRecordTest {
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
        driver.get("https://demoqa.com/webtables");
    }

    public void addRecord(String firstName, String lastName, int age, String email, int salary, String department) {
        WebElement addButton = driver.findElement(By.xpath("//button[@id='addNewRecordButton']"));
        addButton.click();

        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='firstName']")));

        firstNameInput.sendKeys(firstName);
        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='lastName']")));
        lastNameInput.sendKeys(lastName);
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='userEmail']")));
        emailInput.sendKeys(email);
        WebElement ageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='age']")));
        ageInput.sendKeys(String.valueOf(age));
        WebElement salaryInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='salary']")));
        salaryInput.sendKeys(String.valueOf(salary));
        WebElement departmentInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='department']")));
        departmentInput.sendKeys(department);
        WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='submit']")));
        submitButton.click();

    }
    public int getnumberofrecords(){
        List<WebElement> recordslist = driver.findElements(By.xpath("//*[contains(@id,'edit-record')]"));
        return recordslist.size();
    }
    public void updateLastAddedRecord(String firstName, String lastName, int age, String email, int salary, String department) {
        int recordToUpdate = getnumberofrecords();
        WebElement editButton = driver.findElement(By.xpath("//*[@id='edit-record-" + recordToUpdate + "']"));
        editButton.click();
        WebElement firstNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='firstName']")));
        firstNameInput.clear();
        firstNameInput.sendKeys(firstName);
        WebElement lastNameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='lastName']")));
        lastNameInput.clear();
        lastNameInput.sendKeys(lastName);
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='userEmail']")));
        emailInput.clear();
        emailInput.sendKeys(email);
        WebElement ageInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='age']")));
        ageInput.clear();
        ageInput.sendKeys(String.valueOf(age));
        WebElement salaryInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='salary']")));
        salaryInput.clear();
        salaryInput.sendKeys(String.valueOf(salary));
        WebElement departmentInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='department']")));
        departmentInput.clear();
        departmentInput.sendKeys(department);
        WebElement submitButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='submit']")));
        submitButton.click();
    }

    @Test
    public void addWebTable() {
        int numberofrecords = getnumberofrecords();

        addRecord("John", "Doe", 30, "johndoe@qa.com", 100000, "QA");
        assertEquals(getnumberofrecords(), numberofrecords + 1);
        int numberofRecordsAfterUpdate = getnumberofrecords();
        updateLastAddedRecord("Jane", "Doe", 30, "johndoe@qa.com", 100000, "QA");
        WebElement updatedRecordName = driver.findElement(By.xpath("//div[position()=" + numberofRecordsAfterUpdate + "]/div/div[1]"));
        assertEquals(updatedRecordName.getText(), "Jane");

    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
