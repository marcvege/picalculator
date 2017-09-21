package com.capside.education;

import java.math.BigDecimal;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Javi
 */

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
@Slf4j
public class PiCtrlBrowserIT {
    private static final BigDecimal PI_APROX = new BigDecimal(
        "3.14159265358979323846264338327950288419716939937" + 
        "5105820974944592307816406286208998628034825342117" + 
        "06798214808651328230664709384");
    
    private static final String PATH = "http://localhost:%d";
    
    @LocalServerPort
    private int serverPort;
    
    private static WebDriver driver = null;

    @BeforeClass
    public static void inicializarDriver() {
        // See: https://sites.google.com/a/chromium.org/chromedriver/getting-started
        System.setProperty("webdriver.chrome.driver", "C:\\tools\\seleniumchromedriver\\chromedriver.exe");

        driver = new ChromeDriver();
    }

    @AfterClass
    public static void liquidarDriver() {
        driver.quit();
    }

    @Test
    public void checkWorkflowIsCorrectForCorrectArguments() {
        String url = String.format(PATH, serverPort);
        log.info("Checking {}.", serverPort);
        driver.get(url);

        WebElement inputElem = driver.findElement(By.name("iterations"));
        inputElem.clear();
        inputElem.sendKeys("50");
        WebElement cmdAceptar = driver.findElement(By.tagName("button"));
        cmdAceptar.click();

        log.info("Loading {}.", driver.getCurrentUrl());
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("result")));
        WebElement resultElem = driver.findElement(By.id("result"));

        BigDecimal piValue = new BigDecimal(resultElem.getText());
        Assert.assertTrue("Calculated PI value is approximately correct.",
                piValue.subtract(PI_APROX).abs().doubleValue() < 1E-20);
    }
}
