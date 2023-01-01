package org.inar.saucedemo;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;


public class LoginPageTest {

    WebDriver driver;
    WebElement userNameBox;
    WebElement passwordBox;
    WebElement loginButton;

    WebElement actualLockedUserAlert;


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
        userNameBox = driver.findElement(By.id("user-name"));
        passwordBox = driver.findElement(By.id("user-name"));
        loginButton = driver.findElement(By.id("login-button"));

    }


    @Test(priority = 1)
    public void loginWithValidCredentials() {
        passwordBox.sendKeys("standard_user");
        userNameBox.sendKeys("secret_sauce");
        loginButton.click();

        Assert.assertEquals(driver.getTitle(), "Swag Labs");

    }

    @Test(priority = 2)
    public void loginWithBlockedAccount() {
        passwordBox.sendKeys("locked_out_user");
        userNameBox.sendKeys("secret_sauce");
        loginButton.click();

        actualLockedUserAlert = driver.findElement(By.xpath("//*[@id=\"login_button_container\"]/div/form/div[3]/h3"));
        Assert.assertEquals(actualLockedUserAlert.getText(), "Epic sadface: Password is required");

    }


    @AfterSuite
    public void afterTest() {
        // Perform cleanup tasks or generate test reports here
        driver.quit();
    }
}
