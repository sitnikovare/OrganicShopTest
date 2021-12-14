import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


public class OrganicShopTests {

    WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver_win32/chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--disable-notifications");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        driver.get("https://organic-shops.ru/");
    }

    public void filterPrices(String minPrice, String maxPrice) {
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
    }

    public void goToRegisterPage() {
        driver.findElement(By.xpath("//a[contains(@href,'users')]")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Регистрация')]")).click();
    }

    @Test
    public void pricesTest() {
        String minPrice = "100";
        String maxPrice = "1000";
        filterPrices(minPrice, maxPrice);

        List<WebElement> prices = driver.findElements(By.xpath("//span[@class='price']"));
        ArrayList<Integer> pricesInt = new ArrayList<Integer>();
        for (WebElement pr : prices) {
            pricesInt.add(Integer.parseInt(pr.getText().replace(" ₽", "")));
        }

        Collections.sort(pricesInt);
        boolean minIsTrue = pricesInt.get(0) >= Integer.parseInt(minPrice);
        boolean maxIsTrue = pricesInt.get(pricesInt.size()-1) <= Integer.parseInt(maxPrice);

        Assert.assertTrue(minIsTrue && maxIsTrue);
    }

    @Test
    public void tooltipTest() {
        WebElement element = driver.findElement(By.xpath("//a[@title='Planeta Organica']"));
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
        String toolTipText = element.getAttribute("title");

        Assert.assertEquals("Planeta Organica", toolTipText);
    }

    @Test
    public void signUpTest() {
        goToRegisterPage();

        WebElement element = driver.findElement(By.xpath("//input[@name='name2']"));
        element.sendKeys("Тестимя");

        element = driver.findElement(By.xpath("//input[@name='name1']"));
        element.sendKeys("Тестфамилия");

        element = driver.findElement(By.xpath("//input[@name='name3']"));
        element.sendKeys("Тестотчество");

        element = driver.findElement(By.xpath("//input[@name='nobirthdate']"));
        element.click();

        element = driver.findElement(By.xpath("//input[@name='phone']"));
        element.clear();
        element.sendKeys("9999999999");

        element = driver.findElement(By.xpath("//input[@name='email']"));
        element.sendKeys("somemail@test.com");

        element = driver.findElement(By.xpath("//input[@name='password']"));
        element.sendKeys("somePassovrd1");

        element = driver.findElement(By.xpath("//input[@name='password2']"));
        element.sendKeys("somePassovrd1");

        String prevUrl = driver.getCurrentUrl();

        element = driver.findElement(By.xpath("//input[@name='savebutton']"));
        element.click();

        Assert.assertEquals(prevUrl, driver.getCurrentUrl());
    }

    @After
    public void close() {
        driver.quit();
    }
}
