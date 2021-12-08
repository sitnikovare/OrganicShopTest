import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class OrganicShopTests {

    @org.junit.Test
    public void pricesTest() {
        Preparations preparation = new Preparations();
        String minPrice = "100";
        String maxPrice = "1000";
        WebDriver driver = preparation.filterPrices(minPrice, maxPrice);

        List<WebElement> prices = driver.findElements(By.xpath("//span[@class='price']"));
        ArrayList<Integer> pricesInt = new ArrayList<Integer>();
        for (WebElement pr : prices) {
            pricesInt.add(Integer.parseInt(pr.getText().replace(" ₽", "")));
        }

        Collections.sort(pricesInt);
        boolean minIsTrue = pricesInt.get(0) >= Integer.parseInt(minPrice);
        boolean maxIsTrue = pricesInt.get(pricesInt.size()-1) <= Integer.parseInt(maxPrice);

        Assert.assertTrue(minIsTrue && maxIsTrue);
        driver.close();
    }

    @org.junit.Test
    public void tooltipTest() {
        Preparations preparation = new Preparations();
        WebDriver driver = preparation.loadOrganicShop();

        WebElement element = driver.findElement(By.xpath("//a[@title='Planeta Organica']"));
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
        String toolTipText = element.getAttribute("title");

        Assert.assertEquals("Planeta Organica", toolTipText);
        driver.close();
    }

    @org.junit.Test
    public void signUpTest() {
        Preparations preparation = new Preparations();
        WebDriver driver = preparation.goToRegisterPage();

        WebElement element = driver.findElement(By.xpath("//input[@name='savebutton']"));
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();

        String allertText = driver.switchTo().alert().getText();
        System.out.println(allertText);

        Assert.assertEquals("Пожалуйста, укажите корректно (только буквы - кириллица) Ваше имя", allertText);
        //driver.close();
    }
}
