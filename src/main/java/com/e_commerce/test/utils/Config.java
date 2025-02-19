package com.e_commerce.test.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hospital.test.pageObjects.frontend.ABCD_LandingPage;

public class Config {
    public static  WebDriver driver;
    public ABCD_LandingPage ABCD_LandingPage;

    public static String getConfigProperty(String key) throws IOException {
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\resources\\environment\\properties\\globalData.properties");
        prop.load(fis);
        return prop.getProperty(key);
    }

    public static WebDriver initializeDriver() throws IOException {
        String browserName = getConfigProperty("browser");

        if (browserName.equalsIgnoreCase("chrome")) {
            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
            driver = new ChromeDriver();
        } else if (browserName.equalsIgnoreCase("edge")) {
            System.setProperty("webdriver.edge.driver", "C:\\msedgedriver.exe");
            driver = new EdgeDriver();
        } else if (browserName.equalsIgnoreCase("firefox")) {
            System.setProperty("webdriver.gecko.driver", "C:\\");
            driver = new FirefoxDriver();
        }

        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        return driver;
    }

//    @BeforeMethod(alwaysRun = true)
    public static WebDriver openURL() throws IOException {
        driver = initializeDriver();
        driver.get(getConfigProperty("URL"));
        return driver;
    }


    public List<HashMap<String, String>> getJsonData(String Location) throws IOException {
        String JsonContent = FileUtils.readFileToString(new File(Location), "UTF-8");
        ObjectMapper mapper = new ObjectMapper();
        List<HashMap<String, String>> data = mapper.readValue(JsonContent, new TypeReference<List<HashMap<String, String>>>() {
        });
        return data;
    }

    public String takeScreenshot(String TestCaseName, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File src = ts.getScreenshotAs(OutputType.FILE);
        File location = new File(System.getProperty("user.dir") + "\\src\\test\\java\\HospitalProject\\testScreenshots\\failedTests\\" + TestCaseName + ".png");
        //src\test\java\HospitalProject\testScreenshots\failedTests
        FileUtils.copyFile(src, location);
        return (System.getProperty("user.dir") + "\\src\\test\\java\\HospitalProject\\testScreenshots\\failedTests\\" + TestCaseName + ".png");
    }


}
