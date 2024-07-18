
import org.openqa.selenium.chrome.ChromeDriver;

public class FurecordsPage {

    public FurecordsPage() {
    }

    public ChromeDriver getFurecordsDriver() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost");
        return driver;
    }

    public ChromeDriver getFurecordsLoginDriver() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost/login");
        return driver;
    }

    public ChromeDriver getFurecordsRegisterDriver() {
        ChromeDriver driver = new ChromeDriver();
        driver.get("http://localhost/register");
        return driver;
    }

}
