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

class LoginTest {
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
    @CsvFileSource(resources = "./testLogin/ValidAccount.csv")
    void testLoginSuccess(String email, String password, String expectedResult) {
        driver = furecordsPage.getFurecordsLoginDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_email")));
        usernameField.sendKeys(email);

        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_password")));
        passwordField.sendKeys(password);

        WebElement loginButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.className("login-btn")));
        loginButton.click();

        WebElement headerName = wait.until(
                ExpectedConditions.presenceOfElementLocated(By.className("user-header-name")));

        Assertions.assertEquals(expectedResult, headerName.getText());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "./testLogin/invalidEmailAccount.csv")
    void testLoginInvalidEmail(String email, String password, String expectedResult) {
        driver = furecordsPage.getFurecordsLoginDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_email")));
        usernameField.sendKeys(email);

        WebElement errorMessage = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#register_email_help .ant-form-item-explain-error")));

        Assertions.assertEquals(expectedResult, errorMessage.getText());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "./testLogin/emptyEmail.csv")
    void testLoginEmptyEmail(String password, String expectedResult) {
        driver = furecordsPage.getFurecordsLoginDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement passwordField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_password")));
        passwordField.sendKeys(password);

        WebElement loginButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.className("login-btn")));
        loginButton.click();

        WebElement errorMessage = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#register_email_help .ant-form-item-explain-error")));

        Assertions.assertEquals(expectedResult, errorMessage.getText());
    }

    @ParameterizedTest
    @CsvFileSource(resources = "./testLogin/emptyPassword.csv")
    void testLoginEmptyPassword(String email, String expectedResult) {
        driver = furecordsPage.getFurecordsLoginDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement usernameField = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("register_email")));
        usernameField.sendKeys(email);

        WebElement loginButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.className("login-btn")));
        loginButton.click();

        WebElement errorMessage = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#register_password_help .ant-form-item-explain-error")));

        Assertions.assertEquals(expectedResult, errorMessage.getText());
    }

    @Test
    void testLoginEmptyAll() {
        driver = furecordsPage.getFurecordsLoginDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        WebElement loginButton = wait
                .until(ExpectedConditions.elementToBeClickable(By.className("login-btn")));
        loginButton.click();

        WebElement errorMessageEmail = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#register_email_help .ant-form-item-explain-error")));
        WebElement errorMessagePassword = wait.until(ExpectedConditions
                .presenceOfElementLocated(By.cssSelector("#register_password_help .ant-form-item-explain-error")));
        Assertions.assertTrue(errorMessageEmail.getText().contains("Please input your E-mail!"));
        Assertions.assertTrue(errorMessagePassword.getText().contains("Please input your password!"));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
