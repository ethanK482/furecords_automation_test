import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

class TestAuthor {
    FurecordsPage furecordsPage;
    ChromeDriver driver;

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        furecordsPage = new FurecordsPage();
    }

    @Test
    void testAuthor() {
        driver = furecordsPage.getFurecordsDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement flashcardBtn = wait
                .until(ExpectedConditions.elementToBeClickable(By.className("flashcard-btn")));
        flashcardBtn.click();
        List<WebElement> flashcardItems = wait.until(ExpectedConditions
                .presenceOfAllElementsLocatedBy(By.className("flashcard-item")));
        WebElement firstFlashcardItem = flashcardItems.get(0);
        wait.until(ExpectedConditions.elementToBeClickable(firstFlashcardItem));
        firstFlashcardItem.click();
        Assertions.assertEquals("http://localhost/login", driver.getCurrentUrl());
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
