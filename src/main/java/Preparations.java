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

        WebElement element = driver.findElement(By.xpath("//*[@id='menu']/a[1]"));
        element.click();
        element = driver.findElement(By.xpath("//*[@id='catalog']/div/div[1]/b[1]/a"));
        element.click();
        element = driver.findElement(By.xpath("//*[@id='allfilters']/div[7]/span"));
        element.click();
        element = driver.findElement(By.xpath("//input[@name='price_min']"));
        element.clear();
        element.sendKeys(minPrice);
        element = driver.findElement(By.xpath("//input[@name='price_max']"));
        element.clear();
        element.sendKeys(maxPrice);

        element = driver.findElement(By.xpath("//*[@id='allfilters']/button[1]"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();

        return driver;
    }

    public WebDriver goToRegisterPage() {
        WebDriver driver = loadOrganicShop();

        WebElement element = driver.findElement(By.xpath("/html/body/header/div[1]/nav/div[2]/a[3]"));
        element.click();
        element = driver.findElement(By.xpath("/html/body/div[1]/div[2]/div[1]/p[2]/a"));
        element.click();

        return driver;
    }
}
