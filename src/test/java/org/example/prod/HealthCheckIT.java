package org.example.prod;

import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class HealthCheckIT {

    @Test
    public void healthCheck() throws MalformedURLException {
        DesiredCapabilities cap = DesiredCapabilities.chrome();
        WebDriver driver = new RemoteWebDriver(new URL("http://192.168.0.12:4444/wd/hub"), cap);

        try {
            driver.navigate().to("http://192.168.0.12:9999/tasks/");
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            String version = driver.findElement(By.id("version")).getText();
            Assert.assertTrue(version.isEmpty());
        } finally {
            driver.quit();
        }
    }

}
