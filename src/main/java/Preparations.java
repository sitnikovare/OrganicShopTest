import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class Preparations {

    public WebDriver loadOrganicShop() {
        System.setProperty("webdriver.chrome.driver", "chromedriver_win32/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        driver.get("https://organic-shops.ru/");

        return driver;
    }

    public WebDriver filterPrices(String minPrice, String maxPrice) {
        WebDriver driver = loadOrganicShop();

        WebElement element = driver.findElement(By.xpath("//a[contains(text(),'Каталог')]"));
        element.click();
        driver.findElement(By.xpath("//a[contains(text(),'Все продукты')]")).click();
        driver.findElement(By.xpath("//span[@href='#filt_price']")).click();
        element = driver.findElement(By.xpath("//input[@name='price_min']"));
        element.clear();
        element.sendKeys(minPrice);
        element = driver.findElement(By.xpath("//input[@name='price_max']"));
        element.clear();
        element.sendKeys(maxPrice);

        element = driver.findElement(By.xpath("//button[contains(text(),'Применить')]"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();

        return driver;
    }

    public WebDriver goToRegisterPage() {
        WebDriver driver = loadOrganicShop();

        driver.findElement(By.xpath("//i[@class='fas fa-fw fa-sign-in-alt']")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Регистрация')]")).click();

        return driver;
    }
}
