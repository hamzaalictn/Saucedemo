package org.inar.saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.List;
import java.util.concurrent.TimeUnit;


public class InventoryPageTest {
    WebDriver driver;
    @BeforeSuite
    public void setUpSuite() {
        // code that is executed before the entire test suite
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
    }

    @BeforeTest
    public void setUpTest() {
        // code that is executed before each test in the suite
        driver.get("https://www.saucedemo.com/");
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();
    }


    @Test
    public void allItemsAddToCartBoxClickable() {
        List<WebElement> itemsNameElement = driver.findElements(By.xpath("//div[@class='inventory_item_name']"));
        itemsNameElement.forEach(webElement -> webElement.findElement(By.xpath("//*[@class=\"btn btn_primary btn_small btn_inventory\"]")).click());
    }

    @Test
    public void allItemsSelectedToChart() {

        Assert.assertEquals(Integer.valueOf(driver.findElement(By.xpath("//*[@id=\"shopping_cart_container\"]/a/span")).getText()), 6);
    }
    @AfterSuite
    public void afterTest() {
        // Perform cleanup tasks or generate test reports here
        driver.quit();
    }
}
