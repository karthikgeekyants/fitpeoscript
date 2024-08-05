import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.time.Instant;
import java.util.NoSuchElementException;

public class Test {
    public static WebDriver driver;

    @org.testng.annotations.Test
    public static void launchapplication() throws InterruptedException {

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.get("https://fitpeo.com/");

        Thread.sleep(3000);
        driver.findElement(By.xpath("(//div[contains(text(),'Revenue Calculator')])[1]")).click();
        System.out.println("Clicked on Revenue calculator");

        Thread.sleep(3000);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("window.scrollBy(0,300);");
        System.out.println("Scroll down");
        Thread.sleep(3000);
        WebElement elem = driver.findElement(By.xpath("//h4[normalize-space()='Medicare Eligible Patients']"));
        System.out.println("Scroll down to the calculator slider");


        WebElement sliderThumb = driver.findElement(By.xpath("//span[@class='MuiSlider-thumb MuiSlider-thumbSizeMedium MuiSlider-thumbColorPrimary MuiSlider-thumb MuiSlider-thumbSizeMedium MuiSlider-thumbColorPrimary css-sy3s50']"));
        Actions move = new Actions(driver);
        Action action = (Action) move.dragAndDropBy(sliderThumb, 93, 0).build();
        action.perform();

        Thread.sleep(3000);
        WebElement inputelement = driver.findElement(By.xpath("//input[@type='number']"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].value = '';", inputelement);

        try {
            inputelement.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", inputelement);
        }

        String value = "560";
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1];" +
                        "arguments[0].dispatchEvent(new Event('input', { bubbles: true }));" +
                        "arguments[0].dispatchEvent(new Event('change', { bubbles: true }));",
                inputelement, value
        );
        String currentValue = inputelement.getAttribute("value");
        System.out.println("Current Value: " + currentValue);


        js.executeScript("window.scrollBy(0,300);");
        System.out.println("Scroll down");
        Thread.sleep(3000);
        WebElement elemnew = driver.findElement(By.xpath("//p[normalize-space()='CPT-99091']"));

        driver.findElement(By.xpath("(//input[@type='checkbox'])[1]")).click();
        driver.findElement(By.xpath("(//input[@type='checkbox'])[2]")).click();
        driver.findElement(By.xpath("(//input[@type='checkbox'])[3]")).click();
        driver.findElement(By.xpath("(//input[@type='checkbox'])[8]")).click();

        String text = driver.findElement(By.xpath("//p[@class='MuiTypography-root MuiTypography-body1 inter css-hocx5c'][normalize-space()='$110700']")).getText();
        System.out.println(text);

        Assert.assertEquals("$110700", text);

    }
}
