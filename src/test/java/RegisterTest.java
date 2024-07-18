import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

class RegisterTest {
    FurecordsPage furecordsPage;
    ChromeDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        furecordsPage = new FurecordsPage();
    }

    @Test
    void testTitle() {
        driver = furecordsPage.getFurecordsDriver();
        Assertions.assertEquals("FU Records - Online Learning Platform", driver.getTitle());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "./testRegister/emptyFullName.csv")
    void testRegisterEmptyFullName(String email, String password, String confirm, String expectedResult) {
        driver = furecordsPage.getFurecordsRegisterDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_email")));
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_password")));
        passwordField.sendKeys(password);

        WebElement confirmField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_confirm")));
        confirmField.sendKeys(confirm);

        WebElement loginButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.className("register-btn")));
        loginButton.click();

        WebElement errorMessageFullName = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#register_full_name_help .ant-form-item-explain-error")));

        Assertions.assertTrue(errorMessageFullName.getText().contains(expectedResult));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "./testRegister/invalidEmail.csv")
    void testRegisterInvalidEmail(String fullName, String email, String password, String confirm,
            String expectedResult) {
        driver = furecordsPage.getFurecordsRegisterDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement fullNameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_full_name")));
        fullNameField.sendKeys(fullName);

        WebElement emailField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_email")));
        emailField.sendKeys(email);

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_password")));
        passwordField.sendKeys(password);

        WebElement confirmField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_confirm")));
        confirmField.sendKeys(confirm);

        WebElement loginButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.className("register-btn")));
        loginButton.click();

        WebElement errorMessageFullName = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#register_email_help .ant-form-item-explain-error")));

        Assertions.assertTrue(errorMessageFullName.getText().contains(expectedResult));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "./testRegister/passwordInvalid.csv")
    void testRegisterInvalidPassword(String password, String expectedResult) {
        driver = furecordsPage.getFurecordsRegisterDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_password")));
        passwordField.sendKeys(password);

        WebElement errorMessage = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#register_password_help .ant-form-item-explain-error")));

        Assertions.assertTrue(errorMessage.getText().contains(expectedResult));
    }

    @ParameterizedTest
    @CsvFileSource(resources = "./testRegister/passwordNotMatch.csv")
    void testRegisterPasswordNotMatch(String password, String confirm, String expectedResult) {
        driver = furecordsPage.getFurecordsRegisterDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_password")));
        passwordField.sendKeys(password);

        WebElement confirmField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_confirm")));
        confirmField.sendKeys(confirm);

        WebElement errorMessage = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#register_confirm_help .ant-form-item-explain-error")));

        Assertions.assertTrue(errorMessage.getText().contains(expectedResult));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
